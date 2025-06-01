package klz7.api.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import klz7.api.dto.DevolucaoExtendidaDTO;
import klz7.api.dto.LocacaoDTO;
import klz7.api.mapper.LocacaoConverter;
import klz7.api.model.Locacao;
import klz7.api.service.LocacaoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/locacao")
@Slf4j
public class LocacaoController {
	
	private final LocacaoService locacaoService;
	private final LocacaoConverter locacaoConverter;
	
	public LocacaoController(LocacaoService locacaoService, LocacaoConverter locacaoConverter) {
		this.locacaoService = locacaoService;
		this.locacaoConverter = locacaoConverter;
	}
	
	@PostMapping
	public ResponseEntity<Object> salvar (@RequestBody @Valid LocacaoDTO locacaoDTO) {
		log.info("Iniciando salvamento de nova locação no sistema...");
		
		Locacao locacaoSalva = locacaoService.salvar(locacaoDTO);
		
		URI uri = URI.create("/locacao/" + locacaoSalva.getIdLocacao());
		
		log.info("Locação salva com sucesso. ID gerado: {}", locacaoSalva.getIdLocacao());
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{idCliente}/locacoes")
	public ResponseEntity<List<Locacao>> buscarLocacaoPorCliente (@PathVariable Long idCliente) {
		log.info("Buscando locações de cliente pelo ID {} no sistema...", idCliente);
		
		List<Locacao> locacao = locacaoService.buscarLocacoesPorCliente(idCliente);
		
		if (locacao.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			log.info("Locação(oes) encontrada(s) com sucesso.");
			return ResponseEntity.ok(locacao);
		}
	}
	
	@PutMapping("/{idLocacao}/renovarLocacao")
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
	public ResponseEntity<BigDecimal> calcularMulta (@PathVariable Long idLocacao) {
		log.info("Iniciando calculo da multa por dias de atraso da locação de ID {} no sistema...", idLocacao);
		
		BigDecimal multarLocacao = locacaoService.calcularMulta(idLocacao);
		
		log.info("Multa calculada com sucesso para ID {}: R${}", idLocacao, multarLocacao);
		return ResponseEntity.ok(multarLocacao);
	}
	
	@PostMapping("/alugar")
	public ResponseEntity<LocacaoDTO> alugarFilme (@RequestBody @Valid LocacaoDTO locacaoDTO) {
		log.info("Iniciando locação de filme de ID {} pelo cliente de ID {} no sistema...", locacaoDTO.getIdFilmes(), locacaoDTO.getIdCliente());
		
		Locacao alugando = locacaoService.alugarFilme(
				locacaoDTO.getIdCliente(),
				locacaoDTO.getIdFilmes(),
				locacaoDTO.getQuantidade(),
				locacaoDTO.getDataDevolucao());
		
		LocacaoDTO respostaDTO = locacaoConverter.entidadeParaDto(alugando);
		
		log.info("Locação concluída com sucesso.");
		return ResponseEntity.ok(respostaDTO);
	}
	
	

}
