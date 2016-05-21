package dentorestaurant.tests;

import static dentorestaurant.tests.TestUtils.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito;

import dentorestaurant.model.*;
import dentorestaurant.persistence.*;
import dentorestaurant.ui.*;

public class MyControllerTests {

	@Test
	public void testMyController_NoErrors() throws MalformedFileException,
			IOException {
		
		HashMap<Categoria, List<Portata>> portateMap = new HashMap<Categoria, List<Portata>>();
		portateMap.put(Categoria.ANTIPASTO,
				Arrays.asList(new Portata[] { createAntipasto("A01"),
						createAntipasto("A02") }));
		portateMap.put(Categoria.PRIMO,
				Arrays.asList(new Portata[] { createPrimo("P02"),
						createPrimo("P04") }));
		portateMap.put(Categoria.SECONDO,
				Arrays.asList(new Portata[] { createAntipasto("S03"),
						createAntipasto("S05") }));
		portateMap.put(Categoria.DESSERT,
				Arrays.asList(new Portata[] { createAntipasto("D01"),
						createAntipasto("D02") }));
		
		List<Menu> menuList = new ArrayList<Menu>();

		Menu menu1 = new Menu("Test1");
		menu1.getPortate(Categoria.ANTIPASTO).add(portateMap.get(Categoria.ANTIPASTO).get(0));
		menu1.getPortate(Categoria.PRIMO).add(portateMap.get(Categoria.PRIMO).get(0));
		menu1.getPortate(Categoria.SECONDO).add(portateMap.get(Categoria.SECONDO).get(0));
		menu1.getPortate(Categoria.DESSERT).add(portateMap.get(Categoria.DESSERT).get(0));
		menuList.add(menu1);
		
		Menu menu2 = new Menu("Test2");
		menu2.getPortate(Categoria.ANTIPASTO).add(portateMap.get(Categoria.ANTIPASTO).get(1));
		menu2.getPortate(Categoria.PRIMO).add(portateMap.get(Categoria.PRIMO).get(1));
		menu2.getPortate(Categoria.SECONDO).add(portateMap.get(Categoria.SECONDO).get(1));
		menu2.getPortate(Categoria.DESSERT).add(portateMap.get(Categoria.DESSERT).get(1));
		menuList.add(menu2);

		PortateReader portateReader = mock(PortateReader.class);
		when(portateReader.caricaPortate()).thenReturn(portateMap);

		MenuReader menuReader = mock(MenuReader.class);
		when(menuReader.caricaMenu(portateMap)).thenReturn(menuList);

		UserInteractor userInteractor = mock(UserInteractor.class);

		MyController controller = new MyController(portateReader, menuReader,
				userInteractor);

		verify(portateReader, times(1)).caricaPortate();
		verify(portateReader, times(1)).close();
		verify(menuReader, times(1)).caricaMenu(portateMap);
		verify(menuReader, times(1)).close();
		verifyZeroInteractions(userInteractor);
		
		Collection<Menu> readMenu = controller.getMenus();
		assertTrue(readMenu.contains(menu1));
		assertTrue(readMenu.contains(menu2));
	}

	@Test
	public void testMyController_caricaMenuThrowsMalformedFileException()
			throws MalformedFileException, IOException {
		Map<Categoria, List<Portata>> map = new HashMap<Categoria, List<Portata>>();

		PortateReader portateReader = mock(PortateReader.class);
		when(portateReader.caricaPortate()).thenReturn(map);

		MenuReader menuReader = mock(MenuReader.class);
		when(menuReader.caricaMenu(map)).thenThrow(
				new MalformedFileException("XXX My Message XXX"));

		UserInteractor userInteractor = mock(UserInteractor.class);

		new MyController(portateReader, menuReader, userInteractor);

		verify(portateReader, times(1)).caricaPortate();
		verify(menuReader, times(1)).caricaMenu(map);
		verify(userInteractor, times(1)).showMessage(Mockito.anyString());
		verify(userInteractor, times(1)).shutDownApplication();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMyController_caricaInsegnamentiThrowsMalformedFileException()
			throws MalformedFileException, IOException {
		PortateReader portateReader = mock(PortateReader.class);
		when(portateReader.caricaPortate()).thenThrow(
				new MalformedFileException("XXX My Message XXX"));

		MenuReader menuReader = mock(MenuReader.class);

		UserInteractor userInteractor = mock(UserInteractor.class);
//		doThrow(new MockException()).when(userInteractor).shutDownApplication();

//		try {
			new MyController(portateReader, menuReader, userInteractor);
//		} catch (MockException e) {
//		}

		verify(portateReader, times(1)).caricaPortate();
		verify(menuReader, never()).caricaMenu(Mockito.anyMap());
		verify(userInteractor, times(1)).showMessage(Mockito.anyString());
		verify(userInteractor, times(1)).shutDownApplication();
	}

	@Test
	public void testSostituisci_FailDaSostituireNonPresenteNelMenu()
			throws MalformedFileException, IOException {
		HashMap<Categoria, List<Portata>> portateMap = new HashMap<Categoria, List<Portata>>();
		portateMap.put(Categoria.ANTIPASTO,
				Arrays.asList(new Portata[] { createAntipasto("A01"),
						createAntipasto("A02") }));
		portateMap.put(Categoria.PRIMO,
				Arrays.asList(new Portata[] { createPrimo("P02"),
						createPrimo("P04") }));
		portateMap.put(Categoria.SECONDO,
				Arrays.asList(new Portata[] { createSecondo("S03"),
						createSecondo("S05") }));
		portateMap.put(Categoria.DESSERT,
				Arrays.asList(new Portata[] { createDessert("D01"),
						createDessert("D02") }));
		
		List<Menu> menuList = new ArrayList<Menu>();

		Menu menu1 = new Menu("Test1");
		menu1.getPortate(Categoria.ANTIPASTO).add(portateMap.get(Categoria.ANTIPASTO).get(0));
		menu1.getPortate(Categoria.PRIMO).add(portateMap.get(Categoria.PRIMO).get(0));
		menu1.getPortate(Categoria.SECONDO).add(portateMap.get(Categoria.SECONDO).get(0));
		menu1.getPortate(Categoria.DESSERT).add(portateMap.get(Categoria.DESSERT).get(0));
		menuList.add(menu1);

		PortateReader insReader = mock(PortateReader.class);
		when(insReader.caricaPortate()).thenReturn(portateMap);

		MenuReader pianiReader = mock(MenuReader.class);
		when(pianiReader.caricaMenu(portateMap)).thenReturn(menuList);

		UserInteractor userInteractor = mock(UserInteractor.class);

		Portata daMettere = portateMap.get(Categoria.ANTIPASTO).get(1);

		MyController controller = new MyController(insReader, pianiReader,
				userInteractor);
		Ordine ordine = controller.creaOrdine(menu1, "Pinco Pallino");
		ordine.aggiungiPortata(portateMap.get(Categoria.ANTIPASTO).get(0));
		ordine.aggiungiPortata(portateMap.get(Categoria.PRIMO).get(0));
		ordine.aggiungiPortata(portateMap.get(Categoria.SECONDO).get(0));
		ordine.aggiungiPortata(portateMap.get(Categoria.DESSERT).get(0));
		
		String result = controller.sostituisciPortata(ordine, daMettere);

		verifyZeroInteractions(userInteractor);

		assertNotNull(result);
	}
}
