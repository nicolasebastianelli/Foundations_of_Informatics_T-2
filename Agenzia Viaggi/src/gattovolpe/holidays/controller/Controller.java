package gattovolpe.holidays.controller;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import gattovolpe.holidays.model.Destinazione;
import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;

public interface Controller {
	
	List<Pacchetto> evalPackage(Comparator<Pacchetto> criterio,
			TipoPacchetto tipo,
			String stato,
			double prezzo,
			Date start,
			Date end);

	Set<Destinazione> getDestinazioni();
	
}
