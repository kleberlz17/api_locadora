package klz7.api.mapper;

import org.springframework.stereotype.Component;

import klz7.api.dto.FilmesDTO;
import klz7.api.model.Filmes;

@Component
public class FilmesConverter {

	public Filmes dtoParaEntidade(FilmesDTO dto) {
		Filmes filmes = new Filmes();
		filmes.setIdFilme(dto.getIdFilme());
		filmes.setNome(dto.getNome());
		filmes.setDataLancamento(dto.getDataLancamento());
		filmes.setDiretor(dto.getDiretor());
		filmes.setGenero(dto.getGenero());
		filmes.setEstoque(dto.getEstoque());;
		return filmes;
	}

	public FilmesDTO entidadeParaDto(Filmes entidade) {
		return new FilmesDTO(
				entidade.getIdFilme(),
				entidade.getNome(),
				entidade.getDataLancamento(),
				entidade.getDiretor(),
				entidade.getGenero(),
				entidade.getEstoque());
	}

}
