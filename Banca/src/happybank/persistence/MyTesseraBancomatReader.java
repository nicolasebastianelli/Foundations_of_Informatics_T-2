package happybank.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import happybank.model.ContoCorrente;
import happybank.model.TesseraBancomat;

public class MyTesseraBancomatReader implements TesseraBancomatReader
{

	@Override
	public List<TesseraBancomat> read(Reader reader, Map<String, ContoCorrente> contoCorrenteMap) throws IOException,
			BadFileFormatException
	{
		BufferedReader buffered = new BufferedReader(reader);
		ArrayList<TesseraBancomat> tessere = new ArrayList<TesseraBancomat>();

		try
		{
			String line;
			while ((line = buffered.readLine()) != null)
			{
				StringTokenizer tokenizer = new StringTokenizer(line);
				String id = tokenizer.nextToken();
				String pin = tokenizer.nextToken();
				int maxPrelievo = Integer.parseInt(tokenizer.nextToken());

				ContoCorrente cc = contoCorrenteMap.get(id);
				if (cc == null)
					throw new BadFileFormatException("Conto Corrente non trovato con id: " + id);

				tessere.add(new TesseraBancomat(pin, maxPrelievo, cc));
			}
		}
		catch (NumberFormatException | NoSuchElementException e)
		{
			throw new BadFileFormatException(e);
		}

		return tessere;
	}

}
