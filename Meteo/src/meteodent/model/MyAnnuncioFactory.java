package meteodent.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyAnnuncioFactory implements AnnuncioFactory {

	public MyAnnuncioFactory() {
	}

	@Override
	public Annuncio crea(Collection<Previsione> previsioni) {
		if (previsioni == null || previsioni.size() == 0 || !checkStessaDataLocalita(previsioni))
			throw new IllegalArgumentException("previsioni");
		
		ArrayList<Previsione> previsioniList = new ArrayList<Previsione>(previsioni);
		Annuncio annuncio;
		if (previsioni.size() == 1) {
			annuncio = creaDaPrevisioneSingola(previsioniList.get(0));
		} else {
			annuncio = creaDaPrevisioniMultiple(previsioniList);
		}
		return annuncio;
	}
	
	private Annuncio creaDaPrevisioniMultiple(ArrayList<Previsione> previsioniList) {		
		GregorianCalendar cal = new GregorianCalendar();
		
		int pesoTotale = 0;		
		int accProbPioggia = 0;		
		Previsione previsione;
		
		previsione = previsioniList.get(0);
		Date dayDate = DateUtils.getDayDate(previsione.getDataOra());
		String localita = previsione.getLocalita();
		
		previsione = previsioniList.get(previsioniList.size() - 1);
		cal.setTime(previsione.getDataOra());
		int pesoDaUltimaPrevisioneAMezzanotte = 24 - cal.get(GregorianCalendar.HOUR_OF_DAY);
		pesoTotale += pesoDaUltimaPrevisioneAMezzanotte;
		accProbPioggia += previsione.getProbabilitaPioggia() * pesoDaUltimaPrevisioneAMezzanotte;
		
		
		for (int i = 1; i < previsioniList.size(); i++) {
			Previsione prec = previsioniList.get(i-1);
			Previsione corr = previsioniList.get(i);
			
			cal.setTime(prec.getDataOra());
			int oraPrec = cal.get(GregorianCalendar.HOUR_OF_DAY);
			
			cal.setTime(corr.getDataOra());
			int oraCorr = cal.get(GregorianCalendar.HOUR_OF_DAY);
			
			int pesoCorrente = oraCorr - oraPrec;
			accProbPioggia += prec.getProbabilitaPioggia() * pesoCorrente;
			pesoTotale += pesoCorrente;
		}
		
		int probPioggia = (int) ((double)accProbPioggia) / pesoTotale;
		String testo = getTesto(probPioggia);

		return new Annuncio(dayDate, localita, probPioggia, testo);
	}

	private Annuncio creaDaPrevisioneSingola(Previsione previsione) {
		return new Annuncio(
				DateUtils.getDayDate(previsione.getDataOra()), 
				previsione.getLocalita(),
				previsione.getProbabilitaPioggia(),
				getTesto(previsione.getProbabilitaPioggia()));
	}

	private boolean checkStessaDataLocalita(Collection<Previsione> previsioni) {
		String localita = null;
		Date data = null;
		
		for (Previsione previsione : previsioni) {
			String currentLocalita = previsione.getLocalita(); 
			if (localita == null) {
				localita = currentLocalita;
			} else if (!localita.equals(currentLocalita)) {
				return false;
			}
			
			Date currentDayDate = DateUtils.getDayDate(previsione.getDataOra()); 
			if (data == null) {
				data = currentDayDate;
			} else if (!data.equals(currentDayDate)) {
				return false;
			}
		}
		
		return true;
	}

	private String getTesto(int probabilitaPioggia) {
		String testo = "Giornata ";
		if (probabilitaPioggia < 5) {
			testo += "serena";
		} else if (probabilitaPioggia < 10) {
			testo += "quasi sereno";
		} else if (probabilitaPioggia < 25) {
			testo += "con possibili piogge sparse";
		} else if (probabilitaPioggia < 50) {
			testo += "variabile";
		} else if (probabilitaPioggia < 65) {
			testo += "con piogge diffuse";
		} else if (probabilitaPioggia < 80) {
			testo += "con piogge";
		} else {
			testo += "con piogge insistenti e generalizzate";
		}
		testo += ", con probabilità di pioggia del " + probabilitaPioggia + "%";
		return testo;
	}

}
