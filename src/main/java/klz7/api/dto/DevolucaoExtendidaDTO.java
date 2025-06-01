package klz7.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class DevolucaoExtendidaDTO {
	
	@NotNull(message = "A data da devolução é obrigatória.")
	private LocalDate dataDevolucao;
	
	public DevolucaoExtendidaDTO(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
	public DevolucaoExtendidaDTO() {
		
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	
}
