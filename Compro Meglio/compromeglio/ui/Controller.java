package compromeglio.ui;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compromeglio.model.*;

public abstract class Controller implements IController {
	
	protected Set<Bene> beni;
	protected Monitoraggio monitoraggio;
	protected List<Rilevazione> rilevazioni;
	
	private UserInteractor userInteractor;
	private NumberFormat formatter;
	
	public Controller(UserInteractor userInteractor) {
		
		this.userInteractor = userInteractor;
		this.formatter = NumberFormat.getInstance();
		this.formatter.setMaximumFractionDigits(2);
	}
	
	public List<Rilevazione> getRilevazioni() {
		
		return rilevazioni;
	}
	
	public Map<Long, Bene> getMappaBeni() {
		Map<Long, Bene> result = new HashMap<Long, Bene>();
		for(Bene b: beni) {
			result.put(b.getCodice(), b);
		}
		return result;
	}
	
	public UserInteractor getUserInteractor() {
		return userInteractor;
	}
	
	@Override
	public String getPrezzoMedioPerCategoria(Categoria c) {
		
		try {
			return "EUR " + formatter.format(monitoraggio.getPrezzoMedio(c));
		} catch(NonRilevatoException e) {
			return "";
		}
	}
	
	@Override
	public String getMigliorPrezzoPerBene(Bene b) {
		
		try {
			return "EUR " + formatter.format(monitoraggio.getMigliorRilevazione(b.getCodice()).getPrezzo());
		} catch(NonRilevatoException e) {
			return "";
		}
	}

	@Override
	public String getPrezzoMedioPerBene(Bene b) {
	
		try {
			return "EUR " + formatter.format(monitoraggio.getPrezzoMedio(b.getCodice()));
		} catch(NonRilevatoException e) {
			return "";
		}
		
	}
	
	@Override
	public String getMigliorRilevazionePerBene(Bene b) {
		try {
			return 
					"Miglior rilevazione: " +
					monitoraggio.getMigliorRilevazione(b.getCodice()).toFullString();
		} catch (NonRilevatoException e) {
			return "";
		}
	}	

}
