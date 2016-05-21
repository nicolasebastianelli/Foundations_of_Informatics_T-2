package happybank.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import happybank.model.AbstractBancomat;
import happybank.model.TaglioBanconota;

public class MyBancomatConfigurationReader implements BancomatConfigurationReader
{
	@Override
	public void configura(Reader reader, AbstractBancomat bancomat) throws IOException, BadFileFormatException
	{
		try
		{
		BufferedReader buffered = new BufferedReader(reader);
		String line = buffered.readLine();
		if (line == null)
			throw new BadFileFormatException();
		StringTokenizer tokenizer = new StringTokenizer(line);
		TaglioBanconota piccoloTaglio = parseBanconota(tokenizer.nextToken());
		checkToken(tokenizer, "x");
		int numPiccoloTaglio = Integer.parseInt(tokenizer.nextToken());
		checkToken(tokenizer, "+");
		TaglioBanconota grandeTaglio = parseBanconota(tokenizer.nextToken());
		checkToken(tokenizer, "x");
		int numGrandeTaglio = Integer.parseInt(tokenizer.nextToken());
		
		if (piccoloTaglio.getValore() >= grandeTaglio.getValore())
			throw new BadFileFormatException("piccoloTaglio.getTaglio() >= grandeTaglio.getTaglio()");
		
		bancomat.caricaBanconote(piccoloTaglio, numPiccoloTaglio, grandeTaglio, numGrandeTaglio);
		}
		catch (NoSuchElementException | NumberFormatException e)
		{
			throw new BadFileFormatException(e);
		}
	}
	
	private void checkToken(StringTokenizer tokenizer, String expectedToken) throws BadFileFormatException
	{
		try
		{
			String token = tokenizer.nextToken();
			if (!token.equals(expectedToken))
				throw new BadFileFormatException("Expected token (" + expectedToken + ") not found but found " + token);
		}
		catch (NoSuchElementException e)
		{
			throw new BadFileFormatException(e);
		}
		
	}
	
	private TaglioBanconota parseBanconota(String token) throws BadFileFormatException
	{
		int taglio = Integer.parseInt(token);
		for (TaglioBanconota b : TaglioBanconota.values())
		{
			if (taglio == b.getValore())
			{
				return b;
			}
		}
		throw new BadFileFormatException("Banconota non riconosciuta");
	}

}
