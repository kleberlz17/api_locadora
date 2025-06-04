package klz7.api.mapper;

import org.springframework.stereotype.Component;

import klz7.api.dto.ClienteDTO;
import klz7.api.model.Cliente;

@Component
public class ClienteConverter {
	
	public Cliente dtoParaEntidade(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setId(dto.getId());
		cliente.setNome(dto.getNome());
		cliente.setDataNascimento(dto.getDataNascimento());
		cliente.setCpf(dto.getCpf());
		cliente.setTelefone(dto.getTelefone());
		cliente.setEmail(dto.getEmail());
		cliente.setEndereco(dto.getEndereco());
		return cliente;
	}
	
	public ClienteDTO entidadeParaDto(Cliente entidade) {
		return new ClienteDTO(
				entidade.getId(),
				entidade.getNome(),
				entidade.getDataNascimento(),
				entidade.getCpf(),
				entidade.getTelefone(),
				entidade.getEmail(),
				entidade.getEndereco()
				);
	}

}
