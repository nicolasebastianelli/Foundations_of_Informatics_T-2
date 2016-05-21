package gattovolpe.holidays.tests;

import static org.junit.Assert.*;
import static gattovolpe.utils.DateUtil.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import gattovolpe.holidays.model.*;

import org.junit.Before;
import org.junit.Test;

public class PacchettoTest {

	private Pacchetto pacchettoTestInPeriod;
	private Pacchetto pacchettoSoloVolo;
	
	@Before
	public void setUp() {
		pacchettoTestInPeriod = new Pacchetto();
		pacchettoTestInPeriod.setTipo(TipoPacchetto.VOLOHOTEL);
		GregorianCalendar inizio1 = new GregorianCalendar();
		inizio1.set(2012, Calendar.JUNE, 20, 14, 15, 33);
		pacchettoTestInPeriod.setDataInizio(inizio1.getTime());
		pacchettoTestInPeriod.setDurataGiorni(20);
		pacchettoTestInPeriod.setCosto(199);
		pacchettoTestInPeriod.setNome("PACCHETTO1");
		pacchettoTestInPeriod.setDescrizione("descrizionetest");
		Destinazione destinazioneTest =
				new Destinazione();
		destinazioneTest.setStato("statotest");
		destinazioneTest.setLuogo("luogotest");
		pacchettoTestInPeriod.setDestinazione(destinazioneTest);
		
		pacchettoSoloVolo = new Pacchetto();
		pacchettoSoloVolo.setTipo(TipoPacchetto.SOLOVOLO);
		GregorianCalendar inizio2 = new GregorianCalendar();
		inizio2.set(2012, Calendar.AUGUST, 15, 17, 33, 01);
		pacchettoSoloVolo.setDataInizio(inizio2.getTime());
		pacchettoSoloVolo.setDurataGiorni(0);
	}

	//--- test isInPeriod
	
