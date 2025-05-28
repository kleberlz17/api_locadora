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
	
	public Optional<Cliente> buscarPorId(Long idCliente) {
		log.info("Cliente encontrado com o ID digitado: {}", idCliente);
		return clienteRepository.findById(idCliente);
	}
	
	public List<Cliente> buscarPorNome(String nome) {
		log.info("Clientes encontrados com o nome digitado: {}", nome);
		return clienteRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	public Optional<Cliente> buscarPorCpf(String cpf) {
		log.info("Cliente encontrado com o CPF digitado: {}", cpf);
		return clienteRepository.findByCpfContainingIgnoreCase(cpf);
	}
	
	private Cliente atualizarCampo(Long idCliente, Consumer<Cliente> atualizador) {
		Cliente clienteAtualizado = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));
		atualizador.accept(clienteAtualizado);
		return clienteRepository.save(clienteAtualizado);
	}
	
	public Cliente alterarTelefone(Long idCliente, String telefoneNovo) {
		if(telefoneNovo == null || telefoneNovo.isEmpty()) {
			throw new IllegalArgumentException("O novo telefone não deve ser nulo ou vazio.");
		}
		
		Cliente clienteAtualizado = atualizarCampo(idCliente, cliente -> {
			cliente.setTelefone(telefoneNovo);
			clienteValidator.validarTelefone(cliente);
		});
		
		log.info("Telefone do cliente com ID {} foi atualizado para: {}", idCliente, clienteAtualizado.getTelefone());
		return clienteAtualizado;
	}
	
	public Cliente alterarEmail(Long idCliente, String emailNovo) {
		if(emailNovo == null || emailNovo.isEmpty()) {
			throw new IllegalArgumentException("O novo email não deve ser nulo ou vazio.");
		}
		
		Cliente clienteAtualizado = atualizarCampo(idCliente, cliente -> {
			cliente.setEmail(emailNovo);
			clienteValidator.validarEmail(cliente);
		});
		
		log.info("Email do cliente com ID {} foi atualizado para: {}", idCliente, clienteAtualizado.getEmail());
		return clienteAtualizado;
	}
	
	public Cliente alterarEndereco(Long idCliente, String enderecoNovo) {
		if(enderecoNovo == null || enderecoNovo.isEmpty()) {
			throw new IllegalArgumentException("O novo endereço não deve ser nulo ou vazio.");
		}
		
		Cliente clienteAtualizado = atualizarCampo(idCliente, cliente -> {
			cliente.setEndereco(enderecoNovo);
		});
		
		log.info("Endereço do cliente com ID {} foi atualizado para: {}", idCliente, clienteAtualizado.getEndereco());
		return clienteAtualizado;
	}
	
	

}
