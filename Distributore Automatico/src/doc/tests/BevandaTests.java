package doc.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Test;

import doc.model.*;

public class BevandaTests {

	@Test
	public void testBevanda() {
		Bevanda b = new Bevanda("Pippo", 10, 20, 30);

		assertEquals("Pippo", b.getNome());
		assertEquals(10, b.getPrezzoMin(), 0.01);
		assertEquals(20, b.getPrezzoBase(), 0.01);
		assertEquals(30, b.getPrezzoMax(), 0.01);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBevanda_failNomeNull() {
		new Bevanda(null, 10, 20, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBevanda_failNomeWhitespace() {
		new Bevanda("        ", 10, 20, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBevanda_failPrezzoMinLessThanZero() {
		new Bevanda("Pippo", -10, 20, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBevanda_failPrezzoBaseLessThanZero() {
		new Bevanda("Pippo", 10, -20, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBevanda_failPrezzoMaxLessThanZero() {
		new Bevanda("Pippo", 10, 20, -30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBevanda_failPrezzoMixGreaterThanBaseZero() {
		new Bevanda("Pippo", 100, 20, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBevanda_failPrezzoBaseGreaterThanMaxZero() {
		new Bevanda("Pippo", 10, 200, 30);
	}

	@Test
	public void testSetStrategia() {
		Bevanda b = new Bevanda("Pippo", 10, 20, 30);
		
		DentoStrategia st = mock(DentoStrategia.class);
		b.setStrategia(st);
		
		assertSame(st, b.getStrategia());
		
		verifyZeroInteractions(st);
	}

	@Test
	public void testGetStrategia_Default() {
		Bevanda b = new Bevanda("Pippo", 10, 20, 30);
		
		assertTrue(b.getStrategia() instanceof DefaultDentoStrategia);
	}

	@Test
	public void testGetPrezzo_DefaultStrategy() {
		Bevanda b = new Bevanda("Pippo", 10, 20, 30);
		ArrayList<Acquisto> acquisti = new ArrayList<>();
		Cliente cliente = new Cliente("10", "Test");
		
		assertEquals(20, b.getPrezzo(acquisti, cliente), 0.01);
	}

	@Test
	public void testGetPrezzo_MockStrategy_InsideRange() {
		Bevanda b = new Bevanda("Pippo", 10, 20, 30);
		ArrayList<Acquisto> acquisti = new ArrayList<>();
		Cliente cliente = new Cliente("10", "Test");
		
		DentoStrategia st = mock(DentoStrategia.class);
		when(st.calcolaSconto(acquisti, cliente))
			.thenReturn(0.25f);
		b.setStrategia(st);
		
		assertEquals(15, b.getPrezzo(acquisti, cliente), 0.01);
	}

	@Test
	public void testGetPrezzo_MockStrategy_OutsideLowRange() {
		Bevanda b = new Bevanda("Pippo", 10, 20, 30);
		ArrayList<Acquisto> acquisti = new ArrayList<>();
		Cliente cliente = new Cliente("10", "Test");
		
		DentoStrategia st = mock(DentoStrategia.class);
		when(st.calcolaSconto(acquisti, cliente))
			.thenReturn(0.75f);
		b.setStrategia(st);
		
		assertEquals(10, b.getPrezzo(acquisti, cliente), 0.01);
	}

	@Test
	public void testGetPrezzo_MockStrategy_OutsideHighRange() {
		Bevanda b = new Bevanda("Pippo", 10, 20, 30);
		ArrayList<Acquisto> acquisti = new ArrayList<>();
		Cliente cliente = new Cliente("10", "Test");
		
		DentoStrategia st = mock(DentoStrategia.class);
		when(st.calcolaSconto(acquisti, cliente))
			.thenReturn(-0.75f);
		b.setStrategia(st);
		
		assertEquals(30, b.getPrezzo(acquisti, cliente), 0.01);
	}

	@Test
	public void testToString() {
		Bevanda b = new Bevanda("Pippo", 10, 20, 30);
		
		assertEquals("Pippo", b.toString());
	}

}
