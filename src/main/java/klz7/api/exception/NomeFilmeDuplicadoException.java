package klz7.api.exception;

@SuppressWarnings("serial")
public class NomeFilmeDuplicadoException extends RuntimeException {
	
	public NomeFilmeDuplicadoException(String message) {
		super(message);
	}

}
