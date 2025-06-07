package klz7.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import klz7.api.model.Filmes;
import klz7.api.repository.FilmesRepository;
import klz7.api.validator.FilmesValidator;

public class FilmesServiceTest {

	@Mock
	private FilmesRepository filmesRepository;
	
	@Mock
	private FilmesValidator filmesValidator;
	
	@InjectMocks
	private FilmesService filmesService;
	
	private Filmes filme1;
	private Filmes filme2;
	private Filmes filme3;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		filme1 = new Filmes();
		filme1.setIdFilme(1L);
		filme1.setNome("The Batman");
		filme1.setDataLancamento(LocalDate.of(2022, 3, 12));
		filme1.setDiretor("Matt Reeves");
		filme1.setGenero("super-heróis");
		filme1.setEstoque(1);
		
		filme2 = new Filmes();
		filme2.setIdFilme(3L);
		filme2.setNome("Batman Begins");
		filme2.setDataLancamento(LocalDate.of(2005, 6, 17));
		filme2.setDiretor("Christopher Nolan");
		filme2.setGenero("super-heróis");
		filme2.setEstoque(3);
		
		filme3 = new Filmes();
		filme3.setIdFilme(4L);
		filme3.setNome("Batman: O Cavaleiro das Trevas");
		filme3.setDataLancamento(LocalDate.of(2008, 7, 18));
		filme3.setDiretor("Christopher Nolan");
		filme3.setGenero("super-heróis");
		filme3.setEstoque(1);
	}
	
	@Test
	void testSalvarFilmeComSucesso() {
		Filmes filmes = new Filmes();
		filmes.setIdFilme(2L);
		filmes.setNome("Avengers: Endgame");
		filmes.setDataLancamento(LocalDate.of(2019, 4, 25));
		filmes.setDiretor("Joe Russo");
		filmes.setGenero("super-heróis");
		filmes.setEstoque(1);
		
		when(filmesRepository.save(any(Filmes.class))).thenReturn(filmes);
		
		Filmes resultado = filmesService.salvar(filmes);
		
		assertNotNull(resultado);
		assertEquals(filmes.getNome(), resultado.getNome());
		assertEquals(filmes.getDiretor(), resultado.getDiretor());
		verify(filmesRepository).save(filmes);
		verify(filmesValidator).validarTudo(filmes);
	}
	
	@Test
	void testBuscarFilmePorIdComSucesso() {
		when(filmesRepository.findById(1L)).thenReturn(Optional.of(filme1));
		Optional<Filmes> resultadoId = filmesService.buscarPorId(1L);
		
		assertTrue(resultadoId.isPresent());
		assertEquals(filme1.getIdFilme(), resultadoId.get().getIdFilme());
		verify(filmesRepository, times(1)).findById(1L);
	}
	
	@Test
	void testBuscarFilmePorNomeComSucesso() {
		when(filmesRepository.findByNomeContainingIgnoreCase("batman")).thenReturn(List.of(filme1, filme2));
		List<Filmes> resultadoNome = filmesService.buscarPorNome("batman");
		
		assertNotNull(resultadoNome);
		assertEquals(2, resultadoNome.size());
		verify(filmesRepository, times(1)).findByNomeContainingIgnoreCase("batman");
	}
	
	@Test
	void testBuscarPorDataLancamentoComSucesso() {
		when(filmesRepository.findByDataLancamento(LocalDate.of(2005, 6, 17))).thenReturn(List.of(filme1));
		List<Filmes> resultadoData = filmesService.buscarPorDataLancamento(LocalDate.of(2005, 6, 17));
		
		assertNotNull(resultadoData);
		assertEquals(1, resultadoData.size());
		verify(filmesRepository, times(1)).findByDataLancamento(LocalDate.of(2005, 6, 17));
	}
	
	@Test
	void testBuscarPorDiretorComSucesso() {
		when(filmesRepository.findByDiretorContainingIgnoreCase("nolan")).thenReturn(List.of(filme2, filme3));
		List<Filmes> resultadoDiretor = filmesService.buscarPorDiretor("nolan");
		
		assertNotNull(resultadoDiretor);
		assertEquals(2, resultadoDiretor.size());
		verify(filmesRepository, times(1)).findByDiretorContainingIgnoreCase("nolan");
	}
	
	@Test
	void testBuscarPorGeneroComSucesso() {
		when(filmesRepository.findByGeneroContainingIgnoreCase("herois")).thenReturn(List.of(filme1, filme2, filme3));
		List<Filmes> resultadoGenero = filmesService.buscarPorGenero("herois");
		
		assertNotNull(resultadoGenero);
		assertEquals(3, resultadoGenero.size());
		verify(filmesRepository, times(1)).findByGeneroContainingIgnoreCase("herois");
	}
	
	@Test
	void testBuscarPorEstoqueComSucesso() {
		when(filmesRepository.findByEstoque(1)).thenReturn(List.of(filme1, filme3));
		List<Filmes> resultadoEstoque = filmesService.buscarPorEstoque(1);
		
		assertNotNull(resultadoEstoque);
		assertEquals(2, resultadoEstoque.size());
		verify(filmesRepository, times(1)).findByEstoque(1);
	}
	
	@Test
	void testAlterarEstoqueComSucesso() {
		Long idFilme = filme3.getIdFilme();
		int estoqueNovo = 4;
		
		when(filmesRepository.findById(idFilme)).thenReturn(Optional.of(filme3));
		when(filmesRepository.save(any(Filmes.class))).thenReturn(filme3);
		
		Filmes resultado = filmesService.alterarEstoque(idFilme, estoqueNovo);
		
		assertNotNull(resultado);
		assertEquals(estoqueNovo, resultado.getEstoque());
		verify(filmesValidator).validarEstoque(estoqueNovo);
		verify(filmesRepository).save(filme3);
	}
	
	@Test
	void testAlterarDataLancamentoComSucesso() {
		Long idFilme = filme2.getIdFilme();
		LocalDate dataNova = LocalDate.of(2006, 3, 2);
		
		when(filmesRepository.findById(idFilme)).thenReturn(Optional.of(filme2));
		when(filmesRepository.save(any(Filmes.class))).thenReturn(filme2);
		
		Filmes resultado = filmesService.alterarDataLancamento(idFilme, dataNova);
		
		assertNotNull(resultado);
		assertEquals(dataNova, resultado.getDataLancamento());
		verify(filmesValidator).validarDataLancamento(dataNova);
		verify(filmesRepository).save(filme2);
	}
	
	@Test
	void testAlterarNomeFilmeComSucesso() {
		Long idFilme = filme1.getIdFilme();
		String nomeNovo = "Batman novo";
		
		when(filmesRepository.findById(idFilme)).thenReturn(Optional.of(filme1));
		when(filmesRepository.save(any(Filmes.class))).thenReturn(filme1);
		
		Filmes resultado = filmesService.alterarNomeFilme(idFilme, nomeNovo);
		
		assertNotNull(resultado);
		assertEquals(nomeNovo, resultado.getNome());
		verify(filmesValidator).validarDuplicidadeNome(nomeNovo, idFilme);
		verify(filmesRepository).save(filme1);
	}
	
	@Test
	void testDeletarFilmePorIdComSucesso() {
		Long idFilme2 = filme2.getIdFilme();
		
		when(filmesRepository.findById(idFilme2)).thenReturn(Optional.of(filme2));
		
		filmesService.deletarFilmePorId(idFilme2);
		
		verify(filmesRepository, times(1)).delete(filme2);
	}
}
