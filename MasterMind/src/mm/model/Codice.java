package mm.model;

import java.io.Serializable;
import java.util.Arrays;

public class Codice implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Colore[] colori;

	public Codice(Colore[] colori)
	{
		if (colori.length != Configuration.LunghezzaCodice)
			throw new IllegalArgumentException();

		this.colori = Arrays.copyOf(colori, Configuration.LunghezzaCodice, Colore[].class);
	}

	public int getCount()
	{
		return colori.length;
	}

	public Colore getColore(int index)
	{
		return colori[index];
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Codice))
			return false;
		
		Codice other = (Codice) o;

		if (other.getCount() != getCount())
			return false;

		for (int i = 0; i < getCount(); ++i)
			if (getColore(i) != other.getColore(i))
				return false;

		return true;
	}
}