	@Test
	public void test_is_in_period_caso_standard() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.MAY, 22, 0, 0, 0);
		end.set(2012, Calendar.SEPTEMBER, 5, 16, 45, 10);
		
		assertTrue( pacchettoTestInPeriod.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_is_in_period_con_data_iniziale_coincidente_con_periodo_validita_ora_mezzanotte() {
		GregorianCalendar start = 
				new GregorianCalendar();
		start.set(2012, Calendar.JUNE, 20, 0, 0, 0);
		
		assertTrue( pacchettoTestInPeriod.isInPeriod( start.getTime(), null));
	}
	
	@Test
	public void test_is_in_period_con_date_iniziali_finali_coincidenti() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.JUNE, 20, 0, 0, 0);
		end.set(2012, Calendar.JULY, 10, 23, 59, 59);
		
		assertTrue( pacchettoTestInPeriod.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_fuori_periodo_fine_coincidente_con_inizio() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.MAY, 20, 0, 0, 0);
		end.set(2012, Calendar.JUNE, 20, 0, 0, 0);
		
		assertFalse( pacchettoTestInPeriod.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_fuori_periodo_inizio_coincidente_con_fine() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.JULY, 10, 0, 0, 0);
		end.set(2012, Calendar.JULY, 20, 0, 0, 0);
		
		assertFalse( pacchettoTestInPeriod.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_fuori_periodo_per_data_di_inizio() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.JUNE, 10, 0, 0, 0);
		end.set(2012, Calendar.JUNE, 30, 0, 0, 0);
		
		assertFalse( pacchettoTestInPeriod.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_fuori_periodo_per_data_di_fine() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.JULY, 1, 0, 0, 0);
		end.set(2012, Calendar.JULY, 30, 0, 0, 0);
		
		assertFalse( pacchettoTestInPeriod.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_fuori_periodo_marginalmente_per_data_di_fine() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.JUNE, 20, 16, 50, 10);
		end.set(2012, Calendar.JULY, 9, 23, 59, 59);
		
		assertFalse( pacchettoTestInPeriod.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_in_periodo_con_data_di_fine_aperta() {
		GregorianCalendar start = 
				new GregorianCalendar();
		start.set(2012, Calendar.JUNE, 15, 16, 50, 10);
		
		assertTrue( pacchettoTestInPeriod.isInPeriod( start.getTime(), null));
	}
	
	@Test
	public void test_in_periodo_con_data_di_fine_aperta_e_data_inizio_coincidente() {
		GregorianCalendar start = 
				new GregorianCalendar();
		start.set(2012, Calendar.JUNE, 20, 1, 50, 10);
		
		assertTrue( pacchettoTestInPeriod.isInPeriod( start.getTime(), null));
	}
	
	@Test
	public void test_fuori_periodo_con_data_fine_aperta() {
		GregorianCalendar start = 
				new GregorianCalendar();
		start.set(2012, Calendar.JUNE, 21, 19, 50, 10);
		
		assertFalse( pacchettoTestInPeriod.isInPeriod( start.getTime(), null));
	}
	
	@Test
	public void test_solovolo_data_ricerca_corretta() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.AUGUST, 15, 16, 50, 10);
		end.set(2012, Calendar.JULY, 11, 1, 12, 0);
		
		assertTrue( pacchettoSoloVolo.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_solovolo_data_ricerca_corretta_senza_data_fine() {
		GregorianCalendar start = 
				new GregorianCalendar();
		start.set(2012, Calendar.AUGUST, 15, 16, 50, 10);
		
		assertTrue( pacchettoSoloVolo.isInPeriod( start.getTime(), null));
	}
	
	@Test
	public void test_solovolo_data_ricerca_diversa() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.AUGUST, 10, 16, 50, 10);
		end.set(2012, Calendar.SEPTEMBER, 11, 1, 12, 0);
		
		assertFalse( pacchettoSoloVolo.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_solovolo_data_ricerca_diversa_senza_data_fine() {
		GregorianCalendar start = 
				new GregorianCalendar();
		start.set(2012, Calendar.AUGUST, 10, 16, 50, 10);
		
		assertFalse( pacchettoSoloVolo.isInPeriod( start.getTime(), null));
	}
	
	@Test
	public void test_solovolo_data_ricerca_diversa_successiva() {
		GregorianCalendar start = 
				new GregorianCalendar();
		GregorianCalendar end = 
				new GregorianCalendar();
		start.set(2012, Calendar.AUGUST, 25, 16, 50, 10);
		end.set(2012, Calendar.SEPTEMBER, 11, 1, 12, 0);
		
		assertFalse( pacchettoSoloVolo.isInPeriod( start.getTime(), end.getTime()));
	}
	
	@Test
	public void test_solovolo_data_ricerca_diversa_successiva_senza_data_fine() {
		GregorianCalendar start = 
				new GregorianCalendar();
		start.set(2012, Calendar.AUGUST, 25, 16, 50, 10);
		
		assertFalse( pacchettoSoloVolo.isInPeriod( start.getTime(), null));
	}
	
	//--- costruzione
	
	@Test
	public void test_costruzione_viene_portata_a_termine_senza_errori() {
		Pacchetto pacchetto =
				new Pacchetto();
		assertTrue(pacchetto!=null);
	}
	
	//--- getters & setters
	@Test
	public void test_tutti_gli_accessors_funzionano_correttamente() {
		assertEquals(TipoPacchetto.VOLOHOTEL, pacchettoTestInPeriod.getTipo());
		assertEquals("statotest", pacchettoTestInPeriod.getDestinazione().getStato());
		assertEquals("luogotest", pacchettoTestInPeriod.getDestinazione().getLuogo());
		assertEquals(199, pacchettoTestInPeriod.getCosto(), 0.1);
		assertEquals(20, pacchettoTestInPeriod.getDurataGiorni());
		assertEquals("descrizionetest", pacchettoTestInPeriod.getDescrizione());
		
		Calendar myCal =
				new GregorianCalendar();
		myCal.set(2012, Calendar.JUNE, 20);
		assertEquals(normalizeDate(myCal.getTime()), normalizeDate(pacchettoTestInPeriod.getDataInizio()) );
		
		assertEquals("descrizionetest", pacchettoTestInPeriod.getDescrizione());
	}
	
}
