package gattovolpe.holidays.tests;

import static gattovolpe.utils.DateUtil.*;
import static org.junit.Assert.*;

import gattovolpe.holidays.controller.CostoComparator;
import gattovolpe.holidays.controller.DurataComparator;
import gattovolpe.holidays.controller.InizioComparator;
import gattovolpe.holidays.model.Destinazione;
import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;
import gattovolpe.holidays.persistence.InvalidPackageFormatException;
import gattovolpe.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class ComparatorTests {
	
	private static List<Pacchetto> pacchetti;
	
	@BeforeClass
	public static void setUp() {
		pacchetti = new ArrayList<Pacchetto>();
		
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
		p1.setDurataGiorni(10);
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
		p1.setDurataGiorni(12);
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
		p1.setDurataGiorni(12);
		p1.setNome("ITALIA3");
		p1.setTipo(TipoPacchetto.COMPLETO);

		pacchetti.add(p1);
		
		p1 = new Pacchetto();
		p1.setDestinazione(d1);
		p1.setCosto(311);
		try {
			p1.setDataInizio(DateUtil.parseDate("10/07/2012"));
		} catch (InvalidPackageFormatException e) {
			e.printStackTrace();
		}
		p1.setDescrizione(null);
		p1.setDurataGiorni(15);
		p1.setNome("ITALIA4");
		p1.setTipo(TipoPacchetto.COMPLETO);

		pacchetti.add(p1);
	}
	
	@Test
	public void test_comparator_costo() {
		CostoComparator costo = new CostoComparator();
		assertTrue(costo.compare(pacchetti.get(0), pacchetti.get(1)) >0);
		assertTrue(costo.compare(pacchetti.get(1), pacchetti.get(2)) <0);
		pacchetti.get(2).setCosto(pacchetti.get(0).getCosto());
		assertEquals(0, costo.compare(pacchetti.get(0), pacchetti.get(2)));
	}
	
	@Test
	public void test_comparator_durata() {
		DurataComparator durata =
				new DurataComparator(11);
		assertTrue(durata.compare(pacchetti.get(4), pacchetti.get(1)) >0);
		assertTrue(durata.compare(pacchetti.get(2), pacchetti.get(4)) <0);
		assertEquals(0, durata.compare(pacchetti.get(1), pacchetti.get(2)));
	}
	
	@Test
	public void test_comparator_inizio() throws InvalidPackageFormatException {
		InizioComparator inizio =
				new InizioComparator(parseDate("6/7/2012"));
		assertTrue(inizio.compare(pacchetti.get(3), pacchetti.get(1)) >0);
		assertTrue(inizio.compare(pacchetti.get(2), pacchetti.get(1)) <0);
		assertEquals(0, inizio.compare(pacchetti.get(2), pacchetti.get(4)));
	}
	
}

