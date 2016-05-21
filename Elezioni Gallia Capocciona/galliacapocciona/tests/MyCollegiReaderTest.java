package galliacapocciona.tests;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.*;
import galliacapocciona.model.Collegio;
import galliacapocciona.model.Partito;
import galliacapocciona.persistence.BadFileFormatException;
import galliacapocciona.persistence.MyCollegiReader;

import org.junit.Test;

public class MyCollegiReaderTest {
	
	private Partito getPartito(Collegio collegio, String nome) {
		for (Partito p : collegio.getListaPartiti()) {
			if (p.getNome().equals(nome)) {
				return p;
			}
		}
		return null;
	}

	@Test
	public void testCaricaElementi() throws IOException, BadFileFormatException {
		String data = "	Gialli\tVerdi\n1\t43412\t43291\n2\t53049\t41317\n3\t40040\t41255";
		StringReader reader = new StringReader(data);
		
		MyCollegiReader collegiReader = new MyCollegiReader();
		List<Collegio> collegi = collegiReader.caricaElementi(reader);
		assertEquals(3, collegi.size());	
		
		List<Partito> partiti = Collegio.generaListaPartiti(collegi);
		assertEquals(2, partiti.size());
		
		Partito p;
		
		p = getPartito(collegi.get(0), "Gialli");
		assertEquals(43412, p.getVoti());
		p = getPartito(collegi.get(0), "Verdi");
		assertEquals(43291, p.getVoti());
		
		p = getPartito(collegi.get(1), "Gialli");
		assertEquals(53049, p.getVoti());
		p = getPartito(collegi.get(1), "Verdi");
		assertEquals(41317, p.getVoti());

		p = getPartito(collegi.get(2), "Gialli");
		assertEquals(40040, p.getVoti());
		p = getPartito(collegi.get(2), "Verdi");
		assertEquals(41255, p.getVoti());
	}

	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementi_NumeroPartitiInferioreADue() throws IOException, BadFileFormatException {
		String data = "	Gialli\n1\t43412\t43291\n2\t53049\t41317\n3\t40040\t41255";
		StringReader reader = new StringReader(data);
		
		MyCollegiReader collegiReader = new MyCollegiReader();
		collegiReader.caricaElementi(reader);
	}

	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementi_NumeroCampiVotiInferioreANumeroPartiti() throws IOException, BadFileFormatException {
		String data = "	Gialli\tVerdi\n1\t43412\t43291\n2\t41317\n3\t40040\t41255";
		StringReader reader = new StringReader(data);
		
		MyCollegiReader collegiReader = new MyCollegiReader();
		collegiReader.caricaElementi(reader);
	}

	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementi_NumeroCampiVotiSuperioreANumeroPartiti() throws IOException, BadFileFormatException {
		String data = "	Gialli\tVerdi\n1\t43412\t43291\n2\t53049\t41317\t12312\n3\t40040\t41255";
		StringReader reader = new StringReader(data);
		
		MyCollegiReader collegiReader = new MyCollegiReader();
		collegiReader.caricaElementi(reader);
	}

	@Test(expected = BadFileFormatException.class)
	public void testCaricaElementi_CampiVotiNonNumerici() throws IOException, BadFileFormatException {
		String data = "	Gialli\tVerdi\n1\t43412\t43291\n2\t53049\t41XX317\n3\t40040\t41255";
		StringReader reader = new StringReader(data);
		
		MyCollegiReader collegiReader = new MyCollegiReader();
		collegiReader.caricaElementi(reader);
	}

}
