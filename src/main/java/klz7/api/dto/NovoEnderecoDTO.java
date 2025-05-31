package klz7.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NovoEnderecoDTO {

	@NotBlank(message = "O endereço é obrigatório.")
	@Size(min = 5, max = 100, message = "Fora do tamanho padrão permitido.")
	String endereco;
	
	public NovoEnderecoDTO(String endereco) {
		this.endereco = endereco;
	}
	
	public NovoEnderecoDTO() {
		
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	
}
