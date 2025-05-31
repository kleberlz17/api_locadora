package klz7.api.controller;

import java.net.URI;
import java.time.LocalDate;
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
import klz7.api.dto.FilmesDTO;
import klz7.api.dto.NovaDataLancamentoDTO;
import klz7.api.dto.NovoEstoqueDTO;
import klz7.api.dto.NovoNomeFilmeDTO;
import klz7.api.mapper.FilmesConverter;
import klz7.api.model.Filmes;
import klz7.api.service.FilmesService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/filmes")
@Slf4j
public class FilmesController {
	
	private final FilmesService filmesService;
	private final FilmesConverter filmesConverter;
	
	public FilmesController(FilmesService filmesService, FilmesConverter filmesConverter) {
		this.filmesService = filmesService;
		this.filmesConverter = filmesConverter;
	}
	
	@PostMapping
	public ResponseEntity<Object> salvar (@RequestBody @Valid FilmesDTO filmesDTO) {
		log.info("Iniciando salvamento de novo filme no sistema...");
		Filmes filmes = filmesConverter.dtoParaEntidade(filmesDTO);
		Filmes filmesSalvo = filmesService.salvar(filmes);
		
		URI uri = URI.create("/filmes/" + filmesSalvo.getIdFilme());
		
		log.info("Filme salvo com sucesso. ID gerado: {}", filmesSalvo.getIdFilme());
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{idFilme}")
	public ResponseEntity<Filmes> buscarPorId (@PathVariable Long idFilme) {
		log.info("Buscando filme pelo ID no sistema...");
		Optional<Filmes> filme = filmesService.buscarPorId(idFilme);
		
		if (filme.isPresent()) {
			log.info("Filme encontrado pelo ID com sucesso.");
			return ResponseEntity.ok(filme.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Filmes>> buscarPorNome (@PathVariable String nome) {
		log.info("Buscando o(s) filme(s) pelo nome no sistema...");
		List<Filmes> filmesNome = filmesService.buscarPorNome(nome);
		
		if(filmesNome.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			log.info("Filme(s) encontrado(s) pelo nome com sucesso.");
			return ResponseEntity.ok(filmesNome);
		}
	}
	
	@GetMapping("/dataLancamento/{dataLancamento}")
	public ResponseEntity<List<Filmes>> buscarPorDataLancamento (@PathVariable LocalDate dataLancamento) {
		log.info("Buscando o(s) filme(s) pela data de lançamento no sistema...");
		List<Filmes> filmesDataLancamento = filmesService.buscarPorDataLancamento(dataLancamento);
		
		if(filmesDataLancamento.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			log.info("Filme(s) encontrado(s) pela data de lançamento com sucesso.");
			return ResponseEntity.ok(filmesDataLancamento);
		}
	}
	
	@GetMapping("/diretor/{diretor}")
	public ResponseEntity<List<Filmes>> buscarPorDiretor (@PathVariable String diretor) {
		log.info("Buscando o(s) filme(s) pelo nome do diretor no sistema...");
		List<Filmes> filmesDiretor = filmesService.buscarPorDiretor(diretor);
		
		if(filmesDiretor.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			log.info("Filme(s) encontrado(s) pelo nome do diretor com sucesso.");
			return ResponseEntity.ok(filmesDiretor);
		}
	}
	
	@GetMapping("/genero/{genero}")
	public ResponseEntity<List<Filmes>> buscarPorGenero (@PathVariable String genero) {
		log.info("Buscando o(s) filme(s) pelo genero no sistema...");
		List<Filmes> filmesGenero = filmesService.buscarPorGenero(genero);
		
		if(filmesGenero.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			log.info("Filme(s) encontrado(s) pelo genero com sucesso.");
			return ResponseEntity.ok(filmesGenero);
		}
	}
	
	@GetMapping("/estoque/{estoque}")
	public ResponseEntity<List<Filmes>> buscarPorEstoque (@PathVariable int estoque) {
		log.info("Buscando o(s) filme(s) pelas unidades no estoque no sistema...");
		List<Filmes> filmesEstoque = filmesService.buscarPorEstoque(estoque);
		
		if(filmesEstoque.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			log.info("Filme(s) encontrado(s) pelas unidades no estoque com sucesso.");
			return ResponseEntity.ok(filmesEstoque);
		}
	}
	
	@PutMapping("{idFilme}/novoEstoque")
	public ResponseEntity<Filmes> alterarEstoque (@PathVariable Long idFilme, @RequestBody @Valid NovoEstoqueDTO novoEstoqueDTO) {
		log.info("Iniciando alteração do estoque do filme de ID {} no sistema...", idFilme);
		
		try {
			Filmes estoqueAlterado = filmesService.alterarEstoque(idFilme, novoEstoqueDTO.getEstoque());
			
			log.info("Estoque do filme foi alterado com sucesso.");
			return ResponseEntity.ok(estoqueAlterado);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar alterar o estoque do filme no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}	
	}
	
	@PutMapping("{idFilme}/novaDataLancamento")
	public ResponseEntity<Filmes> alterarDataLancamento(@PathVariable Long idFilme, @RequestBody @Valid NovaDataLancamentoDTO novaDataLancamentoDTO) {
		log.info("Iniciando alteração da data de lançamento do filme de ID {} no sistema...", idFilme);
		
		try {
			Filmes dataLancamentoAlterada = filmesService.alterarDataLancamento(idFilme, novaDataLancamentoDTO.getDataLancamento());
			
			log.info("Data de lançamento do filme foi alterada com sucesso.");
			return ResponseEntity.ok(dataLancamentoAlterada);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar alterar a data de lançamento do filme no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("{idFilme}/novoNomeFilme")
	public ResponseEntity<Filmes> alterarNomeFilme(@PathVariable Long idFilme, @RequestBody @Valid NovoNomeFilmeDTO novoNomeFilmeDTO) {
		log.info("Iniciando alteração do nome do filme de ID {} no sistema...", idFilme);
		
		try {
			Filmes nomeFilmeAlterado = filmesService.alterarNomeFilme(idFilme, novoNomeFilmeDTO.getNome());
			
			log.info("Nome do filme foi alterado com sucesso.");
			return ResponseEntity.ok(nomeFilmeAlterado);
			
		} catch (RuntimeException ex) {
			log.error("Erro ao tentar alterar o nome do filme no sistema: {}", ex.getMessage());
			return ResponseEntity.notFound().build();
		}
		
	}
	
	

}
