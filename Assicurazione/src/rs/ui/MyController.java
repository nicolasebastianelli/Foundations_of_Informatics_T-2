package rs.ui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

import rs.model.StimaSintetica;
import rs.model.StimaSinteticaFactory;
import rs.model.StimaRischio;
import rs.persistence.BadFileFormatException;
import rs.persistence.StimaRischioReader;

public class MyController implements Controller {
	private static final String FILE_NAME = "StimeRischio.txt";
	private Map<String, Collection<StimaRischio>> stimeRischioMap;
	private UserInteractor userInteractor;
	private StimaRischioReader stimaRischioReader;
	private StimaSinteticaFactory stimaSinteticaFactory;
	
	public MyController(UserInteractor userInteractor, StimaRischioReader stimaRischioReader, StimaSinteticaFactory stimaSinteticaFactory) {
		this.userInteractor = userInteractor;
		this.stimaRischioReader = stimaRischioReader;
		this.stimaSinteticaFactory = stimaSinteticaFactory;
	}
	
	public void start() {
		try (Reader reader = new FileReader(FILE_NAME)) {
			stimeRischioMap = stimaRischioReader.readFrom(reader);
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
	
	private void assertStimeRischioMapNotNull() {
		if (stimeRischioMap == null)
			throw new RuntimeException("stimeRischioMap == null");
	}
	
	private Collection<StimaRischio> filterByDate(Collection<StimaRischio> stimeRischio, int anno) {
		ArrayList<StimaRischio> result = new ArrayList<StimaRischio>();
		for (StimaRischio stimaRischio : stimeRischio) {
			if (stimaRischio.getAnno() == anno) {
				result.add(stimaRischio);
			}
		}
		return result;
	}

	@Override
	public Collection<String> getCitta() {
		assertStimeRischioMapNotNull();
		return stimeRischioMap.keySet();
	}
	
	@Override 
	public Collection<Integer> getAnniPerCitta(String citta) {
		assertStimeRischioMapNotNull();
		Collection<StimaRischio> stimeRischio = stimeRischioMap.get(citta);
		TreeSet<Integer> distinctAnni = new TreeSet<Integer>();
		for (StimaRischio stimaRischio : stimeRischio) {
			distinctAnni.add(stimaRischio.getAnno());
		}
		return distinctAnni;
	}

	@Override
	public Collection<StimaRischio> getStimeRischio(String citta, int anno) {
		assertStimeRischioMapNotNull();
		Collection<StimaRischio> stimeRischio = stimeRischioMap.get(citta);
		return filterByDate(stimeRischio, anno);
	}

	@Override
	public StimaSintetica getStimaSintetica(String localita, int anno) {
		assertStimeRischioMapNotNull();
		Collection<StimaRischio> stimeRischio = getStimeRischio(localita, anno);
		return stimaSinteticaFactory.create(stimeRischio);
	}

}
