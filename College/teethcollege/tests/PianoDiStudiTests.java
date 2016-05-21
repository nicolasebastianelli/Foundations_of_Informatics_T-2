package teethcollege.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.StringTokenizer;

import org.junit.Test;

import teethcollege.model.Categoria;
import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;
import teethcollege.model.Semestre;

public class PianoDiStudiTests {

	private static Insegnamento creaInsegnamentoObbligatorio() {
		return new Insegnamento(654321, "Pippo 1", "PPP/NN", 6, Semestre.PRIMO,
				Categoria.OBBLIGATORIO);
	}

//	private static Insegnamento creaInsegnamentoFacoltativo() {
//		return new Insegnamento(654321, "Pippo 1", "PPP/NN", 6, Semestre.PRIMO,
//				Categoria.A_SCELTA);
//	}

	private static Insegnamento creaInsegnamento(int i) {
		Semestre sem = null;
		Categoria cat = null;
		switch (i % 2)
		{
		case 0 : 
			sem = Semestre.PRIMO;
			cat = Categoria.OBBLIGATORIO;
			break;
		case 1 : 
			sem = Semestre.SECONDO;
			cat = Categoria.A_SCELTA;
			break;
		}
		return new Insegnamento(i, "XXX" + i + "XXX", "SSD" + i + "SSD", 12, sem, cat);
	}

	@Test
	public void successConstructorAndAccessors() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognome", "123456");

		assertEquals("nome", pds.getNome());
		assertEquals("cognome", pds.getCognome());
		assertEquals("123456", pds.getMatricola());
		assertEquals("cognome nome", pds.getCognomeNome());
		assertEquals(123456L, pds.getMatricolaAsLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void failConstructor_nomeNull() {
		new PianoDiStudi(null, "cognome", "123456");
	}

	@Test(expected = IllegalArgumentException.class)
	public void failConstructor_cognomeNull() {
		new PianoDiStudi("nome", null, "123456");
	}

	@Test(expected = IllegalArgumentException.class)
	public void failConstructor_matricolaNull() {
		new PianoDiStudi("nome", "cognome", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void failConstructor_matricolaNotALong() {
		new PianoDiStudi("nome", "cognome", "Peppina");
	}

	@Test
	public void testGetInsegnamenti() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognome", "123456");
		assertNotNull(pds.getInsegnamenti());
		assertEquals(0, pds.getInsegnamenti().size());
	}

	@Test
	public void testAggiungiInsegnamento() {
		Insegnamento toAdd = creaInsegnamentoObbligatorio();
		PianoDiStudi pds = new PianoDiStudi("nome", "cognome", "123456");
		pds.aggiungiInsegnamento(toAdd);

		assertEquals(1, pds.getInsegnamenti().size());
		assertEquals(toAdd, pds.getInsegnamenti().get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAggiungiInsegnamento_duplicatedInsegnamento() {
		Insegnamento toAdd = creaInsegnamentoObbligatorio();
		PianoDiStudi pds = new PianoDiStudi("nome", "cognome", "123456");
		pds.aggiungiInsegnamento(toAdd);
		pds.aggiungiInsegnamento(toAdd);
	}

	@Test
	public void testIsValid_NotValidIfEmpty() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognome", "123456");
		assertFalse(pds.isValid());
	}

	@Test
	public void testIsValid_NotValidNotEnoughCfus() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognome", "123456");
		pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		assertFalse(pds.isValid());
	}

	@Test
	public void testIsValid_Ok() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognome", "123456");
		for (int i = 0; i < 30; i++) {
			pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		}
		assertTrue(pds.isValid());
	}

	@Test
	public void testGetSommaCrediti_TotalIs0() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognome", "123456");
		assertEquals(0, pds.getSommaCrediti());
	}

	@Test
	public void testIsValid_TotalIs18() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognome", "123456");
		pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		
		assertEquals(18, pds.getSommaCrediti());
	}

	@Test
	public void testToString() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognxxx", "123456");
		String result = pds.toString();
		
		assertTrue(result.contains("nome"));
		assertTrue(result.contains("cognxxx"));
		assertTrue(result.contains("123456"));
	}

	@Test
	public void testToFullString() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognxxx", "123456");
		pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		pds.aggiungiInsegnamento(creaInsegnamentoObbligatorio());
		String result = pds.toFullString();
		
		StringTokenizer tok = new StringTokenizer(result, "\n");
		
		String line = tok.nextToken();
		assertTrue(line.contains("nome"));
		assertTrue(line.contains("cognxxx"));
		assertTrue(line.contains("123456"));
		
		for (int i = 0; i < 3; i++) {
			line = tok.nextToken();
			assertTrue(line.contains("Pippo 1"));
		}
	}

	@Test
	public void testSostituisciInsegnamento() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognxxx", "123456");
		Insegnamento i1 = creaInsegnamento(1);	//A scelta
		Insegnamento i2 = creaInsegnamento(2);	//Obbligatorio
		Insegnamento i3 = creaInsegnamento(3);	//A scelta
		Insegnamento i4 = creaInsegnamento(4);	//Obbligatorio
		pds.aggiungiInsegnamento(i1);
		pds.aggiungiInsegnamento(i2);
		pds.aggiungiInsegnamento(i4);
		
		pds.sostituisciInsegnamento(i1, i3);
		
		List<Insegnamento> insegnamenti = pds.getInsegnamenti();
		assertFalse(insegnamenti.contains(i1));
		assertTrue(insegnamenti.contains(i2));
		assertTrue(insegnamenti.contains(i3));
		assertTrue(insegnamenti.contains(i4));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSostituisciInsegnamento_failNonPresente() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognxxx", "123456");
		Insegnamento i1 = creaInsegnamento(1);
		Insegnamento i2 = creaInsegnamento(2);
		Insegnamento i4 = creaInsegnamento(4);
		pds.aggiungiInsegnamento(i1);
		pds.aggiungiInsegnamento(i2);
		
		pds.sostituisciInsegnamento(i4, i1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSostituisciInsegnamento_failSostituitoObbligatorio() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognxxx", "123456");
		Insegnamento i1 = creaInsegnamento(1);
		Insegnamento i2 = creaInsegnamento(2);
		Insegnamento i3 = creaInsegnamento(3);
		pds.aggiungiInsegnamento(i1);
		pds.aggiungiInsegnamento(i2);
		
		pds.sostituisciInsegnamento(i2, i3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSostituisciInsegnamento_failSostituendoObbligatorio() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognxxx", "123456");
		Insegnamento i1 = creaInsegnamento(1);
		Insegnamento i2 = creaInsegnamento(2);
		Insegnamento i4 = creaInsegnamento(4);
		pds.aggiungiInsegnamento(i1);
		pds.aggiungiInsegnamento(i2);
		
		pds.sostituisciInsegnamento(i1, i4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSostituisciInsegnamento_failCreditiDiversi() {
		PianoDiStudi pds = new PianoDiStudi("nome", "cognxxx", "123456");
		Insegnamento i1 = creaInsegnamento(1);
		Insegnamento i2 = creaInsegnamento(2);
		Insegnamento i3 = new Insegnamento(654321, "Pippo 1", "PPP/NN", 10, Semestre.PRIMO,
				Categoria.A_SCELTA);
		pds.aggiungiInsegnamento(i1);
		pds.aggiungiInsegnamento(i2);
		
		pds.sostituisciInsegnamento(i1, i3);
	}

}
