package klz7.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class NovaDataLancamentoDTO {
	
	@NotNull(message = "A data de lançamento é obrigatória.")
	private LocalDate dataLancamento;
	
	public NovaDataLancamentoDTO (LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
	public NovaDataLancamentoDTO() {
		
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	
}
