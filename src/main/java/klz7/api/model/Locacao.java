package klz7.api.model;

import java.time.LocalDate;

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
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_filme", nullable = false)
	private Filmes filmes;

	@Column(name = "data_locacao", nullable = false)
	private LocalDate dataLocacao;

	@Column(name = "data_devolucao", nullable = false)
	private LocalDate dataDevolucao;

	@Column(name = "devolvido", nullable = false)
	private boolean devolvido;

	@Column(name = "quantidade", nullable = false)
	private int quantidade;
}
