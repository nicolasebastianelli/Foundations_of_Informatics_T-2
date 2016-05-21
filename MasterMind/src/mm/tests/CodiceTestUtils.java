package mm.tests;

import mm.model.Codice;
import mm.model.GeneratoreCodice;

public class CodiceTestUtils
{	
	public static Codice createDifferentFrom(Codice codice)
	{
		Codice generated;
		do
		{
			generated = GeneratoreCodice.creaCodice();			
		}
		while (codice.equals(generated));
		return generated;
	}
}
