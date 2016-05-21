package ed.ui;

import java.util.Collection;

import ed.model.Bolletta;
import ed.model.Utente;

public interface Controller
{
	Collection<String> getNomiTariffe();
	Bolletta creaBolletta(String nomeTariffa, Utente utente, int mese, int anno, double consumo);
}
