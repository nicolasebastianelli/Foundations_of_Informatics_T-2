package mm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Partita implements Serializable
{
	private static final long serialVersionUID = 1L;

	private MatchingAlgorithm matchingAlgorithm;
	private Codice segreto;
	private ArrayList<CodiceRisposta> giocate = new ArrayList<CodiceRisposta>();
	private boolean isPartitaChiusa;

	public Partita(MatchingAlgorithm matchingAlgorithm)
	{
		this.matchingAlgorithm = matchingAlgorithm;
		this.segreto = GeneratoreCodice.creaCodice();
	}

	public Codice getSegreto()
	{
		return segreto;
	}

	public CodiceRisposta addTentativo(Codice tentativo)
	{
		if (isPartitaChiusa)
			throw new IllegalStateException();

		Risposta risposta = matchingAlgorithm.match(segreto, tentativo);
		CodiceRisposta codiceRisposta = new CodiceRisposta(tentativo, risposta);
		giocate.add(codiceRisposta);
		isPartitaChiusa = risposta.allMatch() || giocate.size() >= Configuration.GiocateMassime;
		return codiceRisposta;
	}

	public boolean isPartitaChiusa()
	{
		return isPartitaChiusa;
	}

	public Iterable<CodiceRisposta> getGiocate()
	{
		return giocate;
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Partita))
			return false;
		
		Partita other = (Partita) o;
		
		return matchingAlgorithm.getClass() == other.matchingAlgorithm.getClass() &&
				isPartitaChiusa == other.isPartitaChiusa &&
				getSegreto().equals(other.getSegreto()) && 
				giocateEquals(giocate);
	}

	private boolean giocateEquals(ArrayList<CodiceRisposta> giocate2)
	{
		if (giocate.size() == giocate2.size())
			for (int i = 0; i < giocate.size(); ++i)
				if (!giocate.get(i).equals(giocate2.get(i)))
					return false;
		return true;
	}
}
