package dentinia.model;

public class ValorePartito implements Comparable<ValorePartito> {

	private int valore;
	private Partito partito;

	public ValorePartito(int valore, Partito partito) {
		this.valore = valore;
		this.partito = partito;
	}
	
	public int getValore() {
		return valore;
	}
	
	public Partito getPartito() {
		return partito;
	}

	@Override
	public int compareTo(ValorePartito dp) {
		return dp.getValore() - getValore();
	}

}
