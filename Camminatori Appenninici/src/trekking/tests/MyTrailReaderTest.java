package trekking.tests;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import trekking.model.Trail;
import trekking.model.TrailHead;
import trekking.persistence.InvalidTrailFormatException;
import trekking.persistence.MyTrailReader;
import trekking.persistence.TrailReader;

public class MyTrailReaderTest {

	private List<Trail> sentieriTest;

	@Test
	public void test_lettura_tutti_sentieri() throws InvalidTrailFormatException {
		
		StringBuilder sb =
				new StringBuilder();
		sb.append("CAI129, Segavecchia (925), Corno alle Scale (1725), 3.8 km, Difficolta 2\n");
		sb.append("CAI000, Corno alle Scale (1725), BBBB (1732), 1.1 km, Difficolta 3\n");
		sb.append("CAI121, BBBB (1732), CCCC (1005), 3.4 km, Difficolta 1\n");
		sb.append("CAI111, MMMM (800), NNNN (900), 5.0 km, Difficolta 2\n");
		sb.append("CAI109, NNNN (900), PPPP (500), 3.5 km, Difficolta 1\n");
		
		TrailReader reader =
				new MyTrailReader( new StringReader(sb.toString()) );
		
		sentieriTest = reader.readTrails();
		assertEquals(5, sentieriTest.size());
	}
	
	@Test
	public void testSentiero1() throws InvalidTrailFormatException {
		StringBuilder sb =
				new StringBuilder();
		sb.append("CAI129, Segavecchia (925), Corno alle Scale (1725), 3.8 km, Difficolta 2\n");
		
		TrailReader reader =
				new MyTrailReader( new StringReader(sb.toString()) );
		
		List<Trail> sentieri =
				reader.readTrails();
		
		assertEquals(1, sentieri.size());
		
		Trail sentiero =
				sentieri.get(0);
		
		assertEquals("CAI129", sentiero.getName());
		
		TrailHead partenza = new TrailHead();
		partenza.setName("Segavecchia");
		partenza.setAltitude(925);
		
		TrailHead arrivo = new TrailHead();
		arrivo.setName("Corno alle Scale");
		arrivo.setAltitude(1725);
		
		assertEquals(partenza, sentiero.getTrailHead() );
		assertEquals(arrivo, sentiero.getTrailEnd() );
		assertEquals(3.8, sentiero.getLength(), 0.01);
		assertEquals(2, sentiero.getDifficulty().getValue());
	}

	@Test
	public void testSentiero2() throws InvalidTrailFormatException {
		StringBuilder sb =
				new StringBuilder();
		sb.append("CAI000,Corno alle Scale (1725), BBBB (1732),1.1 km,   Difficolta 3\n");

		TrailReader reader =
				new MyTrailReader( new StringReader(sb.toString()) );
		
		List<Trail> sentieri =
				reader.readTrails();
		
		assertEquals(1, sentieri.size());
		
		Trail sentiero =
				sentieri.get(0);
		
		assertEquals("CAI000", sentiero.getName());
		
		TrailHead partenza = new TrailHead();
		partenza.setName("Corno alle Scale");
		partenza.setAltitude(1725);
		
		TrailHead arrivo = new TrailHead();
		arrivo.setName("BBBB");
		arrivo.setAltitude(1732);
		
		assertEquals(partenza, sentiero.getTrailHead() );
		assertEquals(arrivo, sentiero.getTrailEnd() );
		assertEquals(1.1, sentiero.getLength(), 0.01);
		assertEquals(3, sentiero.getDifficulty().getValue());
	}
	
	@Test
	public void testSentiero3() throws InvalidTrailFormatException {
		StringBuilder sb =
				new StringBuilder();
		sb.append("CAI121, BBBB (1732),CCCC (1005),3.4 km,Difficolta 1\n");
		
		TrailReader reader =
				new MyTrailReader( new StringReader(sb.toString()) );
		
		List<Trail> sentieri =
				reader.readTrails();
		
		assertEquals(1, sentieri.size());
		
		Trail sentiero =
				sentieri.get(0);
		
		assertEquals("CAI121", sentiero.getName());
		
		TrailHead partenza = new TrailHead();
		partenza.setName("BBBB");
		partenza.setAltitude(1732);
		
		TrailHead arrivo = new TrailHead();
		arrivo.setName("CCCC");
		arrivo.setAltitude(1005);
		
		assertEquals(partenza, sentiero.getTrailHead() );
		assertEquals(arrivo, sentiero.getTrailEnd() );
		assertEquals(3.4, sentiero.getLength(), 0.01);
		assertEquals(1, sentiero.getDifficulty().getValue());
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_nome_sentiero_mancante() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader(" Segavecchia (925), Corno alle Scale (1725), 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_nome_stazione_partenza_mancante() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, (925), Corno alle Scale (1725), 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_altezza_stazione_partenza_mancante() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia, Corno alle Scale (1725), 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_altezza_stazione_partenza_malformata() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (9x44), Corno alle Scale (1725), 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_altezza_stazione_partenza_non_fra_parentesi_valide1() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia 944), Corno alle Scale (1725), 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_altezza_stazione_partenza_non_fra_parentesi_valide2() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia 944, Corno alle Scale (1725), 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_nome_stazione_arrivo_mancante() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), (1725), 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_altezza_stazione_arrivo_mancante() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale, 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_altezza_stazione_arrivo_malformata() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale (FFFF), 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_altezza_stazione_arrivo_non_fra_parentesi_valide1() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (944), Corno alle Scale 1725), 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_altezza_stazione_arrivo_non_fra_parentesi_valide2() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (944), Corno alle Scale 1725, 3.8 km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_distanza_mancante() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale (1725), 4.3h, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_distanza_valore_non_valido() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale (1725), 3.H km, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_distanza_indicazione_km_mancante() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale (1725), 3.8, Difficolta 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_difficolta_mancante() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale (1725), 3.8 km\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_difficolta_keyword_errata() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale (1725), 3.8 km, Difficile 2\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_difficolta_valore_mancante() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale (1725), 3.8 km, Difficolta\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_difficolta_valore_errato() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale (1725), 3.8 km, Difficolta X\n") );
		reader.readTrails();
	}
	
	@Test(expected=InvalidTrailFormatException.class)
	public void test_difficolta_valore_fuori_scala() throws InvalidTrailFormatException {	
		TrailReader reader =
				new MyTrailReader( new StringReader("CAI129, Segavecchia (925), Corno alle Scale (1725), 3.8 km, Difficolta 9\n") );
		reader.readTrails();
	}
}
