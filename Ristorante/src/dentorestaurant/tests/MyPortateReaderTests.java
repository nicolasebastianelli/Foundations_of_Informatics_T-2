package dentorestaurant.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import dentorestaurant.model.*;
import dentorestaurant.persistence.*;

public class MyPortateReaderTests {

	@Test
	public void successfulConstructor() {
		new MyPortateReader(new StringReader("La Peppina fa il caffè"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void failingConstructor_NullParam() {
		new MyPortateReader(null);
	}

	@Test
	public void testCaricaPortate() throws MalformedFileException,
			IOException {
		final String portate = "A01, Prosciutto e melone, ANTIPASTO, 9.00\nA02, Antipasto di mare, ANTIPASTO, 8.00\nP01, Trenette al pesto, PRIMO, 6.50\n";

		MyPortateReader portateReader = new MyPortateReader(new StringReader(
				portate));
		Map<Categoria, List<Portata>> portataMap = portateReader.caricaPortate();

		assertEquals(4, portataMap.size());

		List<Portata> portatePerCategoria;

		portatePerCategoria = portataMap.get(Categoria.ANTIPASTO);
		assertNotNull(portatePerCategoria);
		assertEquals(2, portatePerCategoria.size());
		assertPortata(portatePerCategoria.get(0), "A01", "Prosciutto e melone", 9, Categoria.ANTIPASTO);
		assertPortata(portatePerCategoria.get(1), "A02", "Antipasto di mare", 8, Categoria.ANTIPASTO);
		
		portatePerCategoria = portataMap.get(Categoria.PRIMO);
		assertNotNull(portatePerCategoria);
		assertEquals(1, portatePerCategoria.size());
		assertPortata(portatePerCategoria.get(0), "P01", "Trenette al pesto", 6.5, Categoria.PRIMO);
		
		portatePerCategoria = portataMap.get(Categoria.SECONDO);
		assertNotNull(portatePerCategoria);
		assertEquals(0, portatePerCategoria.size());
		
		portatePerCategoria = portataMap.get(Categoria.DESSERT);
		assertNotNull(portatePerCategoria);
		assertEquals(0, portatePerCategoria.size());
	}

	private void assertPortata(Portata p, String codice, String nome,
			double prezzo, Categoria categoria) {
		assertEquals(codice, p.getCodice());
		assertEquals(nome, p.getNome());
		assertEquals(prezzo, p.getPrezzo(), 0.01);
		assertEquals(categoria, p.getCategoria());
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaPortate_MalformedPrezzo()
			throws MalformedFileException, IOException {
		final String portate = "A01, Prosciutto e melone, ANTIPASTO, X.00\nA02, Antipasto di mare, ANTIPASTO, 8.00\nP01, Trenette al pesto, PRIMO, 6.50\n";

		MyPortateReader portateReader = new MyPortateReader(new StringReader(
				portate));
		portateReader.caricaPortate();
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaPortate_Malformed_MissingTokenCategoria()
			throws MalformedFileException, IOException {
		final String portate = "A01, Prosciutto e melone, 9.00\nA02, Antipasto di mare, ANTIPASTO, 8.00\nP01, Trenette al pesto, PRIMO, 6.50\n";

		MyPortateReader portateReader = new MyPortateReader(new StringReader(
				portate));
		portateReader.caricaPortate();
	}

	@Test(expected = IOException.class)
	public void testClose_readerCloseClosesInnerReader() throws IOException {
		StringReader reader = new StringReader("La Peppina fa il caffè");
		assertTrue(reader.ready());
		MyPortateReader portateReader = new MyPortateReader(reader);
		portateReader.close();
		reader.ready();
	}

}
