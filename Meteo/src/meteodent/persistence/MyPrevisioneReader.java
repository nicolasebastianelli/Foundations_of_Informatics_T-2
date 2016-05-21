package meteodent.persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

import meteodent.model.Previsione;

public class MyPrevisioneReader implements PrevisioneReader {
	
	private static final int TOKEN_COUNT = 4;
	private static final String SEPARATOR = "|";
	private static final String TEMPERATURA_TOKEN_START = "T=";
	private static final String PROBABILITA_PIOGGIA_TOKEN_START = "Prob.Pioggia ";
	private static final String PERCENTAGE = "%";

	@Override
	public Map<String, Collection<Previsione>> readFrom(Reader reader) throws IOException, BadFileFormatException {
		HashMap<String, Collection<Previsione>> result = new HashMap<String, Collection<Previsione>>();
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		Previsione previsione;
		while ((previsione = readPrevisione(bufferedReader)) != null) {
			Collection<Previsione> previsioni = result.get(previsione.getLocalita());
			if (previsioni == null) {
				previsioni = new TreeSet<Previsione>(new PrevisioneComparator());
				result.put(previsione.getLocalita(), previsioni);
			}
			previsioni.add(previsione);
		}
		
		return result;
	}

	private Previsione readPrevisione(BufferedReader reader) throws IOException, BadFileFormatException {
		String line = reader.readLine();
		if (line == null || line.trim().isEmpty())
			return null;
		
		StringTokenizer tokenizer = new StringTokenizer(line, SEPARATOR); 
		if (tokenizer.countTokens() != TOKEN_COUNT)
			throw new BadFileFormatException("I token devono essere " + TOKEN_COUNT);
		
		String localita = tokenizer.nextToken().trim();
		if (localita.isEmpty())
			throw new BadFileFormatException("Località mancante");		
		
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.ITALY);
		Date dataOra;
		try {
			dataOra = dateFormat.parse(tokenizer.nextToken().trim());
		} catch (ParseException e) {
			throw new BadFileFormatException("Formato data errato", e);
		}
		
		String temperaturaToken = tokenizer.nextToken().trim();
		if (!temperaturaToken.startsWith(TEMPERATURA_TOKEN_START))
			throw new BadFileFormatException("Formato temperatura errato: manca il preambolo");
		int temperatura;
		try {
			temperatura = Integer.parseInt(temperaturaToken.substring(TEMPERATURA_TOKEN_START.length()));
		} catch (NumberFormatException e) {
			throw new BadFileFormatException("Formato temperatura errato: non è un intero");
		}
		
		String probabilitaPioggiaToken = tokenizer.nextToken().trim();
		if (!probabilitaPioggiaToken.startsWith(PROBABILITA_PIOGGIA_TOKEN_START))
			throw new BadFileFormatException("Formato probabilità pioggia errato: manca il preambolo");
		if (!probabilitaPioggiaToken.endsWith(PERCENTAGE))
			throw new BadFileFormatException("Formato probabilità pioggia errato: manca il simbolo '%'");
		int probabilitaPioggia;
		try {
			int startIndex = PROBABILITA_PIOGGIA_TOKEN_START.length();
			int endIndex = probabilitaPioggiaToken.length() - PERCENTAGE.length();
			String probabilitaPioggiaNumber = probabilitaPioggiaToken.substring(startIndex, endIndex).trim();
			probabilitaPioggia = Integer.parseInt(probabilitaPioggiaNumber);
		} catch (NumberFormatException e) {
			throw new BadFileFormatException("Formato probabilità pioggia errato: non è una percentuale");
		}

		return new Previsione(localita, dataOra, temperatura,
				probabilitaPioggia);
	}

}
