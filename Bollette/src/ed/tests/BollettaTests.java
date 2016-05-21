package ed.tests;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;

import ed.model.*;

public class BollettaTests
{

	@Test
	public void testBolletta()
	{
		Bolletta b = new Bolletta(TestData.UtenteTest, 10, 2020, "MyTariffa", 123);
		assertSame(TestData.UtenteTest, b.getUtente());
		assertEquals(10, b.getMese());
		assertEquals(2020, b.getAnno());
		assertEquals("MyTariffa", b.getNomeTariffa());
		assertEquals(123, b.getConsumo(), 0.001);
	}

	@Test
	public void testAddLineaBollettaLineaBolletta()
	{
		Bolletta b = new Bolletta(TestData.UtenteTest, 10, 2020, "MyTariffa", 123);
		assertEquals(0, b.getLineeBolletta().size());
		LineaBolletta lb = new LineaBolletta("Ciao", 100);
		b.addLineaBolletta(lb);
		assertEquals(1, b.getLineeBolletta().size());
		assertTrue(b.getLineeBolletta().contains(lb));
	}

	@Test
	public void testStampa()
	{
		Bolletta b = new Bolletta(TestData.UtenteTest, 10, 2020, "MyTariffa", 123);
		b.addLineaBolletta(new LineaBolletta("Ciao1", 100));
		b.addLineaBolletta(new LineaBolletta("Ciao2", 200));
		b.addLineaBolletta(new LineaBolletta("Ciao3", 300));
		b.addLineaBolletta(new LineaBolletta("Ciao4", 400));
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);		
		b.stampa(pw);		
		pw.close();
		
		String res = sw.toString();
		
		assertTrue(res.contains(TestData.Cognome));
		assertTrue(res.contains(TestData.Nome));
		assertTrue(res.contains(TestData.CF));
		assertTrue(res.contains("MyTariffa"));
		assertTrue(res.contains("Ottobre"));
		assertTrue(res.contains("2020"));
		assertTrue(res.contains("123"));
		
		assertTrue(res.contains("Ciao1"));
		assertTrue(res.contains("100"));
		
		assertTrue(res.contains("Ciao2"));
		assertTrue(res.contains("200"));

		assertTrue(res.contains("Ciao3"));
		assertTrue(res.contains("300"));

		assertTrue(res.contains("Ciao4"));
		assertTrue(res.contains("400"));

	}

}
