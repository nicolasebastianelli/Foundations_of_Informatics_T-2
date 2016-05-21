package zannotaxi.tests.persistence;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import zannotaxi.model.TariffaADistanza;
import zannotaxi.model.TariffaATempo;
import zannotaxi.model.TariffaTaxi;
import zannotaxi.persistence.BadFileFormatException;
import zannotaxi.persistence.MyTariffaTaxiReader;

public class MyTariffaTaxiReaderTest {

	@Test
	public void testCostruttore() throws BadFileFormatException {
		new MyTariffaTaxiReader(new StringReader(""));
	}

	@Test
	public void testLeggiTariffe() throws BadFileFormatException {
		
		String text = "T0 TEMPO	12 s\t0.15\nT1 DISTANZA	100 m\t 0.25 \t 0.00 10.00\n"+
		"T2 DISTANZA\t85 m   0.35\t \t10.00  25.00\nT3 DISTANZA	 65 m  	\t0.50\t25.00";
		
		MyTariffaTaxiReader testObject =
				new MyTariffaTaxiReader(new StringReader(text));
		
		List<TariffaTaxi> tariffeTest= testObject.leggiTariffe();
		
		assertEquals(4, tariffeTest.size());
		
		TariffaATempo tt = (TariffaATempo) tariffeTest.get(0);
		assertEquals("T0", tt.getNome());
		assertEquals(12, tt.getDurataScatto(), 0.0001);
		assertEquals(0.15, tt.getValoreScattoInEuro(), 0.0001);
		
		TariffaADistanza td = (TariffaADistanza) tariffeTest.get(1);
		assertEquals("T1", td.getNome());
		assertEquals(100, td.getDistanzaDiScatto(), 0.0001);
		assertEquals(0.25, td.getValoreScattoInEuro(), 0.0001);
		assertEquals(0, td.getLimiteMinimoApplicabilitaEuro(), 0.0001);
		assertEquals(10, td.getLimiteMassimoApplicabilitaEuro(), 0.0001);
		
		td = (TariffaADistanza) tariffeTest.get(2);
		assertEquals("T2", td.getNome());
		assertEquals(85, td.getDistanzaDiScatto(), 0.0001);
		assertEquals(0.35, td.getValoreScattoInEuro(), 0.0001);
		assertEquals(10, td.getLimiteMinimoApplicabilitaEuro(), 0.0001);
		assertEquals(25, td.getLimiteMassimoApplicabilitaEuro(), 0.0001);

		
		td = (TariffaADistanza) tariffeTest.get(3);
		assertEquals("T3", td.getNome());
		assertEquals(65, td.getDistanzaDiScatto(), 0.0001);
		assertEquals(0.50, td.getValoreScattoInEuro(), 0.0001);
		assertEquals(25, td.getLimiteMinimoApplicabilitaEuro(), 0.0001);
		assertEquals(Double.MAX_VALUE, td.getLimiteMassimoApplicabilitaEuro(), 0.0001);

	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffeDueTariffeATempoNonSonoAmmesse() throws BadFileFormatException {
		
		String text = "T0 TEMPO\t0.15\nT1 DISTANZA	100 m\t 0.25 \t 0.00 10.00\n"+
				"T2 DISTANZA\t85 m   0.35\t \t10.00  25.00\nX TEMPO 12 s\t0.15\nT3 DISTANZA	 65 m  	\t0.50\t25.00";
		
		MyTariffaTaxiReader testObject =
				new MyTariffaTaxiReader(new StringReader(text));
		
		testObject.leggiTariffe();
	}
	
	@Test
	public void testLeggiTariffeSoloTariffeADistanzaSonoAmmesse() throws BadFileFormatException {
		
		String text = "T1 DISTANZA	100 m\t 0.25 \t 0.00 10.00\n"+
				"T2 DISTANZA\t85 m   0.35\t \t10.00  25.00\nT3 DISTANZA	 65 m  	\t0.50\t25.00";
		
		MyTariffaTaxiReader testObject =
				new MyTariffaTaxiReader(new StringReader(text));
		
		List<TariffaTaxi> tariffeTest= testObject.leggiTariffe();
		
		assertEquals(3, tariffeTest.size());
	}

	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffeMancaTokenDurataTempo() throws BadFileFormatException {
		
		String text = "T0 TEMPO\t0.15\nT1 DISTANZA	100 m\t 0.25 \t 0.00 10.00\n"+
		"T2 DISTANZA\t85 m   0.35\t \t10.00  25.00\nT3 DISTANZA	 65 m  	\t0.50\t25.00";
		
		MyTariffaTaxiReader testObject =
				new MyTariffaTaxiReader(new StringReader(text));
		
		testObject.leggiTariffe();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testLeggiTariffeErroreInTipo() throws BadFileFormatException {
		
		String text = "T0 TEMPO\t0.15\nT1 DISTXXXXNZA	100 m\t 0.25 \t 0.00 10.00\n"+
		"T2 DISTANZA\t85 m   0.35\t \t10.00  25.00\nT3 DISTANZA	 65 m  	\t0.50\t25.00";
		
		MyTariffaTaxiReader testObject =
				new MyTariffaTaxiReader(new StringReader(text));
		
		testObject.leggiTariffe();
	}
	
	@Test
	public void testLeggiTariffeCasoValidoConSeparatoriDiversi() throws BadFileFormatException {
		
		String text = "T1 DISTANZA	100   \t m\t 0.25 \t 0.00 10.00\n"+
				"T2 DISTANZA\t85 m   0.35\t \t10.00  25.00\nT3 DISTANZA	 65\t   \t m  	\t0.50\t25.00";
		
		MyTariffaTaxiReader testObject =
				new MyTariffaTaxiReader(new StringReader(text));
		
		List<TariffaTaxi> tariffeTest= testObject.leggiTariffe();
		
		assertEquals(3, tariffeTest.size());
	}
}
