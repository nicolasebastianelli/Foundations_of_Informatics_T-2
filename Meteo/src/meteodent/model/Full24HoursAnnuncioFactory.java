package meteodent.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class Full24HoursAnnuncioFactory implements AnnuncioFactory {
	
	public Full24HoursAnnuncioFactory() {
	}

	@Override
	public Annuncio crea(Collection<Previsione> previsioni) {
		if (previsioni == null || previsioni.size() == 0 || !checkStessaDataLocalita(previsioni))
			throw new IllegalArgumentException("previsioni");
		
		ArrayList<Previsione> previsioniList = new ArrayList<Previsione>(previsioni);
		
		GregorianCalendar cal = new GregorianCalendar();
		
		int pesoTotale = 0;		
		int accProbPioggia = 0;		
		Previsione previsione;
		
		previsione = previsioniList.get(0);
		Date dayDate = DateUtils.getDayDate(previsione.getDataOra());
		String localita = previsione.getLocalita();
		
		cal.setTime(previsione.getDataOra());
		int pesoDaMezzanotteAPrimaPrevisione = cal.get(GregorianCalendar.HOUR_OF_DAY);
		pesoTotale += pesoDaMezzanotteAPrimaPrevisione;
		accProbPioggia += previsione.getProbabilitaPioggia() * pesoDaMezzanotteAPrimaPrevisione;
		
		previsione = previsioniList.get(previsioniList.size() - 1);
		cal.setTime(previsione.getDataOra());
		int pesoDaUltimaPrevisioneAMezzanotte = 24 - cal.get(GregorianCalendar.HOUR_OF_DAY);
		pesoTotale += pesoDaUltimaPrevisioneAMezzanotte;
		accProbPioggia += previsione.getProbabilitaPioggia() * pesoDaUltimaPrevisioneAMezzanotte;
		
		int pesoCorrente = 0;
		for (int i = 1; i < previsioniList.size(); i++) {
			Previsione prec = previsioniList.get(i-1);
			Previsione corr = previsioniList.get(i);
			
			cal.setTime(prec.getDataOra());
			int oraPrec = cal.get(GregorianCalendar.HOUR_OF_DAY);
			
			cal.setTime(corr.getDataOra());
			int oraCorr = cal.get(GregorianCalendar.HOUR_OF_DAY);
			
			pesoCorrente += oraCorr - oraPrec;
			accProbPioggia += prec.getProbabilitaPioggia() * pesoCorrente;
			pesoTotale += pesoCorrente;
		}
		
		int probPioggia = (int) ((double)accProbPioggia) / pesoTotale;
		String testo = getTesto(probPioggia);

		return new Annuncio(dayDate, localita, probPioggia, testo);
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
		if (probabilitaPioggia < 5) {
			return "Sereno";
		} else if (probabilitaPioggia < 10) {
			return "Quasi sereno";
		} else if (probabilitaPioggia < 25) {
			return "Possibili piogge sparse";
		} else if (probabilitaPioggia < 50) {
			return "Variabile";
		} else if (probabilitaPioggia < 65) {
			return "Piogge diffuse";
		} else if (probabilitaPioggia < 80) {
			return "Piogge";
		} else {
			return "Piogge insistenti e generalizzate";
		}
	}

}
