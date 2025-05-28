package klz7.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CpfDuplicadoException.class)
	public ResponseEntity<ErroResposta> handleCpfDuplicado(CpfDuplicadoException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.CONFLICT.value(), "CPF já cadastrado.");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(EmailDuplicadoException.class)
	public ResponseEntity<ErroResposta> handleEmailDuplicado(EmailDuplicadoException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.CONFLICT.value(), "Email já cadastrado.");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(TelefoneDuplicadoException.class)
	public ResponseEntity<ErroResposta> handleTelefoneDuplicado(TelefoneDuplicadoException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.CONFLICT.value(), "Telefone já cadastrado.");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(EstoqueNegativoException.class)
	public ResponseEntity<ErroResposta> handleEstoqueNegativo(EstoqueNegativoException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Estoque não pode ser negativo.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(NomeFilmeDuplicadoException.class)
	public ResponseEntity<ErroResposta> handleNomeFilmeDuplicado(NomeFilmeDuplicadoException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.CONFLICT.value(), "Filme já cadastrado.");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(DataLocacaoInvalidaException.class)
	public ResponseEntity<ErroResposta> handleDataLocacaoInvalida(DataLocacaoInvalidaException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Data de locação inválida.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(DataDevolucaoInvalidaException.class)
	public ResponseEntity<ErroResposta> handleDataDevolucaoInvalida(DataDevolucaoInvalidaException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Data de devolução inválida.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);	
	}
	
	@ExceptionHandler(DataLancamentoInvalidaException.class)
	public ResponseEntity<ErroResposta> handleDataLancamentoInvalida(DataLancamentoInvalidaException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Data de lançamento inválida.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(QuantidadeNegativaException.class)
	public ResponseEntity<ErroResposta> handleQuantidadeNegativa(QuantidadeNegativaException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "A quantidade não pode ser negativa.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(ClienteNaoEncontradoException.class)
	public ResponseEntity<ErroResposta> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "O cliente não foi encontrado no banco de dados.");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(FilmeNuloVazioException.class)
	public ResponseEntity<ErroResposta> handleFilmeNuloVazioException(FilmeNuloVazioException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "O filme não pode ser nulo ou vazio.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);	
	}

	@ExceptionHandler(FilmeNaoEncontradoException.class)
	public ResponseEntity<ErroResposta> handleFilmeNaoEncontradoException(FilmeNaoEncontradoException ex) {
		ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "O filme não foi encontrado no banco de dados.");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
}
