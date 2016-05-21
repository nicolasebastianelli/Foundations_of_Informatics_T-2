package pharmame.tests.model;

import static org.junit.Assert.*;

import org.junit.Test;

import pharmame.model.Farmaco;
import pharmame.model.NomeFilter;

public class NomeFilterTests {
	
	private static final Farmaco farmaco = new Farmaco(123456, "principioAttivo", "Spirulato", "Anto' detto Mazzamosche", "confezione", 3.33f, "ditta");
	
	@Test
	public void testFilter_True_SameCase() {
		NomeFilter filter = new NomeFilter("Mazza");
		assertTrue(filter.filter(farmaco));
	}
	
	@Test
	public void testFilter_True_DifferentCase() {
		NomeFilter filter = new NomeFilter("DETTO");
		assertTrue(filter.filter(farmaco));
	}
	
	@Test
	public void testFilter_False() {
		NomeFilter filter = new NomeFilter("Sergio");
		assertFalse(filter.filter(farmaco));
	}

}