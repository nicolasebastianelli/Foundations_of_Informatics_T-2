package doc.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.Test;

import doc.model.DentoStrategiaConfigurabile;
import doc.persistence.MalformedFileException;

public class TextStrategiaRepositoryTests {

	@Test
	public void testTextStrategieRepository() throws IOException,
			MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest(
				"Cipz");

		assertEquals(0, sr.getAll().size());
	}

	@Test
	public void testReadElement() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo	SU	240	ORE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		DentoStrategiaConfigurabile dsc = sr.exposedReadElement(reader);
		
		assertEquals("Orzo", dsc.getNome());
		assertEquals(240, dsc.getOre());
		Map<String, Integer> pm = dsc.getPunteggiBevande();
		assertEquals(3, pm.size());
		assertNotNull(pm.get("Caffè"));
		assertEquals(2, (int) pm.get("Caffè"));
		assertNotNull(pm.get("Cioccolata"));
		assertEquals(-1, (int) pm.get("Cioccolata"));
		assertNotNull(pm.get("Orzo"));
		assertEquals(-2, (int) pm.get("Orzo"));
		
		String lastLine = reader.readLine();
		assertEquals("THIS LINE SHOULD NOT BE READ!!!", lastLine);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingSTRATEGIA() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "XSTRATEGIAX	Orzo	SU	240	ORE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
//	@Test(expected = MalformedFileException.class)
//	public void testReadElement_ORIG() throws IOException, MalformedFileException {
//		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
//		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
//		
//		String toRead = "STRATEGIA	Orzo	SU	240	ORE\n" +
//					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
//					"FINE STRATEGIA\n" +
//					"THIS LINE SHOULD NOT BE READ!!!";
//		BufferedReader reader = new BufferedReader(new StringReader(toRead));
//		sr.exposedReadElement(reader);
//	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingTabBetweenSTRATEGIAAndOrzo() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA Orzo	SU	240	ORE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingTabBetweenOrzoAndSU() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo SU	240	ORE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingTabBetweedSUAnd240() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo	SU 240	ORE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_NumberFormatExceptionReadingOre() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo	SU	2X40	ORE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingTabBetween240AndORE() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo	SU	240 ORE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingORE() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo	SU	240	OXRE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingEqualsSign1() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo	SU	240	ORE\n" +
					"Caffè 2, Cioccolata = -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingEqualsSign2() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo	SU	240	ORE\n" +
					"Caffè = 2, Cioccolata -1, Orzo = -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingEqualsSign3() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo	SU	240	ORE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo -2\n" +
					"FINE STRATEGIA\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadElement_MissingFINE_STRATEGIA() throws IOException, MalformedFileException {
		TextStrategiaRepositoryUnderTest.setReader(new StringReader(""));
		TextStrategiaRepositoryUnderTest sr = new TextStrategiaRepositoryUnderTest("Cipz");
		
		String toRead = "STRATEGIA	Orzo	SU	240	ORE\n" +
					"Caffè = 2, Cioccolata = -1, Orzo = -2\n" +
					"XFINEXSTRATEGIAX\n" +
					"THIS LINE SHOULD NOT BE READ!!!";
		BufferedReader reader = new BufferedReader(new StringReader(toRead));
		sr.exposedReadElement(reader);
	}
}
