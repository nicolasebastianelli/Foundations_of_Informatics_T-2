package doc.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import doc.model.Acquisto;

public class BinAcquistoRepository extends BinRepository<Acquisto> implements AcquistoRepository {

	public BinAcquistoRepository(String fileName) throws IOException, MalformedFileException {
		super(fileName);
	}

	private void writeAll() throws FileNotFoundException, IOException {
		try (OutputStream os = getOutputStream()) {
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(getStorage());
		}
	}

	@Override
	public void add(Acquisto acquisto) throws IOException {
		getStorage().add(acquisto);
		writeAll();
	}

}
