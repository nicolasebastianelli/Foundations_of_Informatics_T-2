package gattovolpe.holidays.tests;

import static org.junit.Assert.*;
import static gattovolpe.utils.DateUtil.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import gattovolpe.holidays.controller.Controller;
import gattovolpe.holidays.controller.MyController;
import gattovolpe.holidays.model.Destinazione;
import gattovolpe.holidays.model.GattoVolpeHolidays;
import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;
import gattovolpe.holidays.persistence.InvalidPackageFormatException;
import gattovolpe.holidays.persistence.PackageReader;
import gattovolpe.utils.DateUtil;

import org.junit.BeforeClass;
import org.junit.Test;

public class ControllerTest {
	
	private static Controller controller = null;

	@BeforeClass
	public static void setup() throws InvalidPackageFormatException {
		
		GattoVolpeHolidays gvh = 
				new GattoVolpeHolidays();
		
		gvh.caricaPacchetti( new PackageMockReader() );
		
		controller =
				new MyController( gvh );
	}
	
	class TestComparator implements Comparator<Pacchetto> {
		@Override
		public int compare(Pacchetto arg0, Pacchetto arg1) {
			return 0;
		}
	}
	
	@Test
	public void test_base() throws ParseException, InvalidPackageFormatException {
		List<Pacchetto> completo = 
				controller.evalPackage( new TestComparator(),
						TipoPacchetto.COMPLETO,
						"ITALIA",
						2000,
						parseDate("01/01/2012"),
						parseDate("31/12/2012") );
		
		assertEquals(3, completo.size());
	}
	
	@Test
	public void test_filtraggio_tipo_vacanza() throws ParseException, InvalidPackageFormatException {
		List<Pacchetto> completo =
				controller.evalPackage( new TestComparator(),
				TipoPacchetto.COMPLETO, 
				"ITALIA",
				555,
				parseDate("01/07/2012"), 
				parseDate("01/08/2012"));
		assertEquals(3, completo.size());
		for(Pacchetto p : completo) {
			assertEquals(TipoPacchetto.COMPLETO, p.getTipo());
		}
	}
	
	@Test
	public void test_filtraggio_data_iniziale() throws ParseException, InvalidPackageFormatException {
		List<Pacchetto> datainiziale =
				controller.evalPackage( new TestComparator(),
				TipoPacchetto.COMPLETO, 
				"ITALIA",
				555,
				parseDate("02/07/2012"), 
				parseDate("30/07/2012"));
		assertEquals(2, datainiziale.size());
		for(Pacchetto p : datainiziale) {
			assertTrue( getDateDifference(parseDate("02/07/2012"), p.getDataInizio()) >=0);
		}
	}
	
	@Test
	public void test_prezzo_max() throws ParseException, InvalidPackageFormatException {
		List<Pacchetto> cinquecento =
				controller.evalPackage( new TestComparator(),
				TipoPacchetto.COMPLETO, 
				"ITALIA",
				500,
				parseDate("01/07/2012"), 
				parseDate("30/07/2012"));
		assertEquals(2, cinquecento.size());
		for(Pacchetto p : cinquecento) {
			assertTrue(p.getCosto()<=500);
		}
	}
	
	@Test
	public void test_filtraggio_destinazione() throws ParseException, InvalidPackageFormatException {
		List<Pacchetto> italia =
				controller.evalPackage( new TestComparator(),
				TipoPacchetto.COMPLETO, 
				"ITALIA",
				600,
				parseDate("01/07/2012"), 
				parseDate("30/07/2012"));
		assertEquals(3, italia.size());
		for(Pacchetto p : italia) {
			assertEquals("ITALIA", p.getDestinazione().getStato());
		}
	}
	
