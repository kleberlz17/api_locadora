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

}
