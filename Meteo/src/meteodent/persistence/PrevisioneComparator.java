package meteodent.persistence;

import java.util.Comparator;

import meteodent.model.Previsione;

class PrevisioneComparator implements Comparator<Previsione> {

	@Override
	public int compare(Previsione o1, Previsione o2) {
		return o1.getDataOra().compareTo(o2.getDataOra());
	}
	
}