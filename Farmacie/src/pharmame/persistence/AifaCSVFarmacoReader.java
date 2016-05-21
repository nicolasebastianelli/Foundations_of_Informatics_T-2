package pharmame.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.StringTokenizer;

import pharmame.model.Farmaco;

public class AifaCSVFarmacoReader implements FarmacoReader {
	
	private final String Header = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC";
	private NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ITALY);
	
	@Override
	public Collection<Farmaco> readFrom(Reader inputreader) throws BadFileFormatException, IOException {
		ArrayList<Farmaco> output = new ArrayList<>();
		BufferedReader reader = new BufferedReader(inputreader);
		
		//Header Check
		String line = reader.readLine();
		if (!line.toUpperCase().startsWith(Header.toUpperCase()))
			throw new BadFileFormatException("Header non corretto");
		
		while ((line = reader.readLine()) != null) {
			output.add(readFarmaco(line));
		}
		
		return output;
	}
	
	private Farmaco readFarmaco(String line) throws BadFileFormatException {
		StringTokenizer tokenizer = new StringTokenizer(line, ";");
		if (tokenizer.countTokens() < 5)
			throw new BadFileFormatException("Token non sufficienti");
		
		String principioAttivo = tokenizer.nextToken(); 
		if (principioAttivo.trim().equals(""))
			throw new BadFileFormatException("Principio attivo mancante");
		
		String gruppoEquivalenza = tokenizer.nextToken();
		if (gruppoEquivalenza.trim().equals(""))
			throw new BadFileFormatException("Gruppo equivalenza mancante");
		
		String nome = tokenizer.nextToken(";*");
		if (nome.trim().equals(""))
			throw new BadFileFormatException("Nome mancante");
		
		String confezione = tokenizer.nextToken(";*");
		if (confezione.trim().equals(""))
			throw new BadFileFormatException("Confezione mancante");
		
		String prezzoAsString = tokenizer.nextToken();
		float prezzo;
		try {
			Number value = numberFormat.parse(prezzoAsString);
			prezzo = value.floatValue();
		} catch (ParseException e) {
			throw new BadFileFormatException("Errore nel formato del prezzo: " + prezzoAsString, e);
		}
		String ditta = tokenizer.nextToken();
		if (ditta.trim().equals(""))
			throw new BadFileFormatException("Ditta mancante");
		
		String codiceAsString = tokenizer.nextToken();
		int codice;
		try {
			codice = Integer.parseInt(codiceAsString);
		} catch (NumberFormatException e) {
			throw new BadFileFormatException("Errore nel formato del codice: " + codiceAsString, e);
		}
		
		return new Farmaco(codice, principioAttivo, gruppoEquivalenza, nome, confezione, prezzo, ditta);
	}

}
