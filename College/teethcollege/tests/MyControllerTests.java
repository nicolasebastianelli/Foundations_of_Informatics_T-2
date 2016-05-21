package teethcollege.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static teethcollege.tests.TestUtils.*;

import org.junit.Test;
import org.mockito.Mockito;

import teethcollege.model.Categoria;
import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;
import teethcollege.model.Semestre;
import teethcollege.persistence.InsegnamentiReader;
import teethcollege.persistence.MalformedFileException;
import teethcollege.persistence.PianiReader;
import teethcollege.pianidistudi.ui.MyController;
import teethcollege.pianidistudi.ui.UserInteractor;

public class MyControllerTests {

	@Test
	public void testMyController_NoErrors() throws MalformedFileException,
			IOException {
		Map<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		List<PianoDiStudi> piani = new ArrayList<PianoDiStudi>();

		PianoDiStudi piano1 = new PianoDiStudi("1_Uno", "1_Due", "1");
		PianoDiStudi piano2 = new PianoDiStudi("2_Uno", "2_Due", "2");
		for (long i = 1; i <= 30; i++) {
			Insegnamento ins = createObbligatorio(i);
			map.put(i, ins);
			piano1.aggiungiInsegnamento(ins);
			piano2.aggiungiInsegnamento(ins);
		}
		piani.add(piano1);
		piani.add(piano2);

		InsegnamentiReader insReader = mock(InsegnamentiReader.class);
		when(insReader.caricaInsegnamenti()).thenReturn(map);

		PianiReader pianiReader = mock(PianiReader.class);
		when(pianiReader.caricaPianiDiStudi(map)).thenReturn(piani);

		UserInteractor userInteractor = mock(UserInteractor.class);

		MyController controller = new MyController(insReader, pianiReader,
				userInteractor);

		verify(insReader, times(1)).caricaInsegnamenti();
		verify(insReader, times(1)).close();
		verify(pianiReader, times(1)).caricaPianiDiStudi(map);
		verify(pianiReader, times(1)).close();
		verifyZeroInteractions(userInteractor);

		Comparator<Object> hashCodeComparator = new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				return o1.hashCode() - o2.hashCode();
			}

		};

		ArrayList<Insegnamento> originalList = new ArrayList<Insegnamento>(
				map.values());
		Collections.sort(originalList, hashCodeComparator);
		ArrayList<Insegnamento> loadedList = new ArrayList<Insegnamento>(
				controller.getInsegnamenti());
		Collections.sort(loadedList, hashCodeComparator);
		assertEquals(originalList.size(), loadedList.size());
		for (int i = 0; i < originalList.size(); i++) {
			assertSame(originalList.get(i), loadedList.get(i));
		}

		Collections.sort(piani, hashCodeComparator);
		ArrayList<PianoDiStudi> loadedPianiList = new ArrayList<PianoDiStudi>(
				controller.getPianiDiStudi());
		Collections.sort(loadedPianiList, hashCodeComparator);
		assertEquals(piani.size(), loadedPianiList.size());
		assertSame(piani.get(0), loadedPianiList.get(0));
		assertSame(piani.get(1), loadedPianiList.get(1));
	}

	@Test
	public void testMyController_caricaPianiDiStudiThrowsMalformedFileException()
			throws MalformedFileException, IOException {
		Map<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();

		InsegnamentiReader insReader = mock(InsegnamentiReader.class);
		when(insReader.caricaInsegnamenti()).thenReturn(map);

		PianiReader pianiReader = mock(PianiReader.class);
		when(pianiReader.caricaPianiDiStudi(map)).thenThrow(
				new MalformedFileException("XXX My Message XXX"));

		UserInteractor userInteractor = mock(UserInteractor.class);
		doThrow(new MockException()).when(userInteractor).shutDownApplication();

		try {
			new MyController(insReader, pianiReader, userInteractor);
		} catch (MockException e) {
		}

		verify(insReader, times(1)).caricaInsegnamenti();
		verify(insReader, times(1)).close();
		verify(pianiReader, times(1)).caricaPianiDiStudi(map);
		verify(userInteractor, times(1)).showMessage(Mockito.anyString());
		verify(userInteractor, times(1)).shutDownApplication();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMyController_caricaInsegnamentiThrowsMalformedFileException()
			throws MalformedFileException, IOException {
		InsegnamentiReader insReader = mock(InsegnamentiReader.class);
		when(insReader.caricaInsegnamenti()).thenThrow(
				new MalformedFileException("XXX My Message XXX"));

		PianiReader pianiReader = mock(PianiReader.class);

		UserInteractor userInteractor = mock(UserInteractor.class);
		doThrow(new MockException()).when(userInteractor).shutDownApplication();

		try {
			new MyController(insReader, pianiReader, userInteractor);
		} catch (MockException e) {
		}

		verify(insReader, times(1)).caricaInsegnamenti();
		verify(pianiReader, never()).caricaPianiDiStudi(Mockito.anyMap());
		verify(userInteractor, times(1)).showMessage(Mockito.anyString());
		verify(userInteractor, times(1)).shutDownApplication();
	}

	@Test
	public void testSostituisci_FailDaSostituireNonPresenteNelPianoDiStudi()
			throws MalformedFileException, IOException {
		Map<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		List<PianoDiStudi> piani = new ArrayList<PianoDiStudi>();

		PianoDiStudi piano1 = new PianoDiStudi("1_Uno", "1_Due", "1");
		for (long i = 1; i <= 32; i++) {
			Insegnamento ins = createAScelta(i);
			map.put(i, ins);
			if (i < 31) {
				piano1.aggiungiInsegnamento(ins);
			}
		}
		piani.add(piano1);

		InsegnamentiReader insReader = mock(InsegnamentiReader.class);
		when(insReader.caricaInsegnamenti()).thenReturn(map);

		PianiReader pianiReader = mock(PianiReader.class);
		when(pianiReader.caricaPianiDiStudi(map)).thenReturn(piani);

		UserInteractor userInteractor = mock(UserInteractor.class);

		Insegnamento daMettere = map.get(31L);
		Insegnamento daTogliere = map.get(32L);

		MyController controller = new MyController(insReader, pianiReader,
				userInteractor);
		String result = controller.sostituisci(piano1, daTogliere, daMettere);

		verify(insReader, times(1)).caricaInsegnamenti();
		verify(insReader, times(1)).close();
		verify(pianiReader, times(1)).caricaPianiDiStudi(map);
		verify(pianiReader, times(1)).close();
		verifyZeroInteractions(userInteractor);

		assertNotNull(result);

		assertFalse(piano1.getInsegnamenti().contains(daMettere));
		assertFalse(piano1.getInsegnamenti().contains(daTogliere));
		assertTrue(piano1.isValid());
	}

	@Test
	public void testSostituisci_FailDaSostituireObbligatorio()
			throws MalformedFileException, IOException {
		Map<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		List<PianoDiStudi> piani = new ArrayList<PianoDiStudi>();

		PianoDiStudi piano1 = new PianoDiStudi("1_Uno", "1_Due", "1");
		for (long i = 1; i <= 32; i++) {
			Insegnamento ins = i < 31 ? createObbligatorio(i)
					: createAScelta(i);
			map.put(i, ins);
			if (i < 31) {
				piano1.aggiungiInsegnamento(ins);
			}
		}
		piani.add(piano1);

		InsegnamentiReader insReader = mock(InsegnamentiReader.class);
		when(insReader.caricaInsegnamenti()).thenReturn(map);

		PianiReader pianiReader = mock(PianiReader.class);
		when(pianiReader.caricaPianiDiStudi(map)).thenReturn(piani);

		UserInteractor userInteractor = mock(UserInteractor.class);

		Insegnamento daMettere = map.get(31L);
		Insegnamento daTogliere = map.get(1L);

		MyController controller = new MyController(insReader, pianiReader,
				userInteractor);
		String result = controller.sostituisci(piano1, daTogliere, daMettere);

		verify(insReader, times(1)).caricaInsegnamenti();
		verify(insReader, times(1)).close();
		verify(pianiReader, times(1)).caricaPianiDiStudi(map);
		verify(pianiReader, times(1)).close();
		verifyZeroInteractions(userInteractor);

		assertNotNull(result);

		assertFalse(piano1.getInsegnamenti().contains(daMettere));
		assertTrue(piano1.getInsegnamenti().contains(daTogliere));
		assertTrue(piano1.isValid());
	}

	@Test
	public void testSostituisci_FailDaMettereObbligatorio()
			throws MalformedFileException, IOException {
		Map<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		List<PianoDiStudi> piani = new ArrayList<PianoDiStudi>();

		PianoDiStudi piano1 = new PianoDiStudi("1_Uno", "1_Due", "1");
		for (long i = 1; i <= 32; i++) {
			Insegnamento ins = i < 31 ? createAScelta(i)
					: createObbligatorio(i);
			map.put(i, ins);
			if (i < 31) {
				piano1.aggiungiInsegnamento(ins);
			}
		}
		piani.add(piano1);

		InsegnamentiReader insReader = mock(InsegnamentiReader.class);
		when(insReader.caricaInsegnamenti()).thenReturn(map);

		PianiReader pianiReader = mock(PianiReader.class);
		when(pianiReader.caricaPianiDiStudi(map)).thenReturn(piani);

		UserInteractor userInteractor = mock(UserInteractor.class);

		Insegnamento daMettere = map.get(31L);
		Insegnamento daTogliere = map.get(1L);

		MyController controller = new MyController(insReader, pianiReader,
				userInteractor);
		String result = controller.sostituisci(piano1, daTogliere, daMettere);

		verify(insReader, times(1)).caricaInsegnamenti();
		verify(insReader, times(1)).close();
		verify(pianiReader, times(1)).caricaPianiDiStudi(map);
		verify(pianiReader, times(1)).close();
		verifyZeroInteractions(userInteractor);

		assertNotNull(result);

		assertFalse(piano1.getInsegnamenti().contains(daMettere));
		assertTrue(piano1.getInsegnamenti().contains(daTogliere));
		assertTrue(piano1.isValid());
	}

	@Test
	public void testSostituisci_FailCfuNonUguali()
			throws MalformedFileException, IOException {
		Map<Long, Insegnamento> map = new HashMap<Long, Insegnamento>();
		List<PianoDiStudi> piani = new ArrayList<PianoDiStudi>();

		PianoDiStudi piano1 = new PianoDiStudi("1_Uno", "1_Due", "1");
		for (long i = 1; i <= 30; i++) {
			Insegnamento ins = createAScelta(i);
			map.put(i, ins);
			piano1.aggiungiInsegnamento(ins);
		}
		piani.add(piano1);

		InsegnamentiReader insReader = mock(InsegnamentiReader.class);
		when(insReader.caricaInsegnamenti()).thenReturn(map);

		PianiReader pianiReader = mock(PianiReader.class);
		when(pianiReader.caricaPianiDiStudi(map)).thenReturn(piani);

		UserInteractor userInteractor = mock(UserInteractor.class);

		Insegnamento daMettere = new Insegnamento(100, "100", "XXX", 10, Semestre.PRIMO, Categoria.A_SCELTA);
		map.put(100L, daMettere);
		Insegnamento daTogliere = map.get(1L);

		MyController controller = new MyController(insReader, pianiReader,
				userInteractor);
		String result = controller.sostituisci(piano1, daTogliere, daMettere);

		verify(insReader, times(1)).caricaInsegnamenti();
		verify(insReader, times(1)).close();
		verify(pianiReader, times(1)).caricaPianiDiStudi(map);
		verify(pianiReader, times(1)).close();
		verifyZeroInteractions(userInteractor);

		assertNotNull(result);

		assertFalse(piano1.getInsegnamenti().contains(daMettere));
		assertTrue(piano1.getInsegnamenti().contains(daTogliere));
		assertTrue(piano1.isValid());
	}

}
