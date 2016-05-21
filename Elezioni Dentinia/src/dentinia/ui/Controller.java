package dentinia.ui;

import java.io.IOException;
import java.util.List;

import dentinia.model.Partito;

public interface Controller {

	void ricalcola(String meccanismoSelezionato);
	
	void salvaSuFile(String nomeFile) throws IOException;
	
	String[] getCalcolatoriSeggi();

	List<Partito> getListaPartiti();
}
