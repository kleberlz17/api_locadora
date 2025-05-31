package klz7.api.dto;

public class NovoEstoqueDTO {

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
