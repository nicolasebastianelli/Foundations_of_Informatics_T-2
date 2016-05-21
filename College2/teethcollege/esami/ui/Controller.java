package teethcollege.esami.ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import teethcollege.model.*;
import teethcollege.persistence.*;

public abstract class Controller {
	
//	public abstract Esame getNuovoEsame(Insegnamento corso, String voto, String data);
	public abstract Carriera aggiungiEsame(Insegnamento corso, String voto, String data, PianoDiStudi prescelto);
	public abstract List<Esame> getEsami(String matricola); 
	protected abstract void salvaEsami(String matricola, List<Esame> esami);
	
	private Map<Long, Insegnamento> insegnamenti;
	private Collection<PianoDiStudi> pianiDiStudi;
	private UserInteractor userInteractor;
	
	public Controller(String nomeFileInsegnamenti, String nomeFileCarriere, UserInteractor userInteractor) {
		this.userInteractor = userInteractor;
		FileReader reader = null;
		// FASE 1: caricamento insegnamenti
		try {
			reader = new FileReader(nomeFileInsegnamenti);
			InsegnamentiReader myInsReader = new MyInsegnamentiReader(reader);
			insegnamenti = myInsReader.caricaInsegnamenti();
			reader.close();
			
			reader = new FileReader(nomeFileCarriere);
			PianiReader myReader = new MyPianiReader(reader);
			pianiDiStudi = myReader.caricaPianiDiStudi(insegnamenti);
			reader.close();
		} catch (IOException e) {
			if (reader != null) {
				try{
					reader.close();
				}
				catch (Exception ex) {}
			}
			this.userInteractor.showMessage("Errore nella lettura del file");
			this.userInteractor.shutDownApplication();
		}
		// FASE 2: caricamento piani di studio (con nomi studenti)
		ArrayList<PianoDiStudi> pianiScartati = new ArrayList<PianoDiStudi>();
		for (PianoDiStudi c : pianiDiStudi) {
			if (!c.isValid()) {
				pianiScartati.add(c);				
			}
		}
		for (PianoDiStudi c : pianiScartati) {
			pianiDiStudi.remove(c);
		}
		if (!pianiScartati.isEmpty()) {
			userInteractor.showMessage("Piani di studio scartati: " + pianiScartati);
		}
	}

	public Collection<PianoDiStudi> getPianiDiStudi() {
		return pianiDiStudi;
	}

	public Collection<Insegnamento> getInsegnamenti() {
		return insegnamenti.values();
	}
	
	protected Map<Long, Insegnamento> getMappaInsegnamenti() {
		return insegnamenti;
	}
	
	protected UserInteractor getUserInteractor() {
		return userInteractor;
	}

}
