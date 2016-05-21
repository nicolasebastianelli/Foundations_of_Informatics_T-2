package ed.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import ed.model.*;
import ed.persistence.*;
import ed.ui.*;

public class MyControllerTests
{

	@Test
	public void testReadTariffe()
	{
		TariffaReaderMock reader = new TariffaReaderMock();
		MyController ctrl = new MyController(reader, new MessageManagerMock());
		
		StringReader stringReader = new StringReader("Dont care");
		ctrl.leggiTariffe(stringReader);
	}
	
	@Test
	public void testReadTariffe_Error()
	{
		TariffaReaderMock_Error reader = new TariffaReaderMock_Error();
		MessageManagerMock messageManager = new MessageManagerMock();
		MyController ctrl = new MyController(reader, messageManager);
		
		StringReader stringReader = new StringReader("Dont care");
		ctrl.leggiTariffe(stringReader);
		
		assertEquals(1, messageManager.getMessages().size());
	}

	@Test
	public void testGetNomiTariffe()
	{
		TariffaReaderMock reader = new TariffaReaderMock();
		MyController ctrl = new MyController(reader, new MessageManagerMock());
		
		StringReader stringReader = new StringReader("Dont care");
		ctrl.leggiTariffe(stringReader);
		
		Collection<String> nomi = ctrl.getNomiTariffe();
		
		assertTrue(nomi.contains("Uno"));
		assertTrue(nomi.contains("Due"));
		assertTrue(nomi.contains("Tre"));
	}

	@Test
	public void testCreaBolletta()
	{
		TariffaReaderMock reader = new TariffaReaderMock();
		MyController ctrl = new MyController(reader, new MessageManagerMock());
		
		StringReader stringReader = new StringReader("Dont care");
		ctrl.leggiTariffe(stringReader);
		
		Bolletta b = ctrl.creaBolletta("Uno", TestData.UtenteTest, TestData.Mese, TestData.Anno, 111);
		
		assertEquals("Uno", b.getNomeTariffa());
		assertSame(TestData.UtenteTest, b.getUtente());
		assertEquals(TestData.Mese, b.getMese());
		assertEquals(TestData.Anno, b.getAnno());
		assertEquals(111, b.getConsumo(), 0.01);
		assertEquals(1, b.getLineeBolletta().size());
		assertEquals("LineaBolletta - Test", b.getLineeBolletta().get(0).getVoce());
	}

}

class TariffaMock extends Tariffa
{

	public TariffaMock(String nome)
	{
		super(nome);
	}

	@Override
	public Bolletta creaBolletta(Utente utente, int mese, int anno, double consumo)
	{		
		Bolletta bolletta = new Bolletta(utente, mese, anno, getNome(), consumo);
		bolletta.addLineaBolletta(new LineaBolletta("LineaBolletta - Test", 100));
		return bolletta;
	}
}

class TariffaReaderMock implements TariffaReader
{

	@Override
	public Collection<Tariffa> leggiTariffe(Reader reader) throws IOException, BadFileFormatException
	{
		ArrayList<Tariffa> tariffe = new ArrayList<Tariffa>();
		tariffe.add(new TariffaMock("Uno"));
		tariffe.add(new TariffaMock("Due"));
		tariffe.add(new TariffaMock("Tre"));
		return tariffe;
	}
	
}

class TariffaReaderMock_Error implements TariffaReader
{

	@Override
	public Collection<Tariffa> leggiTariffe(Reader reader) throws IOException, BadFileFormatException
	{
		throw new BadFileFormatException();
	}
	
}

class MessageManagerMock implements MessageManager
{

	private ArrayList<String> messages = new ArrayList<String>();
	
	@Override
	public void showMessage(String message)
	{
		messages.add(message);
	}
	
	public List<String> getMessages()
	{
		return messages;
	}
}
