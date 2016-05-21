package mm.tests;

import mm.model.Codice;
import mm.model.MatchingAlgorithm;
import mm.model.Risposta;

public class DummyMatchingAlgorithm implements MatchingAlgorithm
{

	private static final long serialVersionUID = 4L;

	@Override
	public Risposta match(Codice segreto, Codice tentativo)
	{
		return new Risposta(1, 1);
	}

}
