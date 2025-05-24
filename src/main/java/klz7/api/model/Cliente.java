package klz7.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente", nullable = false)
	private Long idCliente;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(name = "telefone", nullable = false)
	private String telefone;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "endereco", nullable = false)
	private String endereco;

	@Column(name = "dvds_com", nullable = false)
	private String dvdsCom;

	public Cliente(Long idCliente, String nome, LocalDate dataNascimento, String telefone, String email,
			String endereco, String dvdsCom) {
		this.idCliente = idCliente;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.dvdsCom = dvdsCom;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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

	public String getDvdsCom() {
		return dvdsCom;
	}

	public void setDvdsCom(String dvdsCom) {
		this.dvdsCom = dvdsCom;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nome=" + nome + ", dataNascimento=" + dataNascimento
				+ ", telefone=" + telefone + ", email=" + email + ", endereco=" + endereco + ", dvdsCom=" + dvdsCom
				+ "]";
	}

}
