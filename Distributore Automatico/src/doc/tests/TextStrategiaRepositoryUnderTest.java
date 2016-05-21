package doc.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import doc.model.DentoStrategiaConfigurabile;
import doc.persistence.MalformedFileException;
import doc.persistence.TextStrategieRepository;

public class TextStrategiaRepositoryUnderTest extends TextStrategieRepository {

	private static Reader reader;
	
	public TextStrategiaRepositoryUnderTest(String fileName)
			throws IOException, MalformedFileException {
		super(fileName);
	}
	
	public static void setReader(Reader reader) {
		TextStrategiaRepositoryUnderTest.reader = reader;
	}
	
	@Override
	public Reader getReader() {
		return reader;
	}
	
	public DentoStrategiaConfigurabile exposedReadElement(BufferedReader bufferedReader) throws MalformedFileException, IOException {
		return (DentoStrategiaConfigurabile) readElement(bufferedReader);		
	}
}
