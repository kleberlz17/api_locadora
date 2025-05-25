package klz7.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Locacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_locacao", nullable = false)
	private Long idLocacao;

	@ManyToOne
	private Cliente cliente;

	@ManyToOne
	private Filmes filmes;

	@Column(name = "data_locacao", nullable = false)
	private LocalDate dataLocacao;

	@Column(name = "data_devolucao", nullable = false)
	private LocalDate dataDevolucao;

	@Column(name = "devolvido", nullable = false)
	private boolean devolvido;

	@Column(name = "quantidade", nullable = false)
	private int quantidade;

	public Locacao(Long idLocacao, Cliente cliente, Filmes filmes, LocalDate dataDevolucao, boolean devolvido,
			int quantidade) {
		this.idLocacao = idLocacao;
		this.cliente = cliente;
		this.filmes = filmes;
		this.dataDevolucao = dataDevolucao;
		this.devolvido = devolvido;
		this.quantidade = quantidade;
	}
	
	public Locacao() {
		
	}

	public Long getIdLocacao() {
		return idLocacao;
	}

	public void setIdLocacao(Long idLocacao) {
		this.idLocacao = idLocacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Filmes getFilmes() {
		return filmes;
	}

	public void setFilmes(Filmes filmes) {
		this.filmes = filmes;
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

	@Override
	public String toString() {
		return "Locacao [idLocacao=" + idLocacao + ", cliente=" + cliente + ", filmes=" + filmes + ", dataLocacao="
				+ dataLocacao + ", dataDevolucao=" + dataDevolucao + ", devolvido=" + devolvido + ", quantidade="
				+ quantidade + "]";
	}

}
