package zannotaxi.ui;

import zannotaxi.model.CorsaTaxi;

public interface Controller {

	String[] getDescrizioniCorse();
	
	CorsaTaxi getCorsaPerDescrizione(String descrizioneCorsa);
	
	String[][] getLineeDiCosto(CorsaTaxi corsa);
}
