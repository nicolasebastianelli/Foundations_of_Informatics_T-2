package oroscopo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import oroscopo.model.Oroscopo;
import oroscopo.model.OroscopoMensile;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class OroscopoMensileTest {

	@Test
	public void testOroscopoMensileCostruttore1() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.getPrevisione()).thenReturn("p0.previsione");
		when(p0.getValore()).thenReturn(1);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile(SegnoZodiacale.ACQUARIO.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.ARIETE.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.BILANCIA.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.CANCRO.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.CAPRICORNO.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.LEONE.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.GEMELLI.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.PESCI.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.SAGITTARIO.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.SCORPIONE.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.TORO.toString(), p0, p0, p0);
		new OroscopoMensile(SegnoZodiacale.VERGINE.toString(), p0, p0, p0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOroscopoMensileCostruttore1fail() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile("QUESTO_SEGNO_NON_ESISTE", p0, p0, p0);
	}

	@Test
	public void testOroscopoMensileCostruttore2() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile(SegnoZodiacale.ACQUARIO, p0, p0, p0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOroscopoMensileCostruttore2fail_1() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile(SegnoZodiacale.ACQUARIO, null, p0, p0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOroscopoMensileCostruttore2fail_2() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile(SegnoZodiacale.ACQUARIO, p0, null, p0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOroscopoMensileCostruttore2fail_3() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile(SegnoZodiacale.ACQUARIO, p0, p0, null);
	}
	
	@Test
	public void testOroscopoMensileCostruttore2fail_previsione_valida_per_segno() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		when(p0.validaPerSegno(SegnoZodiacale.BILANCIA)).thenReturn(false);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile(SegnoZodiacale.CANCRO, p1, p0, p1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOroscopoMensileCostruttore2fail_previsione_lavoro_non_valida_per_segno() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		when(p0.validaPerSegno(SegnoZodiacale.BILANCIA)).thenReturn(false);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile(SegnoZodiacale.BILANCIA, p1, p0, p1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOroscopoMensileCostruttore2fail_previsione_amore_non_valida_per_segno() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(false);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile(SegnoZodiacale.SCORPIONE, p0, p1, p1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOroscopoMensileCostruttore2fail_previsione_salute_non_valida_per_segno() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		when(p0.validaPerSegno(SegnoZodiacale.TORO)).thenReturn(false);
		when(p0.validaPerSegno(SegnoZodiacale.VERGINE)).thenReturn(false);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		new OroscopoMensile(SegnoZodiacale.VERGINE, p1, p1, p0);
	}

	@Test
	public void testGetSegnoZodiacale() {
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Oroscopo daTestare =
				new OroscopoMensile(SegnoZodiacale.ACQUARIO.toString(), p0, p0, p0);
		
		assertEquals(SegnoZodiacale.ACQUARIO, daTestare.getSegnoZodiacale());
	}

	@Test
	public void testGetPrevisioneAmore() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Oroscopo daTestare =
				new OroscopoMensile(SegnoZodiacale.CAPRICORNO, p0, p1, p1);
		
		assertEquals(p0, daTestare.getPrevisioneAmore());
	}

	@Test
	public void testGetPrevisioneSalute() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Oroscopo daTestare =
				new OroscopoMensile(SegnoZodiacale.CAPRICORNO, p1, p1, p0);
		
		assertEquals(p0, daTestare.getPrevisioneSalute());
	}

	@Test
	public void testGetPrevisioneLavoro() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Oroscopo daTestare =
				new OroscopoMensile(SegnoZodiacale.CAPRICORNO, p1, p0, p1);
		
		assertEquals(p0, daTestare.getPrevisioneLavoro());
	}

	@Test
	public void testGetFortuna() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.getValore()).thenReturn(2);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.getValore()).thenReturn(4);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p2 = mock(Previsione.class);
		when(p2.getValore()).thenReturn(6);
		when(p2.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Oroscopo daTestare =
				new OroscopoMensile(SegnoZodiacale.ARIETE, p0, p1, p2);
		
		assertEquals(4, daTestare.getFortuna());
	}
	
	@Test
	public void testGetFortunaArrotondamentoPerDifetto() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.getValore()).thenReturn(2);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.getValore()).thenReturn(3);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p2 = mock(Previsione.class);
		when(p2.getValore()).thenReturn(5);
		when(p2.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Oroscopo daTestare =
				new OroscopoMensile(SegnoZodiacale.ARIETE, p0, p1, p2);
		
		assertEquals(3, daTestare.getFortuna());
	}
	
	@Test
	public void testGetFortunaArrotondamentoPerEccesso() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.getValore()).thenReturn(2);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p1 = mock(Previsione.class);
		when(p1.getValore()).thenReturn(3);
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p2 = mock(Previsione.class);
		when(p2.getValore()).thenReturn(6);
		when(p2.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Oroscopo daTestare =
				new OroscopoMensile(SegnoZodiacale.ARIETE, p0, p1, p2);
		
		assertEquals(4, daTestare.getFortuna());
	}

	@Test
	public void testToString() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.getPrevisione()).thenReturn("p0.txt.prev");
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);

		Previsione p1 = mock(Previsione.class);
		when(p1.getPrevisione()).thenReturn("p1.txt.prev");
		when(p1.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Previsione p2 = mock(Previsione.class);
		when(p2.getPrevisione()).thenReturn("p2.txt.prev");
		when(p2.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		String testoDaVerificare =
				new OroscopoMensile("SAGITTARIO", p0, p1, p2).toString();
		
		assertTrue(testoDaVerificare.indexOf(p0.getPrevisione())>-1);
		assertTrue(testoDaVerificare.indexOf(p1.getPrevisione())>-1);
		assertTrue(testoDaVerificare.indexOf(p2.getPrevisione())>-1);
	}

	@Test
	public void testCompareTo_primoSegnoPrecedente() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.getValore()).thenReturn(2);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Comparable<Oroscopo> o1 = 
				new OroscopoMensile("CANCRO", p0, p0, p0);
		
		Oroscopo o2 = 
				new OroscopoMensile("PESCI", p0, p0, p0);
		
		assertTrue(o1.compareTo(o2) < 0);
	}
	
	@Test
	public void testCompareTo_primoSegnoSuccessivo() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.getValore()).thenReturn(2);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Comparable<Oroscopo> o1 = 
				new OroscopoMensile("PESCI", p0, p0, p0);
		
		Oroscopo o2 = 
				new OroscopoMensile("ACQUARIO", p0, p0, p0);
		
		assertTrue(o1.compareTo(o2) > 0);
	}
	
	@Test
	public void testCompareTo_segniUguali() {
		
		Previsione p0 = mock(Previsione.class);
		when(p0.getValore()).thenReturn(2);
		when(p0.validaPerSegno(any(SegnoZodiacale.class))).thenReturn(true);
		
		Comparable<Oroscopo> o1 = 
				new OroscopoMensile("GEMELLI", p0, p0, p0);
		
		Oroscopo o2 = 
				new OroscopoMensile("GEMELLI", p0, p0, p0);
		
		assertTrue(o1.compareTo(o2) == 0);
	}

}
