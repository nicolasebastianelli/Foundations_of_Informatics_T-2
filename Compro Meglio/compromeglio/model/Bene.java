package compromeglio.model;

import java.util.Arrays;

public class Bene implements Comparable<Bene> {

	private long codice;
	private String descrizione;
	private Categoria categoria;

	public Bene(long codice, String descrizione, Categoria categoria) {
		this.codice = codice;
		this.descrizione = descrizione;
		this.categoria = categoria;
	}

	public long getCodice() {
		return codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public String toString() {
		char[] trentaSpazi = new char[30];
		Arrays.fill(trentaSpazi, ' ');
		String nomeConSpazi = (descrizione + new String(trentaSpazi)).substring(0, 30);
		return codice + "\t" + categoria + "\t" + nomeConSpazi;
	}

	public int compareTo(Bene that) {
		return Long.compare(that.getCodice(), this.getCodice());
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Bene)) {
			return false;
		}
		Bene o = (Bene)other;
		return this.getCategoria() == o.getCategoria() &&
				this.getCodice() == o.getCodice() &&
				this.getDescrizione().equals(o.getDescrizione());
	}
	
	public int hashCode() {
		return new Long(codice).hashCode() + descrizione.hashCode() + categoria.hashCode();
	}


}
