package mm.persistence;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import mm.model.Partita;

public class MyPartitaPersister implements PartitaPersister
{

	@Override
	public Partita read(InputStream source) throws IOException, BadFileFormatException
	{
		ObjectInputStream ois = new ObjectInputStream(source);
		try
		{
			Partita result = (Partita) ois.readObject();
			ois.close();
			return result;
		}
		catch (ClassNotFoundException | EOFException e)
		{
			try
			{
				ois.close();
			}
			catch (Exception ex)
			{				
			}
			throw new BadFileFormatException("Errore nel formato del file", e);
		}
	}

	@Override
	public void write(Partita partita, OutputStream dest) throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(dest);
		oos.writeObject(partita);
		try
		{
			oos.close();
		}
		catch (Exception ex)
		{				
		}
	}

}
