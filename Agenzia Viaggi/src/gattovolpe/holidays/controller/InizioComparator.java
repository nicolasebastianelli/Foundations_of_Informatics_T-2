package gattovolpe.holidays.controller;

import gattovolpe.holidays.model.Pacchetto;

import java.util.Comparator;
import java.util.Date;

import static gattovolpe.utils.DateUtil.*;

public class InizioComparator implements Comparator<Pacchetto> {
	
	private Date dataInizio;
	public InizioComparator(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	
	@Override
	public int compare(Pacchetto arg0, Pacchetto arg1) {
		int d1 = 
				Math.abs(getDateDifference(arg0.getDataInizio(), dataInizio));
		int d2 = 
				Math.abs(getDateDifference(arg1.getDataInizio(), dataInizio));
		return d1 < d2 ? -1 : ( d1 > d2 ? 1 : 0);
	}

}