	@Test
	public void test_filtraggio_data_finale() throws ParseException, InvalidPackageFormatException {
		List<Pacchetto> datafinale =
				controller.evalPackage( new TestComparator(),
				TipoPacchetto.COMPLETO, 
				"ITALIA",
				555,
				parseDate("1/07/2012"), 
				parseDate("29/07/2012"));
		assertEquals(2, datafinale.size());
		for(Pacchetto p : datafinale) {
			Calendar cal =
					new GregorianCalendar();
			cal.setTime(p.getDataInizio());
			cal.add(Calendar.DAY_OF_MONTH, p.getDurataGiorni());
			assertTrue( getDateDifference(parseDate("29/07/2012"), normalizeDate(cal.getTime())) <=0);
		}
	}
	
	@Test
	public void test_get_destinazioni() {

		Set<Destinazione> destinazioni =
				controller.getDestinazioni();

		Destinazione antigua =
				new Destinazione();
		antigua.setStato("ANTIGUA");
		antigua.setLuogo("notavail");

		Destinazione italia =
				new Destinazione();
		italia.setStato("ITALIA");
		italia.setLuogo("Trento");

		Destinazione cuba =
				new Destinazione();
		cuba.setStato("CUBA");
		cuba.setLuogo("Guardalavaca");

		assertTrue( destinazioni.contains(antigua) );
		assertTrue( destinazioni.contains(italia) );
		assertFalse( destinazioni.contains(cuba) );
		assertEquals(2, destinazioni.size());
	}

}

class PackageMockReader implements PackageReader 
{

	@Override
	public List<Pacchetto> readPackages() throws InvalidPackageFormatException {

		List<Pacchetto> pacchetti = new ArrayList<Pacchetto>();

		Pacchetto p1 = new Pacchetto();
		Destinazione d1 = new Destinazione();

		d1.setLuogo("notavail");
		d1.setStato("ANTIGUA");
		p1.setDestinazione(d1);
		p1.setCosto(1417);
		try {
			p1.setDataInizio(DateUtil.parseDate("01/08/2012"));
		} catch (InvalidPackageFormatException e) {
			e.printStackTrace();
		}
		p1.setDescrizione("descr\ndescrizione linea 2");
		p1.setDurataGiorni(0);
		p1.setNome("ANTIGUA0");
		p1.setTipo(TipoPacchetto.SOLOVOLO);

		pacchetti.add(p1);

		p1 = new Pacchetto();
		d1 = new Destinazione();
		d1.setLuogo("Trento");
		d1.setStato("ITALIA");
		p1.setDestinazione(d1);
		p1.setCosto(490);
		try {
			p1.setDataInizio(DateUtil.parseDate("1/07/2012"));
		} catch (InvalidPackageFormatException e) {
			e.printStackTrace();
		}
		p1.setDescrizione(null);
		p1.setDurataGiorni(15);
		p1.setNome("ITALIA0");
		p1.setTipo(TipoPacchetto.COMPLETO);

		pacchetti.add(p1);

		p1 = new Pacchetto();
		p1.setDestinazione(d1);
		p1.setCosto(500);
		try {
			p1.setDataInizio(DateUtil.parseDate("02/07/2012"));
		} catch (InvalidPackageFormatException e) {
			e.printStackTrace();
		}
		p1.setDescrizione("cavareno passo mendola");
		p1.setDurataGiorni(15);
		p1.setNome("ITALIA2");
		p1.setTipo(TipoPacchetto.COMPLETO);

		pacchetti.add(p1);

		p1 = new Pacchetto();
		p1.setDestinazione(d1);
		p1.setCosto(555);
		try {
			p1.setDataInizio(DateUtil.parseDate("15/07/2012"));
		} catch (InvalidPackageFormatException e) {
			e.printStackTrace();
		}
		p1.setDescrizione(null);
		p1.setDurataGiorni(15);
		p1.setNome("ITALIA3");
		p1.setTipo(TipoPacchetto.COMPLETO);

		pacchetti.add(p1);

		return pacchetti;
	}
		
}
