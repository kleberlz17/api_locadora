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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import klz7.api.dto.AluguelRequestDTO;
import klz7.api.dto.DevolucaoExtendidaDTO;
import klz7.api.dto.LocacaoDTO;
import klz7.api.model.Cliente;
import klz7.api.model.Filmes;
import klz7.api.model.Locacao;
import klz7.api.repository.ClienteRepository;
import klz7.api.repository.FilmesRepository;
import klz7.api.repository.LocacaoRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback
@Slf4j
public class LocacaoControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private LocacaoRepository locacaoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FilmesRepository filmesRepository;
	
	private Long idLocacao;
	
	private Long id; // cliente
	private Long id2;
	
	private Long idFilme;
	private Long idFilmes2;

	@BeforeEach
	void setUp() {
		Cliente cliente = new Cliente(
				"Chris Prince",
				LocalDate.of(1992, 4, 12),
				"77777777777",
				"21988888888",
				"chris@email.com",
				"Oxford Street - 333");
		
		Cliente clienteSalvo = clienteRepository.save(cliente);
		id = clienteSalvo.getId();
		
		Cliente cliente2 = new Cliente(
				"Noel Noah",
				LocalDate.of(1990, 10, 23),
				"66666666666",
				"21944444444",
				"noel@email.com",
				"French Quarter - 12");
		
		Cliente clienteSalvo2 = clienteRepository.save(cliente2);
		id2 = clienteSalvo2.getId();
		
		
		Filmes filmes = new Filmes(
				"Avengers",
				LocalDate.of(2012, 4, 27),
				"Joss Whedon",
				"Super-heróis",
				1);
		
		Filmes filmesSalvo = filmesRepository.save(filmes);
		idFilme = filmesSalvo.getIdFilme();
		
		Filmes filmes2 = new Filmes(
				"Eternals",
				LocalDate.of(2021, 11, 4),
				"Chloé Zhao",
				"Super-heróis",
				1);
		
		Filmes filmesSalvo2 = filmesRepository.save(filmes2);
		idFilmes2 = filmesSalvo2.getIdFilme();
		
		Locacao locacao = new Locacao(
				cliente,
				filmes,
				LocalDate.of(2025, 5, 29),
				LocalDate.of(2025, 6, 2),
				false,
				1);
		
		Locacao locacaoSalva = locacaoRepository.save(locacao);
		idLocacao = locacaoSalva.getIdLocacao();
	}
	
	@Test
	void testSalvarLocacaoComSucesso() throws Exception {
		LocacaoDTO locacaoDTO = new LocacaoDTO();
		locacaoDTO.setId(id);
		locacaoDTO.setIdFilmes(idFilme);
		locacaoDTO.setDataLocacao(LocalDate.now());
		locacaoDTO.setDataDevolucao(LocalDate.of(2025, 6, 12));
		locacaoDTO.setDevolvido(false);
		locacaoDTO.setQuantidade(1);
	
		mockMvc.perform(post("/locacao")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(locacaoDTO))) //a locação agora vem de fora, tem que converter;
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString("/locacao/")));
	}
	
	@Test
	void testBuscarLocacaoPorClienteComSucesso() throws Exception {
		mockMvc.perform(get("/locacao/{id}/locacoes", id))
		.andExpect(status().isOk());
	}
	
	@Test
	void testRenovarLocacaoComSucesso() throws Exception {
		DevolucaoExtendidaDTO devolucaoExtendidaDTO = new DevolucaoExtendidaDTO();
		devolucaoExtendidaDTO.setDataDevolucao(LocalDate.of(2025, 6, 20));
		
		mockMvc.perform(put("/locacao/{idLocacao}/renovarLocacao", idLocacao)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(devolucaoExtendidaDTO))) //DevolucaoExtendida vem de fora, deve ser convertida;
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dataDevolucao").value("2025-06-20"));
	}
	
	@Test
	void testBuscarHistoricoFilmeComSucesso() throws Exception {
		mockMvc.perform(get("/locacao/{idFilme}/historico", idFilme))
		.andExpect(status().isOk());
	}
	
	@Test
	void testCalcularMultaComSucesso() throws Exception {
		mockMvc.perform(post("/locacao/{idLocacao}/multa", idLocacao)) //Pra simular atraso eu alterei o devolvido e a data de devolução pra dias anteriores ao de hoje.
		.andExpect(status().isOk());
	}
	
	@Test
	void testAlugarFilmeComSucesso() throws Exception {
		AluguelRequestDTO aluguelDTO = new AluguelRequestDTO(); 
		aluguelDTO.setId(id2);
		aluguelDTO.setIdFilmes(idFilmes2);
		aluguelDTO.setQuantidade(1);
		aluguelDTO.setDataDevolucao(LocalDate.of(2025, 6, 12));
		
		mockMvc.perform(post("/locacao/alugar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(aluguelDTO)))
				.andExpect(status().isOk());
	}
	
	@Test
	void testEstoqueReduzidoAposLocacaoComSucesso() throws Exception {
		Filmes filmeAntes = filmesRepository.findById(idFilmes2)
				.orElseThrow();
		
		int estoqueAntes = filmeAntes.getEstoque();
		log.info("Estoque antes da locação: {}", estoqueAntes);
		
		LocacaoDTO novaLocacao = new LocacaoDTO(id, idFilmes2, 1, LocalDate.now().plusDays(5));
		mockMvc.perform(post("/locacao/alugar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novaLocacao)))
				.andExpect(status().isOk());
		
		Filmes filmeDepois = filmesRepository.findById(idFilmes2)
				.orElseThrow();
		int estoqueDepois = filmeDepois.getEstoque();
		log.info("Estoque depois da locação: {}", estoqueDepois);
		
		assertEquals(estoqueAntes - 1, estoqueDepois);
	}

}
