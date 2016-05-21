package oroscopo.persistence.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.BadFileFormatException;
import oroscopo.persistence.TextFileOroscopoRepository;
import oroscopo.persistence.OroscopoRepository;

public class MyOroscopoReaderTest {

	@Test
	public void testMyOroscopoReader() throws IOException, BadFileFormatException {
		
		Reader mr = new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE");
		
		new TextFileOroscopoRepository(mr);
	}
	
	@Test
	public void testMyOroscopoReaderMultiSezione() throws IOException, BadFileFormatException {
		
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		new TextFileOroscopoRepository(mr);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testMyOroscopoReaderNomeSezioneMancante1() throws IOException, BadFileFormatException {
		
		Reader mr = 
				new StringReader("avrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		new TextFileOroscopoRepository(mr);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testMyOroscopoReaderNomeSezioneMancante2() throws IOException, BadFileFormatException {
		
		Reader mr = 
				new StringReader("stringasenzaspazichepuosempresuccedere\t\t4\tARIETE,TORO,GEMELLI\naltrastringasenzaspazi\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		new TextFileOroscopoRepository(mr);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testMyOroscopoReaderMancaValore() throws IOException, BadFileFormatException {
		
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t\t\nFINE");
		
		new TextFileOroscopoRepository(mr);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testMyOroscopoReaderMancaFine() throws IOException, BadFileFormatException {
		
		Reader mr = 
				new StringReader("avrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\n\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		new TextFileOroscopoRepository(mr);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testMyOroscopoReaderSezioneSenzaFrasi() throws IOException, BadFileFormatException {
		
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\n\nFINE");
		
		new TextFileOroscopoRepository(mr);
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testMyOroscopoReaderSegniNonParsabili() throws IOException, BadFileFormatException {
		
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tMUCCA,SIAMESI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		new TextFileOroscopoRepository(mr);
	}

	@Test
	public void testGetPrevisioniPerSezioneEsistenteLowercase() throws IOException, BadFileFormatException {
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		OroscopoRepository or = 
				new TextFileOroscopoRepository(mr);
		
		assertEquals(2, or.getPrevisioni("nomesezione").size());
	}
	
	@Test
	public void testLetturaCorrettaPrevisioni1() throws IOException, BadFileFormatException {
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		OroscopoRepository or = 
				new TextFileOroscopoRepository(mr);
		
		assertEquals("avrai la testa un po' altrove", or.getPrevisioni("nomesezione").get(0).getPrevisione());
		assertEquals(4, or.getPrevisioni("nomesezione").get(0).getValore());
		Set<SegnoZodiacale> validi = new HashSet<SegnoZodiacale>() {
			private static final long serialVersionUID = 1L;

		{
				add(SegnoZodiacale.ARIETE);
				add(SegnoZodiacale.TORO);
				add(SegnoZodiacale.GEMELLI);
			}};
		for(SegnoZodiacale s : SegnoZodiacale.values()) {
			if(validi.contains(s)) assertTrue(or.getPrevisioni("nomesezione").get(0).validaPerSegno(s));
			else
				assertFalse(or.getPrevisioni("nomesezione").get(0).validaPerSegno(s));
		}
		
		assertEquals("grande intimita'", or.getPrevisioni("nomesezione").get(1).getPrevisione());
		assertEquals(9, or.getPrevisioni("nomesezione").get(1).getValore());
		for(SegnoZodiacale s : SegnoZodiacale.values()) {
			assertTrue(or.getPrevisioni("nomesezione").get(1).validaPerSegno(s));
		}
	}
	
	@Test
	public void testLetturaCorrettaPrevisioni2() throws IOException, BadFileFormatException {
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		OroscopoRepository or = 
				new TextFileOroscopoRepository(mr);
		
		assertEquals(1, or.getPrevisioni("sezione2").size());
		assertEquals("testo di prova", or.getPrevisioni("sezione2").get(0).getPrevisione());
		assertEquals(66, or.getPrevisioni("sezione2").get(0).getValore());
		
		for(SegnoZodiacale s : SegnoZodiacale.values()) {
			assertTrue(or.getPrevisioni("sezione2").get(0).validaPerSegno(s));
		}
	}
	
	@Test
	public void testGetPrevisioniPerSezioneEsistenteNomeUppercase() throws IOException, BadFileFormatException {
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		OroscopoRepository or = 
				new TextFileOroscopoRepository(mr);
		
		assertEquals(2, or.getPrevisioni("NOMESEZIONE").size());
	}
	
	@Test
	public void testGetPrevisioniPerSezioneNonEsistenteRestituisceNull() throws IOException, BadFileFormatException {
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		
		OroscopoRepository or = 
				new TextFileOroscopoRepository(mr);
		
		assertNull(or.getPrevisioni("SEZIONECHENONCE"));
	}

	@Test
	public void testGetSezioni() throws IOException, BadFileFormatException {
		Reader mr = 
				new StringReader("NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n" + 
						"SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE");
		OroscopoRepository or = 
				new TextFileOroscopoRepository(mr);
		
		assertEquals(2, or.getSettori().size());
		assertTrue(or.getSettori().contains("nomesezione"));
		assertTrue(or.getSettori().contains("sezione2"));
	}

}
