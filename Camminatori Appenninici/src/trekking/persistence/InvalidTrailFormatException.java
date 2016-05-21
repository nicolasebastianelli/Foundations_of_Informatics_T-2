package trekking.persistence;

public class InvalidTrailFormatException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidTrailFormatException(String message) {
		super(message);
	}
	
}
