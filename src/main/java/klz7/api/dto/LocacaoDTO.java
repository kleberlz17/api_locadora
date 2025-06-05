package klz7.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LocacaoDTO {

	@Min(1)
	@Max(999999)
	private Long idLocacao;
	
	@Min(1)
	@Max(999999)
	private Long id;
	
	@Min(1)
	@Max(999999)
	private Long idFilmes;

	@NotNull(message = "A data da locação é obrigatória.")
	private LocalDate dataLocacao;

	@NotNull(message = "A data da devolução é obrigatória.")
	private LocalDate dataDevolucao;

	private boolean devolvido;

	@NotNull(message = "A quantidade é obrigatória.")
	@Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
	private int quantidade;

	public LocacaoDTO(Long idLocacao, Long id, Long idFilmes, LocalDate dataLocacao, LocalDate dataDevolucao, boolean devolvido,
			int quantidade) {
		this.idLocacao = idLocacao;
		this.id = id;
		this.idFilmes = idFilmes;
		this.dataLocacao = dataLocacao;
		this.dataDevolucao = dataDevolucao;
		this.devolvido = devolvido;
		this.quantidade = quantidade;
	}
	
	public LocacaoDTO(Long id, Long idFilmes, int quantidade, LocalDate dataDevolucao) { // Pro teste controller de redução de estoque. 
		this.id = id;
		this.idFilmes = idFilmes;
		this.quantidade = quantidade;
		this.dataDevolucao = dataDevolucao;
		
	}
	
	public LocacaoDTO() {
		
	}

	public Long getIdLocacao() {
		return idLocacao;
	}

	public void setIdLocacao(Long idLocacao) {
		this.idLocacao = idLocacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdFilmes() {
		return idFilmes;
	}

	public void setIdFilmes(Long idFilmes) {
		this.idFilmes = idFilmes;
	}

	public LocalDate getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(LocalDate dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public boolean isDevolvido() {
		return devolvido;
	}

	public void setDevolvido(boolean devolvido) {
		this.devolvido = devolvido;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	
	

	
}
