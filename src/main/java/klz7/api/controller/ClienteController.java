package klz7.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import klz7.api.dto.ClienteDTO;
import klz7.api.dto.NovoEmailDTO;
import klz7.api.dto.NovoEnderecoDTO;
import klz7.api.dto.NovoTelefoneDTO;
import klz7.api.mapper.ClienteConverter;
import klz7.api.model.Cliente;
import klz7.api.service.ClienteService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/clientes")
@Slf4j
public class ClienteController {
	
	private final ClienteService clienteService;
	private final ClienteConverter clienteConverter;
	
	public ClienteController(ClienteService clienteService, ClienteConverter clienteConverter) {
		this.clienteService = clienteService;
		this.clienteConverter = clienteConverter;
	}
	
	@PostMapping
	public ResponseEntity<Object> salvar (@RequestBody @Valid ClienteDTO clienteDTO) {
		log.info("Iniciando salvamento de novo cliente...");
		Cliente cliente = clienteConverter.dtoParaEntidade(clienteDTO);
		Cliente clienteSalvo = clienteService.salvar(cliente);
		
		URI uri = URI.create("/clientes/" + clienteSalvo.getIdCliente());
		
		log.info("Cliente salvo com sucesso. ID gerado: {}", clienteSalvo.getIdCliente());
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{idCliente}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long idCliente) {
		log.info("Buscando cliente pelo ID no sistema...");
		Optional<Cliente> cliente = clienteService.buscarPorId(idCliente);
		
		if (cliente.isPresent()) {
			log.info("Cliente encontrado pelo ID com sucesso.");
			return ResponseEntity.ok(cliente.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Cliente>> buscarPorNome(@PathVariable String nome) {
		log.info("Buscando cliente pelo nome no sistema...");
		List<Cliente> clienteNome = clienteService.buscarPorNome(nome);
		
		if (clienteNome.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			log.info("Cliente encontrado pelo nome com sucesso.");
			return ResponseEntity.ok(clienteNome);
		}
	}
	
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
		log.info("Buscando cliente pelo CPF no sistema...");
		Optional<Cliente> clienteCpf = clienteService.buscarPorCpf(cpf);
		
		if (clienteCpf.isPresent()) {
			log.info("Cliente encontrado pelo CPF com sucesso.");
			return ResponseEntity.ok(clienteCpf.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{idCliente}/novoTelefone")
	public ResponseEntity<Cliente> alterarTelefone(@PathVariable Long idCliente, @RequestBody @Valid NovoTelefoneDTO novoTelefoneDTO) {
		log.info("Iniciando alteração de telefone do cliente de ID {} no sistema...", idCliente);
		
		try {
			Cliente telefoneAlterado = clienteService.alterarTelefone(idCliente, novoTelefoneDTO.getTelefone());
			
			log.info("Telefone do cliente foi alterado com sucesso.");
			return ResponseEntity.ok(telefoneAlterado);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar alterar o telefone do cliente no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}	
	}
	
	@PutMapping("/{idCliente}/novoEmail")
	public ResponseEntity<Cliente> alterarEmail(@PathVariable Long idCliente, @RequestBody @Valid NovoEmailDTO novoEmailDTO) {
		log.info("Iniciando alteração de email do cliente de ID {} no sistema...", idCliente);
		
		try {
			Cliente emailAlterado = clienteService.alterarEmail(idCliente, novoEmailDTO.getEmail());
			
			log.info("Email do cliente foi alterado com sucesso.");
			return ResponseEntity.ok(emailAlterado);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar alterar o email do cliente no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{idCliente}/novoEndereco")
	public ResponseEntity<Cliente> alterarEndereco(@PathVariable Long idCliente, @RequestBody @Valid NovoEnderecoDTO novoEnderecoDTO) {
		log.info("Iniciando alteração de endereço do cliente de ID {} no sistema...", idCliente);
	
		try { 
			Cliente enderecoAlterado = clienteService.alterarEndereco(idCliente, novoEnderecoDTO.getEndereco());
			
			log.info("Endereço do cliente foi alterado com sucesso.");
			return ResponseEntity.ok(enderecoAlterado);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar alterar o endereço do cliente no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
}
