package zannonia.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import zannonia.model.Autostrada;
import zannonia.model.Tratta;
import zannonia.persistence.MalformedFileException;
import zannonia.persistence.MyAutostradeReader;

public class AutostradeReaderTests
{

	@Test
	public void testReadAutostrade() throws IOException, MalformedFileException
	{
		HashMap<String, Tratta> tratte = new HashMap<String, Tratta>();		
		tratte.put("MO-BO", new Tratta("mo-bo", 1, 10));
		tratte.put("BO", new Tratta("bo", 2, 20));
		tratte.put("BO-FI", new Tratta("bo-fi", 3, 30));
		tratte.put("FI", new Tratta("fi", 4, 40));
		tratte.put("BO-RN", new Tratta("bo-RN", 5, 50));		
		
		String txt = "Z1  mo-bo bo bo-fi fi\nZ14 bo bo-rn";
		MyAutostradeReader reader = new MyAutostradeReader();
		
		List<Autostrada> autos = reader.readAutostrade(new StringReader(txt), tratte);
		
		assertEquals(2, autos.size());
		
		Autostrada a = autos.get(0);
		assertEquals("Z1", a.getNome());
		assertEquals(4, a.getTratte().size());
		assertEquals(tratte.get("MO-BO"), a.getTratte().get(0));
		assertEquals(tratte.get("BO"), a.getTratte().get(1));
		assertEquals(tratte.get("BO-FI"), a.getTratte().get(2));
		assertEquals(tratte.get("FI"), a.getTratte().get(3));
		
		a = autos.get(1);
		assertEquals("Z14", a.getNome());
		assertEquals(2, a.getTratte().size());
		assertEquals(tratte.get("BO"), a.getTratte().get(0));
		assertEquals(tratte.get("BO-RN"), a.getTratte().get(1));
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadAutostrade_MalformedMissingTratta() throws IOException, MalformedFileException
	{
		HashMap<String, Tratta> tratte = new HashMap<String, Tratta>();		
		tratte.put("MO-BO", new Tratta("mo-bo", 1, 10));
		tratte.put("BO", new Tratta("bo", 2, 20));
		tratte.put("BO-FI", new Tratta("bo-fi", 3, 30));
		tratte.put("FI", new Tratta("fi", 4, 40));
		tratte.put("BO-RN", new Tratta("bo-RN", 5, 50));		
		
		String txt = "Z1  mo-bo NO bo-fi fi\nZ14 bo bo-rn";
		MyAutostradeReader reader = new MyAutostradeReader();
		
		reader.readAutostrade(new StringReader(txt), tratte);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadAutostrade_MalformedMissingEverything() throws IOException, MalformedFileException
	{
		HashMap<String, Tratta> tratte = new HashMap<String, Tratta>();		
		tratte.put("MO-BO", new Tratta("mo-bo", 1, 10));
		tratte.put("BO", new Tratta("bo", 2, 20));
		tratte.put("BO-FI", new Tratta("bo-fi", 3, 30));
		tratte.put("FI", new Tratta("fi", 4, 40));
		tratte.put("BO-RN", new Tratta("bo-RN", 5, 50));		
		
		String txt = "Z1  mo-bo bo bo-fi fi\n \n";
		MyAutostradeReader reader = new MyAutostradeReader();
		
		reader.readAutostrade(new StringReader(txt), tratte);
	}
}
