package rs.model;

public class StimaRischio implements Comparable<StimaRischio> {

	private String citta;
	private Month mese;
	private int anno;
	private int rischio;

	public StimaRischio(String citta, Month mese, int anno, int rischio) {
		this.citta = citta;
		this.mese = mese;
		this.anno = anno;
		this.rischio = rischio;
	}

	public String getCitta() {
		return citta;
	}

	public Month getMese() {
		return mese;
	}
	
	public int getAnno() {
		return anno;
	}

	public int getRischio() {
		return rischio;
	}
	
	@Override
	public String toString() {
		return  "" + getMese().toConventionalMonth() + "/" + getAnno() + " - Rischio " + getRischio() + "%";
	}

	@Override
	public int compareTo(StimaRischio o) {
		int diff = getAnno() - o.getAnno();
		return diff != 0 ? diff : getMese().toConventionalMonth() - o.getMese().toConventionalMonth();
	}
}
