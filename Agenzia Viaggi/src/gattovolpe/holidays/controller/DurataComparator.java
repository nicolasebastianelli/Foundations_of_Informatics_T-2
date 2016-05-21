package gattovolpe.holidays.controller;

import gattovolpe.holidays.model.Pacchetto;

import java.util.Comparator;

public class DurataComparator implements Comparator<Pacchetto> {

	private int durata;
	public DurataComparator(int durata) {
		this.durata = durata;
	}

	@Override
	public int compare(Pacchetto o1, Pacchetto o2) {
		int d1 =
				Math.abs( o1.getDurataGiorni() - durata );
		int d2 = 
				Math.abs( o2.getDurataGiorni() - durata );
		return d1 < d2 ? -1 : ( d1 > d2 ? 1 : 0 );
		
	}

}
