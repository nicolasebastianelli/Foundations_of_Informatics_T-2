package meteodent.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Previsione {

	private static final DateFormat currentDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ITALY);
	
	private String localita;
	private Date dataOra;
	private int temperatura;
	private int probabilitaPioggia;

	public Previsione(String localita, Date dataOra, int temperatura, int probabilitaPioggia) {
		this.localita = localita;
		this.dataOra = dataOra;
		this.temperatura = temperatura;
		this.probabilitaPioggia = probabilitaPioggia;
	}

	public String getLocalita() {
		return localita;
	}

	public Date getDataOra() {
		return dataOra;
	}

	public int getTemperatura() {
		return temperatura;
	}

	public int getProbabilitaPioggia() {
		return probabilitaPioggia;
	}
	
	@Override
	public String toString() {
		return currentDateFormat.format(getDataOra()) + " - " + "Temp. " + getTemperatura() + "° - Prob. Pioggia " + getProbabilitaPioggia() + "%";
	}
}
