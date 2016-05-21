package pharmame.tests.model;

import static org.junit.Assert.*;

import org.junit.Test;

import pharmame.model.Farmaco;
import pharmame.model.GruppoEquivalenzaFilter;

public class GruppoEquivalenzaFilterTests {
	
	private static final Farmaco farmaco = new Farmaco(123456, "principioAttivo", "Spirulato", "nome", "confezione", 3.33f, "ditta");
	
	@Test
	public void testFilter_True_SameCase() {
		GruppoEquivalenzaFilter filter = new GruppoEquivalenzaFilter("pirul");
		assertTrue(filter.filter(farmaco));
	}
	
	@Test
	public void testFilter_True_DifferentCase() {
		GruppoEquivalenzaFilter filter = new GruppoEquivalenzaFilter("LATO");
		assertTrue(filter.filter(farmaco));
	}
	
	@Test
	public void testFilter_False() {
		GruppoEquivalenzaFilter filter = new GruppoEquivalenzaFilter("tachipirella");
		assertFalse(filter.filter(farmaco));
	}

}
