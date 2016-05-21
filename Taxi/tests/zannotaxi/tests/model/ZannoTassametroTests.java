package zannotaxi.tests.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import zannotaxi.model.*;

public class ZannoTassametroTests {

	@Test
	public void testZannoTassametro_Ctor() {
		TariffaATempo tt = new TariffaATempo("TT", 100, 123);
		ArrayList<TariffaADistanza> tds = new ArrayList<TariffaADistanza>();
		tds.add(new TariffaADistanza("TD1", 50, 11, 0, 10));
		tds.add(new TariffaADistanza("TD2", 50, 9, 10, 20));
		ZannoTassametro zt = new ZannoTassametro(20, 5, 10, tt, tds);
		
		assertEquals(20, zt.getVelocitaLimite(), 0.00001);
		assertEquals(5, zt.getScattoIniziale(FasciaOraria.DIURNA), 0.00001);
		assertEquals(10, zt.getScattoIniziale(FasciaOraria.NOTTURNA), 0.00001);
		assertSame(tt, zt.getTariffaATempo());
		assertSame(tds.get(0), zt.getTariffeADistanza().get(0));
		assertSame(tds.get(1), zt.getTariffeADistanza().get(1));
	} 
	
	@Test
	public void testZannoTassametro_SoloTempo() {
		TariffaATempo tt = new TariffaATempo("TT", 100, 100);
		ArrayList<TariffaADistanza> tds = new ArrayList<TariffaADistanza>();
		tds.add(new TariffaADistanza("TD1", 50, 7, 0, 10));
		tds.add(new TariffaADistanza("TD2", 25, 7, 10, Double.MAX_VALUE));
		ZannoTassametro zt = new ZannoTassametro(20, 5, 10, tt, tds);
		
		CorsaTaxi ct = new CorsaTaxi("C1", new SimpleTime(10, 10), 10, 100, 15, 100);	
		assertEquals(2 * tt.getValoreScattoInEuro(), zt.calcolaCostoVariabile(ct), 0.0001);
	}
	
	@Test
	public void testZannoTassametro_SoloDistanza_TD1() {
		TariffaATempo tt = new TariffaATempo("TT", 100, 100);
		ArrayList<TariffaADistanza> tds = new ArrayList<TariffaADistanza>();
		tds.add(new TariffaADistanza("TD1", 50, 1, 0, 1000));
		tds.add(new TariffaADistanza("TD2", 25, 1, 1000, Double.MAX_VALUE));
		ZannoTassametro zt = new ZannoTassametro(20, 5, 10, tt, tds);
		
		CorsaTaxi ct = new CorsaTaxi("C1", new SimpleTime(10, 10), 30, 60, 60, 60);	
		assertEquals(30 * tds.get(0).getValoreScattoInEuro(), zt.calcolaCostoVariabile(ct), 0.0001);
	}
	
	@Test
	public void testZannoTassametro_SoloDistanza_TD1_TD2() {
		TariffaATempo tt = new TariffaATempo("TT", 100, 100);
		ArrayList<TariffaADistanza> tds = new ArrayList<TariffaADistanza>();
		tds.add(new TariffaADistanza("TD1", 50, 1, 0, 5));
		tds.add(new TariffaADistanza("TD2", 50, 2, 5, Double.MAX_VALUE));
		ZannoTassametro zt = new ZannoTassametro(20, 5, 10, tt, tds);
		
		CorsaTaxi ct = new CorsaTaxi("C1", new SimpleTime(10, 10), 30, 60, 60, 60);	
		assertEquals(5 * tds.get(0).getValoreScattoInEuro() +
				5 * tds.get(1).getValoreScattoInEuro() +
				20 * tds.get(1).getValoreScattoInEuro(), zt.calcolaCostoVariabile(ct), 0.0001);
	}
	
	@Test
	public void testZannoTassametro_Tempo_TD1() {
		TariffaATempo tt = new TariffaATempo("TT", 0.1, 10);
		ArrayList<TariffaADistanza> tds = new ArrayList<TariffaADistanza>();
		tds.add(new TariffaADistanza("TD1", 50, 1, 0, 100));
		tds.add(new TariffaADistanza("TD2", 50, 1, 100, Double.MAX_VALUE));
		ZannoTassametro zt = new ZannoTassametro(20, 5, 10, tt, tds);
		
		CorsaTaxi ct = new CorsaTaxi("C1", new SimpleTime(10, 10), 10, 60, 60, 60);	
		assertEquals(6 * tt.getValoreScattoInEuro() +
				20 * tds.get(0).getValoreScattoInEuro(), zt.calcolaCostoVariabile(ct), 0.0001);
	}
	
	@Test
	public void testZannoTassametro_TD1_Tempo() {
		TariffaATempo tt = new TariffaATempo("TT", 0.1, 10);
		ArrayList<TariffaADistanza> tds = new ArrayList<TariffaADistanza>();
		tds.add(new TariffaADistanza("TD1", 50, 1, 0, 100));
		tds.add(new TariffaADistanza("TD2", 50, 1, 100, Double.MAX_VALUE));
		ZannoTassametro zt = new ZannoTassametro(20, 5, 10, tt, tds);
		
		CorsaTaxi ct = new CorsaTaxi("C1", new SimpleTime(10, 10), 60, 60, 10, 60);	
		assertEquals(6 * tt.getValoreScattoInEuro() +
				20 * tds.get(0).getValoreScattoInEuro(), zt.calcolaCostoVariabile(ct), 0.0001);
	}

}
