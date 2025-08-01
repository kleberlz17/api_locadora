package klz7.api.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.hamcrest.Matchers.containsString;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import klz7.api.dto.FilmesDTO;
import klz7.api.dto.NovaDataLancamentoDTO;
import klz7.api.dto.NovoEstoqueDTO;
import klz7.api.dto.NovoNomeFilmeDTO;
import klz7.api.model.Filmes;
import klz7.api.repository.FilmesRepository;

@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
@Transactional
@Rollback
public class FilmesControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private FilmesRepository filmesRepository;
	
	private Long idFilme;
	
	private Long idFilme2;
	
	@BeforeEach
	void setUp() {
		Filmes filmes = new Filmes(
				"Avengers",
				LocalDate.of(2012, 4, 27),
				"Joss Whedon",
				"Super-heróis",
				1);
		
		Filmes filmeSalvo = filmesRepository.save(filmes);
		idFilme = filmeSalvo.getIdFilme();
		
		Filmes filmes2 = new Filmes(
				"Homem-Aranha: Sem Volta para Casa",
				LocalDate.of(2021, 12, 16),
				"Jon Watts",
				"Super-heróis",
				2);
		
		Filmes filmeSalvo2 = filmesRepository.save(filmes2);
		idFilme2 = filmeSalvo2.getIdFilme();
	}
	
	@Test
	void testSalvarFilmeComSucesso() throws Exception {
		FilmesDTO filmesDTO = new FilmesDTO();
		filmesDTO.setNome("Os Fantasmas de Scrooge");
		filmesDTO.setDataLancamento(LocalDate.of(2009, 11, 6));
		filmesDTO.setDiretor("Robert Zemeckis");
		filmesDTO.setGenero("Animação");
		filmesDTO.setEstoque(1);
		
		mockMvc.perform(post("/filmes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(filmesDTO)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString("/filmes/")));
	}
	
	@Test
	void testBuscarPeloIdComSucesso() throws Exception {
		mockMvc.perform(get("/filmes/{idFilme}", idFilme))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome").value("Avengers"))
		.andExpect(jsonPath("$.genero").value("Super-heróis"));
	}
	
	@Test
	void testBuscarPeloNomeComSucesso() throws Exception {
		String nome = "Avengers";
		mockMvc.perform(get("/filmes/nome/{nome}", nome))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome").value("Avengers")); //[0] Indice 0 da lista.
	}
	
	@Test
	void testBuscarPorDataLancamentoComSucesso() throws Exception {
		LocalDate dataLancamento = LocalDate.of(2012, 4, 27);
		mockMvc.perform(get("/filmes/dataLancamento/{dataLancamento}", dataLancamento))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].dataLancamento").value("2012-04-27"));
	}
	
	@Test
	void testBuscarPorDiretorComSucesso() throws Exception {
		String diretor = "Jon Watts";
		mockMvc.perform(get("/filmes/diretor/{diretor}", diretor))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].diretor").value("Jon Watts"));
	}
	
	@Test
	void testBuscarPorGeneroComSucesso() throws Exception {
		String genero = "Super-heróis";
		mockMvc.perform(get("/filmes/genero/{genero}", genero))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].genero").value("Super-heróis"));//Vai chamar a lista a partir do indice 0.
	}
	
	@Test
	void testBuscarPorEstoqueComSucesso() throws Exception {
		int estoque = 2;
		mockMvc.perform(get("/filmes/estoque/{estoque}", estoque))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].estoque").value(2));
	}
	
	@Test
	void testAlterarEstoqueComSucesso() throws Exception {
		NovoEstoqueDTO novoEstoqueDTO = new NovoEstoqueDTO();
		novoEstoqueDTO.setEstoque(7);
		
		mockMvc.perform(put("/filmes/{idFilme}/novoEstoque", idFilme2)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novoEstoqueDTO))) //novoEstoque vem de fora, tem que converter ele.
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.estoque").value(7));
	}
	
	@Test
	void testAlterarDataLancamentoComSucesso() throws Exception {
		NovaDataLancamentoDTO novaDataLancamentoDTO = new NovaDataLancamentoDTO();
		novaDataLancamentoDTO.setDataLancamento(LocalDate.of(2022, 4, 10));
		
		mockMvc.perform(put("/filmes/{idFilme}/novaDataLancamento", idFilme2)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novaDataLancamentoDTO))) //novaDataLancamento vem de fora, tem que converter ela.
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dataLancamento").value("2022-04-10"));
	}
	
	@Test
	void testAlterarNomeFilmeComSucesso() throws Exception {
		NovoNomeFilmeDTO novoNomeFilmeDTO = new NovoNomeFilmeDTO();
		novoNomeFilmeDTO.setNome("Os Vingadores");
		
		mockMvc.perform(put("/filmes/{idFilme}/novoNomeFilme", idFilme)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novoNomeFilmeDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("Os Vingadores"));
	}
	
	@Test
	void testDeletarFilmePorIdComSucesso() throws Exception {
		mockMvc.perform(delete("/filmes/{idFilme}/deletar", idFilme2))
		.andExpect(status().isNoContent());
	}

}
