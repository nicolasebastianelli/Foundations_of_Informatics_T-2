package doc.persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

public abstract class TextRepository<T> implements Repository<T> {

	private static final int MarkLimit = 200;
	
	private String fileName;
	private ArrayList<T> storage;

	public TextRepository(String fileName) throws IOException, MalformedFileException {
		if (fileName == null || fileName.trim().isEmpty())
			throw new IllegalArgumentException();
		
		this.fileName = fileName;
		readAll();
	}
	
	public String getFileName() {
		return this.fileName;
	}

	protected Reader getReader() throws FileNotFoundException {
		return new FileReader(fileName);
	}

	private void readAll() throws IOException, MalformedFileException {
		storage = new ArrayList<T>();
		try (Reader outerReader = getReader()) {
			BufferedReader reader = new BufferedReader(outerReader);
			reader.mark(MarkLimit);
			while ((reader.readLine()) != null) {
				reader.reset();
				T element = readElement(reader);
				storage.add(element);
				reader.mark(MarkLimit);
			}
		}
	}

	protected abstract T readElement(BufferedReader reader) throws MalformedFileException,
			IOException;

	public Collection<T> getAll() {
		return storage;
	}
}
