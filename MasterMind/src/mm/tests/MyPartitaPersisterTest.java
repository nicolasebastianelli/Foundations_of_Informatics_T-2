package mm.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import mm.model.Codice;
import mm.model.Partita;
import mm.persistence.BadFileFormatException;
import mm.persistence.MyPartitaPersister;

import org.junit.Test;

public class MyPartitaPersisterTest
{

	@Test
	public void testReadWrite() throws IOException, BadFileFormatException
	{
		Partita partita1 = new Partita(new DummyMatchingAlgorithm());
		
		Codice segreto = partita1.getSegreto();
		
		partita1.addTentativo(CodiceTestUtils.createDifferentFrom(segreto));
		partita1.addTentativo(CodiceTestUtils.createDifferentFrom(segreto));
		partita1.addTentativo(CodiceTestUtils.createDifferentFrom(segreto));
		
		MyPartitaPersister persister = new MyPartitaPersister();		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		persister.write(partita1, outputStream);
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		
		Partita partita2 = persister.read(inputStream);
		
		assertEquals(partita1, partita2);
	}
	
	@Test(expected = IOException.class)
	public void testReadWriteBad() throws IOException, BadFileFormatException
	{
		MyPartitaPersister persister = new MyPartitaPersister();
		persister.read(new ByteArrayInputStream(new byte[100]));
	}
}
