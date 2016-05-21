package compromeglio.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import compromeglio.model.Bene;
import compromeglio.model.Rilevazione;

public class MyRilevazioniReader implements RilevazioniReader {

	private BufferedReader reader;

	public MyRilevazioniReader(Reader reader) {
		if (reader == null)
			throw new IllegalArgumentException();

		this.reader = new BufferedReader(reader);
	}

	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

	@Override
	public List<Rilevazione> caricaRilevazioni(Map<Long, Bene> beni)
			throws MalformedFileException, IOException {

		// codice, luogo, data, prezzo
		try {
			
			List<Rilevazione> lista = new ArrayList<Rilevazione>();

			String line = null;
			while ((line = reader.readLine()) != null) {

				StringTokenizer tokenizer = new StringTokenizer(line, ",\r\n");

				long codice = Long.parseLong(tokenizer.nextToken().trim());
				String luogo = tokenizer.nextToken().trim();
				String data = tokenizer.nextToken().trim();
				String prezzo = tokenizer.nextToken().trim();

				Date d = null;
				try {
					d = formatter.parse(data);
				} catch (ParseException e) {
					throw new MalformedFileException(e);
				}
				
				lista.add(
						new Rilevazione(beni.get(codice), luogo, d, Float.parseFloat(prezzo)) );

			}

			close();

			return lista;
			
		} catch (NoSuchElementException | NumberFormatException e) {
			
			close();
			throw new MalformedFileException(e);
		}
	}

	private void close() {
		try {
			reader.close();
		} catch (Exception e) {

		}
	}
}
