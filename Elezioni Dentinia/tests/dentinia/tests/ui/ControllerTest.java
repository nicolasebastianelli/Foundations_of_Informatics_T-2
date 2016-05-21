package dentinia.tests.ui;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.util.collections.Sets;

import dentinia.model.Partito;
import dentinia.persistence.BadFileFormatException;
import dentinia.persistence.SeggiWriter;
import dentinia.persistence.VotiReader;
import dentinia.ui.MyController;
import static org.mockito.Mockito.*;

public class ControllerTest {

	private MyController controller;
	private VotiReader mockReader;
	private SeggiWriter mockWriter;
	
	@Before
	public void setUp() throws Exception {
		
		mockReader = mock(VotiReader.class);
		when(mockReader.getSeggi()).thenReturn(15);
		
		{ // partiti
			Partito tg = new Partito("Topolini gialli", 7460);
			Partito gn = new Partito("Gufi neri", 2040);
			Partito fb = new Partito("Farfalle blu", 3482);
			Partito fr = new Partito("Formiche rosse", 8748);
			Partito bv = new Partito("Bruchi verdi", 8922);
			
			when(mockReader.caricaElementi()).thenReturn( new ArrayList<Partito>( 
					Arrays.asList(tg, gn, fb, fr, bv)));
		}
		
		mockWriter = mock(SeggiWriter.class);
	}

	@Test
	public void testGetListaPartitiResituiscePartitiGestiti() 
			throws IOException, BadFileFormatException {
		
		controller = new MyController(mockReader, mockWriter);
		
		List<Partito> partiti = 
				controller.getListaPartiti();
		
		Assert.assertEquals(5, partiti.size());
	}

	@Test
	public void testMyControllerVieneCostruitoSenzaErrori() throws IOException, BadFileFormatException {
		
		controller = new MyController(mockReader, mockWriter);
		
//		verify(mockReader, atLeastOnce()).caricaElementi();
	}

	@Test
	public void testRicalcolaMetodoQuozienteChiamaGetSeggiSulReaderECalcolaSeggi() 
			throws IOException, BadFileFormatException {
		
		mockReader = mock(VotiReader.class);
		when(mockReader.getSeggi()).thenReturn(15);
		
		Partito tg = new Partito("Topolini gialli", 7460);
		Partito gn = new Partito("Gufi neri", 2040);
		Partito fb = new Partito("Farfalle blu", 3482);
		Partito fr = new Partito("Formiche rosse", 8748);
		Partito bv = new Partito("Bruchi verdi", 8922);
			
		ArrayList<Partito> listaPartiti = new ArrayList<Partito>( 
				Arrays.asList(tg, gn, fb, fr, bv));
			when(mockReader.caricaElementi()).thenReturn(listaPartiti);		
			when(mockReader.getListaPartiti()).thenReturn(listaPartiti);
		
		controller = 
			new MyController(mockReader, mockWriter);
		
		controller.ricalcola("Metodo del quoziente");
		
		verify(mockReader, atLeastOnce()).getSeggi();
		
		Assert.assertEquals(4, tg.getSeggi());
		Assert.assertEquals(1, gn.getSeggi());
		Assert.assertEquals(2, fb.getSeggi());
		Assert.assertEquals(4, fr.getSeggi());
		Assert.assertEquals(4, bv.getSeggi());
	}
	
	@Test
	public void testRicalcolaConMetodoDhondtChiamaGetSeggiSulReaderECalcolaSeggi() 
			throws IOException, BadFileFormatException {
		
		mockReader = mock(VotiReader.class);
		when(mockReader.getSeggi()).thenReturn(15);
		
		Partito tg = new Partito("Topolini gialli", 7460);
		Partito gn = new Partito("Gufi neri", 2040);
		Partito fb = new Partito("Farfalle blu", 3482);
		Partito fr = new Partito("Formiche rosse", 8748);
		Partito bv = new Partito("Bruchi verdi", 8922);
			
		ArrayList<Partito> listaPartiti = new ArrayList<Partito>( 
			Arrays.asList(tg, gn, fb, fr, bv));
		when(mockReader.caricaElementi()).thenReturn(listaPartiti);		
		when(mockReader.getListaPartiti()).thenReturn(listaPartiti);
		
		controller = 
			new MyController(mockReader, mockWriter);
		
		controller.ricalcola("Metodo D'Hondt");
		
		verify(mockReader, atLeastOnce()).getSeggi();
		
		Assert.assertEquals(4, tg.getSeggi());
		Assert.assertEquals(1, gn.getSeggi());
		Assert.assertEquals(1, fb.getSeggi());
		Assert.assertEquals(4, fr.getSeggi());
		Assert.assertEquals(5, bv.getSeggi());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testSalvaSuFileChiamaStampaSeggiConUnaListaDiCinquePartitiEdUnFileWriter() 
			throws IOException, BadFileFormatException {
		
		controller = 
				new MyController(mockReader, mockWriter);
		
		controller.salvaSuFile("test");
		
		ArgumentCaptor<Writer> fileArgument = ArgumentCaptor.forClass(Writer.class);
		ArgumentCaptor<List> listArgument = 
				ArgumentCaptor.forClass(List.class);
		
		verify(mockWriter, atLeastOnce())
			.stampaSeggi(listArgument.capture(), fileArgument.capture() );
		
		Assert.assertEquals(listArgument.getValue().size(), 5);
		Assert.assertTrue(fileArgument.getValue() instanceof FileWriter);
	}

	@Test
	public void testGetCalcolatoriSeggiRestituisceDueMetodiImplementati() 
			throws IOException, BadFileFormatException {
		
		controller = 
				new MyController(mockReader, mockWriter);
		
		String[] calcolatoriSeggi = 
				controller.getCalcolatoriSeggi();
		
		Set<String> cs = Sets.newSet(calcolatoriSeggi);
		
		Assert.assertEquals(2, calcolatoriSeggi.length);
		Assert.assertTrue(cs.contains("Metodo del quoziente"));
		Assert.assertTrue(cs.contains("Metodo D'Hondt"));
	}

}
