package mm.ui;

import mm.model.Codice;
import mm.model.CodiceRisposta;

public interface MainView
{
	void reset();
	void addCodiceRisposta(CodiceRisposta codiceRisposta);
	void showCodiceSegreto(Codice segreto);
	
	void showMessage(String msg);
}
