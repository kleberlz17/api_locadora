package klz7.api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import klz7.api.model.Cliente;
import klz7.api.model.Filmes;
import klz7.api.model.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

	Optional<Locacao> findById(Long idLocacao);
	
	List<Locacao> findByClienteId(Long idCliente);
	
	List<Locacao> findByCliente(Cliente cliente);
	
	List<Locacao> findByFilmes(Filmes filmes);
	
	List<Locacao> findByFilmesIdFilme(Long idFilme);
	
	List<Locacao> findByDataLocacao(LocalDate dataLocacao);
	
	List<Locacao> findByDataDevolucao(LocalDate dataDevolucao);
}
