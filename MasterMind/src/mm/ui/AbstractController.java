package mm.ui;

import mm.model.Codice;
import mm.model.CodiceRisposta;
import mm.model.MatchingAlgorithm;
import mm.model.Partita;
import mm.persistence.PartitaPersister;
import mm.persistence.PartitaWriter;

public abstract class AbstractController
{	
	protected MainView mainView;
	protected MatchingAlgorithm matchingAlgorithm;
	protected PartitaPersister partitaPersister;
	protected PartitaWriter partitaWriter;
	protected Partita partitaCorrente;
	
	protected AbstractController(MatchingAlgorithm matchingAlgorithm, PartitaPersister partitaPersister, PartitaWriter partitaWriter)
	{
		if (matchingAlgorithm == null || partitaPersister == null || partitaWriter == null)
			throw new IllegalArgumentException();

		this.matchingAlgorithm = matchingAlgorithm;
		this.partitaPersister = partitaPersister;
		this.partitaWriter = partitaWriter;

		this.partitaCorrente = new Partita(this.matchingAlgorithm);
	}
	
	public abstract void carica();
	public abstract void salva();
	public abstract void salvaPartita();
	

	public void addTentativo(Codice codice)
	{
		if (mainView == null)
			throw new IllegalStateException("mainView == null");
		if (partitaCorrente.isPartitaChiusa())
			throw new IllegalStateException("partitaCorrente.isPartitaChiusa()");

		CodiceRisposta codiceRisposta = partitaCorrente.addTentativo(codice);
		mainView.addCodiceRisposta(codiceRisposta);
		if (partitaCorrente.isPartitaChiusa())
		{
			mainView.showCodiceSegreto(partitaCorrente.getSegreto());
		}
	}

	public void nuovaPartita()
	{		
		if (mainView == null)
			throw new IllegalStateException("mainView == null");
	
		this.partitaCorrente = new Partita(matchingAlgorithm);
		mainView.reset();
	}

	public boolean isPartitaChiusa()
	{
		return partitaCorrente.isPartitaChiusa();
	}

	
	public void setMainView(MainView mainView)
	{
		this.mainView = mainView;
		this.mainView.reset();
	}
}
