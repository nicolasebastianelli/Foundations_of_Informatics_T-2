package ed.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.junit.Test;

import ed.model.*;
import ed.persistence.*;

public class MyTariffaReaderTests
{

	@Test
	public void testLeggiTariffe() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00 / EXTRA 300\n" +
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLTRE 100\n";
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		ArrayList<Tariffa> tariffe = new ArrayList<Tariffa>(tariffaReader.leggiTariffe(reader));
		
		assertEquals(3, tariffe.size());
		
		Tariffa t = tariffe.get(0);	
		TariffaFlat tf = (TariffaFlat)t;
		assertEquals("FLAT1", t.getNome());
		assertEquals(100, tf.getSogliaMensile(), 0.01);
		assertEquals(200, tf.getQuotaFissaMensile(), 0.01);
		assertEquals(3, tf.getPrezzoKWhExtra(), 0.01);
		
		t = tariffe.get(1);
		TariffaAConsumo tc = (TariffaAConsumo)t;
		assertEquals("CONSUMO1", t.getNome());
		assertFalse(tc.hasSogliaMensile());
		assertEquals(0.1, tc.getPrezzoKWh(), 0.01);
		
		t = tariffe.get(2);
		tc = (TariffaAConsumo)t;
		assertEquals("CONSUMO2", t.getNome());
		assertEquals(0.202, tc.getPrezzoKWh(), 0.001);
		assertTrue(tc.hasSogliaMensile());
		assertEquals(0.303, tc.getPrezzoKWhExtra(), 0.001);
		assertEquals(100, tc.getSogliaMensile(), 0.01);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error1() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00\n" +	//Un campo in meno per tariffa FLAT
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLTRE 100\n";
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error2() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 1X00 / PREZZO 200.00 / EXTRA 300\n" + //NumberFormatException
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLTRE 100\n";
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error3() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 20X0.00 / EXTRA 300\n" + //NumberFormatException
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLTRE 100\n";
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error4() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00 / EXTRA 30X0\n" +	//NumberFormatException
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLTRE 100\n";
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error5() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOOGLIA 100 / PREZZO 200.00 / EXTRA 300\n" +	//Parola chiave errata (SOGLIA)
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLTRE 100\n";
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error6() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZZO 200.00 / EXTRA 300\n" +	//Parola chiave errata (PREZZO)
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLTRE 100\n";
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error7() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00 / EXXXTRA 300\n" +	//Parola chiave errata (EXTRA)
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLTRE 100\n";
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error8() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00 / EXTRA 300\n" +
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZZO 20.20 / 30.30 OLTRE 100\n";	//Parola chiave errata (PREZZO)
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error9() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00 / EXTRA 300\n" +
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLLLTRE 100\n";	//Parola chiave errata (OLTRE)
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error10() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00 / EXTRA 300\n" +
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20X.20 / 30.30 OLTRE 100\n"; //NumberFormatException
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error11() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00 / EXTRA 300\n" +
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30X.30 OLTRE 100\n";	//NumberFormatException
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffe_Error12() throws IOException, BadFileFormatException
	{
		String toRead = "FLAT / FLAT1     / SOGLIA 100 / PREZZO 200.00 / EXTRA 300\n" +
				"A CONSUMO / CONSUMO1 / PREZZO 10.00\n"+
				"A CONSUMO / CONSUMO2 / PREZZO 20.20 / 30.30 OLTRE 10X0\n";
		
		StringReader reader = new StringReader(toRead);
		MyTariffaReader tariffaReader = new MyTariffaReader();
		tariffaReader.leggiTariffe(reader);
	}

}
