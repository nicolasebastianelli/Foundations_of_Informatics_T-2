package rs.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import rs.model.Month;
import rs.model.StimaRischio;
import rs.persistence.BadFileFormatException;
import rs.persistence.MyStimaRischioReader;

public class MyStimaRischioReaderTests {

	@Test
	public void testReadFrom() throws IOException, BadFileFormatException {
		String toRead = "Bologna | luglio 2013 20%\nBologna | febbraio 2013 70%\nMarina di Ravenna | dicembre 2014 80%\n";
		StringReader innerReader = new StringReader(toRead);
		
		MyStimaRischioReader reader = new MyStimaRischioReader();
		Map<String, Collection<StimaRischio>> map = reader.readFrom(innerReader);
		
		assertEquals(2, map.size());
		assertTrue(map.containsKey("Bologna"));
		assertTrue(map.containsKey("Marina di Ravenna"));
		
		ArrayList<StimaRischio> stimeRischio;
		StimaRischio stimaRischio;
		
		stimeRischio = new ArrayList<StimaRischio>(map.get("Bologna"));
		assertEquals(2, stimeRischio.size());
		stimaRischio = stimeRischio.get(0);
		assertEquals("Bologna", stimaRischio.getCitta());
		assertEquals(Month.JULY, stimaRischio.getMese());
		assertEquals(2013, stimaRischio.getAnno());
		assertEquals(20, stimaRischio.getRischio());	
		stimaRischio = stimeRischio.get(1);
		assertEquals("Bologna", stimaRischio.getCitta());
		assertEquals(Month.FEBRUARY, stimaRischio.getMese());
		assertEquals(2013, stimaRischio.getAnno());
		assertEquals(70, stimaRischio.getRischio());
		
		stimeRischio = new ArrayList<StimaRischio>(map.get("Marina di Ravenna"));
		stimaRischio = stimeRischio.get(0);
		assertEquals(1, stimeRischio.size());
		assertEquals("Marina di Ravenna", stimaRischio.getCitta());
		assertEquals(Month.DECEMBER, stimaRischio.getMese());
		assertEquals(2014, stimaRischio.getAnno());
		assertEquals(80, stimaRischio.getRischio());
	}
		
	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_FailMissingLocalitaToken() throws IOException, BadFileFormatException {
		String toRead = "Bologna | luglio 2013 20%\nBologna | febbraio 2013 70%\n | dicembre 2014 80%\n";
		StringReader innerReader = new StringReader(toRead);
		
		MyStimaRischioReader reader = new MyStimaRischioReader();
		reader.readFrom(innerReader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_FailMalformedMonth() throws IOException, BadFileFormatException {
		String toRead = "Bologna | luglio 2013 20%\nBologna | febxxxbraio 2013 70%\nMarina di Ravenna | dicembre 2014 80%\n";
		StringReader innerReader = new StringReader(toRead);
		
		MyStimaRischioReader reader = new MyStimaRischioReader();
		reader.readFrom(innerReader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_FailMalformedMissingMonth() throws IOException, BadFileFormatException {
		String toRead = "Bologna | luglio 2013 20%\nBologna | 2013 70%\nMarina di Ravenna | dicembre 2014 80%\n";
		StringReader innerReader = new StringReader(toRead);
		
		MyStimaRischioReader reader = new MyStimaRischioReader();
		reader.readFrom(innerReader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_FailMissingYear() throws IOException, BadFileFormatException {
		String toRead = "Bologna | luglio 2013 20%\nBologna | febbraio 70%\nMarina di Ravenna | dicembre 2014 80%\n";
		StringReader innerReader = new StringReader(toRead);
		
		MyStimaRischioReader reader = new MyStimaRischioReader();
		reader.readFrom(innerReader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_FailMalformedYear() throws IOException, BadFileFormatException {
		String toRead = "Bologna | luglio 2013 20%\nBologna | febbraio 20xxx13 70%\nMarina di Ravenna | dicembre 2014 80%\n";
		StringReader innerReader = new StringReader(toRead);
		
		MyStimaRischioReader reader = new MyStimaRischioReader();
		reader.readFrom(innerReader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_FailRischioNotANumber() throws IOException, BadFileFormatException {
		String toRead = "Bologna | luglio 2013 20%\nBologna | febbraio 2013 7xx0%\nMarina di Ravenna | dicembre 2014 80%\n";
		StringReader innerReader = new StringReader(toRead);
		
		MyStimaRischioReader reader = new MyStimaRischioReader();
		reader.readFrom(innerReader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_FailMissingRischio() throws IOException, BadFileFormatException {
		String toRead = "Bologna | luglio 2013 20%\nBologna | febbraio 2013\nMarina di Ravenna | dicembre 2014 80%\n";
		StringReader innerReader = new StringReader(toRead);
		
		MyStimaRischioReader reader = new MyStimaRischioReader();
		reader.readFrom(innerReader);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_FailMissingPercSign() throws IOException, BadFileFormatException {
		String toRead = "Bologna | luglio 2013 20%\nBologna | febbraio 2013 70x\nMarina di Ravenna | dicembre 2014 80%\n";
		StringReader innerReader = new StringReader(toRead);
		
		MyStimaRischioReader reader = new MyStimaRischioReader();
		reader.readFrom(innerReader);
	}

}
