package mm.model;

import java.io.Serializable;

public interface MatchingAlgorithm extends Serializable
{
	Risposta match(Codice segreto, Codice tentativo);
}
