package oroscopo.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class TextFileOroscopoRepository implements OroscopoRepository {

	Map<String, List<Previsione>> data;

	public TextFileOroscopoRepository(Reader baseReader) throws IOException,
			BadFileFormatException {

		data = new HashMap<>();

		try (@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(baseReader)) {

			String line = null;
			while ((line = reader.readLine()) != null) {

				if (line.contains(" ") || line.contains("\t")) {
					throw new BadFileFormatException();
				}

				data.put(line.trim().toLowerCase(), caricaPrevisioni(reader));
			}
		}
	}
	
	private List<Previsione> caricaPrevisioni(BufferedReader reader)
			throws IOException, BadFileFormatException {

		List<Previsione> previsioni = new ArrayList<Previsione>();

		try {

			String riga = null;

			while (!(riga = reader.readLine().trim()).equalsIgnoreCase("FINE")) {
				
				if(riga.isEmpty()) continue;
				
				StringTokenizer tokenizer = new StringTokenizer(riga, "\t\r\n");
				String frase = tokenizer.nextToken().trim();
				int valore = Integer.parseInt(tokenizer.nextToken().trim());

				Previsione previsione = null;

				if (tokenizer.hasMoreTokens()) {
					String sAllowedSigns = tokenizer.nextToken();
					try {
						String[] signs = sAllowedSigns.split(",");
						Set<SegnoZodiacale> allowedSigns = new HashSet<>();
						for (String sign : signs) {
							allowedSigns.add(SegnoZodiacale.valueOf(sign.trim()
									.toUpperCase()));
						}
						previsione = new Previsione(frase, valore, allowedSigns);
					} catch (Exception e) {
						throw new BadFileFormatException();
					}
				} else {
					previsione = new Previsione(frase, valore);
				}

				previsioni.add(previsione);
			}

		} catch (Exception e) {
			throw new BadFileFormatException(e);
		}

		if (previsioni.size() == 0)
			throw new BadFileFormatException();

		return previsioni;

	}

	@Override
	public List<Previsione> getPrevisioni(String sezione) {
		return data.get(sezione.trim().toLowerCase());
	}

	@Override
	public Set<String> getSettori() {
		return data.keySet();
	}

}
