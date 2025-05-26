package klz7.api.exception;

@SuppressWarnings("serial")
public class CpfDuplicadoException extends RuntimeException{
	
	public CpfDuplicadoException(String message) {
		super(message);
	}
}
