package teethcollege.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import teethcollege.model.PianoDiStudi;
import teethcollege.model.Insegnamento;

public class MyPianiReader implements PianiReader {
	private BufferedReader reader;

	public MyPianiReader(Reader reader) {
		if (reader == null)
			throw new IllegalArgumentException();

		this.reader = new BufferedReader(reader);
	}

	public List<PianoDiStudi> caricaPianiDiStudi(Map<Long, Insegnamento> mappaInsegnamenti)
			throws MalformedFileException, IOException {
		ArrayList<PianoDiStudi> mappaPianiDiStudi = new ArrayList<PianoDiStudi>();
		PianoDiStudi pianoDiStudi;
		try {
			while ((pianoDiStudi = leggiPianiDiStudi(mappaInsegnamenti)) != null) {
				mappaPianiDiStudi.add(pianoDiStudi);
			}
			close();
		} catch (Exception e) {
			close();
			throw e;
		}
		return mappaPianiDiStudi;
	}

	private PianoDiStudi leggiPianiDiStudi(Map<Long, Insegnamento> mappaInsegnamenti) throws MalformedFileException,
			IOException {
		try {
			String line = reader.readLine();
			if (line == null || line.isEmpty())
				return null;

			// prima riga: STUDENTE\t0000987654\tGiuseppe\tRossi
			StringTokenizer tokenizer = new StringTokenizer(line, "\t\r\n");
			String token = tokenizer.nextToken().trim();
			if (!token.equals("STUDENTE"))
				throw new MalformedFileException("Errore nel formato del file Carriere: manca 'STUDENTE'");
			String matricola = tokenizer.nextToken().trim();

			// Effettua solo la verifica del fatto che matricola sia
			// convertibile in long
			Long.parseLong(matricola);

			String nome = tokenizer.nextToken().trim();
			String cognome = tokenizer.nextToken().trim();
			// seconda riga: PIANO DI STUDI
			line = reader.readLine();
			tokenizer = new StringTokenizer(line, ",\t\r\n");
			token = tokenizer.nextToken().trim();
			if (!token.equals("PIANO DI STUDI"))
				throw new MalformedFileException("Errore nel formato del file Carriere: manca 'PIANO DI STUDI'");
			List<Long> tuttiGliEsami = leggiCodici();
			// ---
			PianoDiStudi c = new PianoDiStudi(nome, cognome, matricola);
			for (Long codice : tuttiGliEsami) {
				Insegnamento insegnamento = mappaInsegnamenti.get(codice);
				if (insegnamento == null)
					throw new MalformedFileException("Insegnamento non trovato nella mappa");
				c.aggiungiInsegnamento(insegnamento);
			}
			System.out.println(c);
			return c;
		} catch (NoSuchElementException | NumberFormatException e) {
			throw new MalformedFileException(e);
		}
	}

	private List<Long> leggiCodici() throws IOException {
		List<Long> codici = new ArrayList<Long>();
		boolean recordNonFinito = true;
		while (reader.ready() && recordNonFinito) {
			String token = null, line = reader.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line, ",\r\n");
			while (tokenizer.hasMoreTokens() && recordNonFinito) {
				token = tokenizer.nextToken().trim();
				recordNonFinito = !token.equals("FINE STUDENTE");
				if (recordNonFinito)
					codici.add(Long.parseLong(token));
			}
		}
		return codici;
	}

	public void close() {
		try {
			reader.close();
		} catch (Exception e) {

		}
	}

}
