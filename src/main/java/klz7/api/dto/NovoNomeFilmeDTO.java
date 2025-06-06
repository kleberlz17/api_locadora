package klz7.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NovoNomeFilmeDTO {
	
	@NotBlank(message = "O nome do filme não deve estar em branco.")
	@Size(min = 1, max = 100, message = "Nome fora do tamanho padrão permitido.")
	@Schema(name = "nome", description = "Nome completo do filme")
	private String nome;
	
	public NovoNomeFilmeDTO(String nome) {
		this.nome = nome;
	}
	
	public NovoNomeFilmeDTO() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
