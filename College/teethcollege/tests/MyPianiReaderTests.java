package teethcollege.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;
import teethcollege.persistence.MalformedFileException;
import teethcollege.persistence.MyPianiReader;

import static teethcollege.tests.TestUtils.*;

public class MyPianiReaderTests {	

	@Test
	public void successfulConstructor() {
		new MyPianiReader(new StringReader("La Peppina fa il caffè"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void failingConstructor_NullParam() {
		new MyPianiReader(null);
	}

	@Test
	public void testCaricaPianiDiStudi() throws MalformedFileException, IOException {
		String toParse = "STUDENTE	0000987654	Giuseppe	Rossi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004," + "28012\n," + "28024\n"
				+ "FINE STUDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004,\n" + "FINE STUDENTE\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		map.put(27991L, createObbligatorio(27991L));
		map.put(28004L, createObbligatorio(28004L));
		map.put(28012L, createObbligatorio(28012L));
		map.put(28024L, createObbligatorio(28024L));
		
		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
		List<PianoDiStudi> piani = reader.caricaPianiDiStudi(map);
		
		PianoDiStudi p;
		List<Insegnamento> ins;
		
		assertEquals(2, piani.size());

		p = piani.get(0);
		assertEquals("Giuseppe", p.getNome());
		assertEquals("Rossi", p.getCognome());
		assertEquals(987654L, p.getMatricolaAsLong());
		ins = p.getInsegnamenti();
		assertEquals(4, ins.size());
		assertEquals(27991L, ins.get(0).getCodice());
		assertEquals(28004L, ins.get(1).getCodice());
		assertEquals(28012L, ins.get(2).getCodice());
		assertEquals(28024L, ins.get(3).getCodice());
		
		p = piani.get(1);
		assertEquals("Paolo", p.getNome());
		assertEquals("Verdi", p.getCognome());
		assertEquals(907652L, p.getMatricolaAsLong());
		ins = p.getInsegnamenti();
		assertEquals(2, ins.size());
		assertEquals(27991L, ins.get(0).getCodice());
		assertEquals(28004L, ins.get(1).getCodice());

	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaPianiDiStudi_missingStudenteKeyword() throws MalformedFileException, IOException {
		String toParse = "STXUDXENTE	0000987654	Giuseppe	Rossi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004," + "28012\n," + "28024\n"
				+ "FINE STUDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004,\n" + "FINE STUDENTE\n\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		map.put(27991L, createObbligatorio(27991L));
		map.put(28004L, createObbligatorio(28004L));
		map.put(28012L, createObbligatorio(28012L));
		map.put(28024L, createObbligatorio(28024L));
		
		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
		reader.caricaPianiDiStudi(map);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaPianiDiStudi_matricolaIsNotALong() throws MalformedFileException, IOException {
		String toParse = "STUDENTE	00009X87654	Giuseppe	Rossi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004," + "28012\n," + "28024\n"
				+ "FINE STUDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004,\n" + "FINE STUDENTE\n\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		map.put(27991L, createObbligatorio(27991L));
		map.put(28004L, createObbligatorio(28004L));
		map.put(28012L, createObbligatorio(28012L));
		map.put(28024L, createObbligatorio(28024L));
		
		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
		reader.caricaPianiDiStudi(map);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaPianiDiStudi_missingNomeOrCognome() throws MalformedFileException, IOException {
		String toParse = "STUDENTE	0000987654	GiuseppeXXX\n"
				+ "PIANO DI STUDI\n" + "27991, 28004," + "28012\n," + "28024\n"
				+ "FINE STUDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004,\n" + "FINE STUDENTE\n\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		map.put(27991L, createObbligatorio(27991L));
		map.put(28004L, createObbligatorio(28004L));
		map.put(28012L, createObbligatorio(28012L));
		map.put(28024L, createObbligatorio(28024L));
		
		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
		reader.caricaPianiDiStudi(map);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaPianiDiStudi_missingPianoDiStudiKeyword() throws MalformedFileException, IOException {
		String toParse = "STUDENTE	0000987654	Giuseppe	Rossi\n"
				+ "PIANOXDIXSTUDI\n" + "27991, 28004," + "28012\n," + "28024\n"
				+ "FINE STUDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004,\n" + "FINE STUDENTE\n\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		map.put(27991L, createObbligatorio(27991L));
		map.put(28004L, createObbligatorio(28004L));
		map.put(28012L, createObbligatorio(28012L));
		map.put(28024L, createObbligatorio(28024L));
		
		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
		reader.caricaPianiDiStudi(map);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaPianiDiStudi_missingFineStudenteKeyword() throws MalformedFileException, IOException {
		String toParse = "STUDENTE	0000987654	Giuseppe	Rossi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004," + "28012\n," + "28024\n"
				+ "FIXNEXSTUXDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004,\n" + "FINE STUDENTE\n\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		map.put(27991L, createObbligatorio(27991L));
		map.put(28004L, createObbligatorio(28004L));
		map.put(28012L, createObbligatorio(28012L));
		map.put(28024L, createObbligatorio(28024L));
		
		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
		reader.caricaPianiDiStudi(map);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaPianiDiStudi_codiceInsegnamentoIsNotALong() throws MalformedFileException, IOException {
		String toParse = "STUDENTE	0000987654	Giuseppe	Rossi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004," + "28XXX012\n," + "28024\n"
				+ "FINE STUDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004,\n" + "FINE STUDENTE\n\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		map.put(27991L, createObbligatorio(27991L));
		map.put(28004L, createObbligatorio(28004L));
		map.put(28012L, createObbligatorio(28012L));
		map.put(28024L, createObbligatorio(28024L));
		
		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
		reader.caricaPianiDiStudi(map);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaPianiDiStudi_codiceInsegnamentoMissingInMap() throws MalformedFileException, IOException {
		String toParse = "STUDENTE	0000987654	Giuseppe	Rossi\n"
				+ "PIANO DI STUDI\n" + "27991, 999999," + "28012\n," + "28024\n"
				+ "FINE STUDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004,\n" + "FINE STUDENTE\n\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		map.put(27991L, createObbligatorio(27991L));
		map.put(28004L, createObbligatorio(28004L));
		map.put(28012L, createObbligatorio(28012L));
		map.put(28024L, createObbligatorio(28024L));
		
		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
		reader.caricaPianiDiStudi(map);
	}

	//@@@ Failing Test Prototype @@@
//	@Test(expected = MalformedFileException.class)
//	public void testCaricaPianiDiStudi_fail() throws MalformedFileException, IOException {
//		String toParse = "STUDENTE	0000987654	Giuseppe	Rossi\n"
//				+ "PIANO DI STUDI\n" + "27991, 28004," + "28012\n," + "28024\n"
//				+ "FINE STUDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
//				+ "PIANO DI STUDI\n" + "27991, 28004,\n" + "FINE STUDENTE\n\n";
//		
//		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
//		map.put(27991L, createObbligatorio(27991L));
//		map.put(28004L, createObbligatorio(28004L));
//		map.put(28012L, createObbligatorio(28012L));
//		map.put(28024L, createObbligatorio(28024L));
//		
//		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
//		List<PianoDiStudi> piani = reader.caricaPianiDiStudi(map);
//	}

	@Test
	public void testCaricaPianiDiStudi_PianoVuoto() throws MalformedFileException, IOException {
		String toParse = "STUDENTE	0000987654	Giuseppe	Rossi\n"
				+ "PIANO DI STUDI\n" + "27991, 28004," + "28012\n," + "28024\n"
				+ "FINE STUDENTE\n" + "STUDENTE	0000907652	Paolo	Verdi\n"
				+ "PIANO DI STUDI\n\n" + "FINE STUDENTE\n\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		map.put(27991L, createObbligatorio(27991L));
		map.put(28004L, createObbligatorio(28004L));
		map.put(28012L, createObbligatorio(28012L));
		map.put(28024L, createObbligatorio(28024L));
		
		MyPianiReader reader = new MyPianiReader(new StringReader(toParse));
		List<PianoDiStudi> piani = reader.caricaPianiDiStudi(map);
		
		PianoDiStudi p;
		List<Insegnamento> ins;
		
		assertEquals(2, piani.size());
		
		p = piani.get(0);
		assertEquals("Giuseppe", p.getNome());
		assertEquals("Rossi", p.getCognome());
		assertEquals(987654L, p.getMatricolaAsLong());
		ins = p.getInsegnamenti();
		assertEquals(4, ins.size());
		assertEquals(27991L, ins.get(0).getCodice());
		assertEquals(28004L, ins.get(1).getCodice());
		assertEquals(28012L, ins.get(2).getCodice());
		assertEquals(28024L, ins.get(3).getCodice());
		
		p = piani.get(1);
		assertEquals("Paolo", p.getNome());
		assertEquals("Verdi", p.getCognome());
		assertEquals(907652L, p.getMatricolaAsLong());
		ins = p.getInsegnamenti();
		assertEquals(0, ins.size());
	}



	@Test(expected = IOException.class)
	public void testClose_readerCloseClosesInnerReader() throws IOException {
		StringReader reader = new StringReader("La Peppina fa il caffè");
		assertTrue(reader.ready());
		MyPianiReader pianiReader = new MyPianiReader(reader);
		pianiReader.close();
		reader.ready();
	}

}
