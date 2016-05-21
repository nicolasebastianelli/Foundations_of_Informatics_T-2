package zannonia.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.Test;

import zannonia.persistence.MalformedFileException;
import zannonia.persistence.MyTratteReader;
import zannonia.model.*;

public class TratteReaderTests
{

	@Test
	public void testReadTratte() throws IOException, MalformedFileException
	{
		String txt = "pr-mo	48	3.10	Canossa\nmo	 	2	0.00\nmo-bo	31	2.40	Modena Sud, Bologna Ovest\n";
		MyTratteReader reader = new MyTratteReader();
		Map<String, Tratta> tratte = reader.readTratte(new StringReader(txt));
		
		assertEquals(3, tratte.size());
		
		Tratta t = tratte.get("PR-MO");
		assertNotNull(t);
		assertEquals("pr-mo", t.getId());
		assertEquals(48, t.getLunghezza(), 0.001);
		assertEquals(3.1, t.getPedaggio(), 0.001);
		assertEquals(1, t.getCaselli().size());
		assertEquals("Canossa", t.getCaselli().get(0).getNome());
		
		t = tratte.get("MO");
		assertNotNull(t);
		assertEquals("mo", t.getId());
		assertEquals(2, t.getLunghezza(), 0.001);
		assertEquals(0, t.getPedaggio(), 0.001);
		assertEquals(0, t.getCaselli().size());
		
		t = tratte.get("MO-BO");
		assertNotNull(t);
		assertEquals("mo-bo", t.getId());
		assertEquals(31, t.getLunghezza(), 0.001);
		assertEquals(2.4, t.getPedaggio(), 0.001);
		assertEquals(2, t.getCaselli().size());
		assertEquals("Modena Sud", t.getCaselli().get(0).getNome());
		assertEquals("Bologna Ovest", t.getCaselli().get(1).getNome());
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTratte_MalformedLunghezza() throws IOException, MalformedFileException
	{
		String txt = "pr-mo	48	3.10	Canossa\nmo	 	b2	0.00\nmo-bo	31	2.40	Modena Sud, Bologna Ovest\n";
		MyTratteReader reader = new MyTratteReader();
		reader.readTratte(new StringReader(txt));
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTratte_MalformedPedaggio() throws IOException, MalformedFileException
	{
		String txt = "pr-mo	48	3.10	Canossa\nmo	 	2	0x00\nmo-bo	31	2.40	Modena Sud, Bologna Ovest\n";
		MyTratteReader reader = new MyTratteReader();
		reader.readTratte(new StringReader(txt));
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTratte_MalformedLine_MissingField() throws IOException, MalformedFileException
	{
		String txt = "pr-mo	48	3.10	Canossa\nmo	 	2\nmo-bo	31	2.40	Modena Sud, Bologna Ovest\n";
		MyTratteReader reader = new MyTratteReader();
		reader.readTratte(new StringReader(txt));
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTratte_MalformedLine_MissingEverything() throws IOException, MalformedFileException
	{
		String txt = "pr-mo	48	3.10	Canossa\n  \nmo-bo	31	2.40	Modena Sud, Bologna Ovest\n";
		MyTratteReader reader = new MyTratteReader();
		reader.readTratte(new StringReader(txt));
	}

}
