package klz7.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Clientes")
public class ClienteController {
	
	private final ClienteService clienteService;
	private final ClienteConverter clienteConverter;
	
	public ClienteController(ClienteService clienteService, ClienteConverter clienteConverter) {
		this.clienteService = clienteService;
		this.clienteConverter = clienteConverter;
	}
	
	@PostMapping
	@Operation(summary = "Salvar", description = "Cadastrar novo cliente")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Cadastrado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "422", description = "Erro de validação"),
		@ApiResponse(responseCode = "409", description = "Cliente já cadastrado")
	})
	public ResponseEntity<Object> salvar (@RequestBody @Valid ClienteDTO clienteDTO) {
		log.info("Iniciando salvamento de novo cliente...");
		Cliente cliente = clienteConverter.dtoParaEntidade(clienteDTO);
		Cliente clienteSalvo = clienteService.salvar(cliente);
		
		URI uri = URI.create("/clientes/" + clienteSalvo.getId());
		
		log.info("Cliente salvo com sucesso. ID gerado: {}", clienteSalvo.getId());
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Buscar por ID", description = "Retorna os dados do cliente pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
		log.info("Buscando cliente pelo ID no sistema...");
		Optional<Cliente> cliente = clienteService.buscarPorId(id);
		
		if (cliente.isPresent()) {
			log.info("Cliente encontrado pelo ID com sucesso.");
			return ResponseEntity.ok(cliente.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/nome/{nome}")
	@Operation(summary = "Buscar por nome", description = "Retorna os dados do cliente pelo nome")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso",
				content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))),
		@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
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
	@Operation(summary = "Buscar por CPF", description = "Retorna os dados do cliente pelo CPF")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
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
	
	@PutMapping("/{id}/novoTelefone")
	@Operation(summary = "Alterar telefone", description = "Altera o telefone do cliente pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Telefone alterado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "400", description = "Telefone não deve ser nulo ou vazio"),
		@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
		@ApiResponse(responseCode = "409", description = "Telefone já cadastrado no sistema")
	})
	public ResponseEntity<Cliente> alterarTelefone(@PathVariable Long id, @RequestBody @Valid NovoTelefoneDTO novoTelefoneDTO) {
		log.info("Iniciando alteração de telefone do cliente de ID {} no sistema...", id);
		
		try {
			Cliente telefoneAlterado = clienteService.alterarTelefone(id, novoTelefoneDTO.getTelefone());
			
			log.info("Telefone do cliente foi alterado com sucesso.");
			return ResponseEntity.ok(telefoneAlterado);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar alterar o telefone do cliente no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}	
	}
	
	@PutMapping("/{id}/novoEmail")
	@Operation(summary = "Alterar email", description =  "Altera o email do cliente pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Email alterado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "400", description = "Email não deve ser nulo ou vazio"),
		@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
		@ApiResponse(responseCode = "409", description = "Email já cadastrado no sistema")
	})
	public ResponseEntity<Cliente> alterarEmail(@PathVariable Long id, @RequestBody @Valid NovoEmailDTO novoEmailDTO) {
		log.info("Iniciando alteração de email do cliente de ID {} no sistema...", id);
		
		try {
			Cliente emailAlterado = clienteService.alterarEmail(id, novoEmailDTO.getEmail());
			
			log.info("Email do cliente foi alterado com sucesso.");
			return ResponseEntity.ok(emailAlterado);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar alterar o email do cliente no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}/novoEndereco")
	@Operation(summary = "Alterar endereço", description = "Altera o endereço do cliente pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Endereço alterado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "400", description = "Endereço não deve ser nulo ou vazio"),
		@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	public ResponseEntity<Cliente> alterarEndereco(@PathVariable Long id, @RequestBody @Valid NovoEnderecoDTO novoEnderecoDTO) {
		log.info("Iniciando alteração de endereço do cliente de ID {} no sistema...", id);
	
		try { 
			Cliente enderecoAlterado = clienteService.alterarEndereco(id, novoEnderecoDTO.getEndereco());
			
			log.info("Endereço do cliente foi alterado com sucesso.");
			return ResponseEntity.ok(enderecoAlterado);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar alterar o endereço do cliente no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}/deletar")
	@Operation(summary = "Deletar cliente", description = "Deleta o cliente pelo ID ")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	public ResponseEntity<Void> deletarClientePorId(@PathVariable Long id) {
		log.info("Deletando cliente de ID {} no sistema...", id);
		clienteService.deletarClientePorId(id);
		return ResponseEntity.noContent().build();
		
	}
}
