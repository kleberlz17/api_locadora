package klz7.api.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import klz7.api.dto.ClienteDTO;
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

}
