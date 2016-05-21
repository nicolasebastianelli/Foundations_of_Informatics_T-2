package dentinia.model;

public class Partito implements Comparable<Partito> {

	private String nome;
	private int voti;
	private int seggi;
	
	public Partito(String nome, int voti) {
		this.nome = nome;
		this.voti = voti;
	}
	
	public int getVoti() {
		return voti;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getSeggi() {
		return seggi;
	}
	
	public void setSeggi(int seggi) {
		this.seggi = seggi;
	}
	
	public String toString(){
		return nome + ":" + " voti " + voti + ", seggi " + seggi;
	}

	public int compareTo(Partito that) {
		return that.getVoti()<this.getVoti() ? -1 :
			   that.getVoti()>this.getVoti() ? +1 : 
			   0; 
	}

}
