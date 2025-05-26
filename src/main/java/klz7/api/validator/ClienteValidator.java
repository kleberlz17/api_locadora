package klz7.api.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import klz7.api.exception.CpfDuplicadoException;
import klz7.api.exception.EmailDuplicadoException;
import klz7.api.exception.TelefoneDuplicadoException;
import klz7.api.model.Cliente;
import klz7.api.repository.ClienteRepository;

@Component
public class ClienteValidator {
	
	private ClienteRepository clienteRepository;
	
	public ClienteValidator(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	 public void validarCliente(Cliente cliente) {
		 if(cpfJaUsadoPorOutroCliente(cliente)) {
			 throw new CpfDuplicadoException("CPF já cadastrado, cliente existe no sistema.");
		 }
	 }
	 
	 public boolean cpfJaUsadoPorOutroCliente(Cliente cliente) {
		 Optional<Cliente> existente = clienteRepository.findByCpfContainingIgnoreCase(cliente.getCpf());
		 return existente.isPresent() && !existente.get().getIdCliente().equals(cliente.getIdCliente());
	 }
	 
	 public void validarEmail(Cliente cliente) {
		 if(emailJaUsadoPorOutroCliente(cliente)) {
			 throw new EmailDuplicadoException("Email já cadastrado, cliente existe no sistema.");
		 }
	 }
	 
	 public boolean emailJaUsadoPorOutroCliente(Cliente cliente) {
		 Optional<Cliente> existente = clienteRepository.findByEmail(cliente.getEmail());
		 return existente.isPresent() && !existente.get().getIdCliente().equals(cliente.getIdCliente());
	 }
	 
	 public void validarTelefone(Cliente cliente) {
		 if(telefoneJaUsadoPorOutroCliente(cliente)) {
			 throw new TelefoneDuplicadoException("Telefone já cadastrado, cliente existe no sistema.");
		 }
	 }
	 
	 public boolean telefoneJaUsadoPorOutroCliente(Cliente cliente) {
		 Optional<Cliente> existente = clienteRepository.findByTelefone(cliente.getTelefone());
		 return existente.isPresent() && !existente.get().getIdCliente().equals(cliente.getIdCliente());
	 }

}
