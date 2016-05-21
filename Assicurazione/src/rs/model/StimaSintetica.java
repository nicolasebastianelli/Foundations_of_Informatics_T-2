package rs.model;

public class StimaSintetica {

	private int anno;
	private String citta;
	private int rischio;
	private String testo;

	public StimaSintetica(int anno, String citta, int rischio, String testo) {
		this.anno = anno;
		this.citta = citta;
		this.rischio = rischio;
		this.testo = testo;
	}

	public int getAnno() {
		return anno;
	}

	public String getCitta() {
		return citta;
	}
	
	public int getRischio() {
		return rischio;
	}
	
	public String getTesto() {
		return testo;
	}
	
	@Override
	public String toString() {
		return "Città: " + getCitta() + " - Anno: " + getAnno() + "\nRischio Furto: " + getRischio() + "%\n" + getTesto(); 
	}

}
