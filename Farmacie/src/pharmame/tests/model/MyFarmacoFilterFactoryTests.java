package pharmame.tests.model;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import pharmame.model.*;

public class MyFarmacoFilterFactoryTests {

	@Test
	public void testGet_PrincipioAttivoFilter() {
		MyFarmacoFilterFactory f = new MyFarmacoFilterFactory();
		Filter<Farmaco> filter = f.get("Principio Attivo", "Pippo");
		assertTrue(filter instanceof PrincipioAttivoFilter);
	}

	@Test
	public void testGet_NomeFilter() {
		MyFarmacoFilterFactory f = new MyFarmacoFilterFactory();
		Filter<Farmaco> filter = f.get("Nome", "Pippo");
		assertTrue(filter instanceof NomeFilter);
	}

	@Test
	public void testGet_GruppoEquivalenzaFilter() {
		MyFarmacoFilterFactory f = new MyFarmacoFilterFactory();
		Filter<Farmaco> filter = f.get("Gruppo Equivalenza", "Pippo");
		assertTrue(filter instanceof GruppoEquivalenzaFilter);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGet_Fail() {
		MyFarmacoFilterFactory f = new MyFarmacoFilterFactory();
		f.get("FAIL!", "Pippo");
	}

	@Test
	public void testGetNames() {
		MyFarmacoFilterFactory f = new MyFarmacoFilterFactory();
		Collection<String> names = f.getNames();
		assertEquals(3, names.size());
		assertTrue(names.contains("Principio Attivo"));
		assertTrue(names.contains("Nome"));
		assertTrue(names.contains("Gruppo Equivalenza"));
	}

}
