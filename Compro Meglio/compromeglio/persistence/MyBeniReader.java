package compromeglio.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import compromeglio.model.Categoria;
import compromeglio.model.Bene;


public class MyBeniReader implements BeniReader {
	private BufferedReader reader;

	public MyBeniReader(Reader reader) {
		if (reader == null)
			throw new IllegalArgumentException("Invalid reader in BeniReader");
		
		this.reader = new BufferedReader(reader);
	}

	@Override
	public Set<Bene> caricaBeni()
			throws MalformedFileException, IOException {
		try {
			Set<Bene> mappaBeni = new HashSet<Bene>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				if(line.trim().isEmpty()) continue;
				
				StringTokenizer tokenizer = new StringTokenizer(line, "\t\r\n");
				long codice = Long.parseLong(tokenizer.nextToken().trim());
				String token = tokenizer.nextToken().trim();
				Categoria cat = null;
				try{
					cat = Categoria.valueOf(token);
					// PASTA, BISCOTTI, CARNE, PESCE, SALUMI, ACQUA, VINO, BIRRA;
				} catch(IllegalArgumentException e) {
					throw new MalformedFileException(
							"Errore nel formato del file: categoria " + token + " inesistente");
				}
				String descrizione = tokenizer.nextToken().trim();
				mappaBeni.add(new Bene(codice, descrizione, cat));
			}
			close();
			return mappaBeni;
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
