package meteodent.model;

import java.util.Date;

public class Annuncio {

	private Date giorno;
	private String localita;
	private int probabilitaPioggia;
	private String testo;

	public Annuncio(Date giorno, String localita, int probabilitaPioggia, String testo) {
		this.giorno = giorno;
		this.localita = localita;
		this.probabilitaPioggia = probabilitaPioggia;
		this.testo = testo;
	}

	public Date getGiorno() {
		return giorno;
	}

	public String getLocalita() {
		return localita;
	}
	
	public int getProbabilitaPioggia() {
		return probabilitaPioggia;
	}
	
	public String getTesto() {
		return testo;
	}

}
