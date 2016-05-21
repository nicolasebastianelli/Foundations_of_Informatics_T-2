package dentorestaurant.tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Menu;
import dentorestaurant.model.Ordine;
import dentorestaurant.model.Portata;

public class OrdineTests {

	@Test
	public void testOrdine() {
		new Ordine(TestUtils.creaMenu("Menu"), "Pippo");
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void testOrdine_FailForNullMenu() {
		new Ordine(null, "Pippo");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOrdine_FailForNameNull() {
		new Ordine(TestUtils.creaMenu("Menu"), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testOrdine_FailFormNameOfSpaces() {
		new Ordine(TestUtils.creaMenu("Menu"), "   ");
	}

	@Test
	public void testGetNomeCliente() {
		Ordine o = new Ordine(TestUtils.creaMenu("Menu"), "Pippo");
		assertEquals("Pippo", o.getNomeCliente());
	}

	@Test
	public void testGetMenu() {
		Menu menu = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(menu, "Pippo");
		assertEquals(menu, o.getMenu());
	}

	@Test
	public void testAggiungiPortata() {
		Menu m = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(m, "Pippo");
		Portata p = m.getPortate(Categoria.ANTIPASTO).get(0);
		o.aggiungiPortata(p);
		
		Map<Categoria, Portata> map = o.getElencoPortate();
		assertEquals(p, map.get(Categoria.ANTIPASTO));
		assertNull(map.get(Categoria.PRIMO));
		assertNull(map.get(Categoria.DESSERT));
		assertNull(map.get(Categoria.DESSERT));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAggiungiPortata_PortataGiaPresente() {
		Menu m = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(m, "Pippo");
		Portata p = m.getPortate(Categoria.ANTIPASTO).get(0);
		o.aggiungiPortata(p);
		o.aggiungiPortata(p);
	}

	public void testIsValid_False() {
		Menu m = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(m, "Pippo");
		Portata p = m.getPortate(Categoria.ANTIPASTO).get(0);
		o.aggiungiPortata(p);
		assertFalse(o.isValid());
	}

	@Test
	public void testIsValid_True() {
		Menu m = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(m, "Pippo");
		o.aggiungiPortata(m.getPortate(Categoria.ANTIPASTO).get(0));
		o.aggiungiPortata(m.getPortate(Categoria.PRIMO).get(0));
		o.aggiungiPortata(m.getPortate(Categoria.SECONDO).get(0));
		o.aggiungiPortata(m.getPortate(Categoria.DESSERT).get(0));
		assertTrue(o.isValid());
	}

	@Test
	public void testGetPrezzoTotale() {
		Menu m = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(m, "Pippo");
		o.aggiungiPortata(m.getPortate(Categoria.ANTIPASTO).get(0));
		o.aggiungiPortata(m.getPortate(Categoria.PRIMO).get(0));
		o.aggiungiPortata(m.getPortate(Categoria.SECONDO).get(0));
		o.aggiungiPortata(m.getPortate(Categoria.DESSERT).get(0));
		assertEquals(41, o.getPrezzoTotale(), 0.001);
	}

	@Test
	public void testSostituisciPortata() {
		Menu m = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(m, "Pippo");
		o.aggiungiPortata(m.getPortate(Categoria.ANTIPASTO).get(0));
		o.sostituisciPortata(m.getPortate(Categoria.ANTIPASTO).get(0), m.getPortate(Categoria.ANTIPASTO).get(1));

		Map<Categoria, Portata> map = o.getElencoPortate();
		assertEquals(m.getPortate(Categoria.ANTIPASTO).get(1), map.get(Categoria.ANTIPASTO));
		assertNull(map.get(Categoria.PRIMO));
		assertNull(map.get(Categoria.DESSERT));
		assertNull(map.get(Categoria.DESSERT));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSostituisciPortata_portataNonPresente() {
		Menu m = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(m, "Pippo");
		o.aggiungiPortata(m.getPortate(Categoria.ANTIPASTO).get(0));
		o.sostituisciPortata(m.getPortate(Categoria.ANTIPASTO).get(1), m.getPortate(Categoria.ANTIPASTO).get(0));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSostituisciPortata_categoriaDiversa() {
		Menu m = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(m, "Pippo");
		o.aggiungiPortata(m.getPortate(Categoria.ANTIPASTO).get(0));
		o.sostituisciPortata(m.getPortate(Categoria.ANTIPASTO).get(0), m.getPortate(Categoria.PRIMO).get(1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSostituisciPortata_nonPresenteNelMenu() {
		Menu m = TestUtils.creaMenu("Menu");
		Ordine o = new Ordine(m, "Pippo");
		o.aggiungiPortata(m.getPortate(Categoria.ANTIPASTO).get(0));
		o.sostituisciPortata(m.getPortate(Categoria.ANTIPASTO).get(0), TestUtils.createAntipasto("KABOOM!"));
	}

}
