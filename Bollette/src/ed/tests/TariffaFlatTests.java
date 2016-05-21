package ed.tests;

import static org.junit.Assert.*;
import static ed.tests.BollettaTestUtils.*;

import org.junit.Test;

import ed.model.*;

public class TariffaFlatTests
{
	
	@Test
	public void testFlat_Soglia_SottoSoglia()
	{
		TariffaFlat t = new TariffaFlat("Flat", 39, 250, 0.25);
		Bolletta bolletta = t.creaBolletta(TestData.UtenteTest, 10, 2012, 100);	
		mustHave(bolletta, 39);
		mustHave(bolletta, 0);
		mustHave(bolletta, 3.9);
		mustHave(bolletta, 42.9);		
	}
	
	@Test
	public void testFlat_Soglia_SopraSoglia()
	{
		TariffaFlat t = new TariffaFlat("Soglia", 39, 250, 0.25);
		Bolletta bolletta = t.creaBolletta(TestData.UtenteTest, 10, 2012, 300);
		mustHave(bolletta, 39);
		mustHave(bolletta, 12.50);
		mustHave(bolletta, 3.41);
		mustHave(bolletta, 5.49);
		mustHave(bolletta, 60.40);
	}

	@Test
	public void testTariffaAFlatCtor()
	{
		TariffaFlat t = new TariffaFlat("Pluto", 20, 10, 1);
		assertEquals("Pluto", t.getNome());
		assertEquals(1, t.getPrezzoKWhExtra(), 0.01);
		assertEquals(20, t.getQuotaFissaMensile(), 0.01);
		assertEquals(10, t.getSogliaMensile(), 0.01);
	}
}
