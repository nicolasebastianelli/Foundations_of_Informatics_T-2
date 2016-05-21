package zannotaxi.tests.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import zannotaxi.model.CorsaTaxi;
import zannotaxi.model.SimpleTime;
import zannotaxi.model.TariffaADistanza;
import zannotaxi.model.TariffaATempo;
import zannotaxi.model.TariffaTaxi;
import zannotaxi.model.ZannoTassametro;
import zannotaxi.persistence.BadFileFormatException;
import zannotaxi.persistence.CorseTaxiReader;
import zannotaxi.persistence.DataNotReadyException;
import zannotaxi.persistence.TariffaTaxiReader;
import zannotaxi.ui.MyController;

public class MyControllerTest {

	MyController testObject;	
	TariffaTaxiReader ttReader;
	ZannoTassametro zt;
	CorseTaxiReader ctr;
	List<CorsaTaxi> corse;

	@Before
	public void setUp() throws Exception {
		
		List<TariffaTaxi> tariffe = Arrays.<TariffaTaxi>asList(
				new TariffaATempo("T0", 0.15, 12),
				new TariffaADistanza("T1", 100, 0.25, 0, 10),
				new TariffaADistanza("T2", 85, 0.35, 10, 25)
				);
		
		ttReader = mock(TariffaTaxiReader.class);
		when(ttReader.leggiTariffe()).thenReturn(tariffe);

		zt = mock(ZannoTassametro.class);
	
		corse = Arrays.asList(
				new CorsaTaxi("corsa1", new SimpleTime(9, 40),
					new double[] { 8.33, 16.67, 25, 33.33, 41.67, 50, 58.33 }),
				new CorsaTaxi("corsa2", new SimpleTime(22, 40),
					new double[] { 5.56, 11.11, 16.67, 22.22, 27.78, 33.33, 38.89, 44.44 })
				);
		
		ctr = mock(CorseTaxiReader.class);
		when(ctr.getCorse()).thenReturn(corse);
		
		testObject = new MyController(ctr, zt);
		
	}

	@Test
	public void test_costruzione_del_controller_prevede_chiamata_a_corseTaxiReader_leggiCorse() 
			throws BadFileFormatException, IOException {

		CorseTaxiReader nCtr = mock(CorseTaxiReader.class);
		InputStream is = mock(InputStream.class);
		
		testObject = new MyController(ttReader, nCtr, is);
		
		verify(nCtr, times(1)).leggiCorse( is );
	}

	@Test
	public void testGetDescrizioniCorse() throws BadFileFormatException {
		
		String[] descrizioni = 
				testObject.getDescrizioniCorse();
		
		assertEquals(2, descrizioni.length);
		assertEquals("corsa1", descrizioni[0]);
		assertEquals("corsa2", descrizioni[1]);
	}

	@Test
	public void test_get_corsa_per_descrizione_prima_corsa() {
		CorsaTaxi corsa1 = 
				testObject.getCorsaPerDescrizione("corsa1");
		assertEquals( "corsa1", corsa1.getDettagliCorsa() );
		assertEquals( 9, corsa1.getOraPartenza().getOre() );
		assertEquals( 40, corsa1.getOraPartenza().getMinuti() );
		assertEquals( 7, corsa1.getRilevazioniDistanze().length );
	}
	
	@Test
	public void test_get_corsa_per_descrizione_seconda_corsa() {
		CorsaTaxi corsa1 = 
				testObject.getCorsaPerDescrizione("corsa2");
		assertEquals( "corsa2", corsa1.getDettagliCorsa() );
		assertEquals( 22, corsa1.getOraPartenza().getOre() );
		assertEquals( 40, corsa1.getOraPartenza().getMinuti() );
		assertEquals( 8, corsa1.getRilevazioniDistanze().length );
	}
	
	@Test
	public void test_get_corsa_per_descrizione_non_esiste() {
		CorsaTaxi corsax = 
				testObject.getCorsaPerDescrizione("NONESISTE");
		assertNull( corsax );
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test_getCorsaPerDescrizione_con_reader_notready_nasconde_eccezione() throws DataNotReadyException {
		
		when(ctr.getCorse()).thenThrow(DataNotReadyException.class);
		
		testObject.getCorsaPerDescrizione("corsa1");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test_getDescrizioniCorse_con_reader_notready_nasconde_eccezione() throws DataNotReadyException {
		
		when(ctr.getCorse()).thenThrow(DataNotReadyException.class);
		
		testObject.getDescrizioniCorse();
	}

	@Test
	public void testGetLineeDiCosto() {
		String[][] linee =
				testObject.getLineeDiCosto(corse.get(0));
		
		assertEquals(4, linee.length);
		
		verify(zt, times(1)).calcolaCosto(corse.get(0));
	}

}
