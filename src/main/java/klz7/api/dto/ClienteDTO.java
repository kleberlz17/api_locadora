package klz7.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

	@Min(1)
	@Max(999999)
	private Long idCliente;

	@NotBlank(message = "O nome do cliente não deve estar em branco.")
	@Size(min = 3, max = 100, message = "Fora do tamanho padrão permitido.")
	private String nome;

	@NotNull(message = "A data de nascimento é obrigatória.")
	private LocalDate dataNascimento;

	@Pattern(regexp = "\\d{11}", message = "Telefone deve conter 11 números.")
	private String telefone;

	@NotBlank(message = "O email não deve estar em branco.")
	@Email(message = "Email inválido.")
	private String email;

	@NotBlank(message = "O endereço é obrigatório.")
	@Size(min = 5, max = 100, message = "Fora do tamanho padrão permitido.")
	private String endereco;

	@NotBlank(message = "A quantidade de DVDs com o cliente não deve estar em branco.")
	private String dvdsCom;

	public ClienteDTO(Long idCliente, String nome, LocalDate dataNascimento, String telefone, String email,
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

}
