package klz7.api.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import klz7.api.dto.AluguelRequestDTO;
import klz7.api.dto.DevolucaoExtendidaDTO;
import klz7.api.dto.LocacaoDTO;
import klz7.api.mapper.LocacaoConverter;
import klz7.api.model.Locacao;
import klz7.api.service.LocacaoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/locacao")
@Slf4j
@Tag(name = "Locações")
public class LocacaoController {
	
	private final LocacaoService locacaoService;
	private final LocacaoConverter locacaoConverter;
	
	public LocacaoController(LocacaoService locacaoService, LocacaoConverter locacaoConverter) {
		this.locacaoService = locacaoService;
		this.locacaoConverter = locacaoConverter;
	}
	
	@PostMapping
	@Operation(summary = "Salvar", description = "Cadastrar nova locação")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Cadastrado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Locacao.class))),
		@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
		@ApiResponse(responseCode = "404", description = "Filme não encontrado"),
		@ApiResponse(responseCode = "400", description = "Data de locação não deve estar em um ponto futuro"),
		@ApiResponse(responseCode = "400", description = "Data de devolução não deve estar em um ponto passado"),
		@ApiResponse(responseCode = "400", description = "Quantidade não deve ser negativa")
	})
	public ResponseEntity<Object> salvar (@RequestBody @Valid LocacaoDTO locacaoDTO) {
		log.info("Iniciando salvamento de nova locação no sistema...");
		
		Locacao locacaoSalva = locacaoService.salvar(locacaoDTO);
		
		URI uri = URI.create("/locacao/" + locacaoSalva.getIdLocacao());
		
		log.info("Locação salva com sucesso. ID gerado: {}", locacaoSalva.getIdLocacao());
		return ResponseEntity.created(uri).body(locacaoSalva); //retornando o body agora.(json)
	}
	
	@GetMapping("/{id}/locacoes")
	@Operation(summary = "Buscar locações por cliente", description = "Retorna locações do cliente pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Locação(ões) encontrada(s) com sucesso",
				content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Locacao.class)))),
		@ApiResponse(responseCode = "404", description = "Locação(ões) não encontrada(s)")
	})
	public ResponseEntity<List<Locacao>> buscarLocacaoPorCliente (@PathVariable Long id) {
		log.info("Buscando locações de cliente pelo ID {} no sistema...", id);
		
		List<Locacao> locacao = locacaoService.buscarLocacoesPorCliente(id);
		
		if (locacao.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			log.info("Locação(oes) encontrada(s) com sucesso.");
			return ResponseEntity.ok(locacao);
		}
	}
	
	@PutMapping("/{idLocacao}/renovarLocacao")
	@Operation(summary = "Renovar locação", description = "Renova a lovação pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Renovação concluída com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Locacao.class))),
		@ApiResponse(responseCode = "400", description = "Devolução extendida não deve ser nula"),
		@ApiResponse(responseCode = "400", description = "Devolução extendida não deve estar em um ponto passado")
	})
	public ResponseEntity<Locacao> renovarLocacao (@PathVariable Long idLocacao, @RequestBody @Valid DevolucaoExtendidaDTO devolucaoExtendidaDTO) {
		log.info("Iniciando renovação da locação de ID {} no sistema...", idLocacao);
		
		try {
			Locacao locacaoExtendida = locacaoService.renovarLocacao(idLocacao, devolucaoExtendidaDTO.getDataDevolucao());
			
			log.info("Renovação da locação foi concluida com sucesso.");
			return ResponseEntity.ok(locacaoExtendida);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar renovar a locação no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{idFilme}/historico")
	@Operation(summary = "Buscar historico de filme", description = "Retorna o histórico de locação de um filme pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Histórico da locação encontrado com sucesso",
				content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Locacao.class)))),
		@ApiResponse(responseCode = "404", description = "Histórico da locação do filme  não encontrado")
	})
	public ResponseEntity<List<Locacao>> buscarHistoricoFilme (@PathVariable Long idFilme) {
		log.info("Buscando o histórico de locação do filme de ID {} no sistema...", idFilme);
		
		List<Locacao> locacaoHistorico = locacaoService.buscarHistoricoFilme(idFilme);
		
		if(locacaoHistorico.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			log.info("Historico  do filme encontrado com sucesso.");
			return ResponseEntity.ok(locacaoHistorico);
		}
	}
	
	@PostMapping("/{idLocacao}/multa")
	@Operation(summary = "Calcular multa", description = "Calcula a multa de atraso da locação pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Multa calculada com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Locacao.class))),
		@ApiResponse(responseCode = "404", description = "Locação não encontrada")
	})
	public ResponseEntity<BigDecimal> calcularMulta (@PathVariable Long idLocacao) {
		log.info("Iniciando calculo da multa por dias de atraso da locação de ID {} no sistema...", idLocacao);
		
		BigDecimal multarLocacao = locacaoService.calcularMulta(idLocacao);
		
		log.info("Multa calculada com sucesso para ID {}: R${}", idLocacao, multarLocacao);
		return ResponseEntity.ok(multarLocacao);
	}
	
	@PostMapping("/alugar")
	@Operation(summary = "Alugar filme", description = "Metodo para alugar o filme")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Filme alugado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Locacao.class))),
		@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
		@ApiResponse(responseCode = "404", description = "Filme não encontrado"),
		@ApiResponse(responseCode = "400", description = "Quantidade não deve ser negativa ou nula"),
		@ApiResponse(responseCode = "409", description = "Estoque sem unidades suficientes")
	})
	public ResponseEntity<LocacaoDTO> alugarFilme (@RequestBody @Valid AluguelRequestDTO aluguelDTO) {
		log.info("Iniciando locação de filme de ID {} pelo cliente de ID {} no sistema...", aluguelDTO.getIdFilmes(), aluguelDTO.getId());
		
		Locacao alugando = locacaoService.alugarFilme(
				aluguelDTO.getId(),
				aluguelDTO.getIdFilmes(),
				aluguelDTO.getQuantidade(),
				aluguelDTO.getDataDevolucao());
		
		LocacaoDTO respostaDTO = locacaoConverter.entidadeParaDto(alugando);
		
		log.info("Locação concluída com sucesso.");
		return ResponseEntity.ok(respostaDTO);
	}
	@DeleteMapping("/{idLocacao}/deletar")
	@Operation(summary = "Deletar locação", description = "Deletar locação pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Locação deletada com sucesso."),
		@ApiResponse(responseCode = "404", description = "Locação não encontrada")
	})
	public ResponseEntity<Void> deletarLocacaoPorId (@PathVariable Long idLocacao) {
		log.info("Deletando locação de ID {} no sistema...", idLocacao);
		locacaoService.deletarLocacaoPorId(idLocacao);
		
		log.info("Locação foi deletada com sucesso.");
		return ResponseEntity.noContent().build();
	}
	
	

}
