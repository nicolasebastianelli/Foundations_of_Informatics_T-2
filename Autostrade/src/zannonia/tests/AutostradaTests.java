package zannonia.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import zannonia.model.Autostrada;
import zannonia.model.Tratta;

public class AutostradaTests
{

	@Test
	public void testAutostrada()
	{
		new Autostrada("Z18");
	}

	@Test
	public void testGetNome()
	{
		Autostrada a = new Autostrada("Z18");
		assertEquals("Z18", a.getNome());
	}

	@Test
	public void testAddGetTratte()
	{
		Autostrada a = new Autostrada("Z18");
		assertEquals(0, a.getTratte().size());
		
		Tratta t1 = new Tratta("T1", 10, 10);
		a.addTratta(t1);
		assertEquals(1, a.getTratte().size());
		assertEquals(t1, a.getTratte().get(0));
		
		Tratta t2 = new Tratta("T2", 10, 10);
		a.addTratta(t2);
		assertEquals(2, a.getTratte().size());
		assertEquals(t2, a.getTratte().get(1));
	}

	@Test
	public void testGetNext()
	{
		Autostrada a = new Autostrada("Z18");
		
		Tratta t1 = new Tratta("T1", 10, 10);
		a.addTratta(t1);
		
		Tratta t2 = new Tratta("T2", 10, 10);
		a.addTratta(t2);
		
		assertEquals(t2, a.getNext(t1, true));
		assertEquals(t1, a.getNext(t2, false));
		assertNull(a.getNext(t1, false));
		assertNull(a.getNext(t2, true));
	}
}
