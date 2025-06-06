package klz7.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class NovoEstoqueDTO {

	@Schema(name = "estoque", description = "Estoque do filme em número")
	int estoque;
	
	public NovoEstoqueDTO(int estoque) {
		this.estoque = estoque;
	}
	
	public NovoEstoqueDTO() {
		
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	
	
}
