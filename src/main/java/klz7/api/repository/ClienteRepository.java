package klz7.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import klz7.api.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	List<Cliente> findByNomeContainingIgnoreCase(String nome);
	
	Optional<Cliente> findByTelefone(String telefone);
	
	Optional<Cliente> findById(Long id);
	
	Optional<Cliente> findByCpfContainingIgnoreCase(String cpf);
	
	Optional<Cliente> findByEmail(String email);
}
