package ed.tests;

import static org.junit.Assert.fail;
import ed.model.Bolletta;
import ed.model.LineaBolletta;
import ed.model.Utente;

public class BollettaTestUtils
{	
	public static void mustHave(Bolletta b, double valore)
	{
		for (LineaBolletta linea : b.getLineeBolletta())
		{
			if (valore - 0.01 < linea.getValore() && linea.getValore() < valore + 0.01)
			{
				return;
			}
		}
		fail("LineaBolletta con valore " + valore + " non trovata.");
	}
	
	public static Utente getUtente()
	{
		return null;
	}
}
