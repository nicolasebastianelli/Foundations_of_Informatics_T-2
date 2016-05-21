package doc.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import doc.model.*;
import doc.persistence.MalformedFileException;

public class BinAcquistoRepositoryTests {

	@Test
	public void testBinAcquistoRepository() throws IOException, MalformedFileException {	
		BinAcquistoRepositoryUnderTest.setInputStream(null);	
		BinAcquistoRepositoryUnderTest bar = new BinAcquistoRepositoryUnderTest("Cipz");
		assertEquals("Cipz", bar.getFileName());		
	}
	
	@Test
	public void testWriteThenRead() throws IOException, MalformedFileException {	
		BinAcquistoRepositoryUnderTest.setInputStream(null);
		BinAcquistoRepositoryUnderTest bar = new BinAcquistoRepositoryUnderTest("Cipz");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bar.setOutputStream(os);		
		bar.add(AcquistoFactory.createAcq(new Cliente("10", "Nome 10"), new Bevanda("Bev1", 10, 20, 30), 20, 0.2));
		//Butta via il primo output stream che contiene solo un elemento;
		//questo secondo deve contenere entrambi gli elementi
		os = new ByteArrayOutputStream();
		bar.setOutputStream(os);
		bar.add(AcquistoFactory.createAcq(new Cliente("20", "Nome 20"), new Bevanda("Bev2", 20, 30, 40), 50, 0.1));
		
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		BinAcquistoRepositoryUnderTest.setInputStream(is);
		bar = new BinAcquistoRepositoryUnderTest("Cipz");
		Acquisto[] acqs = bar.getAll().toArray(new Acquisto[0]);
		
		assertEquals("10", acqs[0].getCliente().getId());
		assertEquals("Nome 10", acqs[0].getCliente().getNome());
		assertEquals("Bev1", acqs[0].getBevanda().getNome());
		assertEquals(10f, acqs[0].getBevanda().getPrezzoMin(), 0.1);
		assertEquals(20f, acqs[0].getBevanda().getPrezzoBase(), 0.1);
		assertEquals(30f, acqs[0].getBevanda().getPrezzoMax(), 0.1);
		assertEquals(20f, acqs[0].getPrezzo(), 0.1);
		assertEquals(new Date(AcquistoFactory.Now.getTime() - (long)(0.2 * 60 * 60 * 1000)), acqs[0].getData());
		
		assertEquals("20", acqs[1].getCliente().getId());
		assertEquals("Nome 20", acqs[1].getCliente().getNome());
		assertEquals("Bev2", acqs[1].getBevanda().getNome());
		assertEquals(20f, acqs[1].getBevanda().getPrezzoMin(), 0.1);
		assertEquals(30f, acqs[1].getBevanda().getPrezzoBase(), 0.1);
		assertEquals(40f, acqs[1].getBevanda().getPrezzoMax(), 0.1);
		assertEquals(50f, acqs[1].getPrezzo(), 0.1);
		assertEquals(new Date(AcquistoFactory.Now.getTime() - (long)(0.1 * 60 * 60 * 1000)), acqs[1].getData());
	}

}
