package klz7.api.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class DevolucaoExtendidaDTO {
	
	@NotNull(message = "A data da devolução é obrigatória.")
	@Schema(name = "dataDevolucao", description = "Data de devolução extendida no formato AAAA-MM-DD")
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
