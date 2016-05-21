package zannonia.persistence;

import java.io.*;
import java.util.*;

import zannonia.model.*;

public class MyAutostradeReader implements AutostradeReader
{
	@Override
	public ArrayList<Autostrada> readAutostrade(Reader autoReader, Map<String, Tratta> tratteMap) throws IOException,
			MalformedFileException
	{
		ArrayList<Autostrada> autostrade = new ArrayList<Autostrada>();
		BufferedReader reader = new BufferedReader(autoReader);
		try
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				StringTokenizer tokenizer = new StringTokenizer(line, "\t ");
				
				String id = tokenizer.nextToken();
				Autostrada autostrada = new Autostrada(id);

				while (tokenizer.hasMoreTokens())
				{
					String nomeTratta = tokenizer.nextToken().toUpperCase();
					Tratta tratta = tratteMap.get(nomeTratta);
					if (tratta == null)
						throw new MalformedFileException("Tratta non trovata: " + nomeTratta);
					
					autostrada.addTratta(tratta);
				}
				autostrade.add(autostrada);
			}
		}
		catch (NoSuchElementException | NumberFormatException e)
		{
			throw new MalformedFileException(e);
		}
		return autostrade;
	}
}
