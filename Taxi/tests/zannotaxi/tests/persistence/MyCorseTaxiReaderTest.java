package zannotaxi.tests.persistence;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import zannotaxi.ZannoUtils;
import zannotaxi.model.CorsaTaxi;
import zannotaxi.persistence.BadFileFormatException;
import zannotaxi.persistence.DataNotReadyException;
import zannotaxi.persistence.MyCorseTaxiReader;

public class MyCorseTaxiReaderTest {
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, IOException
	{
		ZannoUtils.main(new String[0]);
	}
	
	@Test
	public void tetsCostruttore() {
		new MyCorseTaxiReader();
	}

	@Test
	public void testLeggiCorse() throws FileNotFoundException, BadFileFormatException, IOException, DataNotReadyException {
		MyCorseTaxiReader objectToTest = new MyCorseTaxiReader();
		
		objectToTest.leggiCorse(new FileInputStream("CORSE.BIN"));
		
		objectToTest.getCorse();
	}

	@Test
	public void testGetCorseCasoStandard() throws FileNotFoundException, BadFileFormatException, IOException, DataNotReadyException {
		MyCorseTaxiReader objectToTest = new MyCorseTaxiReader();
		
		objectToTest.leggiCorse(new FileInputStream("CORSE.BIN"));
		
		List<CorsaTaxi> corse = objectToTest.getCorse();
		assertEquals(8, corse.size());
	}

	@Test(expected=DataNotReadyException.class)
	public void testGetCorseConDatiNonLetti() throws DataNotReadyException {
		MyCorseTaxiReader objectToTest = new MyCorseTaxiReader();
		
		objectToTest.getCorse();
	}
	
	@Test(expected=FileNotFoundException.class)
	public void testGetFileNonEsistente() throws FileNotFoundException, BadFileFormatException, IOException, DataNotReadyException {
		MyCorseTaxiReader objectToTest = new MyCorseTaxiReader();
		
		objectToTest.leggiCorse(new FileInputStream("NONESISTE.BIN"));
	}

	@Test(expected=DataNotReadyException.class)
	public void testGetCorseDopoLetturaInErroreRitornaNonLeggibile() throws DataNotReadyException, FileNotFoundException, BadFileFormatException, IOException {
		MyCorseTaxiReader objectToTest = new MyCorseTaxiReader();
		
		objectToTest.leggiCorse(new FileInputStream("CORSE.BIN"));
		
		List<CorsaTaxi> corse = objectToTest.getCorse();
		assertEquals(8, corse.size());
		
		try {
			objectToTest.leggiCorse(new FileInputStream("NONVALIDE.BIN"));
		} catch(BadFileFormatException e) {
			// ok
		}
		
		objectToTest.getCorse();
	}
	
}
