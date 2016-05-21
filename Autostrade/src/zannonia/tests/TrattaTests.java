package zannonia.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import zannonia.model.Autostrada;
import zannonia.model.Casello;
import zannonia.model.Tratta;

public class TrattaTests
{

	@Test
	public void testTratta()
	{
		new Tratta("ra-bo", 10, 20);
	}

	@Test
	public void testGetId()
	{
		Tratta t = new Tratta("ra-bo", 10, 20);
		assertEquals("ra-bo", t.getId());
	}

	@Test
	public void testGetPedaggio()
	{
		Tratta t = new Tratta("ra-bo", 10, 20);
		assertEquals(10, t.getPedaggio(), 0.000001);
	}

	@Test
	public void testGetLunghezza()
	{
		Tratta t = new Tratta("ra-bo", 10, 20);
		assertEquals(20, t.getLunghezza(), 0.000001);
	}

	@Test
	public void testAddGetCaselli()
	{
		Tratta t = new Tratta("ra-bo", 10, 20);
		assertEquals(0, t.getCaselli().size());
		
		Casello c1 = new Casello("Ravenna");
		t.addCasello(c1);
		assertEquals(1, t.getCaselli().size());
		assertTrue(t.getCaselli().contains(c1));
		assertEquals(c1, t.getCaselli().get(0));
		
		Casello c2 = new Casello("Lugo");
		t.addCasello(c2);
		assertEquals(2, t.getCaselli().size());
		assertTrue(t.getCaselli().contains(c2));
		assertEquals(c2, t.getCaselli().get(1));
	}

	@Test
	public void testAddGetAutostrade()
	{
		Tratta t = new Tratta("ra-bo", 10, 20);
		assertEquals(0, t.getAutostrade().size());
		assertEquals(0, t.getAutostrade().size());
		
		Autostrada a1 = new Autostrada("Z1");
		t.addAutostrada(a1);
		assertEquals(1, t.getAutostrade().size());
		assertTrue(t.getAutostrade().contains(a1));
		
		Autostrada a2 = new Autostrada("Z1");
		t.addAutostrada(a2);
		assertEquals(2, t.getAutostrade().size());
		assertTrue(t.getAutostrade().contains(a2));
	}
	@Test
	public void testIsTrattaInterscambio()
	{
		Tratta t = new Tratta("ra-bo", 10, 20);
		assertFalse(t.isTrattaInterscambio());
		
		Autostrada a1 = new Autostrada("Z1");
		t.addAutostrada(a1);
		assertFalse(t.isTrattaInterscambio());
		
		Autostrada a2 = new Autostrada("Z1");
		t.addAutostrada(a2);
		assertTrue(t.isTrattaInterscambio());
	}

}
