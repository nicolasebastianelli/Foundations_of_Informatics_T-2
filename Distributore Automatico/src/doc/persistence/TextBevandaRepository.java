package doc.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import doc.model.Bevanda;

public class TextBevandaRepository extends TextRepository<Bevanda> {

	public TextBevandaRepository(String fileName) throws IOException,
			MalformedFileException {		
		super(fileName);
	}

	@Override
	protected Bevanda readElement(BufferedReader reader) throws MalformedFileException, IOException {
		String line = reader.readLine();
		StringTokenizer tokenizer = new StringTokenizer(line, "\t");
		if (tokenizer.countTokens() != 4)
			throw new MalformedFileException("Bevanda: devono esserci 4 token.");
		String nome = tokenizer.nextToken();
		float prezzoMin = readFloat(tokenizer.nextToken());
		float prezzoBase = readFloat(tokenizer.nextToken());
		float prezzoMax = readFloat(tokenizer.nextToken());
		return new Bevanda(nome, prezzoMin, prezzoBase, prezzoMax);
	}
	
	private float readFloat(String value) throws MalformedFileException {
		try {
			return Float.parseFloat(value);
		}
		catch (NumberFormatException e) {
			throw new MalformedFileException("Bevanda: prezzo mal formattato.", e);
		}
	}

}
