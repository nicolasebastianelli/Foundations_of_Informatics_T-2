package doc.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import doc.model.DentoStrategia;
import doc.model.DentoStrategiaConfigurabile;

public class TextStrategieRepository extends TextRepository<DentoStrategia> {
	private final String HEADER = "header";
	private final String PUNTEGGI = "punteggi";
	private final String FOOTER = "footer";	
	
	private final String STRATEGIA = "STRATEGIA";
	private final String SU = "SU";
	private final String ORE = "ORE";
	
	private final String PUNTEGGIO = "punteggio";
		
	private final String FINE_STRATEGIA = "FINE STRATEGIA";
	
	public TextStrategieRepository(String fileName) 
			throws IOException, MalformedFileException {
		super(fileName);
	}

	@Override
	protected DentoStrategia readElement(BufferedReader reader) throws MalformedFileException, IOException {
		//Lettura header		
		String line = reader.readLine();
		StringTokenizer tokenizer = new StringTokenizer(line, "\t");
		if (tokenizer.countTokens() != 5)
			throw new MalformedFileException("Strategia: header - devono esserci 5 token.");
		expect(STRATEGIA, tokenizer.nextToken().trim(), HEADER);
		String nome = tokenizer.nextToken().trim();
		expect(SU, tokenizer.nextToken().trim(), HEADER);
		int ore = readInt(tokenizer.nextToken().trim(), ORE, HEADER);
		if (ore <= 0)
			throw new MalformedFileException("Strategia: header - ore deve essere un intero positivo.");
		expect(ORE, tokenizer.nextToken().trim(), HEADER);
		
		//Lettura pesi
		line = reader.readLine();
		if (line == null || line.trim().isEmpty())
			throw new MalformedFileException("Strategia: attesa linea pesi.");
		tokenizer = new StringTokenizer(line, ",");
		Map<String, Integer> punteggiBevande = readPunteggiBevande(tokenizer);
				
		//Lettura footer
		line = reader.readLine();
		if (line != null) {
			line = line.trim();
		}
		expect(FINE_STRATEGIA, line, FOOTER);
		
		return new DentoStrategiaConfigurabile(nome, punteggiBevande, ore);
	}
	
	private Map<String, Integer> readPunteggiBevande(StringTokenizer tokenizer) throws MalformedFileException {
		HashMap<String, Integer> mappaPunteggi = new HashMap<>();
		while (tokenizer.hasMoreTokens()) {
			StringTokenizer innerTokenizer = new StringTokenizer(tokenizer.nextToken(), "=");
			if (innerTokenizer.countTokens() != 2)
				throw new MalformedFileException("Strategia: punteggi, attesi 2 token per bevanda separati da '='.");
			String bevanda = innerTokenizer.nextToken().trim();
			int punteggio = readInt(innerTokenizer.nextToken().trim(), PUNTEGGIO, PUNTEGGI);
			mappaPunteggi.put(bevanda, punteggio);
		}
		return mappaPunteggi;
	}

	private void expect(String expected, String actual, String where) throws MalformedFileException {
		if (!expected.equals(actual))
			throw new MalformedFileException("Strategia: " + where + " - atteso " + expected);
	}
	
	private int readInt(String value, String fieldName, String where) throws MalformedFileException {
		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			throw new MalformedFileException("Strategia: " + where + " - " + fieldName + " deve essere intero.");
		}
	}
}
