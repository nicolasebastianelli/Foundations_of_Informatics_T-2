package galliacapocciona.persistence;

import galliacapocciona.model.Collegio;
import galliacapocciona.model.Partito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class MyCollegiReader implements CollegiReader {

	public List<Collegio> caricaElementi(Reader reader) throws IOException, BadFileFormatException {
		List<Collegio> listaCollegi = new ArrayList<Collegio>();
		BufferedReader innerReader = new BufferedReader(reader);
		String line = innerReader.readLine();
		if (line == null)
			throw new BadFileFormatException("Formato file errato - prima riga mancante");
		
		StringTokenizer tokenizer = new StringTokenizer(line, "\t \n");
		if (tokenizer.countTokens() < 2)
			throw new BadFileFormatException("Formato file errato - meno di due partiti");
		
		String[] nomiPartiti = new String[tokenizer.countTokens()];
		int tokenCount = tokenizer.countTokens();
		for (int i = 0; i < tokenCount; i++) {
			nomiPartiti[i] = tokenizer.nextToken();
		}		

		while ((line = innerReader.readLine()) != null) {
			tokenizer = new StringTokenizer(line, "\t \n");			
			tokenCount = tokenizer.countTokens();
			if (tokenCount != nomiPartiti.length + 1)
				throw new BadFileFormatException("Formato file errato - conteggi voti diverso del numero di partiti");
			String nomeCollegio = "c" + tokenizer.nextToken();
			SortedSet<Partito> partiti = new TreeSet<Partito>();
			for (int i = 1; i < tokenCount; i++) {
				int voti;
				try {
					voti = Integer.parseInt(tokenizer.nextToken());
				} catch (NumberFormatException e) {
					throw new BadFileFormatException("Formato file errato - il numero di voti non è un valore intero", e);
				}
				partiti.add(new Partito(nomiPartiti[i - 1], voti));
			}
			listaCollegi.add(new Collegio(nomeCollegio, partiti));
		}
		return listaCollegi;
	}
}
