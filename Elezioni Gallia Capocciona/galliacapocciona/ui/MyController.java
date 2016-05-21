package galliacapocciona.ui;

import galliacapocciona.model.CalcolatoreSeggi;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MyController extends Controller {

	public MyController(UserInteractor userInteractor) {
		super(userInteractor);
	}
	
	public String[] getCalcolatoriSeggi() {
		return CalcolatoreSeggi.getCalcolatoriSeggi();
	}

	public Map<String, Integer> calcola(String nomeCalcolatoreSeggi, int numeroSeggi) {
		if (numeroSeggi < getSeggiMinimi()) {
			getUserInteractor().showMessage("Impossibile assegnare meno seggi dei collegi: " + getSeggiMinimi());
			return null;
		}
		
		CalcolatoreSeggi cs;
		try {
			cs = CalcolatoreSeggi.getInstance(nomeCalcolatoreSeggi);
		} catch (NoSuchAlgorithmException e1) {
			getUserInteractor().showMessage("Calcolatore Seggi non valido.");
			return null;
		}		
		
		try {
			return cs.assegnaSeggi(numeroSeggi, getListaCollegi());
		} catch (Exception ex) {
			getUserInteractor().showMessage(ex.getMessage());
			return null;
		}
	}

}
