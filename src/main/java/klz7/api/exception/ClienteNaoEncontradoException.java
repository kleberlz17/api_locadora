package klz7.api.exception;

@SuppressWarnings("serial")
public class ClienteNaoEncontradoException extends RuntimeException {
	
	public ClienteNaoEncontradoException(String message) {
		super(message);
	}

}
