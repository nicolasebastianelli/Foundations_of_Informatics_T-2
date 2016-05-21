package compromeglio.tests;

import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import compromeglio.model.Bene;
import compromeglio.model.Categoria;
import compromeglio.persistence.BeniReader;
import compromeglio.persistence.MalformedFileException;
import compromeglio.persistence.MyBeniReader;

public class MyBeniReaderTest {


	@Test
	public void testCaricaElementi() throws IOException, MalformedFileException {
		
		String data = 
				"11111\tPASTA\tSpaghetti Dentilla 3 500g\n" +
				"11114\tPASTA\tSpaghetti Dentilla 5 500g\n" +
				"11116\tPASTA\tRigatoni Zannoni\n" +
				"22222\tCARNE\tPollo intero\n";
		
		StringReader reader = new StringReader(data);
		
		BeniReader mbr = new MyBeniReader( reader );
		Set<Bene> beni = mbr.caricaBeni();
		
		Assert.assertEquals(4, beni.size());
		Assert.assertTrue(beni.contains(new Bene(11111, "Spaghetti Dentilla 3 500g", Categoria.PASTA)));
		Assert.assertTrue(beni.contains(new Bene(11114, "Spaghetti Dentilla 5 500g", Categoria.PASTA)));
		Assert.assertTrue(beni.contains(new Bene(11116, "Rigatoni Zannoni", Categoria.PASTA)));
		Assert.assertTrue(beni.contains(new Bene(22222, "Pollo intero", Categoria.CARNE)));
		
	}
	
	@Test
	public void testCaricaElementiFileVuotoRestituisceSetVuoto() throws IOException, MalformedFileException {
		
		String data = "\n\n";
		
		StringReader reader = new StringReader(data);
		
		BeniReader mbr = new MyBeniReader( reader );
		Set<Bene> beni = mbr.caricaBeni();
		
		Assert.assertEquals(0, beni.size());
	}

	@Test(expected = MalformedFileException.class)
	public void testCaricaElementiNumeroElementiInRigaNonCorretto_1() throws IOException, MalformedFileException {
		String data = 
				"11111\tPASTA\tSpaghetti Dentilla 3 500g\n" +
				"11114\tPASTA\tSpaghetti Dentilla 5 500g\n" +
				"11116\tRigatoni Zannoni\n" +
				"22222\tCARNE\tPollo intero\n";
		
		StringReader reader = new StringReader(data);
		
		BeniReader mbr = new MyBeniReader( reader );
		mbr.caricaBeni();
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaElementiNumeroElementiInRigaNonCorretto_2() throws IOException, MalformedFileException {
		String data = 
				"11111\tPASTA\tSpaghetti Dentilla 3 500g\n" +
				"11114\tPASTA\t\t\n" +
				"11116\tPASTA\tRigatoni Zannoni\n" +
				"22222\tCARNE\tPollo intero\n";
		
		StringReader reader = new StringReader(data);
		
		BeniReader mbr = new MyBeniReader( reader );
		mbr.caricaBeni();
	}
	
	@Test(expected = MalformedFileException.class)
	public void testCaricaElementiCodiceNonNumerico() throws IOException, MalformedFileException {
		String data = 
				"11111\tPASTA\tSpaghetti Dentilla 3 500g\n" +
				"11X16\tPASTA\tRigatoni Zannoni\n" +
				"22222\tCARNE\tPollo intero\n";
		
		StringReader reader = new StringReader(data);
		
		BeniReader mbr = new MyBeniReader( reader );
		mbr.caricaBeni();
	}

}
