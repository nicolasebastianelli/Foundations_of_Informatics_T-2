package galliacapocciona.model;

public class Partito implements Comparable<Partito> {

	private String nome;
	private int voti;
	
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
	
	public String toString(){
		return nome + " " + voti + ";";
	}

	public int compareTo(Partito that) {
		return that.getVoti()<this.getVoti() ? -1 :
			   that.getVoti()>this.getVoti() ? +1 : 
			   0; 
	}

	public boolean equals(Object that) {
		if (!(that instanceof Partito)) return false;
		return ((Partito)that).getVoti()==this.getVoti(); 
	}
	
	public int hashCode(){
		return nome.hashCode() + 31* voti;
	} 

}
