package compromeglio.persistence;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import compromeglio.model.Bene;
import compromeglio.model.Rilevazione;

public interface RilevazioniReader {
	public List<Rilevazione> caricaRilevazioni(Map<Long, Bene> beni) throws IOException;
}
