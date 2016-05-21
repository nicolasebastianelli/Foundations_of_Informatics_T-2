package teethcollege.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import teethcollege.model.Esame;
import teethcollege.model.Insegnamento;

public class MyEsamiManager implements EsamiManager {

	public List<Esame> caricaEsami(Reader reader, Map<Long, Insegnamento> mappaInsegnamenti) throws MalformedFileException, IOException {
		BufferedReader myReader = new BufferedReader(reader);
		ArrayList<Esame> allExams = new ArrayList<Esame>();
		Esame esame;
		while ((esame = leggiEsame(myReader, mappaInsegnamenti)) != null) {
			allExams.add(esame);
		}
		myReader.close();
		return allExams;
	}

	public void salvaEsami(Writer writer, List<Esame> esami) throws IOException {
		PrintWriter myWriter = new PrintWriter(writer, true); // autoflush ON
		for (Esame e: esami){
			myWriter.println(e.toCanonicalString());
		}
		myWriter.close();
	}

	
	private Esame leggiEsame(BufferedReader reader, Map<Long, Insegnamento> mappaInsegnamenti)
			throws MalformedFileException, IOException {
		String line = reader.readLine();
		if (line == null || line.isEmpty())
			return null;
		try {
			StringTokenizer tokenizer = new StringTokenizer(line, ",\t\r\n");
			long codice = Long.parseLong(tokenizer.nextToken().trim());
			int voto = Integer.parseInt(tokenizer.nextToken().trim());
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
			Date data = formatter.parse(tokenizer.nextToken().trim());
			Insegnamento insegnamento = mappaInsegnamenti.get(codice);
			if (insegnamento == null)
				throw new MalformedFileException("Codice insegnamento non valido");
			return new Esame(insegnamento, voto, data);
		} catch (NoSuchElementException | NumberFormatException | ParseException e) {
			reader.close();
			throw new MalformedFileException(e);
		}
	}
}
