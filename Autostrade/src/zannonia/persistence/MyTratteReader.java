package zannonia.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import zannonia.model.Casello;
import zannonia.model.Tratta;

public class MyTratteReader implements TratteReader
{

	@Override
	public Map<String, Tratta> readTratte(Reader tratteReader) throws IOException, MalformedFileException
	{
		HashMap<String, Tratta> tratte = new HashMap<String, Tratta>();
		BufferedReader reader = new BufferedReader(tratteReader);
		try
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				StringTokenizer tokenizer = new StringTokenizer(line, "\t ");
				
				String id = tokenizer.nextToken();
				double km = Double.parseDouble(tokenizer.nextToken());
				double pedaggio = Double.parseDouble(tokenizer.nextToken());
				Tratta tratta = new Tratta(id, pedaggio, km);
				if (tokenizer.hasMoreTokens())
				{
					StringTokenizer caselloTokenizer = new StringTokenizer(tokenizer.nextToken("\t"), ",");
					while (caselloTokenizer.hasMoreTokens())
					{
						String caselloId = caselloTokenizer.nextToken().trim();
						Casello casello = new Casello(caselloId);
						casello.setTratta(tratta);
						tratta.addCasello(casello);
					}
				}
				tratte.put(tratta.getId().toUpperCase(), tratta);
			}
		}
		catch (NoSuchElementException | NumberFormatException e)
		{
			throw new MalformedFileException(e);
		}
		return tratte;
	}

}
