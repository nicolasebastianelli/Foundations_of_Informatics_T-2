package ed.ui;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;

import ed.model.Bolletta;
import ed.model.Tariffa;
import ed.model.Utente;
import ed.persistence.BadFileFormatException;
import ed.persistence.TariffaReader;

public class MyController implements Controller
{
	private HashMap<String, Tariffa> mappaTariffe = new HashMap<String, Tariffa>();
	private TariffaReader tariffaReader;
	private MessageManager messageManager;
	
	public MyController(TariffaReader tariffaReader, MessageManager messageManager)
	{
		this.tariffaReader = tariffaReader;
		this.messageManager = messageManager;
	}
	
	public void leggiTariffe(Reader reader)
	{
		try
		{
			Collection<Tariffa> tariffe = tariffaReader.leggiTariffe(reader);
			for (Tariffa tariffa : tariffe)
			{
				mappaTariffe.put(tariffa.getNome(), tariffa);
			}
		}
		catch (IOException e)
		{
			messageManager.showMessage("Problema di IO - File tariffe non caricato");
		}
		catch (BadFileFormatException e)
		{
			messageManager.showMessage("Errore nel formato del file - File tariffe non caricato");
		}
	}

	@Override
	public Collection<String> getNomiTariffe()
	{
		return mappaTariffe.keySet();
	}

	@Override
	public Bolletta creaBolletta(String nomeTariffa, Utente utente, int mese, int anno, double consumo)
	{
		Tariffa tariffa = mappaTariffe.get(nomeTariffa);
		Bolletta bolletta = tariffa.creaBolletta(utente, mese, anno, consumo);
		return bolletta;
	}

}
