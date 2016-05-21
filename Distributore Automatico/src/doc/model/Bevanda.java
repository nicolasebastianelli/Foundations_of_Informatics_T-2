package doc.model;

import java.io.Serializable;
import java.util.Collection;

public class Bevanda implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final DefaultDentoStrategia NessunaStrategia = new DefaultDentoStrategia();
	
	private String nome;
	private float prezzoMin;
	private float prezzoBase;
	private float prezzoMax;
	private DentoStrategia strategia;
	
	public Bevanda(String nome, float prezzoMin, float prezzoBase, float prezzoMax) {
		if (nome == null || nome.trim().isEmpty())
			throw new IllegalArgumentException("nome");
		if (prezzoMin < 0 || prezzoBase < 0 || prezzoMax < 0)
			throw new IllegalArgumentException("I prezzi devono essere positivi o nulli");
		if (!(prezzoMin <= prezzoBase && prezzoBase <= prezzoMax))
			throw new IllegalArgumentException("I prezzi devono essere in sequenza corretta");
		
		this.nome = nome;
		this.prezzoMin = prezzoMin;
		this.prezzoBase = prezzoBase;
		this.prezzoMax = prezzoMax;
	}

	public String getNome() {
		return nome;
	}
	
	public float getPrezzoMin() {
		return prezzoMin;
	}

	public float getPrezzoBase() {
		return prezzoBase;
	}
	
	public float getPrezzoMax() {
		return prezzoMax;
	}
	
	public void setStrategia(DentoStrategia strategia) {
		this.strategia = strategia;
	}
	
	public DentoStrategia getStrategia() {
		return strategia != null ? strategia : NessunaStrategia;
	}
	
	public float getPrezzo(Collection<Acquisto> acquisti, Cliente cliente) {
		float sconto = getStrategia().calcolaSconto(acquisti, cliente);
		float prezzo = getPrezzoBase() - sconto * getPrezzoBase();
		if (prezzo < prezzoMin) {
			prezzo = prezzoMin;
		} else if (prezzo > prezzoMax) {
			prezzo = prezzoMax;
		}
		return prezzo;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
