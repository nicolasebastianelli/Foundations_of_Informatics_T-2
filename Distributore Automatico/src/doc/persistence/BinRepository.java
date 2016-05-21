package doc.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class BinRepository<T> implements Repository<T> {

	private String fileName;
	private ArrayList<T> storage;

	public BinRepository(String fileName) throws IOException, MalformedFileException {
		if (fileName == null || fileName.trim().isEmpty())
			throw new IllegalArgumentException();

		this.fileName = fileName;
		readAll();
	}
	
	public String getFileName() {
		return this.fileName;
	}

	protected InputStream getInputStream() throws FileNotFoundException {
		return new FileInputStream(fileName);
	}

	protected OutputStream getOutputStream() throws IOException {
		return new FileOutputStream(fileName);
	}
	
	protected ArrayList<T> getStorage() {
		return storage;
	}

	@Override
	public Collection<T> getAll() {
		return storage;
	}
	
	@SuppressWarnings("unchecked")
	private void readAll() throws IOException, MalformedFileException {
		try (InputStream is = getInputStream()) {
			ObjectInputStream ois = new ObjectInputStream(is);
			storage = (ArrayList<T>) ois.readObject();
		} catch (FileNotFoundException e) {
			storage = new ArrayList<T>();
		} catch (ClassNotFoundException | 
				StreamCorruptedException | 
				InvalidClassException | 
				OptionalDataException | 
				ClassCastException e) {
			throw new MalformedFileException(e);
		}
	}

}