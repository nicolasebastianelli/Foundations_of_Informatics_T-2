package mm.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import mm.model.CodiceRisposta;
import mm.model.MatchingAlgorithm;
import mm.persistence.PartitaPersister;
import mm.persistence.PartitaWriter;
import mm.persistence.BadFileFormatException;

public class MyController extends AbstractController
{
	private static final String FileName = "Partita.dat";
	private static final String TxtFileName = "Partita.txt";

	public MyController(MatchingAlgorithm matchingAlgorithm, PartitaPersister partitaPersister, PartitaWriter partitaWriter)
	{
		super(matchingAlgorithm, partitaPersister, partitaWriter);
	}

	@Override
	public void carica()
	{
		if (mainView == null)
			throw new IllegalStateException("mainView == null");

		try
		{
			partitaCorrente = partitaPersister.read(new FileInputStream(FileName));
			mainView.reset();
			for (CodiceRisposta codiceRisposta : partitaCorrente.getGiocate())
			{
				mainView.addCodiceRisposta(codiceRisposta);
			}
		}
		catch (IOException | BadFileFormatException e)
		{
			mainView.showMessage("Nessuna partita da caricare");
		}
	}

	@Override
	public void salva()
	{
		if (mainView == null)
			throw new IllegalStateException("mainView == null");
		if (partitaCorrente.isPartitaChiusa())
			throw new IllegalStateException("partitaCorrente.isPartitaChiusa()");

		try
		{
			partitaPersister.write(partitaCorrente, new FileOutputStream(FileName));
		}
		catch (IOException e)
		{
			mainView.showMessage("Errore nella scrittura del file");
		}
	}

	@Override
	public void salvaPartita()
	{
		if (mainView == null)
			throw new IllegalStateException("partitaView == null");
		if (!partitaCorrente.isPartitaChiusa())
			throw new IllegalStateException("!partitaCorrente.isPartitaChiusa()");

		try
		{
			partitaWriter.write(partitaCorrente, new PrintWriter(new FileWriter(TxtFileName)));
		}
		catch (IOException e)
		{
			mainView.showMessage("Errore nella scrittura del file");
		}
	}
}
