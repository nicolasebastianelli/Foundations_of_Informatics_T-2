package zannotaxi.persistence;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import zannotaxi.model.TariffaADistanza;
import zannotaxi.model.TariffaATempo;
import zannotaxi.model.TariffaTaxi;
import zannotaxi.model.TariffaTaxi.TipoTariffa;

public class MyTariffaTaxiReader implements TariffaTaxiReader {

	private List<TariffaTaxi> listaTariffe;
	
	public MyTariffaTaxiReader(Reader r) throws BadFileFormatException {
		
		BufferedReader reader = new BufferedReader(r);
		
		listaTariffe = new ArrayList<TariffaTaxi>();
		
		String line = null;

		try {

			while ((line = reader.readLine()) != null) {
				
				StringTokenizer tokenizer = new StringTokenizer(line, " \t\n\r");
				String nome = tokenizer.nextToken().trim();
				
				TipoTariffa tipo = 
						TipoTariffa.valueOf(tokenizer.nextToken().trim());
				
				switch(tipo) {
				case DISTANZA:
					{
						double distanzaDiScatto = Double.parseDouble(tokenizer.nextToken().trim()); // 100
						tokenizer.nextToken(); // m
						double valoreScatto = Double.parseDouble(tokenizer.nextToken().trim()); // 0.20
						double applicabileDaEuro = Double.parseDouble(tokenizer.nextToken().trim()); // 10.0
						double applicabileFinoAEuro = Double.MAX_VALUE;
						if(tokenizer.hasMoreTokens()) {
							applicabileFinoAEuro = Double.parseDouble(tokenizer.nextToken().trim()); // 20.0
						}
						listaTariffe.add( new TariffaADistanza(nome, distanzaDiScatto, valoreScatto, applicabileDaEuro, applicabileFinoAEuro) );
					}
					break;
				case TEMPO:
					{
						double tempoDiScatto = 
								Double.parseDouble(tokenizer.nextToken().trim()); // 12
						tokenizer.nextToken(); // s
						double valoreScatto = Double.parseDouble(tokenizer.nextToken().trim()); // 0.20
						
						listaTariffe.add( new TariffaATempo(nome, valoreScatto, tempoDiScatto) );
					}
					break;
				}
			}

		} catch (Exception e) {
			throw new BadFileFormatException(e);
		}
		
	}
	
	@Override
	public List<TariffaTaxi> leggiTariffe() {
		return listaTariffe;
	}

}
