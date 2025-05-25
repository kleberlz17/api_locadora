package klz7.api.mapper;

import org.springframework.stereotype.Component;

import klz7.api.dto.LocacaoDTO;
import klz7.api.model.Locacao;

@Component
public class LocacaoConverter {
	
	public Locacao dtoParaEntidade(LocacaoDTO dto) {
		Locacao locacao = new Locacao();
		locacao.setIdLocacao(dto.getIdLocacao());
		locacao.setDataLocacao(dto.getDataLocacao());
		locacao.setDataDevolucao(dto.getDataDevolucao());
		locacao.setDevolvido(dto.isDevolvido());
		locacao.setQuantidade(dto.getQuantidade());
		return locacao;
	}
	
	public LocacaoDTO entidadeParaDto(Locacao entidade) {
		return new LocacaoDTO(
				entidade.getIdLocacao(),
				entidade.getDataLocacao(),
				entidade.getDataDevolucao(),
				entidade.isDevolvido(),
				entidade.getQuantidade());
	}

}
