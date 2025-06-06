package klz7.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NovoEmailDTO {
	
	@NotBlank(message = "O campo de email é obrigatório.")
	@Email(message = "O email digitado é inválido.")
	@Schema(name = "email", description = "Novo endereço de e-mail válido")
	private String email;
	
	public NovoEmailDTO(String email) {
		this.email = email;
	}
	
	public NovoEmailDTO() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
