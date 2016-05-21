package ed.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import ed.model.Tariffa;
import ed.model.TariffaAConsumo;
import ed.model.TariffaFlat;

public class MyTariffaReader implements TariffaReader
{
	@Override
	public Collection<Tariffa> leggiTariffe(Reader reader) throws IOException, BadFileFormatException
	{
		List<Tariffa> elencoTariffe = new ArrayList<Tariffa>();
		BufferedReader fin = null;
		String line;
		try
		{
			fin = new BufferedReader(reader);

			while ((line = fin.readLine()) != null)
			{
				StringTokenizer tokenizer = new StringTokenizer(line, "/\t");
				String tipo = tokenizer.nextToken().trim();
				Tariffa t = null;
				if (tipo.equalsIgnoreCase("FLAT"))
				{					
					t = readFlat(tokenizer);
				}
				else if (tipo.equalsIgnoreCase("A CONSUMO"))
				{ 
					t = readConsumo(tokenizer);
				}
				else
				{
					throw new BadFileFormatException("Tariffa non riconosciuta");
				}
				elencoTariffe.add(t);
			}
			fin.close();
		}
		catch (IOException e)
		{
			throw new BadFileFormatException(e);
		}
		catch (NoSuchElementException e)
		{
			throw new BadFileFormatException(e);
		}
		catch (NumberFormatException e)
		{
			throw new BadFileFormatException(e);
		}
		return elencoTariffe;
	}
	
	private Tariffa readConsumo(StringTokenizer tokenizer) throws BadFileFormatException
	{
		// "A CONSUMO"
		// A CONSUMO / MONORARIA 1 / PREZZO 10.70
		String nome = tokenizer.nextToken().trim();

		String wordPrezzo = tokenizer.nextToken("/ \t").trim();
		if (!wordPrezzo.equalsIgnoreCase("PREZZO"))
			throw new BadFileFormatException("Expected PREZZO");
		
		String numPrezzo = tokenizer.nextToken().trim();
		double prezzo = Double.parseDouble(numPrezzo) / 100;

		if (tokenizer.hasMoreTokens())
		{
			String numExtra = tokenizer.nextToken().trim();
			double extra = Double.parseDouble(numExtra) / 100;

			String wordOltre = tokenizer.nextToken().trim();
			if (!wordOltre.equalsIgnoreCase("OLTRE"))
					throw new BadFileFormatException("Expected OLTRE");
			
			String numOltre = tokenizer.nextToken().trim();
			double oltre = Double.parseDouble(numOltre);
			return new TariffaAConsumo(nome, prezzo, oltre, extra);
		}
		else
		{
			return new TariffaAConsumo(nome, prezzo);
		}
	}

	private Tariffa readFlat(StringTokenizer tokenizer) throws BadFileFormatException
	{
		// FLAT / CASA MINI / SOGLIA 150 / PREZZO 29.00 / EXTRA 25
		String nome = tokenizer.nextToken().trim();

		String wordSoglia = tokenizer.nextToken("/ \t").trim();
		if (!wordSoglia.equalsIgnoreCase("SOGLIA"))
				throw new BadFileFormatException("Expected SOGLIA");
		
		String numSoglia = tokenizer.nextToken().trim();
		double soglia = Double.parseDouble(numSoglia);

		String wordPrezzo = tokenizer.nextToken().trim();
		if (!wordPrezzo.equalsIgnoreCase("PREZZO"))
				throw new BadFileFormatException("Expected PREZZO");
		
		String numPrezzo = tokenizer.nextToken().trim();
		double prezzo = Double.parseDouble(numPrezzo);

		String wordExtra = tokenizer.nextToken().trim();
		if (!wordExtra.equalsIgnoreCase("EXTRA"))
				throw new BadFileFormatException("Expected EXTRA");
		String numExtra = tokenizer.nextToken().trim();
		double extra = Double.parseDouble(numExtra) / 100;
		return new TariffaFlat(nome, prezzo, soglia, extra);
	}

}
