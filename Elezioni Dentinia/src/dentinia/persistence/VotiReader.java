package dentinia.persistence;

import java.io.IOException;
import java.util.List;

import dentinia.model.Partito;

public interface VotiReader {
	public List<Partito> caricaElementi() throws IOException, BadFileFormatException;
	public int getSeggi();
	public List<Partito> getListaPartiti();
}
