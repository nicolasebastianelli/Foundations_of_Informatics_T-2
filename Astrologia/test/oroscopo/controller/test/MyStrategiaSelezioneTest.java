package oroscopo.controller.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import oroscopo.controller.MyStrategiaSelezione;
import oroscopo.controller.StrategiaSelezione;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyStrategiaSelezioneTest {

	private List<Previsione> previsioni = 
			Arrays.asList( new Previsione[] { 
					mock(Previsione.class), 
					mock(Previsione.class),
					mock(Previsione.class),
					mock(Previsione.class),
					mock(Previsione.class)});
	
	@Before
	public void setup() {
		when( previsioni.get(0).validaPerSegno(any(SegnoZodiacale.class)))
			.thenReturn(true);
		
		when( previsioni.get(1).validaPerSegno(any(SegnoZodiacale.class)))
		.thenReturn(true);
		
		when( previsioni.get(2).validaPerSegno(any(SegnoZodiacale.class)))
		.thenReturn(false);
		when( previsioni.get(2).validaPerSegno(SegnoZodiacale.ACQUARIO))
		.thenReturn(true);
		
		when( previsioni.get(3).validaPerSegno(any(SegnoZodiacale.class)))
		.thenReturn(false);
		when( previsioni.get(3).validaPerSegno(SegnoZodiacale.PESCI))
		.thenReturn(true);
		when( previsioni.get(3).validaPerSegno(SegnoZodiacale.ACQUARIO))
		.thenReturn(true);
		
		when( previsioni.get(4).validaPerSegno(any(SegnoZodiacale.class)))
		.thenReturn(false);
	}
	
	@Test
	public void testSelezionaPerSegno() {
		
		StrategiaSelezione daTestare = new MyStrategiaSelezione();
		
		for(SegnoZodiacale s : SegnoZodiacale.values()) {
			Previsione p = daTestare.seleziona(previsioni, s);
			assertTrue(p.validaPerSegno(s));
		}
	}
	
	@Test
	public void testDistribuzioneDiProbabilita2su5() {
		
		StrategiaSelezione daTestare = new MyStrategiaSelezione();
		
		int[] probabilita = new int[5];
		for(int i=0; i<10000; i++) {
			Previsione p = daTestare.seleziona(previsioni, SegnoZodiacale.ARIETE);
			probabilita[previsioni.indexOf(p)]++;
		}
		
		assertTrue( probabilita[0] >1000 ); //????
		assertTrue( probabilita[1] >1000 ); //????
		assertEquals(0, probabilita[2] );
		assertEquals(0, probabilita[3] );
		assertEquals(0, probabilita[4] );
	}
	
	@Test
	public void testDistribuzioneDiProbabilita3su5() {
		
		StrategiaSelezione daTestare = new MyStrategiaSelezione();
		
		int[] probabilita = new int[5];
		for(int i=0; i<10000; i++) {
			Previsione p = daTestare.seleziona(previsioni, SegnoZodiacale.PESCI);
			probabilita[previsioni.indexOf(p)]++;
		}
		
		assertTrue( probabilita[0] >1000 ); //????
		assertTrue( probabilita[1] >1000 ); //????
		assertEquals(0, probabilita[2] );
		assertTrue( probabilita[3] >1000 ); //????
		assertEquals(0, probabilita[4] );
	}
	
	@Test
	public void testDistribuzioneDiProbabilita4su5() {
		
		StrategiaSelezione daTestare = new MyStrategiaSelezione();
		
		int[] probabilita = new int[5];
		for(int i=0; i<10000; i++) {
			Previsione p = daTestare.seleziona(previsioni, SegnoZodiacale.ACQUARIO);
			probabilita[previsioni.indexOf(p)]++;
		}
		
		assertTrue( probabilita[0] >1000 ); //????
		assertTrue( probabilita[1] >1000 ); //????
		assertTrue( probabilita[2] >1000 ); //????
		assertTrue( probabilita[3] >1000 ); //????
		assertEquals(0, probabilita[4] );
	}

}
