package klz7.api.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor//Optei por builder,data, args aqui pra evitar erros.
public class Locacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_locacao", nullable = false)
	private Long idLocacao;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	@Schema(description = "Cliente", example = "cliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_filme", nullable = false)
	@Schema(description = "Filmes", example = "filmes")
	private Filmes filmes;

	@Column(name = "data_locacao", nullable = false)
	@Schema(description = "Data da locação", example = "2025-06-06")
	private LocalDate dataLocacao;

	@Column(name = "data_devolucao", nullable = false)
	@Schema(description = "Data da devolução", example = "2025-06-12")
	private LocalDate dataDevolucao;

	@Column(name = "devolvido", nullable = false)
	@Schema(description = "Devolvido", example = "false")
	private boolean devolvido;

	@Column(name = "quantidade", nullable = false)
	@Schema(description = "Quantidade", example = "1")
	private int quantidade;
	
	public Locacao(Cliente cliente, Filmes filmes, LocalDate dataLocacao,
			LocalDate dataDevolucao, boolean devolvido, int quantidade) {
		this.cliente = cliente;
		this.filmes = filmes;
		this.dataLocacao = dataLocacao;
		this.dataDevolucao = dataDevolucao;
		this.devolvido = devolvido;
		this.quantidade = quantidade;
	}
}


