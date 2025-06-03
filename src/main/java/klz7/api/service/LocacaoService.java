package klz7.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;
import klz7.api.dto.LocacaoDTO;
import klz7.api.exception.ClienteNaoEncontradoException;
import klz7.api.exception.DataDevolucaoInvalidaException;
import klz7.api.exception.EstoqueVazioException;
import klz7.api.exception.FilmeNaoEncontradoException;
import klz7.api.exception.LocacaoNaoEncontradaException;
import klz7.api.exception.QuantidadeNegativaNulaException;
import klz7.api.model.Cliente;
import klz7.api.model.Filmes;
import klz7.api.model.Locacao;
import klz7.api.repository.ClienteRepository;
import klz7.api.repository.FilmesRepository;
import klz7.api.repository.LocacaoRepository;
import klz7.api.validator.LocacaoValidator;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LocacaoService {
	
	private final LocacaoRepository locacaoRepository;
	private final ClienteRepository clienteRepository;
	private final FilmesRepository filmesRepository;
	private final LocacaoValidator locacaoValidator;
	
	public LocacaoService(LocacaoRepository locacaoRepository, ClienteRepository clienteRepository, FilmesRepository filmesRepository, LocacaoValidator locacaoValidator) {
		this.locacaoRepository = locacaoRepository;
		this.clienteRepository = clienteRepository;
		this.filmesRepository = filmesRepository;
		this.locacaoValidator = locacaoValidator;
	}

	public Locacao salvar(LocacaoDTO dto) {
		Cliente cliente = clienteRepository.findById(dto.getIdCliente())
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));
		
		Filmes filmes = filmesRepository.findById(dto.getIdFilmes())
				.orElseThrow(() -> new FilmeNaoEncontradoException("Filme não encontrado"));
		
		Locacao locacao = Locacao.builder()
				.cliente(cliente)
				.filmes(filmes)
				.dataLocacao(dto.getDataLocacao())
				.dataDevolucao(dto.getDataDevolucao())
				.devolvido(dto.isDevolvido())
				.quantidade(dto.getQuantidade())
				.build();
		
		locacaoValidator.validarTudo(locacao);
		
		log.info("Locacao do filme {} de ID {} feita pelo cliente {} de ID {}", filmes.getNome(), filmes.getIdFilme(), cliente.getNome(), cliente.getIdCliente());
		return locacaoRepository.save(locacao);
	}
	
	public List<Locacao> buscarLocacoesPorCliente(Long idCliente) {
		List<Locacao> listaLocacao = locacaoRepository.findByClienteId(idCliente);
		log.info("Buscar locações por ID do Cliente: '{}'. Lista: {}", idCliente, listaLocacao);
		return listaLocacao;
	}
	
	private Locacao atualizarCampo(Long idLocacao, Consumer<Locacao> atualizador) {
		Locacao locacaoAtualizada = locacaoRepository.findById(idLocacao)
				.orElseThrow(() -> new LocacaoNaoEncontradaException("Locaçao não encontrada."));
		atualizador.accept(locacaoAtualizada);
		return locacaoRepository.save(locacaoAtualizada);
	}
	
	public Locacao renovarLocacao(Long idLocacao, LocalDate devolucaoExtendida) {
		if(devolucaoExtendida == null) {
			throw new DataDevolucaoInvalidaException("A data de devolução extendida não pode ser nula.");
		}
		
		locacaoValidator.validarDataDevolucao(devolucaoExtendida);
		
		Locacao locacaoAtualizada = atualizarCampo(idLocacao, locacao -> {
			locacao.setDataDevolucao(devolucaoExtendida);
			
		});
		
		log.info("Data de devolução da locação de ID {} foi extendida para: {}", idLocacao, locacaoAtualizada.getDataDevolucao());
		return locacaoAtualizada;
	}
	
	public List<Locacao> buscarHistoricoFilme(Long idFilme){
		List<Locacao> historicoFilme = locacaoRepository.findByFilmesIdFilme(idFilme);
		log.info("Buscar histórico do filme pelo ID: '{}'. Histórico: {}", idFilme, historicoFilme);
		return historicoFilme;
		
	}
	
	public BigDecimal calcularMulta(Long idLocacao) {
		Locacao locacao = locacaoRepository.findById(idLocacao)
				.orElseThrow(() -> new LocacaoNaoEncontradaException("Locação não encontrada."));
		
		if (locacao.isDevolvido()) {
			return BigDecimal.ZERO;
		}
		
		LocalDate hoje = LocalDate.now();
		LocalDate dataDevolucaoPrevista = locacao.getDataDevolucao();
		
		if(!dataDevolucaoPrevista.isBefore(hoje)) {//EX: hoje é DOMINGO, a DEVOLUÇAO NAO É ANTES DE DOMINGO, É DOMINGO OU DEPOIS, PODENDO SER SEGUNDA, ETC.
			//Então ,multa ZERO, ainda está no tempo.
			
			return BigDecimal.ZERO;
		}
		
		long diasDeAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, hoje);
		BigDecimal multaPorDia = new BigDecimal("7.00");
		BigDecimal valorMulta = multaPorDia.multiply(BigDecimal.valueOf(diasDeAtraso));
		
		log.info("Multa calculada para locação ID {}: R${}", locacao.getIdLocacao(), valorMulta);
		return valorMulta;
	}
	
	public Locacao alugarFilme(Long idCliente, Long idFilmes, int quantidade, LocalDate dataDevolucao) {
		Cliente alugador = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));
		
		Filmes alugado = filmesRepository.findById(idFilmes)
				.orElseThrow(() -> new FilmeNaoEncontradoException("Filme não encontrado."));
		
		if (quantidade <= 0) {
			throw new QuantidadeNegativaNulaException("Quantidade selecionada inválida.");
		}
		
		if (alugado.getEstoque() < quantidade) {
			throw new EstoqueVazioException("Sem unidades suficientes disponíveis do filme selecionado.");
		}
		
		alugado.setEstoque(alugado.getEstoque() - quantidade);
		filmesRepository.save(alugado);
		
		Locacao locacao = new Locacao();
		locacao.setCliente(alugador);
		locacao.setFilmes(alugado);
		locacao.setQuantidade(quantidade);
		locacao.setDataLocacao(LocalDate.now());
		locacao.setDataDevolucao(dataDevolucao);
		
		log.info("Locação executada com sucesso: {} (ID {}) alugado por {} (ID {}), quantidade: {}.",
				alugado.getNome(), alugado.getIdFilme(), alugador.getNome(), alugador.getIdCliente(), quantidade);
		return locacaoRepository.save(locacao);	
	}
	
}
