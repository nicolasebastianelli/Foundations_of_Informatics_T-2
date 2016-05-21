package trekking.tests;

import org.junit.Before;
import org.junit.Test;

import trekking.model.Difficulty;
import trekking.model.Itinerary;
import trekking.model.MyItinerary;
import trekking.model.Trail;
import trekking.model.TrailHead;

import static org.junit.Assert.*;

public class TestItinerary {

	private Itinerary itinerary;
	private Trail trail1;
	
	@Before
	public void setUp() {
		itinerary = new MyItinerary();

		TrailHead sA = new TrailHead();
		sA.setName("A");
		sA.setAltitude(0);
		TrailHead sB = new TrailHead();
		sB.setName("B");
		sB.setAltitude(1000);
		
		trail1 = new Trail(sA, sB, Difficulty.MEDIUM);
		trail1.setLength(10.3);
		
		trail1.setName("#1");
		
		itinerary.addTrail(trail1);
	}
	
	@Test
	public void test_accessor_su_sentieri() {
		assertEquals(1, itinerary.getTrails().size());
		
		assertEquals(trail1, itinerary.getTrails().get(0));
	}
	
	@Test
	public void test_aggiungi_sentiero_valido() {
		
		TrailHead sB = new TrailHead();
		sB.setName("B");
		sB.setAltitude(1000);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(2000);
		
		Trail valido = new Trail(sB, sC, Difficulty.MEDIUM);
		valido.setLength(14.7);
		
		itinerary.addTrail(valido);
		
		assertEquals(2, itinerary.getTrails().size());
		itinerary.isValid(2000, 25, Difficulty.MEDIUM, 3.0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_aggiungi_sentiero_non_valido_per_stazione_di_partenza1() {
		
		TrailHead sAltra = new TrailHead();
		sAltra.setName("ALTRA");
		sAltra.setAltitude(1000);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(2000);
		
		Trail invalido = new Trail(sAltra, sC, Difficulty.LOW);
		
		itinerary.addTrail(invalido);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_aggiungi_sentiero_non_valido_per_stazione_di_partenza2() {
		
		TrailHead sAltra = new TrailHead();
		sAltra.setName("B");
		sAltra.setAltitude(900);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(2000);
		
		Trail invalido = new Trail(sAltra, sC, Difficulty.LOW);
		
		itinerary.addTrail(invalido);
	}
	
	@Test
	public void test_aggiungi_sentiero_supera_dislivello_max() {
		
		TrailHead sB = new TrailHead();
		sB.setName("B");
		sB.setAltitude(1000);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(2050);
		
		Trail invalido = new Trail(sB, sC, Difficulty.LOW);
		invalido.setLength(10.1);
		
		itinerary.addTrail(invalido);
		assertNotNull(itinerary.isValid(2000, 25, Difficulty.MEDIUM, 3));
	}

	@Test
	public void test_aggiungi_sentiero_supera_lunghezza_totale()  {
		
		TrailHead sB = new TrailHead();
		sB.setName("B");
		sB.setAltitude(1000);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(2000);
		
		Trail invalido = new Trail(sB, sC, Difficulty.LOW);
		invalido.setLength(14.8);
		
		itinerary.addTrail(invalido);
		assertNotNull(itinerary.isValid(2000, 25, Difficulty.MEDIUM, 3));
	}
	
	@Test
	public void test_aggiungi_sentiero_supera_difficolta_max() {
		
		TrailHead sB = new TrailHead();
		sB.setName("B");
		sB.setAltitude(1000);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(1500);
		
		Trail invalido = new Trail(sB, sC, Difficulty.HIGH);
		invalido.setLength(9.3);
		
		itinerary.addTrail(invalido);
		assertNotNull(itinerary.isValid(2000, 25, Difficulty.MEDIUM, 3));
	}
	
	@Test
	public void test_calcolo_difficolta_media() {
		
		// primo sentiero L10.3 x dislivello 1000, difficolta media (2)
		// secondo sentiero L4 x dislivello 500, difficolta bassa(1)
		// media = (10300 * 2 + 2000 * 1) / (10300 + 2000) = 1.837
		
		TrailHead sB = new TrailHead();
		sB.setName("B");
		sB.setAltitude(1000);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(1500);
		
		Trail valido = new Trail(sB, sC, Difficulty.LOW);
		valido.setLength(4.0);
		
		itinerary.addTrail(valido);
		
		assertEquals(1.84, itinerary.calcAverageDifficulty(), 0.01);
		assertNull(itinerary.isValid(2000, 25, Difficulty.MEDIUM, 3));
	}
	

	@Test
	public void test_calcolo_dislivello_max() {	
		TrailHead sB = new TrailHead();
		sB.setName("B");
		sB.setAltitude(1000);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(2050);
		
		TrailHead sD = new TrailHead();
		sD.setName("D");
		sD.setAltitude(350);
		
		TrailHead sE = new TrailHead();
		sE.setName("E");
		sE.setAltitude(2100);
		
		Trail seconda_salita = new Trail(sB, sC, Difficulty.LOW);
		seconda_salita.setLength(1);
		Trail discesa = new Trail(sC, sD, Difficulty.LOW);
		discesa.setLength(1);
		
		assertEquals(1000, itinerary.calcMaxAltitudeDifference(), 0.1);
		
		itinerary.addTrail(seconda_salita);
		
		assertEquals(2050, itinerary.calcMaxAltitudeDifference(), 0.1);
		
		itinerary.addTrail(discesa);
		
		assertEquals(2050, itinerary.calcMaxAltitudeDifference(), 0.1);
		
		Trail terza_salita = new Trail(sD, sE, Difficulty.LOW);
		terza_salita.setLength(1);
		
		itinerary.addTrail(terza_salita);
		
		assertEquals(2100, itinerary.calcMaxAltitudeDifference(), 0.1);
	}
	
	@Test
	public void test_calcolo_distanza_tot() {	
		TrailHead sB = new TrailHead();
		sB.setName("B");
		sB.setAltitude(1000);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(2050);
		
		TrailHead sD = new TrailHead();
		sD.setName("D");
		sD.setAltitude(350);
		
		TrailHead sE = new TrailHead();
		sE.setName("E");
		sE.setAltitude(2100);
		
		Trail seconda_salita = new Trail(sB, sC, Difficulty.LOW);
		seconda_salita.setLength(0.7);
		Trail discesa = new Trail(sC, sD, Difficulty.LOW);
		discesa.setLength(1.5);
		
		assertEquals(10.3, itinerary.calcTotalLength(), 0.1);
		
		itinerary.addTrail(seconda_salita);
		
		assertEquals(11, itinerary.calcTotalLength(), 0.1);
		
		itinerary.addTrail(discesa);
		
		assertEquals(12.5, itinerary.calcTotalLength(), 0.1);
		
		Trail terza_salita = new Trail(sD, sE, Difficulty.LOW);
		terza_salita.setLength(2.5);
		
		itinerary.addTrail(terza_salita);
		
		assertEquals(15, itinerary.calcTotalLength(), 0.1);
	}
	
	@Test
	public void test_calcolo_difficolta_max() {	
		TrailHead sB = new TrailHead();
		sB.setName("B");
		sB.setAltitude(1000);
		
		TrailHead sC = new TrailHead();
		sC.setName("C");
		sC.setAltitude(2050);
		
		TrailHead sD = new TrailHead();
		sD.setName("D");
		sD.setAltitude(350);
		
		TrailHead sE = new TrailHead();
		sE.setName("E");
		sE.setAltitude(2100);
		
		Trail seconda_salita = new Trail(sB, sC, Difficulty.LOW);
		seconda_salita.setLength(0.7);
		
		Trail discesa = new Trail(sC, sD, Difficulty.HIGH);
		discesa.setLength(1.5);
		
		assertEquals(Difficulty.MEDIUM, itinerary.calcMaxDifficulty());
		
		itinerary.addTrail(seconda_salita);
		
		assertEquals(Difficulty.MEDIUM, itinerary.calcMaxDifficulty());
		
		itinerary.addTrail(discesa);
		
		assertEquals(Difficulty.HIGH, itinerary.calcMaxDifficulty());
	}
	
}
