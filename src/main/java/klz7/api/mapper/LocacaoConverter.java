package klz7.api.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import klz7.api.dto.LocacaoDTO;
import klz7.api.exception.ClienteNaoEncontradoException;
import klz7.api.exception.FilmeNaoEncontradoException;
import klz7.api.model.Cliente;
import klz7.api.model.Filmes;
import klz7.api.model.Locacao;
import klz7.api.repository.ClienteRepository;
import klz7.api.repository.FilmesRepository;

@Component
public class LocacaoConverter {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FilmesRepository filmesRepository;
	
	public Locacao dtoParaEntidade(LocacaoDTO dto) {
		Locacao locacao = new Locacao();
		locacao.setIdLocacao(dto.getIdLocacao());
		
		Cliente cliente = clienteRepository.findById(dto.getId())
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));
		locacao.setCliente(cliente);
		
		Filmes filmes = filmesRepository.findById(dto.getIdFilmes())
				.orElseThrow(() -> new FilmeNaoEncontradoException("Filme não encontrado."));
		locacao.setFilmes(filmes);
		
		locacao.setDataLocacao(dto.getDataLocacao());
		locacao.setDataDevolucao(dto.getDataDevolucao());
		locacao.setDevolvido(dto.isDevolvido());
		locacao.setQuantidade(dto.getQuantidade());
		return locacao;
	}
	
	public LocacaoDTO entidadeParaDto(Locacao entidade) {
		return new LocacaoDTO(
				entidade.getIdLocacao(),
				entidade.getCliente().getId(), //Pega id do cliente
				entidade.getFilmes().getIdFilme(), //Pega id do filme
				entidade.getDataLocacao(),
				entidade.getDataDevolucao(),
				entidade.isDevolvido(),
				entidade.getQuantidade());
	}

}
