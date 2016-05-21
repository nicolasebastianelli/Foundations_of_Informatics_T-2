package dentorestaurant.model;

import java.text.NumberFormat;
import java.util.Arrays;

public class Portata implements Comparable<Portata> {

	private String codice;
	private String nome;
	private double prezzo;
	private Categoria categoria;

	public Portata(String codice, String nome, Categoria categoria,
			double prezzo) {
		this.codice = codice;
		this.nome = nome;
		this.prezzo = prezzo;
		this.categoria = categoria;
	}

	public String getCodice() {
		return codice;
	}

	public String getNome() {
		return nome;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public String toString() {
		char[] trentaSpazi = new char[30];
		Arrays.fill(trentaSpazi, ' ');
		String nomeConSpazi = (nome + new String(trentaSpazi)).substring(0, 30);
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		// return codice + "\t" + nomeConSpazi + "\tEuro: " +
		// formatter.format(prezzo);
		return nomeConSpazi + "\t" + formatter.format(prezzo);
	}

	public int compareTo(Portata that) {
		return that.getCodice().compareTo(this.getCodice());
	}

}
