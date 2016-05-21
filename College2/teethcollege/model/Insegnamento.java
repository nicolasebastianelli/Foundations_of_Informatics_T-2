package teethcollege.model;

import java.util.Arrays;

public class Insegnamento implements Comparable<Insegnamento> {

	private long codice;
	private String nome, ssd;
	private int cfu;
	private Semestre semestre;
	private Categoria categoria;

	public Insegnamento(long codice, String nome, String ssd, int cfu, Semestre semestre, Categoria categoria) {
		this.codice = codice;
		this.nome = nome;
		this.ssd = ssd;
		this.cfu = cfu;
		this.semestre = semestre;
		this.categoria = categoria;
	}

	public long getCodice() {
		return codice;
	}

	public String getNome() {
		return nome;
	}

	public String getSsd() {
		return ssd;
	}

	public int getCfu() {
		return cfu;
	}

	public Semestre getSemestre() {
		return semestre;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public String toString() {
		char[] trentaSpazi = new char[30];
		Arrays.fill(trentaSpazi, ' ');
		String nomeConSpazi = (nome + new String(trentaSpazi)).substring(0, 30);
		return codice + "\t" + nomeConSpazi + "\t(" + ssd + ")\tCFU:" + cfu
				+ "\tCiclo: " + semestre + "\tTipologia: " + categoria;
	}

	public int compareTo(Insegnamento that) {
		return Long.compare(that.getCodice(), this.getCodice());
	}

}
