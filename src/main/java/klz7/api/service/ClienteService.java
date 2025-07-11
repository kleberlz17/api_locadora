package klz7.api.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import klz7.api.exception.ClienteNaoEncontradoException;
import klz7.api.model.Cliente;
import klz7.api.repository.ClienteRepository;
import klz7.api.validator.ClienteValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {
	
	private final ClienteRepository clienteRepository;
	private final ClienteValidator clienteValidator;
	
	public ClienteService(ClienteRepository clienteRepository, ClienteValidator clienteValidator) {
		this.clienteRepository = clienteRepository;
		this.clienteValidator = clienteValidator;
	}
	
	public Cliente salvar(Cliente cliente) {
		if(cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
			throw new IllegalArgumentException("O CPF não deve ser nulo ou vazio.");
		}
		
		if(cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
			throw new IllegalArgumentException("O email não deve ser nulo ou vazio.");
		}
		
		clienteValidator.validarCliente(cliente);
		log.info("Cliente salvo: {}", cliente);
		return clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> buscarPorId(Long id) {
		Optional<Cliente> clienteId = clienteRepository.findById(id);
		log.info("Buscar por ID: '{}'. Cliente encontrado: {}", id, clienteId);
		return clienteId;
	}
	
	public List<Cliente> buscarPorNome(String nome) {
		List<Cliente> clientesNome = clienteRepository.findByNomeContainingIgnoreCase(nome);
		log.info("Buscar por nome: '{}'. Clientes encontrados: {}", nome, clientesNome);
		return clientesNome;
	}
	
	public Optional<Cliente> buscarPorCpf(String cpf) {
		Optional<Cliente> clienteCpf = clienteRepository.findByCpfContainingIgnoreCase(cpf);
		log.info("Buscar por CPF: '{}'. Cliente encontrado: {}", cpf, clienteCpf);
		return clienteCpf;
	}
	
	private Cliente atualizarCampo(Long id, Consumer<Cliente> atualizador) {
		Cliente clienteAtualizado = clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));
		atualizador.accept(clienteAtualizado);
		return clienteRepository.save(clienteAtualizado);
	}
	
	public Cliente alterarTelefone(Long id, String telefoneNovo) {
		if(telefoneNovo == null || telefoneNovo.isEmpty()) {
			throw new IllegalArgumentException("O novo telefone não deve ser nulo ou vazio.");
		}
		
		Cliente clienteAtualizado = atualizarCampo(id, cliente -> {
			cliente.setTelefone(telefoneNovo);
			clienteValidator.validarTelefone(cliente);
		});
		
		log.info("Telefone do cliente com ID {} foi atualizado para: {}", id, clienteAtualizado.getTelefone());
		return clienteAtualizado;
	}
	
	public Cliente alterarEmail(Long id, String emailNovo) {
		if(emailNovo == null || emailNovo.isEmpty()) {
			throw new IllegalArgumentException("O novo email não deve ser nulo ou vazio.");
		}
		
		Cliente clienteAtualizado = atualizarCampo(id, cliente -> {
			cliente.setEmail(emailNovo);
			clienteValidator.validarEmail(cliente);
		});
		
		log.info("Email do cliente com ID {} foi atualizado para: {}", id, clienteAtualizado.getEmail());
		return clienteAtualizado;
	}
	
	public Cliente alterarEndereco(Long id, String enderecoNovo) {
		if(enderecoNovo == null || enderecoNovo.isEmpty()) {
			throw new IllegalArgumentException("O novo endereço não deve ser nulo ou vazio.");
		}
		
		Cliente clienteAtualizado = atualizarCampo(id, cliente -> {
			cliente.setEndereco(enderecoNovo);
		});
		
		log.info("Endereço do cliente com ID {} foi atualizado para: {}", id, clienteAtualizado.getEndereco());
		return clienteAtualizado;
	}
	
	public void deletarClientePorId(Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente de ID " + id + "não encontrado no sistema."));
		
		clienteRepository.delete(cliente);
		log.info("O cliente de ID {} e nome {} foi deletado.", id, cliente.getNome());
	}
	
	

}
