package klz7.api.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class NovaDataLancamentoDTO {
	
	@NotNull(message = "A data de lançamento é obrigatória.")
	@Schema(name = "dataLancamento", description = "Data de lançamento no formato AAAA-MM-DD")
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
