package pharmame.model;

import java.text.NumberFormat;
import java.util.Locale;

public class Farmaco {
	private static final NumberFormat CurrencyFormat = NumberFormat.getCurrencyInstance(Locale.ITALY);
	
	private int codice;
	private String principioAttivo; 
	private String gruppoEquivalenza; 
	private String nome;
	private String confezione; 
	private float prezzo;
	private String ditta;
	
	public Farmaco(int codice, String principioAttivo, String descrGruppoEquivalenza, String nome, String confezione,
			float prezzo, String ditta) {
		this.codice = codice;
		this.principioAttivo = principioAttivo;
		this.gruppoEquivalenza = descrGruppoEquivalenza;
		this.nome = nome;
		this.confezione = confezione;
		this.prezzo = prezzo;
		this.ditta = ditta;
	}

	public int getCodice() {
		return codice;
	}

	public String getPrincipioAttivo() {
		return principioAttivo;
	}

	public String getGruppoEquivalenza() {
		return gruppoEquivalenza;
	}

	public String getNome() {
		return nome;
	}

	public String getConfezione() {
		return confezione;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public String getDitta() {
		return ditta;
	}
	
	@Override
	public String toString() {
		return "" + codice + " - " + nome + "; " + confezione + "; " + 
				principioAttivo + "; " + gruppoEquivalenza + "; " +
				CurrencyFormat.format(prezzo) + "; " + ditta;
	}

}
