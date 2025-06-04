package klz7.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;


import org.springframework.util.StringUtils;

import klz7.api.exception.FilmeNaoEncontradoException;
import klz7.api.exception.FilmeNuloVazioException;
import klz7.api.model.Filmes;
import klz7.api.repository.FilmesRepository;
import klz7.api.validator.FilmesValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FilmesService {
	
	private final FilmesRepository filmesRepository;
	private final FilmesValidator filmesValidator;
	
	public FilmesService(FilmesRepository filmesRepository, FilmesValidator filmesValidator) {
		this.filmesRepository = filmesRepository;
		this.filmesValidator = filmesValidator;
	}
	
	public Filmes salvar(Filmes filmes) {
		if(!StringUtils.hasText(filmes.getNome())) {//Se não tiver texto.. É melhor que IsEmpty.
			throw new FilmeNuloVazioException("O nome do filme digitado é nulo ou vazio. Digite um nome válido.");
		}
		
		String nomeAjustado = filmes.getNome().trim();//trim evita problema com espaços em branco.
		filmes.setNome(nomeAjustado);
		
		filmesValidator.validarTudo(filmes);
		
		log.info("Filme salvo com nome: {}", nomeAjustado);
		return filmesRepository.save(filmes);
	}
	
	public Optional<Filmes> buscarPorId(Long idFilme) {
		Optional<Filmes> filme = filmesRepository.findById(idFilme);
		log.info("Buscar por ID: '{}'. Filme encontrado: {}", idFilme, filme);
		return filme;
	}
	
	public List<Filmes> buscarPorNome(String nome) {
		List<Filmes> filmes  = filmesRepository.findByNomeContainingIgnoreCase(nome);
		log.info("Buscar por nome: '{}'. Filme(s) encontrado(s): {}", nome, filmes);
		return filmes;
	}
	
	public List<Filmes> buscarPorDataLancamento(LocalDate dataLancamento) {
		List<Filmes> filmesData = filmesRepository.findByDataLancamento(dataLancamento);
		log.info("Buscar por data de lançamento: '{}'. Filme(s) encontrado(s): {}", dataLancamento, filmesData);
		return filmesData;
	}
	
	public List<Filmes> buscarPorDiretor(String diretor) {
		List<Filmes> filmesDiretor = filmesRepository.findByDiretorContainingIgnoreCase(diretor);
		log.info("Buscar por diretor: '{}'. Filme(s) encontrado(s): {}", diretor, filmesDiretor);
		return filmesDiretor;
	}
	
	public List<Filmes> buscarPorGenero(String genero) {
		List<Filmes> filmesGenero = filmesRepository.findByGeneroContainingIgnoreCase(genero);
		log.info("Buscar por gênero: '{}'. Filme(s) encontrado(s): {}", genero, filmesGenero);
		return filmesGenero;
	}
	
	public List<Filmes> buscarPorEstoque(int estoque) {
		//Achei válido por facilitar recarga caso necessária de estoques zerados ou próximos de zerar.
		List<Filmes> filmesEstoque = filmesRepository.findByEstoque(estoque);
		log.info("Buscar por estoque: '{}'. Filme(s) encontrado(s): {}", estoque, filmesEstoque);
		return filmesEstoque;
	}
	
	private Filmes atualizarCampo(Long idFilme, Consumer<Filmes> atualizador) {
		Filmes filmesAtualizado = filmesRepository.findById(idFilme)
				.orElseThrow(() -> new FilmeNaoEncontradoException("Filme não encontrado."));
		atualizador.accept(filmesAtualizado);
		return filmesRepository.save(filmesAtualizado);
	}
	
	public Filmes alterarEstoque(Long idFilme, int estoqueNovo) {
		Filmes filmesAtualizado = atualizarCampo(idFilme, filmes -> {
			filmes.setEstoque(estoqueNovo);
			filmesValidator.validarEstoque(estoqueNovo);
		});
		
		log.info("Estoque do filme com o ID {} foi atualizado para: {}", idFilme, filmesAtualizado.getEstoque());
		return filmesAtualizado;
	}
	
	public Filmes alterarDataLancamento(Long idFilme, LocalDate dataNova) { //Caso tenha sido cadastrada na data errada, porém, não pode ser em data futura ao dia do cadastro.
		if(dataNova == null) {
			throw new IllegalArgumentException("A nova data de lançamento não pode ser nula.");
		}
		
		Filmes filmesAtualizado = atualizarCampo(idFilme, filmes -> {
			filmes.setDataLancamento(dataNova);
			filmesValidator.validarDataLancamento(dataNova);
		});
		
		log.info("Data de lançamento do filme com o ID {} foi atualizada para: {}", idFilme, filmesAtualizado.getDataLancamento());
		return filmesAtualizado;
	}
	
	public Filmes alterarNomeFilme(Long idFilme, String nomeNovo) {
		if(!StringUtils.hasText(nomeNovo)) {
			throw new IllegalArgumentException("O nome digitado não pode ser nulo ou vazio.");
		}
		
		Filmes filmesAtualizado = atualizarCampo(idFilme, filmes -> {
			filmes.setNome(nomeNovo);
			filmesValidator.validarDuplicidadeNome(nomeNovo, idFilme);
		});
		
		log.info("Nome do filme com o ID {} foi atualizado para: {}", idFilme, filmesAtualizado.getNome());
		return filmesAtualizado;
	}
	
	

}
