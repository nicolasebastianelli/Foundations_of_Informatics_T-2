package dentinia.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import dentinia.model.*;

public class MyVotiReader implements VotiReader {

	private List<Partito> listaPartiti;
	private BufferedReader reader;
	private int seggiDaAssegnare;

	public MyVotiReader(Reader reader) {
		this.reader = new BufferedReader(reader);
		listaPartiti = new ArrayList<Partito>();
		seggiDaAssegnare = 0;
	}

	public List<Partito> caricaElementi() throws IOException,
			BadFileFormatException {

		String line = null;

		line = reader.readLine();
		if (line != null && line.contains("SEGGI")) {
			//System.out.println(line.substring(line.indexOf(":") + 1));
			try {
				seggiDaAssegnare = Integer.parseInt(line.substring(
						line.indexOf(":") + 1).trim());
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				throw new BadFileFormatException(e);
			}
		} else
			throw new BadFileFormatException(
					"Formato file errato - manca sezione SEGGI");

		try {

			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ":\n\r");
				String nome = tokenizer.nextToken().trim();
				int voti = Integer.parseInt(tokenizer.nextToken().trim());
				listaPartiti.add(new Partito(nome, voti));
			}

		} catch (NoSuchElementException | NumberFormatException e) {
			throw new BadFileFormatException(e);
		}

		return listaPartiti;
	}

	public int getSeggi() {
		return seggiDaAssegnare;
	}

	public List<Partito> getListaPartiti() {
		return listaPartiti;
	}
}
