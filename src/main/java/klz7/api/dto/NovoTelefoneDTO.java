package klz7.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class NovoTelefoneDTO {
	
	@NotBlank(message = "O campo de telefone é obrigatório.")
	@Pattern(regexp = "\\d{11}", message = "Telefone deve conter 11 números.")
	@Schema(name = "telefone", description = "Novo número de telefone com DDD, apenas números")
	private String telefone;
	
	public NovoTelefoneDTO(String telefone) {
		this.telefone = telefone;
	}
	
	public NovoTelefoneDTO() {
		
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	

}
