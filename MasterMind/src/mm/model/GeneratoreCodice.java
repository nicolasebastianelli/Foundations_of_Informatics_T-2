package mm.model;

import java.util.Random;

public final class GeneratoreCodice
{
	private static final Random random = new Random();
	
	public static Codice creaCodice()
	{
		Colore[] colori = new Colore[Configuration.LunghezzaCodice];
		Colore[] allValues = Colore.values();
		for (int i = 0; i < colori.length; i++)
		{
			colori[i] = allValues[random.nextInt(allValues.length)];
		}
		return new Codice(colori);
	}
}
