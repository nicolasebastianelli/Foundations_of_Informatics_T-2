package teethcollege.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import teethcollege.model.*;
import teethcollege.persistence.MalformedFileException;
import teethcollege.persistence.MyEsamiManager;
import static teethcollege.tests.TestUtils.*;

public class MyEsamiManagerTests {
	
	private static Date createDate(int day, int month, int year) {
		GregorianCalendar cal = new GregorianCalendar(year, month, day, 0, 0, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		return cal.getTime();
	}

	private static void assertEqualsEsame(Esame e, Insegnamento ins, int voto, int day, int month, int year) {
		assertSame(ins, e.getInsegnamento());
		assertEquals(voto, e.getValoreVoto());
		Date date = createDate(day, month, year);
		assertEquals(date, e.getDate());		
	}
	
	@Test
	public void testCaricaEsami_HappyPath() throws MalformedFileException, IOException {
		String data = "27991, 20, 06/20/13\n27993, 20, 06/21/13\n27996, 1, 06/22/13\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		Insegnamento ins1 = createObbligatorio(27991);
		map.put(ins1.getCodice(), ins1);
		Insegnamento ins2 = createObbligatorio(27993);
		map.put(ins2.getCodice(), ins2);
		Insegnamento ins3 = createObbligatorio(27996);
		map.put(ins3.getCodice(), ins3);
		
		MyEsamiManager manager = new MyEsamiManager();
		List<Esame> esami = manager.caricaEsami(new StringReader(data), map);
		
		assertEquals(3, esami.size());
		assertEqualsEsame(esami.get(0), ins1, 20, 20, GregorianCalendar.JUNE, 2013);
		assertEqualsEsame(esami.get(1), ins2, 20, 21, GregorianCalendar.JUNE, 2013);
		assertEqualsEsame(esami.get(2), ins3, 1,  22, GregorianCalendar.JUNE, 2013);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaEsami_MalformedDate() throws MalformedFileException, IOException {
		String data = "27991, 20, 06/20/13\n27993, 20, 06/21/aa\n27996, 1, 22/06/13\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		Insegnamento ins1 = createObbligatorio(27991);
		map.put(ins1.getCodice(), ins1);
		Insegnamento ins2 = createObbligatorio(27993);
		map.put(ins2.getCodice(), ins2);
		Insegnamento ins3 = createObbligatorio(27996);
		map.put(ins3.getCodice(), ins3);
		
		MyEsamiManager manager = new MyEsamiManager();
		manager.caricaEsami(new StringReader(data), map);
	}
	
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaEsami_MalformedVoto() throws MalformedFileException, IOException {
		String data = "27991, 20, 06/20/13\n27993, bb, 06/21/13\n27996, 1, 22/06/13\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		Insegnamento ins1 = createObbligatorio(27991);
		map.put(ins1.getCodice(), ins1);
		Insegnamento ins2 = createObbligatorio(27993);
		map.put(ins2.getCodice(), ins2);
		Insegnamento ins3 = createObbligatorio(27996);
		map.put(ins3.getCodice(), ins3);
		
		MyEsamiManager manager = new MyEsamiManager();
		manager.caricaEsami(new StringReader(data), map);
	}

	
	@Test(expected = MalformedFileException.class)
	public void testCaricaEsami_InsegnamentoNonValido() throws MalformedFileException, IOException {
		String data = "27991, 20, 06/20/13\n30000, 20, 06/21/13\n27996, 1, 22/06/13\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		Insegnamento ins1 = createObbligatorio(27991);
		map.put(ins1.getCodice(), ins1);
		Insegnamento ins2 = createObbligatorio(27993);
		map.put(ins2.getCodice(), ins2);
		Insegnamento ins3 = createObbligatorio(27996);
		map.put(ins3.getCodice(), ins3);
		
		MyEsamiManager manager = new MyEsamiManager();
		manager.caricaEsami(new StringReader(data), map);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaEsami_MalformedCodInsegnamento() throws MalformedFileException, IOException {
		String data = "27991, 20, 06/20/13\n27xxx, 20, 06/21/13\n27996, 1, 22/06/13\n";
		
		HashMap<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		Insegnamento ins1 = createObbligatorio(27991);
		map.put(ins1.getCodice(), ins1);
		Insegnamento ins2 = createObbligatorio(27993);
		map.put(ins2.getCodice(), ins2);
		Insegnamento ins3 = createObbligatorio(27996);
		map.put(ins3.getCodice(), ins3);
		
		MyEsamiManager manager = new MyEsamiManager();
		manager.caricaEsami(new StringReader(data), map);
	}

	@Test
	public void testSalvaEsami() throws IOException {
		ArrayList<Esame> esami = new ArrayList<Esame>();

		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		Esame eIns1 = new Esame(ins1, 10, createDate(8, GregorianCalendar.SEPTEMBER, 2013));
		esami.add(eIns1);
		
		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		Esame eIns2 = new Esame(ins2, 30, createDate(9, GregorianCalendar.SEPTEMBER, 2013));
		esami.add(eIns2);
				
		MyEsamiManager manager = new MyEsamiManager();
		StringWriter writer = new StringWriter();
		manager.salvaEsami(writer, esami);
		
		String out = writer.getBuffer().toString().trim().replace(" ", "");
		assertTrue(out.contains("1,10,9/8/13"));
		assertTrue(out.contains("2,30,9/9/13"));
	}

}
