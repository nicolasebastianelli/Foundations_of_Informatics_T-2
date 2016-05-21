package dentorestaurant.persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Portata;
import dentorestaurant.model.Menu;

public class MyMenuReader implements MenuReader {
	private BufferedReader reader;

	public MyMenuReader(Reader reader) {
		if (reader == null)
			throw new IllegalArgumentException();

		this.reader = new BufferedReader(reader);
	}

	public List<Menu> caricaMenu(Map<Categoria, List<Portata>> mappaPortate)
			throws MalformedFileException, IOException {
		ArrayList<Menu> listaMenu = new ArrayList<Menu>();
		Menu menu;
		while ((menu = leggiMenu(mappaPortate)) != null) {
			listaMenu.add(menu);
		}
		return listaMenu;
	}

	private Menu leggiMenu(Map<Categoria, List<Portata>> mappaPortate)
			throws MalformedFileException, IOException {
		try {
			String line = reader.readLine();
			if (line == null || line.isEmpty())
				return null;

			// prima riga: MENU + nome
			StringTokenizer tokenizer = new StringTokenizer(line);
			String token = tokenizer.nextToken().trim();
			if (!token.equals("MENU"))
				throw new MalformedFileException(
						"Errore nel formato del file: manca 'MENU'");
			String nome = tokenizer.nextToken().trim();

			Menu m = new Menu(nome);

			// righe successive: ELENCO PORTATE CATEGORIA

			line = reader.readLine();
			while (line != null && !line.trim().equalsIgnoreCase("End Menu")) {
				tokenizer = new StringTokenizer(line, ":,\t\r\n");
				token = tokenizer.nextToken().trim();
				// ---
				Categoria c = Categoria.valueOf(token.toUpperCase());				
				List<String> codiciPortateOfferte = new ArrayList<>();
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken().trim();
					codiciPortateOfferte.add(token);
				}
				// ---
				// System.out.println(codiciPortateOfferte);
				// ---
				for (String codice : codiciPortateOfferte) {
					List<Portata> listPortateCategoria = mappaPortate.get(c);
					Portata nostra = null;
					for (Portata p : listPortateCategoria)
						if (p.getCodice().equalsIgnoreCase(codice)) {
							nostra = p;
							break;
						}
					if (nostra == null)
						throw new MalformedFileException(
								"Portata non trovata nella mappa");
					m.getPortate(c).add(nostra);
				}
				line = reader.readLine();
			}
			// ---
			if (!line.equalsIgnoreCase("End Menu"))
				throw new MalformedFileException(
						"Errore nel formato del file: manca 'END MENU'");
			// System.out.println(m.toFullString());
			return m;
		} catch (IllegalArgumentException | NoSuchElementException e) {
			throw new MalformedFileException(e);
		}
	}

	@Override
	public void close() {
		try {
			reader.close();
		} catch (Exception e) {

		}
	}

	// --------------------

	public static void main(String a[]) {
		final String nomeFilePortate = "Portate.txt";
		Reader readerPortate = null;
		try {
			readerPortate = new FileReader(nomeFilePortate);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"File non trovato: " + e.getMessage());
			System.exit(1);
		}
		PortateReader reader1 = new MyPortateReader(readerPortate);
		Map<Categoria, List<Portata>> mappa1 = null;
		try {
			mappa1 = reader1.caricaPortate();
			System.out.println(mappa1);
		} catch (IOException | MalformedFileException e) {
			e.printStackTrace();
		}

		final String nomeFileMenu = "Menu.txt";
		Reader readerMenu = null;
		try {
			readerMenu = new FileReader(nomeFileMenu);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"File non trovato: " + e.getMessage());
			System.exit(1);
		}
		MenuReader reader2 = new MyMenuReader(readerMenu);
		try {
			List<Menu> mappa2 = reader2.caricaMenu(mappa1);
			System.out.println(mappa2);
		} catch (IOException | MalformedFileException e) {
			e.printStackTrace();
		}
	}

}
