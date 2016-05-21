package pharmame.tests.model;

import static org.junit.Assert.*;

import org.junit.Test;

import pharmame.model.Farmaco;
import pharmame.model.PrincipioAttivoFilter;

public class PrincipioAttivoFilterTests {
	
	private static final Farmaco farmaco = new Farmaco(123456, "MIOprincipioAttivo", "Spirulato", "Anto' detto Mazzamosche", "confezione", 3.33f, "ditta");
	
	@Test
	public void testFilter_True_SameCase() {
		PrincipioAttivoFilter filter = new PrincipioAttivoFilter("pioAttiv");
		assertTrue(filter.filter(farmaco));
	}
	
	@Test
	public void testFilter_True_DifferentCase() {
		PrincipioAttivoFilter filter = new PrincipioAttivoFilter("PRINCI");
		assertTrue(filter.filter(farmaco));
	}
	
	@Test
	public void testFilter_False() {
		PrincipioAttivoFilter filter = new PrincipioAttivoFilter("NO_WAY");
		assertFalse(filter.filter(farmaco));
	}

}
