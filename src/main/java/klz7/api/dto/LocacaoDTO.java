package klz7.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LocacaoDTO {

	@Min(1)
	@Max(999999)
	private Long idLocacao;

	@NotNull(message = "A data da locação é obrigatória.")
	private LocalDate dataLocacao;

	@NotNull(message = "A data da devolução é obrigatória.")
	private LocalDate dataDevolucao;

	private boolean devolvido;

	@NotNull(message = "A quantidade é obrigatória.")
	@Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
	private int quantidade;

	public LocacaoDTO(Long idLocacao, LocalDate dataLocacao, LocalDate dataDevolucao, boolean devolvido,
			int quantidade) {
		this.idLocacao = idLocacao;
		this.dataLocacao = dataLocacao;
		this.dataDevolucao = dataDevolucao;
		this.devolvido = devolvido;
		this.quantidade = quantidade;
	}

	public Long getIdLocacao() {
		return idLocacao;
	}

	public void setIdLocacao(Long idLocacao) {
		this.idLocacao = idLocacao;
	}

	public LocalDate getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(LocalDate dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public boolean isDevolvido() {
		return devolvido;
	}

	public void setDevolvido(boolean devolvido) {
		this.devolvido = devolvido;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	
}
