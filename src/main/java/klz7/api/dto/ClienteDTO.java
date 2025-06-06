package klz7.api.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(name = "id", description = "Identificador único do cliente")
	private Long id;

	@NotBlank(message = "O nome do cliente não deve estar em branco.")
	@Size(min = 3, max = 100, message = "Fora do tamanho padrão permitido.")
	@Schema(name = "nome", description = "Nome completo do cliente")
	private String nome;

	@NotNull(message = "A data de nascimento é obrigatória.")
	@Schema(name = "dataNascimento", description = "Data de nascimento no formato AAAA-MM-DD")
	private LocalDate dataNascimento;
	
	@Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 números.")
	@Schema(name = "cpf", description = "CPF do cliente, apenas números")
	private String cpf;

	@Pattern(regexp = "\\d{11}", message = "Telefone deve conter 11 números.")
	@Schema(name = "telefone", description = "Número de telefone com DDD, apenas números")
	private String telefone;

	@NotBlank(message = "O email não deve estar em branco.")
	@Email(message = "Email inválido.")
	@Schema(name = "email", description = "Endereço de e-mail válido")
	private String email;

	@NotBlank(message = "O endereço é obrigatório.")
	@Size(min = 5, max = 100, message = "Fora do tamanho padrão permitido.")
	@Schema(name = "endereco", description = "Endereço residencial do cliente")
	private String endereco;

	public ClienteDTO(Long id, String nome, LocalDate dataNascimento, String cpf, String telefone, String email,
			String endereco) {
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}
	
	public ClienteDTO() {
		
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

	
	
}
