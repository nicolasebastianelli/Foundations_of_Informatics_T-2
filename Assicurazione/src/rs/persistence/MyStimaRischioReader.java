package rs.persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import rs.model.Month;
import rs.model.StimaRischio;

public class MyStimaRischioReader implements StimaRischioReader {
	
	@Override
	public Map<String, Collection<StimaRischio>> readFrom(Reader reader) throws IOException, BadFileFormatException {
		HashMap<String, Collection<StimaRischio>> result = new HashMap<String, Collection<StimaRischio>>();
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		StimaRischio stimaRischio;
		while ((stimaRischio = readStimaRischio(bufferedReader)) != null) {
			Collection<StimaRischio> stimeRischio = result.get(stimaRischio.getCitta());
			if (stimeRischio == null) {
				stimeRischio = new ArrayList<StimaRischio>();
				result.put(stimaRischio.getCitta(), stimeRischio);
			}
			stimeRischio.add(stimaRischio);
		}
		
		return result;
	}

	private StimaRischio readStimaRischio(BufferedReader reader) throws IOException, BadFileFormatException {
		String line = reader.readLine();
		if (line == null || line.trim().isEmpty())
			return null;
		
		StringTokenizer tokenizer = new StringTokenizer(line, "|"); 
		
		String citta = tokenizer.nextToken().trim();
		if (citta.isEmpty())
			throw new BadFileFormatException("Token città assente");		
		
		String s;
		try {
			s = tokenizer.nextToken("| ").trim();
		} catch (NoSuchElementException e) {
			throw new BadFileFormatException("Token mese assente", e);
		}
		Month mese = convertToMonth(s);
		
		int anno;
		try {
			s = tokenizer.nextToken(" ").trim();
			anno = Integer.parseInt(s);
		}
		catch (NoSuchElementException e) {
			throw new BadFileFormatException("Token anno assente", e);
		}
		catch (NumberFormatException e) {
			throw new BadFileFormatException("Anno deve essere un intero");
		}
		
		try {
			s = tokenizer.nextToken().trim();
		}
		catch (NoSuchElementException e) {
			throw new BadFileFormatException("Token rischio furto assente", e);
		}
		if (!s.endsWith("%"))
			throw new BadFileFormatException("Formato rischio furto errato: manca il simbolo '%'");
		int rischioFurto;
		try {
			int endIndex = s.length() - "%".length();
			String rischioFurtoNumber = s.substring(0, endIndex).trim();
			rischioFurto = Integer.parseInt(rischioFurtoNumber);
		} catch (NumberFormatException e) {
			throw new BadFileFormatException("Formato rischio furto errato: non è una percentuale");
		}

		return new StimaRischio(citta, mese, anno, rischioFurto);
	}

	private Month convertToMonth(String mese) throws BadFileFormatException {
		mese = mese.toLowerCase();
		switch (mese) {
		case "gennaio": return Month.JANUARY;
		case "febbraio": return Month.FEBRUARY;
		case "marzo": return Month.MARCH;
		case "aprile": return Month.APRIL;
		case "maggio": return Month.MAY;
		case "giugno": return Month.JUNE;
		case "luglio": return Month.JULY;
		case "agosto": return Month.AUGUST;
		case "settembre": return Month.SEPTEMBER;
		case "ottobre": return Month.OCTOBER;
		case "novembre": return Month.NOVEMBER;
		case "dicembre": return Month.DECEMBER;
		default:
			throw new BadFileFormatException("Mese non valido");
		}
	}

}
