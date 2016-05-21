package zannotaxi.model;

public class SimpleTime {
	private int ore, minuti;

	public SimpleTime(int ore, int minuti) {
		if (ore<0 || ore > 23 || minuti < 0 || minuti>59) throw new IllegalArgumentException("orario assurdo: " + ore + ":" + minuti);
		this.ore = ore;
		this.minuti = minuti;
	}

	public SimpleTime(String orario){
		String[] hm = orario.split(":");
		this.ore = Integer.parseInt(hm[0]);
		this.minuti  = Integer.parseInt(hm[1]);
	}
	
	public int getOre() {
		return ore;
	}

	public int getMinuti() {
		return minuti;
	}

	@Override
	public String toString() {
		return (ore<10 ? "0" : "") + ore + ":" + (minuti<10 ? "0" : "") + minuti;
	}
	
	
}
