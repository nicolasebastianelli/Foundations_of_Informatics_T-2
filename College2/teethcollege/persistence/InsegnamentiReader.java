package teethcollege.persistence;
import java.io.IOException;
import java.util.Map;

import teethcollege.model.Insegnamento;

public interface InsegnamentiReader {
	public Map<Long, Insegnamento> caricaInsegnamenti() throws MalformedFileException, IOException;
}
