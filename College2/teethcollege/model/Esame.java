package teethcollege.model;

import java.text.DateFormat;
import java.util.Date;

public class Esame implements Comparable<Esame> {

	private Insegnamento insegnamento;
	private int voto;
	private Date data;
	DateFormat formatter;
	
	public Esame(Insegnamento insegnamento, int voto, Date data) {
		if (voto<1 || voto>31) throw new IllegalArgumentException("voto " + voto +" non ammesso");
		this.insegnamento = insegnamento;
		this.voto= voto;
		this.data = data;
		formatter = DateFormat.getDateInstance(DateFormat.SHORT);
	}

	public long getCodice() {
		return insegnamento.getCodice();
	}
	
	public Insegnamento getInsegnamento() {
		return insegnamento;
	}

	public int getValoreVoto() {
		return (voto==31 ? 30 : voto);
	}

	public String getVoto() {
		return (voto==31 ? "30L" : ""+voto);
	}

	public Date getDate() {
		return data;
	}

	public String toString() {
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
		return insegnamento.getNome() + "\t\t Voto: " + getVoto() + "\t Data: " + formatter.format(data);
	}

	public String toCanonicalString() {
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
		return "" + getCodice() + ", " + voto + ", " + formatter.format(data);
	}

	public int compareTo(Esame that) {
		return this.data.compareTo(that.data);
	}

}
