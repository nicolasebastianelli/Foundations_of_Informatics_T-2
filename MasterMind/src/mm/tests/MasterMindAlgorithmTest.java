package mm.tests;

import static org.junit.Assert.*;

import mm.model.Codice;
import mm.model.Colore;
import mm.model.ColoreRisposta;
import mm.model.MasterMindAlgorithm;
import mm.model.Risposta;

import org.junit.Test;

public class MasterMindAlgorithmTest
{

	@Test
	public void testFullMatchAllDifferent()
	{
		Codice segreto = new Codice(new Colore[] { Colore.Arancio, Colore.Giallo, Colore.Rosso, Colore.Blu} );
		Codice tentativo = new Codice(new Colore[] { Colore.Arancio, Colore.Giallo, Colore.Rosso, Colore.Blu} );
		MasterMindAlgorithm alg = new MasterMindAlgorithm();
		Risposta risposta = alg.match(segreto, tentativo);
		
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(0));
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(1));
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(2));
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(3));
	}	
	
	@Test
	public void testFullMatchAllEqual()
	{
		Codice segreto = new Codice(new Colore[] { Colore.Arancio, Colore.Arancio, Colore.Arancio, Colore.Arancio} );
		Codice tentativo = new Codice(new Colore[] { Colore.Arancio, Colore.Arancio, Colore.Arancio, Colore.Arancio} );
		MasterMindAlgorithm alg = new MasterMindAlgorithm();
		Risposta risposta = alg.match(segreto, tentativo);
		
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(0));
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(1));
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(2));
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(3));
	}	
	
	@Test
	public void testFullMatchPairs()
	{
		Codice segreto = new Codice(new Colore[] { Colore.Arancio, Colore.Arancio, Colore.Rosso, Colore.Rosso} );
		Codice tentativo = new Codice(new Colore[] { Colore.Arancio, Colore.Arancio, Colore.Rosso, Colore.Rosso} );
		MasterMindAlgorithm alg = new MasterMindAlgorithm();
		Risposta risposta = alg.match(segreto, tentativo);
		
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(0));
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(1));
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(2));
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(3));
	}	
	
	@Test
	public void testOneMatchOnePresent()
	{
		Codice segreto = new Codice(new Colore[] { Colore.Arancio, Colore.Giallo, Colore.Rosso, Colore.Blu} );
		Codice tentativo = new Codice(new Colore[] { Colore.Ciano, Colore.Blu, Colore.Rosso, Colore.Magenta} );
		MasterMindAlgorithm alg = new MasterMindAlgorithm();
		Risposta risposta = alg.match(segreto, tentativo);
		
		assertEquals(2, risposta.getCount());
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(0));
		assertEquals(ColoreRisposta.Bianco, risposta.getColoreRisposta(1));
	}	
	
	@Test
	public void testAllPresent()
	{
		Codice segreto = new Codice(new Colore[] { Colore.Arancio, Colore.Giallo, Colore.Rosso, Colore.Blu} );
		Codice tentativo = new Codice(new Colore[] { Colore.Blu, Colore.Arancio, Colore.Giallo, Colore.Rosso} );
		MasterMindAlgorithm alg = new MasterMindAlgorithm();
		Risposta risposta = alg.match(segreto, tentativo);
		
		assertEquals(ColoreRisposta.Bianco, risposta.getColoreRisposta(0));
		assertEquals(ColoreRisposta.Bianco, risposta.getColoreRisposta(1));
		assertEquals(ColoreRisposta.Bianco, risposta.getColoreRisposta(2));
		assertEquals(ColoreRisposta.Bianco, risposta.getColoreRisposta(3));
	}
	
	@Test
	public void testOneMatchReplicatedColor()
	{
		Codice segreto = new Codice(new Colore[] { Colore.Arancio, Colore.Giallo, Colore.Rosso, Colore.Blu} );
		Codice tentativo = new Codice(new Colore[] { Colore.Arancio, Colore.Arancio, Colore.Arancio, Colore.Arancio} );
		MasterMindAlgorithm alg = new MasterMindAlgorithm();
		Risposta risposta = alg.match(segreto, tentativo);
		
		assertEquals(1, risposta.getCount());
		assertEquals(ColoreRisposta.Nero, risposta.getColoreRisposta(0));
	}
	
	@Test
	public void testNoMatch()
	{
		Codice segreto = new Codice(new Colore[] { Colore.Arancio, Colore.Giallo, Colore.Rosso, Colore.Blu} );
		Codice tentativo = new Codice(new Colore[] { Colore.Magenta, Colore.Ciano, Colore.Grigio, Colore.Verde} );
		MasterMindAlgorithm alg = new MasterMindAlgorithm();
		Risposta risposta = alg.match(segreto, tentativo);
		
		assertEquals(0, risposta.getCount());
	}


}
