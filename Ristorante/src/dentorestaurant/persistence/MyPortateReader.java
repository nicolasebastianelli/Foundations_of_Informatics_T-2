package dentorestaurant.persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Portata;

public class MyPortateReader implements PortateReader {
	private BufferedReader reader;

	public MyPortateReader(Reader reader) {
		if (reader == null)
			throw new IllegalArgumentException("reader null");
		this.reader = new BufferedReader(reader);
	}

	public Map<Categoria, List<Portata>> caricaPortate()
			throws MalformedFileException, IOException {
		try {
			Map<Categoria, List<Portata>> mappaPortate = new HashMap<Categoria, List<Portata>>();
			for (Categoria c : Categoria.values())
				mappaPortate.put(c, new ArrayList<Portata>());
			String line = null;
			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",\r\n");
				String codice = tokenizer.nextToken().trim();
				String nome = tokenizer.nextToken().trim();
				String token = tokenizer.nextToken().trim();
				double prezzo = Double
						.parseDouble(tokenizer.nextToken().trim());
				Categoria cat = Categoria.valueOf(token);
				mappaPortate.get(cat).add(
						new Portata(codice, nome, cat, prezzo));
			}
			return mappaPortate;
		} catch (NoSuchElementException | NumberFormatException e) {
			throw new MalformedFileException(e);
		}
	}

	@Override
	public void close() {
		try {
			reader.close();
		} catch (Exception e) {
			// suppress all...
		}
	}

	public static void main(String a[]) {
		final String nomeFilePortate = "Portate.txt";
		Reader readerPortate = null;
		try {
			readerPortate = new FileReader(nomeFilePortate);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"File non trovato: " + e.getMessage());
			System.exit(1);
		}
		PortateReader reader = new MyPortateReader(readerPortate);
		try {
			Map<Categoria, List<Portata>> mappa = reader.caricaPortate();
			System.out.println(mappa);
		} catch (IOException | MalformedFileException e) {
			e.printStackTrace();
		}
	}
}
