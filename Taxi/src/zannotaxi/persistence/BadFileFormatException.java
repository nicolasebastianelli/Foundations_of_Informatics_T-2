package zannotaxi.persistence;

public class BadFileFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadFileFormatException() {
	}
	
	public BadFileFormatException(Throwable t) {
		super(t);
	}

	public BadFileFormatException(String message) {
		super(message);
	}

}
