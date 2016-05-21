package compromeglio.persistence;

import java.io.IOException;

public class MalformedFileException extends IOException {

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

}
