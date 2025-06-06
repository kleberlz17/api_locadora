package klz7.api.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FilmesDTO {

	@Min(1)
	@Max(999999)
	private Long idFilme;

	@NotBlank(message = "O nome do filme não deve estar em branco.")
	@Size(min = 1, max = 100, message = "Nome fora do tamanho padrão permitido.")
	@Schema(name = "nome", description = "Nome completo do filme")
	private String nome;

	@NotNull(message = "A data de lançamento é obrigatória.") // Pra facilitar busca por data de lançamento(ex = filmes
																// de 2020)
	@Schema(name = "dataLancamento", description = "Data de lançamento no formato AAAA-MM-DD")
	private LocalDate dataLancamento;

	@NotBlank(message = "O nome do diretor não deve estar em branco.") // Pra facilitar busca por nome do diretor.
	@Size(min = 3, max = 100, message = "Nome fora do tamanho padrão permitido.")
	@Schema(name = "diretor", description = "Nome do diretor")
	private String diretor;

	@NotBlank(message = "O genero do filme não deve estar em branco.") // Pra facilitar busca por genero.
	@Size(min = 3, max = 50, message = "Genero fora do tamanho padrão permitido.")
	@Schema(name = "genero", description = "Gênero do filme")
	private String genero;

	@Schema(name = "estoque", description = "Estoque do filme em número")
	private int estoque;

	public FilmesDTO(Long idFilme, String nome, LocalDate dataLancamento, String diretor, String genero, int estoque) {
		this.idFilme = idFilme;
		this.nome = nome;
		this.dataLancamento = dataLancamento;
		this.diretor = diretor;
		this.genero = genero;
		this.estoque = estoque;
	}
	
	public FilmesDTO() {
		
	}

	public Long getIdFilme() {
		return idFilme;
	}

	public void setIdFilme(Long idFilme) {
		this.idFilme = idFilme;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

}
