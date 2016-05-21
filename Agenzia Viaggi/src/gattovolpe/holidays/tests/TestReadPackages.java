package gattovolpe.holidays.tests;

import static org.junit.Assert.*;
import static gattovolpe.utils.DateUtil.*;

import java.io.StringReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;
import gattovolpe.holidays.persistence.InvalidPackageFormatException;
import gattovolpe.holidays.persistence.MyPackageReader;
import gattovolpe.holidays.persistence.PackageReader;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestReadPackages {
	
	private static List<Pacchetto> pacchettiTest;

	@BeforeClass
	public static void setup() throws InvalidPackageFormatException {
		
		StringBuilder sb =
				new StringBuilder();
		sb.append("STARTPACKAGE ANTIGUA0 SOLOVOLO\nANTIGUA, notavail\n01/08/2012, 0 giorni\nEUR 1417\ndescr\ndescrizione linea 2\nENDPACKAGE\n");
		sb.append("STARTPACKAGE ITALIA0 COMPLETO\nITALIA, Trento\n02/07/2012, 15 giorni\nEUR 500\ncavareno passo mendola\nENDPACKAGE\n");
		sb.append("STARTPACKAGE ITALIA0 COMPLETO\nITALIA, Trento\n02/07/2012, 15 giorni\nEUR 500\nENDPACKAGE\n");
		
		PackageReader reader =
				new MyPackageReader( new StringReader(sb.toString()) );
		
		pacchettiTest = reader.readPackages();
		assertEquals(3, pacchettiTest.size());
	}
	
	@Test
	public void testPacchetto1() {
		Pacchetto pacchetto =
				pacchettiTest.get(0);
		assertEquals(1417, pacchetto.getCosto(), 0.1);
		assertEquals(0, pacchetto.getDurataGiorni());
		Calendar myCal = 
				new GregorianCalendar();
		myCal.set(2012, Calendar.AUGUST, 1);
		assertEquals(normalizeDate(myCal.getTime()), normalizeDate(pacchetto.getDataInizio()));
		assertEquals(TipoPacchetto.SOLOVOLO, pacchetto.getTipo());
		assertEquals("ANTIGUA0", pacchetto.getNome());
		assertEquals("ANTIGUA", pacchetto.getDestinazione().getStato());
		assertEquals("notavail", pacchetto.getDestinazione().getLuogo());
		assertEquals("descr\ndescrizione linea 2\n", pacchetto.getDescrizione());
	}

	@Test
	public void testPacchetto2() {
		Pacchetto pacchetto =
				pacchettiTest.get(1);
		assertEquals(500, pacchetto.getCosto(), 0.1);
		assertEquals(15, pacchetto.getDurataGiorni());
		Calendar myCal = 
				new GregorianCalendar();
		myCal.set(2012, Calendar.JULY, 2);
		assertEquals(normalizeDate(myCal.getTime()), normalizeDate(pacchetto.getDataInizio()));
		assertEquals(TipoPacchetto.COMPLETO, pacchetto.getTipo());
		assertEquals("ITALIA0", pacchetto.getNome());
		assertEquals("ITALIA", pacchetto.getDestinazione().getStato());
		assertEquals("Trento", pacchetto.getDestinazione().getLuogo());
		assertEquals("cavareno passo mendola\n", pacchetto.getDescrizione());
	}
	
	@Test
	public void testPacchetto3() {
		Pacchetto pacchetto =
				pacchettiTest.get(2);
		assertEquals(500, pacchetto.getCosto(), 0.1);
		assertEquals(15, pacchetto.getDurataGiorni());
		Calendar myCal = 
				new GregorianCalendar();
		myCal.set(2012, Calendar.JULY, 2);
		assertEquals(normalizeDate(myCal.getTime()), normalizeDate(pacchetto.getDataInizio()));
		assertEquals(TipoPacchetto.COMPLETO, pacchetto.getTipo());
		assertEquals("ITALIA0", pacchetto.getNome());
		assertEquals("ITALIA", pacchetto.getDestinazione().getStato());
		assertEquals("Trento", pacchetto.getDestinazione().getLuogo());
		assertEquals("", pacchetto.getDescrizione());
	}
	
	@Test(expected=InvalidPackageFormatException.class)
	public void test_startpackage_errato() throws InvalidPackageFormatException {	
		PackageReader reader =
				new MyPackageReader( new StringReader("START_____GE MALFORMATO\nITALIA, Trento\n02/07/2012, 15 giorni\nEUR 500\ncavareno passo mendola\nENDPACKAGE\n") );
		reader.readPackages();
	}
	
	@Test(expected=InvalidPackageFormatException.class)
	public void test_manca_tipo_pacchetto() throws InvalidPackageFormatException {	
		PackageReader reader =
				new MyPackageReader( new StringReader("STARTPACKAGE MALFORMATO1\nITALIA, Trento\n02/07/2012, 15 giorni\nEUR 500\ncavareno passo mendola\nENDPACKAGE\n") );
		reader.readPackages();
	}
	
	@Test(expected=InvalidPackageFormatException.class)
	public void test_tipo_pacchetto_non_enum_valido() throws InvalidPackageFormatException {	
		PackageReader reader =
				new MyPackageReader( new StringReader("STARTPACKAGE MALFORMED2 BADENUM\nITALIA, Trento\n02/07/2012, 15 giorni\nEUR 500\ncavareno passo mendola\nENDPACKAGE\n") );
		reader.readPackages();
	}
	
	@Test(expected=InvalidPackageFormatException.class)
	public void test_destinazione_non_valida_manca_luogo() throws InvalidPackageFormatException {	
		PackageReader reader =
				new MyPackageReader( new StringReader("STARTPACKAGE MALFORMED4 VOLOHOTEL\nTrieste\n02/07/2012, 15 giorni\nEUR 500\ncavareno passo mendola\nENDPACKAGE\n") );
		reader.readPackages();
	}
	
	@Test(expected=InvalidPackageFormatException.class)
	public void test_durata_non_in_giorni() throws InvalidPackageFormatException {	
		PackageReader reader =
				new MyPackageReader( new StringReader("STARTPACKAGE MALFORMED6 VOLOHOTEL\nITALIA, Trieste\n02/7/2012, 15 mesi\nEUR 500\ncavareno passo mendola\nENDPACKAGE\n") );
		reader.readPackages();
	}
	
	@Test(expected=InvalidPackageFormatException.class)
	public void test_prezzo_non_in_euro() throws InvalidPackageFormatException {	
		PackageReader reader =
				new MyPackageReader( new StringReader("STARTPACKAGE MALFORMED6 VOLOHOTEL\nITALIA, Trieste\n02/7/2012, 15 mesi\nUSD 500\ncavareno passo mendola\nENDPACKAGE\n") );
		reader.readPackages();
	}
	
	@Test(expected=InvalidPackageFormatException.class)
	public void test_prezzo_non_valido() throws InvalidPackageFormatException {	
		PackageReader reader =
				new MyPackageReader( new StringReader("STARTPACKAGE MALFORMED6 VOLOHOTEL\nITALIA, Trieste\n02/7/2012, 15 mesi\nEUR cinquecento\ncavareno passo mendola\nENDPACKAGE\n") );
		reader.readPackages();
	}
	
}
