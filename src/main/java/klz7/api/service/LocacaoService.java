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
import klz7.api.exception.FilmeNaoEncontradoException;
import klz7.api.exception.LocacaoNaoEncontradaException;
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
		return locacaoRepository.findByClienteId(idCliente);
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
		return locacaoRepository.findByFilmesIdFilme(idFilme);
		
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
	
}
