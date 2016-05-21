package mm.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.StringTokenizer;

import mm.model.Codice;
import mm.model.CodiceRisposta;
import mm.model.Partita;
import mm.persistence.MyPartitaWriter;

import org.junit.Test;

public class MyPartitaWriterTest
{

	@Test
	public void testWrite() throws IOException
	{
		Partita partita1 = new Partita(new DummyMatchingAlgorithm());
		Codice segreto = partita1.getSegreto();
		CodiceRisposta[] cr = new CodiceRisposta[3];
		cr[0] = partita1.addTentativo(CodiceTestUtils.createDifferentFrom(segreto));
		cr[1] = partita1.addTentativo(CodiceTestUtils.createDifferentFrom(segreto));
		cr[2] = partita1.addTentativo(CodiceTestUtils.createDifferentFrom(segreto));
		
		MyPartitaWriter myWriter = new MyPartitaWriter();
		StringWriter writer = new StringWriter();
		myWriter.write(partita1, new PrintWriter(writer));
		
		BufferedReader reader = new BufferedReader(new StringReader(writer.toString()));
		String line = reader.readLine();
		checkLine(line, segreto);
		assertEquals("", reader.readLine().trim());	
		int i = 0;
		while ((line = reader.readLine()) != null)
		{
			checkLine(line, cr[i++].getTentativo());
		}
		assertEquals(i, cr.length);
	}

	private void checkLine(String line, Codice codice)
	{
		StringTokenizer tokenizer = new StringTokenizer(line, ", ");
		for (int i = 0; i < codice.getCount(); ++i)
		{
			String colore = tokenizer.nextToken();
			assertEquals(codice.getColore(i).toString(), colore);
		}
	}

}
