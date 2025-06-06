package klz7.api.model;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Filmes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_filmes", nullable = false)
	private Long idFilme;

	@Column(name = "nome", nullable = false)
	@Schema(description = "Nome completo do filme", example = "The Batman")
	private String nome;

	@Column(name = "data_lancamento", nullable = false)
	@Schema(description = "Data de lançamento", example = "2019-03-03")
	private LocalDate dataLancamento;

	@Column(name = "diretor", nullable = false)
	@Schema(description = "Nome do diretor", example = "Matt Reeves")
	private String diretor;

	@Column(name = "genero", nullable = false)
	@Schema(description = "Gênero do filme", example = "Super-heróis")
	private String genero;

	@OneToMany(mappedBy = "filmes")
	private List<Locacao> locacoes;

	@Column(name = "estoque", nullable = false)
	@Schema(description = "Estoque do filme", example = "2")
	private int estoque;

	public Filmes(String nome, LocalDate dataLancamento, String diretor, String genero, int estoque) {
		this.nome = nome;
		this.dataLancamento = dataLancamento;
		this.diretor = diretor;
		this.genero = genero;
		this.estoque = estoque;
	}
	
	public Filmes() {
		
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

	public List<Locacao> getLocacoes() {
		return locacoes;
	}

	public void setLocacoes(List<Locacao> locacoes) {
		this.locacoes = locacoes;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	@Override
	public String toString() {
		return "Filmes [idFilme=" + idFilme + ", nome=" + nome + ", dataLancamento=" + dataLancamento + ", diretor="
				+ diretor + ", genero=" + genero + ", locacoes=" + locacoes + ", estoque=" + estoque + "]";
	}

}
