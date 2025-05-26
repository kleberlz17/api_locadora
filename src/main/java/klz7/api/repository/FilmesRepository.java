package klz7.api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import klz7.api.model.Filmes;

public interface FilmesRepository extends JpaRepository<Filmes, Long> {
	
	Optional<Filmes> findById(Long idFilme);
	
	List<Filmes> findByNomeContainingIgnoreCase(String nome);
	
	List<Filmes> findByDataLancamento(LocalDate dataLancamento);
	
	List<Filmes> findByDiretorContainingIgnoreCase(String diretor);
	
	List<Filmes> findByGeneroContainingIgnoreCase(String genero);
	
	List<Filmes> findByEstoque(int estoque);
}
