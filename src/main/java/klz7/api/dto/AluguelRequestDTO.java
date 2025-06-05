package klz7.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AluguelRequestDTO {
	
	@NotNull(message = "O ID do cliente é obrigatório.")
	private Long id;
	
	@NotNull(message = "O ID do filme é obrigatório.")
	private Long idFilmes;
	
	@NotNull(message = "A quantidade é obrigatória.")
	@Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
	private int quantidade;
	
	@NotNull(message = "A data de devolução é obrigatória.")
	@FutureOrPresent(message = "A data de devolução deve ser hoje ou no futuro.")
	private LocalDate dataDevolucao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdFilmes() {
		return idFilmes;
	}

	public void setIdFilmes(Long idFilmes) {
		this.idFilmes = idFilmes;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	

}
