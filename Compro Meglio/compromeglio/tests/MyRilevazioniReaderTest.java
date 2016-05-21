package compromeglio.tests;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import compromeglio.model.Bene;
import compromeglio.model.Categoria;
import compromeglio.model.Rilevazione;
import compromeglio.persistence.MalformedFileException;
import compromeglio.persistence.MyRilevazioniReader;
import compromeglio.persistence.RilevazioniReader;

public class MyRilevazioniReaderTest {

	private static Bene[] beniDiProva;

	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy",
			Locale.ITALIAN);

	@Test
	public void testCaricaElementi() throws IOException,
			MalformedFileException, ParseException {

		beniDiProva = new Bene[] {
				new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA),
				new Bene(11114, "Rigatoni Zannoni", Categoria.PASTA),
				new Bene(22222, "Pollo intero", Categoria.CARNE) };

		Map<Long, Bene> mappa = new HashMap<Long, Bene>();
		mappa.put(11111l, beniDiProva[0]);
		mappa.put(11114l, beniDiProva[1]);
		mappa.put(22222l, beniDiProva[2]);

		String data = "11111,  Market Sperduto, 19/01/2014, 1.55\n"
				+ "11114,  Market Dentinia, 18/01/2014, 1.30\n"
				+ "11114,  Market Zannonia, 19/01/2014, 1.25\n"
				+ "22222,  Market Zannonia, 19/01/2014, 3.25\n"
				+ "22222,  Market Sperduto, 19/01/2014, 3.70\n";

		StringReader reader = new StringReader(data);

		RilevazioniReader mrr = new MyRilevazioniReader(reader);
		List<Rilevazione> rilevazioni = mrr.caricaRilevazioni(mappa);

		Assert.assertEquals(5, rilevazioni.size());
		Assert.assertEquals(new Rilevazione(beniDiProva[0], "Market Sperduto",
				df.parse("19/01/2014"), 1.55f), rilevazioni.get(0));
		Assert.assertEquals(new Rilevazione(beniDiProva[1], "Market Dentinia",
				df.parse("18/01/2014"), 1.30f), rilevazioni.get(1));
		Assert.assertEquals(new Rilevazione(beniDiProva[1], "Market Zannonia",
				df.parse("19/01/2014"), 1.25f), rilevazioni.get(2));
		Assert.assertEquals(new Rilevazione(beniDiProva[2], "Market Zannonia",
				df.parse("19/01/2014"), 3.25f), rilevazioni.get(3));
		Assert.assertEquals(new Rilevazione(beniDiProva[2], "Market Sperduto",
				df.parse("19/01/2014"), 3.70f), rilevazioni.get(4));
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaElementiElementoCodiceManncante() throws IOException,
			MalformedFileException, ParseException {

		beniDiProva = new Bene[] {
				new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA),
				new Bene(11114, "Rigatoni Zannoni", Categoria.PASTA),
				new Bene(22222, "Pollo intero", Categoria.CARNE) };

		Map<Long, Bene> mappa = new HashMap<Long, Bene>();
		mappa.put(11111l, beniDiProva[0]);
		mappa.put(11114l, beniDiProva[1]);
		mappa.put(22222l, beniDiProva[2]);

		String data = "11111,  Market Sperduto, 19/01/2014, 1.55\n"
				+ "Market Dentinia, 18/01/2014, 1.30\n"
				+ "11114,  Market Zannonia, 19/01/2014, 1.25\n"
				+ "22222,  Market Zannonia, 19/01/2014, 3.25\n"
				+ "22222,  Market Sperduto, 19/01/2014, 3.70\n";

		StringReader reader = new StringReader(data);

		RilevazioniReader mrr = new MyRilevazioniReader(reader);
		mrr.caricaRilevazioni(mappa);
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaElementiElementoLuogoManncante() throws IOException,
			MalformedFileException, ParseException {

		beniDiProva = new Bene[] {
				new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA),
				new Bene(11114, "Rigatoni Zannoni", Categoria.PASTA),
				new Bene(22222, "Pollo intero", Categoria.CARNE) };

		Map<Long, Bene> mappa = new HashMap<Long, Bene>();
		mappa.put(11111l, beniDiProva[0]);
		mappa.put(11114l, beniDiProva[1]);
		mappa.put(22222l, beniDiProva[2]);

		String data = "11111,  Market Sperduto, 19/01/2014, 1.55\n"
				+ "11114,  Market Zannonia, 19/01/2014, 1.25\n"
				+ "22222,  19/01/2014, 3.25\n"
				+ "22222,  Market Sperduto, 19/01/2014, 3.70\n";

		StringReader reader = new StringReader(data);

		RilevazioniReader mrr = new MyRilevazioniReader(reader);
		mrr.caricaRilevazioni(mappa);
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaElementiElementoDataNonValida() throws IOException,
			MalformedFileException, ParseException {

		beniDiProva = new Bene[] {
				new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA),
				new Bene(11114, "Rigatoni Zannoni", Categoria.PASTA),
				new Bene(22222, "Pollo intero", Categoria.CARNE) };

		Map<Long, Bene> mappa = new HashMap<Long, Bene>();
		mappa.put(11111l, beniDiProva[0]);
		mappa.put(11114l, beniDiProva[1]);
		mappa.put(22222l, beniDiProva[2]);

		String data = "11111,  Market Sperduto, 19/01/2014, 1.55\n"
				+ "11114,  Market Dentinia, 18/01/2014, 1.30\n"
				+ "11114,  Market Zannonia, 34/2014, 1.25\n"
				+ "22222,  Market Zannonia, 19/01/2014, 3.25\n"
				+ "22222,  Market Sperduto, 19/01/2014, 3.70\n";

		StringReader reader = new StringReader(data);

		RilevazioniReader mrr = new MyRilevazioniReader(reader);
		mrr.caricaRilevazioni(mappa);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaElementiElementoDataMancante() throws IOException,
			MalformedFileException, ParseException {

		beniDiProva = new Bene[] {
				new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA),
				new Bene(11114, "Rigatoni Zannoni", Categoria.PASTA),
				new Bene(22222, "Pollo intero", Categoria.CARNE) };

		Map<Long, Bene> mappa = new HashMap<Long, Bene>();
		mappa.put(11111l, beniDiProva[0]);
		mappa.put(11114l, beniDiProva[1]);
		mappa.put(22222l, beniDiProva[2]);

		String data = "11111,  Market Sperduto, 19/01/2014, 1.55\n"
				+ "11114,  Market Dentinia, 18/01/2014, 1.30\n"
				+ "11114,  Market Zannonia, 1.25\n"
				+ "22222,  Market Zannonia, 19/01/2014, 3.25\n"
				+ "22222,  Market Sperduto, 19/01/2014, 3.70\n";

		StringReader reader = new StringReader(data);

		RilevazioniReader mrr = new MyRilevazioniReader(reader);
		mrr.caricaRilevazioni(mappa);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaElementiElementoPrezzoNonValido() throws IOException,
			MalformedFileException, ParseException {

		beniDiProva = new Bene[] {
				new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA),
				new Bene(11114, "Rigatoni Zannoni", Categoria.PASTA),
				new Bene(22222, "Pollo intero", Categoria.CARNE) };

		Map<Long, Bene> mappa = new HashMap<Long, Bene>();
		mappa.put(11111l, beniDiProva[0]);
		mappa.put(11114l, beniDiProva[1]);
		mappa.put(22222l, beniDiProva[2]);

		String data = "11111,  Market Sperduto, 19/01/2014, 1.55\n"
				+ "11114,  Market Dentinia, 18/01/2014, 1.30\n"
				+ "11114,  Market Zannonia, 34/2014, 1.25\n"
				+ "22222,  Market Zannonia, 19/01/2014, 3.F5\n"
				+ "22222,  Market Sperduto, 19/01/2014, 3.70\n";

		StringReader reader = new StringReader(data);

		RilevazioniReader mrr = new MyRilevazioniReader(reader);
		mrr.caricaRilevazioni(mappa);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaElementiPrezzoMancante() throws IOException,
			MalformedFileException, ParseException {

		beniDiProva = new Bene[] {
				new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA),
				new Bene(11114, "Rigatoni Zannoni", Categoria.PASTA),
				new Bene(22222, "Pollo intero", Categoria.CARNE) };

		Map<Long, Bene> mappa = new HashMap<Long, Bene>();
		mappa.put(11111l, beniDiProva[0]);
		mappa.put(11114l, beniDiProva[1]);
		mappa.put(22222l, beniDiProva[2]);

		String data = "11111,  Market Sperduto, 19/01/2014, 1.55\n"
				+ "11114,  Market Dentinia, 18/01/2014\n"
				+ "11114,  Market Zannonia, 34/2014, 1.25\n"
				+ "22222,  Market Zannonia, 19/01/2014, 3.25\n"
				+ "22222,  Market Sperduto, 19/01/2014, 3.70\n";

		StringReader reader = new StringReader(data);

		RilevazioniReader mrr = new MyRilevazioniReader(reader);
		mrr.caricaRilevazioni(mappa);
	}

}
