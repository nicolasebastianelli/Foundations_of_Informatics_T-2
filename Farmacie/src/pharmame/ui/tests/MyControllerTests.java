package pharmame.ui.tests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import pharmame.model.Farmaco;
import pharmame.model.FarmacoFilterFactory;
import pharmame.model.Filter;
import pharmame.persistence.BadFileFormatException;
import pharmame.persistence.FarmacoReader;
import pharmame.ui.MainView;
import pharmame.ui.MyController;

public class MyControllerTests {

	@SuppressWarnings("unchecked")
	@Test
	public void testStart() throws BadFileFormatException, IOException {
		MainView view = mock(MainView.class);
		
		FarmacoReader reader = mock(FarmacoReader.class);
		ArrayList<Farmaco> result = new ArrayList<>();
		result.add(new Farmaco(1234, "AAA", "BBB", "CCC", "DDD", 1.11f, "FFF"));
		result.add(new Farmaco(4321, "GGG", "HHH", "III", "LLL", 2.22f, "MMM"));
		when(reader.readFrom(any(Reader.class))).thenReturn(result);
		
		FarmacoFilterFactory filterFactory = mock(FarmacoFilterFactory.class);
		List<String> filterNames = Arrays.asList("One", "Two", "Three");
		when(filterFactory.getNames()).thenReturn(filterNames);
		
		MyController ctrl = new MyController(reader, view, filterFactory);
		ctrl.start();
		
		verify(view, Mockito.times(1)).setVisible(true);
		verify(view, Mockito.times(1)).setController(ctrl);
		verify(view, Mockito.times(1)).setFilterNames(filterNames);
		verify(view, never()).setOutput(any(String[].class));
		verify(view, never()).getFarmacoAt(any(Integer.class));
		verify(view, never()).showMessage(anyString());
		verify(view, never()).setFarmaci(any(List.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testStart_FailForBadFileFormat() throws BadFileFormatException, IOException {
		MainView view = mock(MainView.class);
		
		FarmacoReader reader = mock(FarmacoReader.class);
		ArrayList<Farmaco> result = new ArrayList<>();
		result.add(new Farmaco(1234, "AAA", "BBB", "CCC", "DDD", 1.11f, "FFF"));
		result.add(new Farmaco(4321, "GGG", "HHH", "III", "LLL", 2.22f, "MMM"));
		when(reader.readFrom(any(Reader.class))).thenThrow(BadFileFormatException.class);
		
		FarmacoFilterFactory filterFactory = mock(FarmacoFilterFactory.class);
		List<String> filterNames = Arrays.asList("One", "Two", "Three");
		when(filterFactory.getNames()).thenReturn(filterNames);
		
		MyController ctrl = new MyController(reader, view, filterFactory);
		ctrl.start();
		
		verify(view, Mockito.times(1)).showMessage(any(String.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testStart_FailForFileNotFound() throws BadFileFormatException, IOException {
		MainView view = mock(MainView.class);
		
		FarmacoReader reader = mock(FarmacoReader.class);
		ArrayList<Farmaco> result = new ArrayList<>();
		result.add(new Farmaco(1234, "AAA", "BBB", "CCC", "DDD", 1.11f, "FFF"));
		result.add(new Farmaco(4321, "GGG", "HHH", "III", "LLL", 2.22f, "MMM"));
		when(reader.readFrom(any(Reader.class))).thenThrow(FileNotFoundException.class);
		
		FarmacoFilterFactory filterFactory = mock(FarmacoFilterFactory.class);
		List<String> filterNames = Arrays.asList("One", "Two", "Three");
		when(filterFactory.getNames()).thenReturn(filterNames);
		
		MyController ctrl = new MyController(reader, view, filterFactory);
		ctrl.start();
		
		verify(view, Mockito.times(1)).showMessage(any(String.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFilterBy() throws BadFileFormatException, IOException {
		MainView view = mock(MainView.class);
		
		FarmacoReader reader = mock(FarmacoReader.class);
		ArrayList<Farmaco> result = new ArrayList<>();
		result.add(new Farmaco(1234, "AAA", "BBB", "CCC", "DDD", 1.11f, "FFF"));
		result.add(new Farmaco(4321, "GGG", "HHH", "III", "LLL", 2.22f, "MMM"));
		when(reader.readFrom(any(Reader.class))).thenReturn(result);
		
		FarmacoFilterFactory filterFactory = mock(FarmacoFilterFactory.class);
		List<String> filterNames = Arrays.asList("One", "Two", "Three");
		when(filterFactory.getNames()).thenReturn(filterNames);
		when(filterFactory.get(anyString(), anyString())).thenReturn(new Filter<Farmaco>() {
			public boolean filter(Farmaco f) {
				return true;
			}
		});
		
		MyController ctrl = new MyController(reader, view, filterFactory);
		ctrl.start();
		ctrl.filterBy("One", "Cipz");
		
		verify(view, Mockito.times(1)).setVisible(true);
		verify(view, Mockito.times(1)).setController(ctrl);
		verify(view, Mockito.times(1)).setFilterNames(filterNames);
		verify(view, Mockito.times(1)).setFarmaci(any(List.class));
		verify(view, never()).setOutput(any(String[].class));
		verify(view, never()).getFarmacoAt(any(Integer.class));
		verify(view, never()).showMessage(anyString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPrintSelected() throws BadFileFormatException, IOException {
		MainView view = mock(MainView.class);
		when(view.getFarmacoAt(1)).thenReturn(new Farmaco(1234, "AAA", "BBB", "CCC", "DDD", 1.11f, "FFF"));
		when(view.getFarmacoAt(2)).thenReturn(new Farmaco(1234, "AAA", "BBB", "CCC", "DDD", 1.11f, "FFF"));
		
		FarmacoReader reader = mock(FarmacoReader.class);
		ArrayList<Farmaco> result = new ArrayList<>();
		result.add(new Farmaco(1234, "AAA", "BBB", "CCC", "DDD", 1.11f, "FFF"));
		result.add(new Farmaco(4321, "GGG", "HHH", "III", "LLL", 2.22f, "MMM"));
		when(reader.readFrom(any(Reader.class))).thenReturn(result);
		
		FarmacoFilterFactory filterFactory = mock(FarmacoFilterFactory.class);
		List<String> filterNames = Arrays.asList("One", "Two", "Three");
		when(filterFactory.getNames()).thenReturn(filterNames);
		when(filterFactory.get(anyString(), anyString())).thenReturn(new Filter<Farmaco>() {
			public boolean filter(Farmaco f) {
				return true;
			}
		});
		
		MyController ctrl = new MyController(reader, view, filterFactory);
		ctrl.start();
		ctrl.printSelected(new int[] {1, 2});
		
		verify(view, Mockito.times(1)).setVisible(true);
		verify(view, Mockito.times(1)).setController(ctrl);
		verify(view, Mockito.times(1)).setFilterNames(filterNames);
		verify(view, never()).setFarmaci(any(List.class));
		verify(view, Mockito.times(1)).getFarmacoAt(1);
		verify(view, Mockito.times(1)).getFarmacoAt(2);
		verify(view, Mockito.times(1)).setOutput(any(String[].class));
		verify(view, never()).showMessage(anyString());
	}

}
