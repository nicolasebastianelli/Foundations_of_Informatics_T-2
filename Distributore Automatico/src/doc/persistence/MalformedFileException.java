package doc.persistence;

public class MalformedFileException extends Exception {

	private static final long serialVersionUID = 1L;

	public MalformedFileException() {
	}

	public MalformedFileException(String arg0) {
		super(arg0);
	}

	public MalformedFileException(Throwable arg0) {
		super(arg0);
	}

	public MalformedFileException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MalformedFileException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
