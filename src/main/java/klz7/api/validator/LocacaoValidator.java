package klz7.api.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import klz7.api.exception.DataDevolucaoInvalidaException;
import klz7.api.exception.DataLocacaoInvalidaException;
import klz7.api.exception.QuantidadeNegativaNulaException;
import klz7.api.model.Locacao;

@Component
public class LocacaoValidator {
	
	public void validarDataLocacao(LocalDate dataLocacao) {
		if (dataLocacao.isAfter(LocalDate.now())) {
			throw new DataLocacaoInvalidaException("A data de locação digitada está num ponto futuro.");
		}
	}
	
	public void validarDataDevolucao(LocalDate dataDevolucao) {
		if(dataDevolucao.isBefore(LocalDate.now())) {
			throw new DataDevolucaoInvalidaException("A data de devolução digitada está num ponto passado.");
		}
		
	}
	
	public void validarQuantidade(int quantidade) {
		if(quantidade < 0) {
			throw new QuantidadeNegativaNulaException("A quantidade não deve ser negativa, somente de 0 pra cima é permitido.");
		}
	}
	
	public void validarTudo(Locacao locacao) { //Pra salvar a locação.
		validarDataLocacao(locacao.getDataLocacao());
		validarDataDevolucao(locacao.getDataDevolucao());
		validarQuantidade(locacao.getQuantidade());
	}
}

