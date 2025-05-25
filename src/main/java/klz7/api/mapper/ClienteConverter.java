package klz7.api.mapper;

import org.springframework.stereotype.Component;

import klz7.api.dto.ClienteDTO;
import klz7.api.model.Cliente;

@Component
public class ClienteConverter {
	
	public Cliente dtoParaEntidade(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(dto.getIdCliente());
		cliente.setNome(dto.getNome());
		cliente.setDataNascimento(dto.getDataNascimento());
		cliente.setTelefone(dto.getTelefone());
		cliente.setEmail(dto.getEmail());
		cliente.setEndereco(dto.getEndereco());
		return cliente;
	}
	
	public ClienteDTO entidadeParaDto(Cliente entidade) {
		return new ClienteDTO(
				entidade.getIdCliente(),
				entidade.getNome(),
				entidade.getDataNascimento(),
				entidade.getTelefone(),
				entidade.getEmail(),
				entidade.getEndereco()
				);
	}

}
