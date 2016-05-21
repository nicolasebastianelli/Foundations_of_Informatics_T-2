package doc.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import doc.persistence.BinAcquistoRepository;
import doc.persistence.MalformedFileException;

public class BinAcquistoRepositoryUnderTest extends BinAcquistoRepository {

	private static InputStream inputStream;
	private OutputStream outputStream;

	public BinAcquistoRepositoryUnderTest(String fileName) throws IOException, MalformedFileException {
		super(fileName);
	}
	
	public BinAcquistoRepositoryUnderTest() throws IOException, MalformedFileException {
		super("NotImportant");
	}
	
	@Override
	protected InputStream getInputStream() throws FileNotFoundException {
		if (inputStream == null)
			throw new FileNotFoundException();
		return inputStream;
	}
	
	public static void setInputStream(InputStream inputStream) {
		BinAcquistoRepositoryUnderTest.inputStream = inputStream;
	}
	
	@Override
	protected OutputStream getOutputStream() throws FileNotFoundException {
		if (this.outputStream == null)
			throw new FileNotFoundException();
		return this.outputStream;
	}
	
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
}
