package dentorestaurant.tests;

import static dentorestaurant.tests.TestUtils.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import org.junit.Test;

import dentorestaurant.model.*;
import dentorestaurant.persistence.*;

public class MyMenuReaderTests {

	@Test
	public void successfulConstructor() {
		new MyMenuReader(new StringReader("La Peppina fa il caffè"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void failingConstructor_NullParam() {
		new MyMenuReader(null);
	}

	@Test
	public void testCaricaMenu() throws MalformedFileException, IOException {
		final String toParse = "MENU base\n" + "ANTIPASTO: A01, A02\n"
				+ "PRIMO: P02, P04\n" + "SECONDO: S03, S05\n" + "DESSERT: D01\n"
				+ "END MENU";

		HashMap<Categoria, List<Portata>> portateMap = new HashMap<Categoria, List<Portata>>();
		portateMap.put(Categoria.ANTIPASTO,
				Arrays.asList(new Portata[] { createAntipasto("A01"),
						createAntipasto("A02") }));
		portateMap.put(Categoria.PRIMO,
				Arrays.asList(new Portata[] { createPrimo("P02"),
						createPrimo("P04") }));
		portateMap.put(Categoria.SECONDO,
				Arrays.asList(new Portata[] { createSecondo("S03"),
						createSecondo("S05") }));
		portateMap.put(Categoria.DESSERT,
				Arrays.asList(new Portata[] { createDessert("D01"),
						createDessert("D02") }));

		MyMenuReader reader = new MyMenuReader(new StringReader(toParse));
		List<Menu> menus = reader.caricaMenu(portateMap);

		assertEquals(1, menus.size());

		Menu menu = menus.get(0);
		assertEquals("base", menu.getNome());
		assertEquals(4, menu.getPortate().size());

		List<Portata> portate;

		portate = menu.getPortate(Categoria.ANTIPASTO);
		assertEquals(2, portate.size());
		assertEquals("A01", portate.get(0).getCodice());
		assertEquals("A02", portate.get(1).getCodice());

		portate = menu.getPortate(Categoria.PRIMO);
		assertEquals(2, portate.size());
		assertEquals("P02", portate.get(0).getCodice());
		assertEquals("P04", portate.get(1).getCodice());

		portate = menu.getPortate(Categoria.SECONDO);
		assertEquals(2, portate.size());
		assertEquals("S03", portate.get(0).getCodice());
		assertEquals("S05", portate.get(1).getCodice());

		portate = menu.getPortate(Categoria.DESSERT);
		assertEquals(1, portate.size());
		assertEquals("D01", portate.get(0).getCodice());

	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaMenu_missingMenuKeyword()
			throws MalformedFileException, IOException {

		final String toParse = "XXMENUXX base\n" + "ANTIPASTO: A01, A02\n"
				+ "PRIMO: P02, P04\n" + "SECONDO: S03, S05\n" + "DESSERT: D01"
				+ "END MENU";

		HashMap<Categoria, List<Portata>> portateMap = new HashMap<Categoria, List<Portata>>();
		portateMap.put(Categoria.ANTIPASTO,
				Arrays.asList(new Portata[] { createAntipasto("A01"),
						createAntipasto("A02") }));
		portateMap.put(Categoria.PRIMO,
				Arrays.asList(new Portata[] { createPrimo("P02"),
						createPrimo("P04") }));
		portateMap.put(Categoria.SECONDO,
				Arrays.asList(new Portata[] { createSecondo("S03"),
						createSecondo("S05") }));
		portateMap.put(Categoria.DESSERT,
				Arrays.asList(new Portata[] { createDessert("D01"),
						createDessert("D02") }));

		MyMenuReader reader = new MyMenuReader(new StringReader(toParse));
		reader.caricaMenu(portateMap);
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaMenu_missingColon() throws MalformedFileException,
			IOException {
		final String toParse = "MENU base\n" + "ANTIPASTO: A01, A02\n"
				+ "PRIMO P02\n" + "SECONDO: S03, S05\n" + "DESSERT: D01"
				+ "END MENU";

		HashMap<Categoria, List<Portata>> portateMap = new HashMap<Categoria, List<Portata>>();
		portateMap.put(Categoria.ANTIPASTO,
				Arrays.asList(new Portata[] { createAntipasto("A01"),
						createAntipasto("A02") }));
		portateMap.put(Categoria.PRIMO,
				Arrays.asList(new Portata[] { createPrimo("P02"),
						createPrimo("P04") }));
		portateMap.put(Categoria.SECONDO,
				Arrays.asList(new Portata[] { createSecondo("S03"),
						createSecondo("S05") }));
		portateMap.put(Categoria.DESSERT,
				Arrays.asList(new Portata[] { createDessert("D01"),
						createDessert("D02") }));

		MyMenuReader reader = new MyMenuReader(new StringReader(toParse));
		reader.caricaMenu(portateMap);
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaMenu_wrongCategoria() throws MalformedFileException,
			IOException {

		final String toParse = "MENU base\n" + "ANTIPASTO: A01, A02\n"
				+ "PRIMO: P02, P04\n" + "XXSECONDOXX: S03, S05\n"
				+ "DESSERT: D01" + "END MENU";

		HashMap<Categoria, List<Portata>> portateMap = new HashMap<Categoria, List<Portata>>();
		portateMap.put(Categoria.ANTIPASTO,
				Arrays.asList(new Portata[] { createAntipasto("A01"),
						createAntipasto("A02") }));
		portateMap.put(Categoria.PRIMO,
				Arrays.asList(new Portata[] { createPrimo("P02"),
						createPrimo("P04") }));
		portateMap.put(Categoria.SECONDO,
				Arrays.asList(new Portata[] { createSecondo("S03"),
						createSecondo("S05") }));
		portateMap.put(Categoria.DESSERT,
				Arrays.asList(new Portata[] { createDessert("D01"),
						createDessert("D02") }));

		MyMenuReader reader = new MyMenuReader(new StringReader(toParse));
		reader.caricaMenu(portateMap);
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaMenu_missingPortataInMap()
			throws MalformedFileException, IOException {

		final String toParse = "MENU base\n" + "ANTIPASTO: A01, A02\n"
				+ "PRIMO: P02, P04\n" + "SECONDO: S03, S05\n" + "DESSERT: D01"
				+ "END MENU";

		HashMap<Categoria, List<Portata>> map = new HashMap<Categoria, List<Portata>>();
		map.put(Categoria.ANTIPASTO,
				Arrays.asList(new Portata[] { createAntipasto("A01"),
						createAntipasto("A02") }));
		map.put(Categoria.PRIMO,
				Arrays.asList(new Portata[] { createPrimo("P04") }));
		map.put(Categoria.SECONDO,
				Arrays.asList(new Portata[] { createSecondo("S03"),
						createSecondo("S05") }));
		map.put(Categoria.DESSERT,
				Arrays.asList(new Portata[] { createDessert("D01"),
						createDessert("D02") }));

		MyMenuReader reader = new MyMenuReader(new StringReader(toParse));
		reader.caricaMenu(map);
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaMenu_missingEndMenuKeyword()
			throws MalformedFileException, IOException {
		final String toParse = "MENU base\n" + "ANTIPASTO: A01, A02\n"
				+ "PRIMO: P02, P04\n" + "SECONDO: S03, S05\n" + "DESSERT: D01"
				+ "XENDX XMENUX";

		HashMap<Categoria, List<Portata>> portateMap = new HashMap<Categoria, List<Portata>>();
		portateMap.put(Categoria.ANTIPASTO,
				Arrays.asList(new Portata[] { createAntipasto("A01"),
						createAntipasto("A02") }));
		portateMap.put(Categoria.PRIMO,
				Arrays.asList(new Portata[] { createPrimo("P02"),
						createPrimo("P04") }));
		portateMap.put(Categoria.SECONDO,
				Arrays.asList(new Portata[] { createSecondo("S03"),
						createSecondo("S05") }));
		portateMap.put(Categoria.DESSERT,
				Arrays.asList(new Portata[] { createDessert("D01"),
						createDessert("D02") }));

		MyMenuReader reader = new MyMenuReader(new StringReader(toParse));
		reader.caricaMenu(portateMap);
	}

	@Test(expected = IOException.class)
	public void testClose_readerCloseClosesInnerReader() throws IOException {
		StringReader reader = new StringReader("La Peppina fa il caffè");
		assertTrue(reader.ready());
		MyMenuReader pianiReader = new MyMenuReader(reader);
		pianiReader.close();
		reader.ready();
	}

}
