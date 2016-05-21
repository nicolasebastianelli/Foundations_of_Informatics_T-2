package teethcollege.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.Map;
import java.util.StringTokenizer;

import teethcollege.model.Categoria;
import teethcollege.model.Insegnamento;
import teethcollege.model.Semestre;

public class MyInsegnamentiReader implements InsegnamentiReader {
	private BufferedReader reader;

	public MyInsegnamentiReader(Reader reader) {
		if (reader == null)
			throw new IllegalArgumentException("reader");
		
		this.reader = new BufferedReader(reader);
	}

	public Map<Long, Insegnamento> caricaInsegnamenti()
			throws MalformedFileException, IOException {
		try {
			Map<Long, Insegnamento> mappaInsegnamenti = new TreeMap<Long, Insegnamento>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",\r\n");
				long codice = Long.parseLong(tokenizer.nextToken().trim());
				String nome = tokenizer.nextToken().trim();
				String ssd = tokenizer.nextToken().trim();
				int cfu = Integer.parseInt(tokenizer.nextToken().trim());
				String token = tokenizer.nextToken().trim();
				Categoria cat = null;
				switch (token) {
				case "OBBLIGATORIO":
					cat = Categoria.OBBLIGATORIO;
					break;
				case "A SCELTA":
					cat = Categoria.A_SCELTA;
					break;
				default:
					throw new MalformedFileException(
							"Errore nel formato del file: categoria " + token
									+ " inesistente");
				}
				token = tokenizer.nextToken().trim();
				Semestre sem = null;
				switch (token) {
				case "S1":
					sem = Semestre.PRIMO;
					break;
				case "S2":
					sem = Semestre.SECONDO;
					break;
				default:
					throw new MalformedFileException(
							"Errore nel formato del file: semestre " + token
									+ " inesistente");
				}
				mappaInsegnamenti.put(codice, new Insegnamento(codice, nome,
						ssd, cfu, sem, cat));
			}
			close();
			return mappaInsegnamenti;
		} catch (NoSuchElementException | NumberFormatException e) {
			close();
			throw new MalformedFileException(e);
		}
	}
	
	public void close() {
		try {
			reader.close();
		} catch (Exception e) {

		}
	}
}
