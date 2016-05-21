package teethcollege.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.Test;

import teethcollege.model.Categoria;
import teethcollege.model.Insegnamento;
import teethcollege.model.Semestre;
import teethcollege.persistence.MalformedFileException;
import teethcollege.persistence.MyInsegnamentiReader;

public class MyInsegnamentiReaderTests {

	@Test
	public void successfulConstructor() {
		new MyInsegnamentiReader(new StringReader("La Peppina fa il caff�"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void failingConstructor_NullParam() {
		new MyInsegnamentiReader(null);
	}

	@Test
	public void testCaricaInsegnamenti() throws MalformedFileException, IOException {
		final String insegnamenti = 
				  "27991, Analisi matematica 1,		MAT/05, 	 9, OBBLIGATORIO, S1\n"
				+ "28004, Fondamenti di Informatica 1,	ING-INF/05, 12, OBBLIGATORIO, S1\n"
				+ "29228, Geometria e Algebra,			MAT/03, 	 6, OBBLIGATORIO, S1\n"
				+ "32099, Diritto dell'informatica,	IUS/20,	     6, A SCELTA,     S2\n"
				+ "38378, Affidabilit� e qualit�,		ING-INF/07,  9, A SCELTA,	  S2\n";
		
		MyInsegnamentiReader insReader = new MyInsegnamentiReader(new StringReader(insegnamenti));
		Map<Long, Insegnamento> insMap = insReader.caricaInsegnamenti();
		
		assertEquals(insMap.size(), 5);
		
		Insegnamento ins;
		
		ins = insMap.get(27991L);
		assertNotNull(ins);
		assertInsegnamento(ins, 27991, "Analisi matematica 1", "MAT/05", 9, Categoria.OBBLIGATORIO, Semestre.PRIMO);
		
		ins = insMap.get(28004L);
		assertNotNull(ins);
		assertInsegnamento(ins, 28004, "Fondamenti di Informatica 1", "ING-INF/05", 12, Categoria.OBBLIGATORIO, Semestre.PRIMO);
		
		ins = insMap.get(29228L);
		assertNotNull(ins);
		assertInsegnamento(ins, 29228, "Geometria e Algebra", "MAT/03", 6, Categoria.OBBLIGATORIO, Semestre.PRIMO);
		
		ins = insMap.get(32099L);
		assertNotNull(ins);
		assertInsegnamento(ins, 32099, "Diritto dell'informatica", "IUS/20", 6, Categoria.A_SCELTA, Semestre.SECONDO);
		
		ins = insMap.get(38378L);
		assertNotNull(ins);
		assertInsegnamento(ins, 38378, "Affidabilit� e qualit�", "ING-INF/07", 9, Categoria.A_SCELTA, Semestre.SECONDO);
	}

	private void assertInsegnamento(Insegnamento ins, long codice, String nome,
			String ssd, int crediti, Categoria categoria, Semestre semestre) {
		assertEquals(ins.getCodice(), codice);
		assertEquals(ins.getNome(), nome);
		assertEquals(ins.getSsd(), ssd);
		assertEquals(ins.getCfu(), crediti);
		assertEquals(ins.getCategoria(), categoria);
		assertEquals(ins.getSemestre(), semestre);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaInsegnamenti_MalformedCodice() throws MalformedFileException, IOException {
		final String insegnamenti = 
				  "27991, Analisi matematica 1,		MAT/05, 	 9, OBBLIGATORIO, S1\n"
				+ "28004, Fondamenti di Informatica 1,	ING-INF/05, 12, OBBLIGATORIO, S1\n"
				+ "29XX8, Geometria e Algebra,			MAT/03, 	 6, OBBLIGATORIO, S1\n"
				+ "32099, Diritto dell'informatica,	IUS/20,	     6, A SCELTA,     S2\n"
				+ "38378, Affidabilit� e qualit�,		ING-INF/07,  9, A SCELTA,	  S2\n";
		
		MyInsegnamentiReader insReader = new MyInsegnamentiReader(new StringReader(insegnamenti));
		insReader.caricaInsegnamenti();
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaInsegnamenti_Malformed_MissingTokenNome() throws MalformedFileException, IOException {
		final String insegnamenti = 
				  "27991, Analisi matematica 1,		MAT/05, 	 9, OBBLIGATORIO, S1\n"
				+ "28004, Fondamenti di Informatica 1,	ING-INF/05, 12, OBBLIGATORIO, S1\n"
				+ "29228,			MAT/03, 	 6, OBBLIGATORIO, S1\n"
				+ "32099, Diritto dell'informatica,	IUS/20,	     6, A SCELTA,     S2\n"
				+ "38378, Affidabilit� e qualit�,		ING-INF/07,  9, A SCELTA,	  S2\n";
		
		MyInsegnamentiReader insReader = new MyInsegnamentiReader(new StringReader(insegnamenti));
		insReader.caricaInsegnamenti();
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaInsegnamenti_Malformed_MissingTokenSsd() throws MalformedFileException, IOException {
		final String insegnamenti = 
				  "27991, Analisi matematica 1,		MAT/05, 	 9, OBBLIGATORIO, S1\n"
				+ "28004, Fondamenti di Informatica 1,	ING-INF/05, 12, OBBLIGATORIO, S1\n"
				+ "29228, Geometria e Algebra,			6, OBBLIGATORIO, S1\n"
				+ "32099, Diritto dell'informatica,	IUS/20,	     6, A SCELTA,     S2\n"
				+ "38378, Affidabilit� e qualit�,		ING-INF/07,  9, A SCELTA,	  S2\n";
		
		MyInsegnamentiReader insReader = new MyInsegnamentiReader(new StringReader(insegnamenti));
		insReader.caricaInsegnamenti();
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaInsegnamenti_Malformed_Cfu() throws MalformedFileException, IOException {
		final String insegnamenti = 
				  "27991, Analisi matematica 1,		MAT/05, 	 9, OBBLIGATORIO, S1\n"
				+ "28004, Fondamenti di Informatica 1,	ING-INF/05, 12, OBBLIGATORIO, S1\n"
				+ "2928, Geometria e Algebra,			MAT/03, 	 XX, OBBLIGATORIO, S1\n"
				+ "32099, Diritto dell'informatica,	IUS/20,	     6, A SCELTA,     S2\n"
				+ "38378, Affidabilit� e qualit�,		ING-INF/07,  9, A SCELTA,	  S2\n";
		
		MyInsegnamentiReader insReader = new MyInsegnamentiReader(new StringReader(insegnamenti));
		insReader.caricaInsegnamenti();
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaInsegnamenti_Malformed_Obbligatorieta() throws MalformedFileException, IOException {
		final String insegnamenti = 
				  "27991, Analisi matematica 1,		MAT/05, 	 9, OBBLIGATORIO, S1\n"
				+ "28004, Fondamenti di Informatica 1,	ING-INF/05, 12, OBBLIGATORIO, S1\n"
				+ "2928, Geometria e Algebra,			MAT/03, 	 6, OBBLIXXXGATORIO, S1\n"
				+ "32099, Diritto dell'informatica,	IUS/20,	     6, A SCELTA,     S2\n"
				+ "38378, Affidabilit� e qualit�,		ING-INF/07,  9, A SCELTA,	  S2\n";
		
		MyInsegnamentiReader insReader = new MyInsegnamentiReader(new StringReader(insegnamenti));
		insReader.caricaInsegnamenti();
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaInsegnamenti_Malformed_Semestre() throws MalformedFileException, IOException {
		final String insegnamenti = 
				  "27991, Analisi matematica 1,		MAT/05, 	 9, OBBLIGATORIO, S1\n"
				+ "28004, Fondamenti di Informatica 1,	ING-INF/05, 12, OBBLIGATORIO, S1\n"
				+ "2928, Geometria e Algebra,			MAT/03, 	 6, OBBLIGATORIO, SX\n"
				+ "32099, Diritto dell'informatica,	IUS/20,	     6, A SCELTA,     S2\n"
				+ "38378, Affidabilit� e qualit�,		ING-INF/07,  9, A SCELTA,	  S2\n";
		
		MyInsegnamentiReader insReader = new MyInsegnamentiReader(new StringReader(insegnamenti));
		insReader.caricaInsegnamenti();
	}

	@Test(expected = IOException.class)
	public void testClose_readerCloseClosesInnerReader()
			throws IOException {
		StringReader reader = new StringReader("La Peppina fa il caff�");
		assertTrue(reader.ready());
		MyInsegnamentiReader insReader = new MyInsegnamentiReader(reader);
		insReader.close();
		reader.ready();
	}

}
