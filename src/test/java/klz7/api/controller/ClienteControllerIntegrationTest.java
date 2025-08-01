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
import klz7.api.dto.ClienteDTO;
import klz7.api.dto.NovoEmailDTO;
import klz7.api.dto.NovoEnderecoDTO;
import klz7.api.dto.NovoTelefoneDTO;
import klz7.api.model.Cliente;
import klz7.api.repository.ClienteRepository;

@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
@Transactional // Testes rodam isoladamente.
@Rollback // Desfaz tudo no banco de dados ao final do teste.
public class ClienteControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;// Conversão de objetos em JSON.
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	private Long id;
	
	private Long id2;
	
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
				LocalDate.of(1990, 5, 22),
				"66666666666",
				"21977777777",
				"noel@email.com",
				"Avenida Champs-Élysées - 21");
		
		Cliente clienteSalvo2 = clienteRepository.save(cliente2);
		id2 = clienteSalvo2.getId();	
	}
	
	@Test
	void testSalvarClienteComSucesso() throws Exception {
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setNome("Bruce Wayne");
		clienteDTO.setDataNascimento(LocalDate.of(1978, 4, 19));
		clienteDTO.setCpf("11111111111");
		clienteDTO.setTelefone("21988888888");
		clienteDTO.setEmail("bruce@email.com");
		clienteDTO.setEndereco("Gotham City");
		
		mockMvc.perform(post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(clienteDTO)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString("/clientes/")));
	}
	
	@Test
	void testBuscarPorIdComSucesso() throws Exception {
		mockMvc.perform(get("/clientes/{id}", id))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome").value("Chris Prince"))
		.andExpect(jsonPath("$.cpf").value("77777777777"));
	}
	
	@Test
	void testBuscarPorNomeComSucesso() throws Exception {
		String nome = "Noel Noah";
		mockMvc.perform(get("/clientes/nome/{nome}" , nome))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome").value("Noel Noah")) // Procura pelo Indice 0 da lista e compara com  o nome.
		.andExpect(jsonPath("$[0].email").value("noel@email.com")); // Procura pelo Indice 0 da lista e compara com  o email.
	}
	
	@Test
	void testBuscarPorCpfComSucesso() throws Exception {
		String cpf = "66666666666";
		mockMvc.perform(get("/clientes/cpf/{cpf}", cpf))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.cpf").value("66666666666"));
	}
	
	@Test
	void testAlterarTelefoneComSucesso() throws Exception {
		NovoTelefoneDTO novoTelefoneDTO = new NovoTelefoneDTO();
		novoTelefoneDTO.setTelefone("21955555555");
		
		mockMvc.perform(put("/clientes/{id}/novoTelefone", id2)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novoTelefoneDTO))) // novoTelefone vem de fora, tem que ter conversão.
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.telefone").value("21955555555"));
	}
	
	@Test
	void testAlterarEmailComSucesso() throws Exception {
		NovoEmailDTO novoEmailDTO = new NovoEmailDTO();
		novoEmailDTO.setEmail("prince@email.com");
		
		mockMvc.perform(put("/clientes/{id}/novoEmail", id)
				.contentType(MediaType.APPLICATION_JSON) 
				.content(objectMapper.writeValueAsString(novoEmailDTO)))// novoEmail vem de fora, tem que ter conversão.
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("prince@email.com"));
	}
	
	@Test
	void testAlterarEnderecoComSucesso() throws Exception {
		NovoEnderecoDTO novoEnderecoDTO = new NovoEnderecoDTO();
		novoEnderecoDTO.setEndereco("Trafalgar Square - 32");
		
		mockMvc.perform(put("/clientes/{id}/novoEndereco", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novoEnderecoDTO)))// novoEndereco vem de fora, tem que ter conversão.
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.endereco").value("Trafalgar Square - 32"));	
	}
	
	@Test
	void testDeletarClientePorIdComSucesso() throws Exception {
		mockMvc.perform(delete("/clientes/{id}/deletar", id))
		.andExpect(status().isNoContent());
	}
	
	

}
