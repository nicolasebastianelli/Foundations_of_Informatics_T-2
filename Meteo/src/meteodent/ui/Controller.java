package meteodent.ui;

import java.util.Collection;
import java.util.Date;

import meteodent.model.Annuncio;
import meteodent.model.Previsione;

public interface Controller {
	Collection<String> getLocalita();
	Collection<Date> getDatePerLocalita(String localita);
	Collection<Previsione> getPrevisioni(String localita, Date data);
	Annuncio getAnnuncio(String localita, Date data);
}
