package klz7.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Filmes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_filme", nullable = false)
	private Long idFilme;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "data_lancamento", nullable = false)
	private LocalDate dataLancamento;

	@Column(name = "diretor", nullable = false)
	private String diretor;

	@Column(name = "genero", nullable = false)
	private String genero;

	@Column(name = "unidades", nullable = false)
	private String unidades;

	public Filmes(Long idFilme, String nome, LocalDate dataLancamento, String diretor, String genero, String unidades) {
		this.idFilme = idFilme;
		this.nome = nome;
		this.dataLancamento = dataLancamento;
		this.diretor = diretor;
		this.genero = genero;
		this.unidades = unidades;
	}

	public Long getIdFilme() {
		return idFilme;
	}

	public void setIdFilme(Long idFilme) {
		this.idFilme = idFilme;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getUnidades() {
		return unidades;
	}

	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}

	@Override
	public String toString() {
		return "Filmes [idFilme=" + idFilme + ", nome=" + nome + ", dataLancamento=" + dataLancamento + ", diretor="
				+ diretor + ", genero=" + genero + ", unidades=" + unidades + "]";
	}

}
