package pharmame.ui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pharmame.model.Farmaco;
import pharmame.model.Filter;
import pharmame.model.FilterApplier;
import pharmame.model.FarmacoFilterFactory;
import pharmame.persistence.BadFileFormatException;
import pharmame.persistence.FarmacoReader;

class FarmacoComparator implements Comparator<Farmaco> {

	public final static FarmacoComparator Instance = new FarmacoComparator();

	@Override
	public int compare(Farmaco x, Farmaco y) {
		return Float.compare(x.getPrezzo(), y.getPrezzo());
	}

}

public class MyController implements Controller {

	private FarmacoReader reader;
	private MainView view;
	private FarmacoFilterFactory filterFactory;

	private Collection<Farmaco> farmaci;

	public MyController(FarmacoReader reader, MainView view, FarmacoFilterFactory filterFactory) {
		this.reader = reader;
		this.view = view;
		this.filterFactory = filterFactory;
	}

	public void start() {
		try (Reader innerReader = new FileReader("Classe_A_per_Principio_Attivo_15.01.2014.csv")) {
			farmaci = reader.readFrom(innerReader);
			view.setController(this);
			view.setFilterNames(filterFactory.getNames());
			view.setVisible(true);
		} catch (BadFileFormatException e) {
			view.showMessage("Errore nel caricamento: " + e.getMessage());
		} catch (FileNotFoundException e) {
			view.showMessage("File non trovato");
		} catch (IOException e) {
			view.showMessage("Errore di I/O: " + e.getMessage());
		}
	}

	@Override
	public void filterBy(String filterName, String searchKey) {
		Filter<Farmaco> filter = filterFactory.get(filterName, searchKey);
		List<Farmaco> filtered = FilterApplier.applyFilter(farmaci, filter);
		view.setFarmaci(filtered);
	}

	public void printSelected(int[] selectedRows) {
		List<Farmaco> farmaciSelezionati = new ArrayList<>();
		for (int selectedRowIndex : selectedRows) {
			farmaciSelezionati.add(view.getFarmacoAt(selectedRowIndex));
		}

		Collections.sort(farmaciSelezionati, FarmacoComparator.Instance);
		List<String> nomiFarmaciSelezionati = new ArrayList<>();
		for (Farmaco f : farmaciSelezionati) {
			nomiFarmaciSelezionati.add(f.toString());
		}
		
		view.setOutput(nomiFarmaciSelezionati.toArray(new String[1]));
	}
}
