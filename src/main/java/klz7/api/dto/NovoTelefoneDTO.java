package klz7.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class NovoTelefoneDTO {
	
	@NotBlank(message = "O campo de telefone é obrigatório.")
	@Pattern(regexp = "\\d{11}", message = "Telefone deve conter 11 números.")
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
