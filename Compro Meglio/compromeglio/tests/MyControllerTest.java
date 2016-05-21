package compromeglio.tests;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import compromeglio.model.Bene;
import compromeglio.model.Categoria;
import compromeglio.model.Rilevazione;
import compromeglio.persistence.BeniReader;
import compromeglio.persistence.MalformedFileException;
import compromeglio.persistence.RilevazioniReader;
import compromeglio.ui.MyController;
import compromeglio.ui.UserInteractor;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MyControllerTest {
	
	@Test
	public void testCostruzionePassandoAllaSuperclasseLoUserInteractorRicevuto() {
		
		UserInteractor ui = mock(UserInteractor.class);		
		BeniReader mockbr = mock(BeniReader.class);
		RilevazioniReader mockrr = mock(RilevazioniReader.class);
		
		MyController ctrl = new MyController(mockbr, mockrr, ui);
		
		assertSame(ui, ctrl.getUserInteractor());
		verify(ui, never()).showMessage(anyString());
	}
	
	@Test
	public void testCostruzioneEffettuaLetturaDeiReaderPassati() throws MalformedFileException, IOException {
		
		UserInteractor ui = mock(UserInteractor.class);		
		BeniReader mockbr = mock(BeniReader.class);
		RilevazioniReader mockrr = mock(RilevazioniReader.class);
		
		new MyController(mockbr, mockrr, ui);
		
		verify(mockbr, times(1)).caricaBeni();
		verify(mockrr, times(1)).caricaRilevazioni(new HashMap<Long, Bene>());
	}
	
	@Test
	public void testCostruzioneLetturaRilevazioniCorretta() throws MalformedFileException, IOException, ParseException {
		
		UserInteractor ui = mock(UserInteractor.class);		
		BeniReader mockbr = mock(BeniReader.class);
		RilevazioniReader mockrr = mock(RilevazioniReader.class);
		
		DateFormat df = 
				new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
		
		Bene[] beniDiProva = new Bene[]{ 
				new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA),
				new Bene(11112, "Spaghetti Dentinia 5", Categoria.PASTA)};
		Map<Long, Bene> mappaTest = new HashMap<Long, Bene>();
		mappaTest.put(beniDiProva[0].getCodice(), beniDiProva[0]);
		mappaTest.put(beniDiProva[1].getCodice(), beniDiProva[1]);
		

		Set<Bene> sBeni = new HashSet<Bene>();
		sBeni.add(beniDiProva[0]);
		sBeni.add(beniDiProva[1]);
		
		when(mockbr.caricaBeni()).thenReturn(sBeni);
		
		when(mockrr.caricaRilevazioni(mappaTest)).
			thenReturn(Arrays.asList(new Rilevazione[] {
					new Rilevazione(beniDiProva[0], "Market Dentinia" ,df.parse("18/01/2014"), 0.95f),
					new Rilevazione(beniDiProva[0], "Market Zannonia" ,df.parse("19/01/2014"), 1.05f),
					new Rilevazione(beniDiProva[0], "Market Sperduto" ,df.parse("19/01/2014"), 1.55f)
			}));
		
		MyController ctrl = new MyController(mockbr, mockrr, ui);
		
		assertEquals(Arrays.asList(new Rilevazione[] {
				new Rilevazione(beniDiProva[0], "Market Dentinia" ,df.parse("18/01/2014"), 0.95f),
				new Rilevazione(beniDiProva[0], "Market Zannonia" ,df.parse("19/01/2014"), 1.05f),
				new Rilevazione(beniDiProva[0], "Market Sperduto" ,df.parse("19/01/2014"), 1.55f)
		}), ctrl.getRilevazioni() );
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCostruzioneLanciaEccezioneSuUserInteractorSeFileMalformato() throws MalformedFileException, IOException {
		
		UserInteractor ui = mock(UserInteractor.class);		
		BeniReader mockbr = mock(BeniReader.class);
		RilevazioniReader mockrr = mock(RilevazioniReader.class);
		
		when(mockbr.caricaBeni()).thenThrow(IOException.class);
		
		new MyController(mockbr, mockrr, ui);
		
		verify(ui, times(1)).showMessage(anyString());
	}
	
	@Test
	public void testGetCategorieRestituisceSetCorretto() throws MalformedFileException, IOException {
		
		UserInteractor ui = mock(UserInteractor.class);		
		BeniReader mockbr = mock(BeniReader.class);
		RilevazioniReader mockrr = mock(RilevazioniReader.class);
		
		Set<Bene> beniDiProva = new HashSet<Bene>();
		beniDiProva.add(new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA));
		beniDiProva.add(new Bene(11112, "Spaghetti Dentinia 5", Categoria.PASTA));
		beniDiProva.add(new Bene(21119, "Tonno insuperabile", Categoria.PESCE));

		when(mockbr.caricaBeni()).thenReturn(beniDiProva);
		
		MyController ctrl = new MyController(mockbr, mockrr, ui);
		
		Set<Categoria> risultatoAtteso = new HashSet<Categoria>();
		risultatoAtteso.add(Categoria.PASTA);
		risultatoAtteso.add(Categoria.PESCE);
		
		assertEquals(risultatoAtteso, ctrl.getCategorie());
	}
	
	@Test
	public void testGetBeniPerCategoriaRestituisceSetCorretto() throws MalformedFileException, IOException {
		
		UserInteractor ui = mock(UserInteractor.class);		
		BeniReader mockbr = mock(BeniReader.class);
		RilevazioniReader mockrr = mock(RilevazioniReader.class);
		
		Set<Bene> beniDiProva = new HashSet<Bene>();
		beniDiProva.add(new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA));
		beniDiProva.add(new Bene(11112, "Spaghetti Dentinia 5", Categoria.PASTA));
		beniDiProva.add(new Bene(21119, "Tonno insuperabile", Categoria.PESCE));

		when(mockbr.caricaBeni()).thenReturn(beniDiProva);
		
		MyController ctrl = new MyController(mockbr, mockrr, ui);
		
		Set<Bene> risultatoAtteso = new HashSet<Bene>();
		risultatoAtteso.add(new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA));
		risultatoAtteso.add(new Bene(11112, "Spaghetti Dentinia 5", Categoria.PASTA));
		
		assertEquals(risultatoAtteso, ctrl.getBeniPerCategoria(Categoria.PASTA));
	}

}
