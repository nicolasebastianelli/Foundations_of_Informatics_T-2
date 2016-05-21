package dentinia.tests.persistence;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import dentinia.model.Partito;
import dentinia.persistence.BadFileFormatException;
import dentinia.persistence.MyVotiReader;
import dentinia.persistence.VotiReader;

public class ReaderTest {

	private VotiReader myReader;

	@Before
	public void setUp() throws Exception {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SEGGI: 15\n");
		sb.append("Topolini Gialli: 7460\n");
		sb.append("Gufi Neri:      2040\n");
		sb.append("Farfalle blu:   3482\n");
		sb.append("Formiche Rosse: 8748\n");
		sb.append("Bruchi verdi:   8922\n");
		
		myReader =
				new MyVotiReader( new StringReader(sb.toString()) );
	}

	@Test
	public void testCostruttoreMyVotiReader() throws BadFileFormatException {
		myReader =
				new MyVotiReader( new StringReader("") );
		Assert.assertNotNull(myReader);
	}

	@Test
	public void testCaricaElementi() throws IOException, BadFileFormatException {
		List<Partito> partiti = myReader.caricaElementi();
		Assert.assertEquals(5, partiti.size());
		Assert.assertEquals(7460, partiti.get(0).getVoti());
		Assert.assertEquals(2040, partiti.get(1).getVoti());
		Assert.assertEquals(3482, partiti.get(2).getVoti());
		Assert.assertEquals(8748, partiti.get(3).getVoti());
		Assert.assertEquals(8922, partiti.get(4).getVoti());
		for(Partito p : partiti) {
			Assert.assertEquals(0, p.getSeggi());
		}
	}

	@Test
	public void testGetSeggiFileNonCaricato() {
		Assert.assertEquals(0, myReader.getSeggi());
	}
	
	@Test
	public void testGetSeggiFileCaricato() throws IOException, BadFileFormatException {
		myReader.caricaElementi();
		Assert.assertEquals(15, myReader.getSeggi());
	}

	@Test
	public void testGetListaPartitiFileNonCaricato() {
		List<Partito> partiti =
				myReader.getListaPartiti();
		Assert.assertEquals(0, partiti.size());
	}
	
	@Test
	public void testGetListaPartitiFileCaricato() throws IOException, BadFileFormatException {
		myReader.caricaElementi();
		List<Partito> partiti = myReader.getListaPartiti();
		Assert.assertEquals(5, partiti.size());
	}
	
	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementiTokenSeggiErrato() throws IOException, BadFileFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("SEGI: 15\n");
		sb.append("Topolini Gialli: 7460\n");
		myReader =
			new MyVotiReader( new StringReader(sb.toString()) );
		myReader.caricaElementi();
	}
	
	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementiTokenSeggiMancante() throws IOException, BadFileFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("15\n");
		sb.append("Topolini Gialli: 7460\n");
		myReader =
			new MyVotiReader( new StringReader(sb.toString()) );
		myReader.caricaElementi();
	}
	
	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementiSeparatoreMancante() throws IOException, BadFileFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("SEGGI 15\n");
		sb.append("Topolini Gialli: 7460\n");
		myReader =
			new MyVotiReader( new StringReader(sb.toString()) );
		myReader.caricaElementi();
	}
	
	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementiSeparatoreMancanteInPartito() throws IOException, BadFileFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("SEGGI: 15\n");
		sb.append("Topolini Gialli: 7460\n");
		sb.append("Formiche Rosse 8748\n");
		myReader =
			new MyVotiReader( new StringReader(sb.toString()) );
		myReader.caricaElementi();
	}
	
	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementiMancaNomePartito() throws IOException, BadFileFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("SEGGI: 15\n");
		sb.append("7460\n");
		sb.append("Formiche Rosse: 8748\n");
		myReader =
			new MyVotiReader( new StringReader(sb.toString()) );
		myReader.caricaElementi();
	}
	
	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementiVotiPartitoMancanti() throws IOException, BadFileFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("SEGGI: 15\n");
		sb.append("Ratti:\n");
		sb.append("Formiche Rosse: 8748\n");
		myReader =
			new MyVotiReader( new StringReader(sb.toString()) );
		myReader.caricaElementi();
	}

}
