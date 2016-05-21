package compromeglio.model;

import java.text.DateFormat;
import java.util.Date;

public class Rilevazione {

	private Bene bene;
	private Date data;
	private String luogo;
	private float prezzo;
	
	public Rilevazione(Bene bene, String luogo, Date data, float prezzo) {
		this.bene = bene;
		this.luogo= luogo;
		this.data = data;
		this.prezzo = prezzo;
	}

	public long getCodiceBene() {
		return bene.getCodice();
	}
	
	public Date getDate() {
		return data;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public String getLuogo(){
		return luogo;
	}
	
	public String toFullString() {
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
		return bene.getDescrizione() + "\r\nPresso: " + getLuogo() + "\r\nData: " + formatter.format(data) + "\r\nPrezzo EUR" + getPrezzo();
	}

	public String toString() {
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
		return "" + getCodiceBene() + ", " + getLuogo() + ", " + formatter.format(data) + ", EUR" + getPrezzo();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Rilevazione)) return false;
		Rilevazione that = (Rilevazione)o;
		return 	this.getCodiceBene()==that.getCodiceBene() && 
				this.getDate().equals(that.getDate()) &&
				this.getLuogo().equals(that.getLuogo()) &&
				Math.abs(this.getPrezzo()-that.getPrezzo())<0.01;
	}
	
	@Override
	public int hashCode() {
		  return 0;
	}

}
