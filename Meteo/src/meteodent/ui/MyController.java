package meteodent.ui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeSet;

import meteodent.model.Annuncio;
import meteodent.model.AnnuncioFactory;
import meteodent.model.DateUtils;
import meteodent.model.Previsione;
import meteodent.persistence.BadFileFormatException;
import meteodent.persistence.PrevisioneReader;

public class MyController implements Controller {
	private static final String FILE_NAME = "Previsioni.txt";
	private Map<String, Collection<Previsione>> previsioniMap;
	private UserInteractor userInteractor;
	private PrevisioneReader previsioneReader;
	private AnnuncioFactory annuncioFactory;
	
	public MyController(UserInteractor userInteractor, PrevisioneReader previsioneReader, AnnuncioFactory annuncioFactory) {
		this.userInteractor = userInteractor;
		this.previsioneReader = previsioneReader;
		this.annuncioFactory = annuncioFactory;
	}
	
	public void start() {
		try (Reader reader = new FileReader(FILE_NAME)) {
			previsioniMap = previsioneReader.readFrom(reader);
		}
		catch (FileNotFoundException e) {
			userInteractor.showMessage("File non trovato: " + FILE_NAME);
			userInteractor.shutDownApplication();
		}
		catch (IOException e) {
			userInteractor.showMessage("Errore di input/output");
			userInteractor.shutDownApplication();
		} catch (BadFileFormatException e) {
			userInteractor.showMessage("Errore nel formato del file: " + e.getMessage());
			userInteractor.shutDownApplication();
		}
	}
	
	private void assertPrevisioniNotNull() {
		if (previsioniMap == null)
			throw new RuntimeException("previsioni == null");
	}
	
	private Collection<Previsione> filterByDate(Collection<Previsione> previsioni, Date data) {
		ArrayList<Previsione> result = new ArrayList<Previsione>();
		for (Previsione previsione : previsioni) {
			if (DateUtils.isSameDayDate(previsione.getDataOra(), data)) {
				result.add(previsione);
			}
		}
		return result;
	}

	@Override
	public Collection<String> getLocalita() {
		assertPrevisioniNotNull();
		return previsioniMap.keySet();
	}
	
	@Override 
	public Collection<Date> getDatePerLocalita(String localita) {
		assertPrevisioniNotNull();
		Collection<Previsione> previsioni = previsioniMap.get(localita);
		TreeSet<Date> distinctDates = new TreeSet<Date>();
		for (Previsione previsione : previsioni) {
			Date dayDate = DateUtils.getDayDate(previsione.getDataOra());
			if (!distinctDates.contains(dayDate)) {
				distinctDates.add(dayDate);
			}
		}
		return distinctDates;
	}

	@Override
	public Collection<Previsione> getPrevisioni(String localita, Date data) {
		assertPrevisioniNotNull();
		Collection<Previsione> previsioni = previsioniMap.get(localita);
		return filterByDate(previsioni, data);
	}

	@Override
	public Annuncio getAnnuncio(String localita, Date data) {
		assertPrevisioniNotNull();
		Collection<Previsione> previsioni = getPrevisioni(localita, data);
		return annuncioFactory.crea(previsioni);
	}

}
