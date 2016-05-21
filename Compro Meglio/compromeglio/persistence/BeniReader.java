package compromeglio.persistence;

import java.io.IOException;
import java.util.Set;

import compromeglio.model.Bene;


public interface BeniReader {
	public Set<Bene> caricaBeni() throws MalformedFileException, IOException;
}
