package ed.tests;

import static org.junit.Assert.*;
import static ed.tests.BollettaTestUtils.*;

import org.junit.Test;

import ed.model.*;

public class TariffaAConsumoTests
{

	@Test
	public void testAConsumo_NoSoglia()
	{
		TariffaAConsumo t = new TariffaAConsumo("NoSoglia", 0.14);
		Bolletta bolletta = t.creaBolletta(TestData.UtenteTest, 10, 2012, 100);
		mustHave(bolletta, 21);
		mustHave(bolletta, 0);
		mustHave(bolletta, 2.10);
		mustHave(bolletta, 23.10);
		
	}
	
	@Test
	public void testAConsumo_Soglia_SottoSoglia()
	{
		TariffaAConsumo t = new TariffaAConsumo("Soglia", 0.14, 200, 0.20);
		Bolletta bolletta = t.creaBolletta(TestData.UtenteTest, 10, 2012, 100);
		mustHave(bolletta, 21);
		mustHave(bolletta, 0);
		mustHave(bolletta, 2.10);
		mustHave(bolletta, 23.10);
	}
	
	@Test
	public void testAConsumo_Soglia_SopraSoglia()
	{
		TariffaAConsumo t = new TariffaAConsumo("Soglia", 0.119, 150, 0.162);
		Bolletta bolletta = t.creaBolletta(TestData.UtenteTest, 10, 2012, 300);
		mustHave(bolletta, 26.78);
		mustHave(bolletta, 36.45);
		mustHave(bolletta, 3.41);
		mustHave(bolletta, 6.66);
		mustHave(bolletta, 73.29);
	}

	@Test
	public void testTariffaAConsumoCtor1()
	{
		TariffaAConsumo t = new TariffaAConsumo("Pippo", 0.1);
		assertFalse(t.hasSogliaMensile());
		assertEquals("Pippo", t.getNome());
		assertEquals(0.1, t.getPrezzoKWh(), 0.01);
	}

	@Test
	public void testTariffaAConsumoCtor2()
	{
		TariffaAConsumo t = new TariffaAConsumo("Pluto", 0.1, 100, 0.2);
		assertTrue(t.hasSogliaMensile());
		assertEquals("Pluto", t.getNome());
		assertEquals(0.1, t.getPrezzoKWh(), 0.01);
		assertEquals(0.2, t.getPrezzoKWhExtra(), 0.01);
		assertEquals(100, t.getSogliaMensile(), 1);
	}
}
