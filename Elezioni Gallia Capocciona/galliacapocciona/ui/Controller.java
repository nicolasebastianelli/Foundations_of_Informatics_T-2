package galliacapocciona.ui;

import galliacapocciona.model.Collegio;
import galliacapocciona.persistence.BadFileFormatException;
import galliacapocciona.persistence.CollegiReader;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Controller {

	private List<Collegio> listaCollegi;
	private int seggiMinimi;
	private UserInteractor userInteractor;
	
	protected Controller(UserInteractor userInteractor) {
		this.userInteractor = userInteractor;
	}

	public void loadData(Reader reader, CollegiReader collegiReader) {
		try {
			this.listaCollegi = collegiReader.caricaElementi(reader);
			this.seggiMinimi = listaCollegi.size();
		} catch (IOException | BadFileFormatException e) {
			this.userInteractor.showMessage(e.getMessage());
			e.printStackTrace();
			this.userInteractor.shutDownApplication();
		}
	}

	public int getSeggiMinimi() {
		return seggiMinimi;
	}

	public List<Collegio> getListaCollegi() {
		return Collections.unmodifiableList(listaCollegi);
	}
	
	protected UserInteractor getUserInteractor() {
		return userInteractor;
	}
	
	public abstract String[] getCalcolatoriSeggi();
	
	public abstract Map<String, Integer> calcola(String nomeCalcolatoreSeggi, int numeroSeggi);

}