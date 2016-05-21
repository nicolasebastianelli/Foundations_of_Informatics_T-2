package rs.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class MyStimaSinteticaFactory implements StimaSinteticaFactory {
	
	private static final long MILLIS_PER_DAY = 24 * 3600 * 1000;
	
	public MyStimaSinteticaFactory() {
	}

	@Override
	public StimaSintetica create(Collection<StimaRischio> stimeRischio) {
		if (stimeRischio == null || stimeRischio.size() == 0 || !checkStessaCittaAnnoDistinctMese(stimeRischio))
			throw new IllegalArgumentException("stimeRischio");
		
		ArrayList<StimaRischio> stimeRischioList = new ArrayList<StimaRischio>(stimeRischio);
		Collections.sort(stimeRischioList);
				
		int accRischio = 0;		
		int anno;
		String citta;
		StimaRischio stimaRischio;
		
		stimaRischio = stimeRischioList.get(0);
		anno = stimaRischio.getAnno();
		citta = stimaRischio.getCitta();		
		Date inizioAnnoDate = DateUtils.createFirstDayOfYear(anno);
		Date stimaRischioDate = DateUtils.createDate(stimaRischio.getMese(), anno);
		int giorniDaInizioAnnoAPrimaStimaRischio = calcDifferenzaInGiorni(stimaRischioDate, inizioAnnoDate);
		accRischio += stimaRischio.getRischio() * giorniDaInizioAnnoAPrimaStimaRischio;
		
		stimaRischio = stimeRischioList.get(stimeRischioList.size() - 1);
		stimaRischioDate = DateUtils.createDate(stimaRischio.getMese(), anno);
		Date inizioAnnoSuccessivoDate = DateUtils.createFirstDayOfYear(anno + 1);
		int giorniDaUltimaStimaRischioAFineAnno = calcDifferenzaInGiorni(inizioAnnoSuccessivoDate, stimaRischioDate);
		accRischio += stimaRischio.getRischio() * giorniDaUltimaStimaRischioAFineAnno;
		
		int pesoCorrente = 0;
		for (int i = 1; i < stimeRischioList.size(); i++) {
			StimaRischio prec = stimeRischioList.get(i-1);
			StimaRischio corr = stimeRischioList.get(i);
			
			Date dataPrec = DateUtils.createDate(prec.getMese(), prec.getAnno());			
			Date dataCorr = DateUtils.createDate(corr.getMese(), corr.getAnno());
			
			pesoCorrente = calcDifferenzaInGiorni(dataCorr, dataPrec);
			accRischio += prec.getRischio() * pesoCorrente;
		}
		
		int giorniTotali = calcDifferenzaInGiorni(inizioAnnoSuccessivoDate, inizioAnnoDate);
		int rischioMedio = (int) Math.round(((double)accRischio) / giorniTotali);
		String testo = getTesto(rischioMedio);

		return new StimaSintetica(anno, citta, rischioMedio, testo);
	}
	
	private int calcDifferenzaInGiorni(Date d1, Date d2) {
		return (int) ((d1.getTime() - d2.getTime()) / MILLIS_PER_DAY);
	}
	
	private boolean checkStessaCittaAnnoDistinctMese(Collection<StimaRischio> previsioni) {
		String citta = null;
		int anno = 0;
		HashSet<Month> monthSet = new HashSet<>();
		for (StimaRischio stimaRischio : previsioni) {
			String currentCitta = stimaRischio.getCitta(); 
			if (citta == null) {
				citta = currentCitta;
			} else if (!citta.equals(currentCitta)) {
				return false;
			}
			
			int currentAnno = stimaRischio.getAnno(); 
			if (anno == 0) {
				anno = currentAnno;
			} else if (anno != currentAnno) {
				return false;
			}
			
			if (monthSet.contains(stimaRischio.getMese())) {
				return false;
			}
			monthSet.add(stimaRischio.getMese());
		}
		
		return true;
	}

	private String getTesto(int rischio) {
		if (rischio < 3) {
			return "rischio zero";
		} else if (rischio < 10) {
			return "rischio minimo";
		} else if (rischio < 25) {
			return "rischio medio-basso";
		} else if (rischio < 50) {
			return "rischio medio";
		} else if (rischio < 75) {
			return "alto rischio";
		} else  {
			return "altissimo rischio";
		}
	}

}
