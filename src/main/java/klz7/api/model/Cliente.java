package klz7.api.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente", nullable = false)
	private Long id;

	@Column(name = "nome", nullable = false)
	@Schema(description = "Nome completo do cliente", example = "Kleber Luiz")
	private String nome;

	@Column(name = "data_nascimento", nullable = false)
	@Schema(description = "Data de nascimento", example = "1999-10-10")
	private LocalDate dataNascimento;

	@Column(name = "cpf", nullable = false)
	@Schema(description = "CPF do cliente", example = "123.456.789-00")
	private String cpf;
	
	@Column(name = "telefone", nullable = false)
	@Schema(description = "Telefone para contato", example = "(21) 99999-7777")
	private String telefone;

	@Column(name = "email", nullable = false)
	@Schema(description = "Email do cliente", example = "kleber@email.com")
	private String email;

	@Column(name = "endereco", nullable = false)
	@Schema(description = "Endereço completo do cliente", example = "Rua J, 777, Centro - RJ")
	private String endereco;

	@OneToMany(mappedBy = "cliente")
	@JsonIgnore//Evitar erro StackOverFlow
	private List<Locacao> locacoes;

	public Cliente(String nome, LocalDate dataNascimento, String cpf, String telefone, String email,
			String endereco) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}
	
	public Cliente() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Locacao> getLocacoes() {
		return locacoes;
	}

	public void setLocacoes(List<Locacao> locacoes) {
		this.locacoes = locacoes;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", cpf=" + cpf
				+ ", telefone=" + telefone + ", email=" + email + ", endereco=" + endereco + ", locacoes=" + locacoes
				+ "]";
	}

	

}