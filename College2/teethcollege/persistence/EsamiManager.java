package teethcollege.persistence;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import teethcollege.model.Esame;
import teethcollege.model.Insegnamento;

public interface EsamiManager {
	public List<Esame> caricaEsami(Reader reader, Map<Long, Insegnamento> mappaInsegnamenti) throws IOException;
	public void salvaEsami(Writer writer, List<Esame> esami) throws IOException;
}
