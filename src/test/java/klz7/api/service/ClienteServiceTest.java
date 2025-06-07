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

import klz7.api.model.Cliente;
import klz7.api.repository.ClienteRepository;
import klz7.api.validator.ClienteValidator;

public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;
	
	@Mock
	private ClienteValidator clienteValidator;
	
	@InjectMocks
	private ClienteService clienteService;
	
	private Cliente cliente;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNome("Chris Prince");
		cliente.setDataNascimento(LocalDate.of(1998, 3, 12 ));
		cliente.setCpf("11111111111");
		cliente.setTelefone("2199999999");
		cliente.setEmail("chris@email.com");
		cliente.setEndereco("Oxford Street - 333");
	}
	
	@Test
	void testSalvarClienteComSucesso() {
		Cliente cliente = new Cliente();
		cliente.setId(2L);
		cliente.setNome("Noel Noah");
		cliente.setDataNascimento(LocalDate.of(1994, 2, 11));
		cliente.setCpf("22222222222");
		cliente.setTelefone("21988888888");
		cliente.setEmail("noel@email.com");
		cliente.setEndereco("Avenida Champs-Élysées - 21");
		
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente); // Simulando comportamento do repository.
		
		Cliente resultado = clienteService.salvar(cliente);
		
		assertNotNull(resultado);
		assertEquals(cliente.getNome(), resultado.getNome());
		assertEquals(cliente.getEmail(), resultado.getEmail());
		verify(clienteRepository).save(cliente);
		verify(clienteValidator).validarCliente(cliente);	
	}
	
	@Test
	void testBuscarPorIdComSucesso() {
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
		Optional<Cliente> resultadoId = clienteService.buscarPorId(1L);
		
		assertTrue(resultadoId.isPresent());
		assertEquals(cliente.getId(), resultadoId.get().getId());
		verify(clienteRepository, times(1)).findById(1L);
	}
	
	@Test
	void testBuscarPorNomeComSucesso() {
		when(clienteRepository.findByNomeContainingIgnoreCase("chris")).thenReturn(List.of(cliente));
		List<Cliente> resultadoNome = clienteService.buscarPorNome("chris");
		
		assertNotNull(resultadoNome);
		assertEquals(1, resultadoNome.size()); // Confere se a lista tem pelo menos 1 resultado.
		assertEquals(cliente.getNome(), resultadoNome.get(0).getNome());// Comparando o nome que vem do teste com o cliente ja criado (posição 0 na lista).
		verify(clienteRepository, times(1)).findByNomeContainingIgnoreCase("chris");	
	}
	
	@Test
	void testBuscarPorCpfComSucesso() {
		when(clienteRepository.findByCpfContainingIgnoreCase("11111111111")).thenReturn(Optional.of(cliente));
		Optional<Cliente> resultadoCpf = clienteService.buscarPorCpf("11111111111");
		
		assertTrue(resultadoCpf.isPresent());
		assertEquals(cliente.getCpf(), resultadoCpf.get().getCpf());
		verify(clienteRepository, times(1)).findByCpfContainingIgnoreCase("11111111111");	
	}
	
	@Test
	void testAlterarTelefoneComSucesso() {
		Long id = cliente.getId();
		String telefoneNovo = "21977777777";
		
		when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
		
		Cliente resultado = clienteService.alterarTelefone(id, telefoneNovo);
		
		assertNotNull(resultado);
		assertEquals(telefoneNovo, resultado.getTelefone());
		verify(clienteValidator).validarTelefone(cliente);
		verify(clienteRepository).save(cliente);
	}
	
	@Test
	void testAlterarEmailComSucesso() {
		Long id = cliente.getId();
		String emailNovo = "prince@email.com";
		
		when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
		
		Cliente resultado = clienteService.alterarEmail(id, emailNovo);
		
		assertNotNull(resultado);
		assertEquals(emailNovo, resultado.getEmail());
		verify(clienteValidator).validarEmail(resultado);
		verify(clienteRepository).save(cliente);
	}
	
	@Test
	void testAlterarEnderecoComSucesso() {
		Long id = cliente.getId();
		String enderecoNovo = "Whitechapel - 77";
		
		when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
		
		Cliente resultado = clienteService.alterarEndereco(id, enderecoNovo);
		
		assertNotNull(resultado);
		assertEquals(enderecoNovo, resultado.getEndereco());
		verify(clienteRepository).save(cliente);
	}
	
	@Test
	void testDeletarClientePorIdComSucesso() {
		Long id = cliente.getId();
		
		when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
		
		clienteService.deletarClientePorId(id);
		
		verify(clienteRepository, times(1)).delete(cliente);
	}
}
