package dentorestaurant.tests;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Menu;
import dentorestaurant.model.Portata;

public final class TestUtils {

	private TestUtils() {
	}

	public static Portata createAntipasto(String codice) {
		return new Portata(codice, "DESC" + codice, Categoria.ANTIPASTO, 9.00);
	}

	public static Portata createPrimo(String codice) {
		return new Portata(codice, "DESC" + codice, Categoria.PRIMO, 12.00);
	}

	public static Portata createSecondo(String codice) {
		return new Portata(codice, "DESC" + codice, Categoria.SECONDO, 15.00);
	}

	public static Portata createDessert(String codice) {
		return new Portata(codice, "DESC" + codice, Categoria.DESSERT, 5.00);
	}

	public static Menu creaMenu(String nome) {
		Menu m = new Menu(nome);

		m.getPortate(Categoria.ANTIPASTO).add(createAntipasto("A1"));
		m.getPortate(Categoria.ANTIPASTO).add(createAntipasto("A2"));

		m.getPortate(Categoria.PRIMO).add(createPrimo("P1"));
		m.getPortate(Categoria.PRIMO).add(createPrimo("P2"));

		m.getPortate(Categoria.SECONDO).add(createSecondo("S1"));
		m.getPortate(Categoria.SECONDO).add(createSecondo("S2"));

		m.getPortate(Categoria.DESSERT).add(createDessert("D1"));
		m.getPortate(Categoria.DESSERT).add(createDessert("D2"));

		return m;
	}

}
