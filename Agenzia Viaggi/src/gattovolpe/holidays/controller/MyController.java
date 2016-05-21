package gattovolpe.holidays.controller;

import gattovolpe.holidays.model.Destinazione;
import gattovolpe.holidays.model.GattoVolpeHolidays;
import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyController implements Controller {

	private GattoVolpeHolidays gvh;
	public MyController( GattoVolpeHolidays gvh ) {
		this.gvh = gvh;
	}
	
	@Override
	public List<Pacchetto> evalPackage( Comparator<Pacchetto> criterio,
			TipoPacchetto tipo,
			String stato,
			double prezzo,
			Date start,
			Date end ) {
		
		List<Pacchetto> allPackages = 
				gvh.getElencoPacchetti();
		
		List<Pacchetto> results = 
				new ArrayList<Pacchetto>();
		
		for(Pacchetto p : allPackages ) {
			
			if(isValid(p, tipo, stato, prezzo, start, end))
				results.add(p);
		}
		
		Collections.sort(results, criterio);
		
		return results;
	}

	private boolean isValid(Pacchetto p, 
			TipoPacchetto tipo,
			String stato,
			double prezzo,
			Date start,
			Date end ) 
	{
		boolean valid = true;
		
		//--- tipo dirimente
		valid &=
				p.getTipo().equals(tipo);
		
		//--- stato corretto
		valid &=
				p.getDestinazione().getStato().equalsIgnoreCase(stato);
		
		//--- prezzo rispettato
		valid &=
				p.getCosto() <= prezzo;

		//--- periodo ok
		valid &=
				p.isInPeriod(start, end);
		
		return valid;
		
	}

	@Override
	public Set<Destinazione> getDestinazioni() {
		Set<Destinazione> destinazioni =
				new HashSet<Destinazione>();
		
		for(Pacchetto p : gvh.getElencoPacchetti()) {
			if(!destinazioni.contains(p.getDestinazione().getStato()))
				destinazioni.add(p.getDestinazione());
		}
		
		return destinazioni;
	}
	
}
