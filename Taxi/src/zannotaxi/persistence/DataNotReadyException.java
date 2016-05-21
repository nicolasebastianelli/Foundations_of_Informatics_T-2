package zannotaxi.persistence;

public class DataNotReadyException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DataNotReadyException() {
	}
	
	public DataNotReadyException(Throwable t) {
		super(t);
	}

	public DataNotReadyException(String message) {
		super(message);
	}
}
