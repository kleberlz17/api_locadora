package klz7.api.validator;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import klz7.api.exception.DataLancamentoInvalidaException;
import klz7.api.exception.EstoqueNegativoException;
import klz7.api.exception.NomeFilmeDuplicadoException;
import klz7.api.model.Filmes;
import klz7.api.repository.FilmesRepository;

@Component
public class FilmesValidator {
	
	private FilmesRepository filmesRepository;
	
	public FilmesValidator(FilmesRepository filmesRepository) {
		this.filmesRepository = filmesRepository;
	}
	
	public void validarEstoque(int estoque) {
		if (estoque <= 0) {
			throw new EstoqueNegativoException("O estoque não deve ser negativo, somente 0 pra cima é permitido.");
		}
	}
	
	public void validarDataLancamento(LocalDate dataLancamento) {
		LocalDate hoje = LocalDate.now();
		
		if (dataLancamento.getYear() > hoje.getYear() ||
				(dataLancamento.getYear() == hoje.getYear() &&
				 dataLancamento.getMonthValue() > hoje.getMonthValue())) {//A data de lançamento do filme não deve estar em ano e mês futuro.
			throw new DataLancamentoInvalidaException("A data de lançamento digitada está num ponto futuro");
		}
	}
	
	public void validarDuplicidadeNome(String nome, Long idAtual) {
		Optional<Filmes> existente = filmesRepository.findByNomeIgnoreCase(nome.trim());//trim pra evitar problemas com espaços em branco.
		if (existente.isPresent() && !existente.get().getIdFilme().equals(idAtual)) {
			throw new NomeFilmeDuplicadoException("Já existe um filme cadastrado com esse nome.");
		}
	}
	
	public void validarTudo(Filmes filmes) { // Esse é pro salvar filme.
		validarDuplicidadeNome(filmes.getNome(), filmes.getIdFilme());
		validarEstoque(filmes.getEstoque());
		validarDataLancamento(filmes.getDataLancamento());
	}
	
	

}
