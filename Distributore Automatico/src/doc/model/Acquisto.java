package doc.model;

import java.io.Serializable;
import java.util.Date;

public class Acquisto implements Serializable, Comparable<Acquisto> {
	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	private Bevanda bevanda;
	private Date data;
	private float prezzo;

	public Acquisto(Cliente cliente, Bevanda bevanda, Date data, float prezzo) {
		this.cliente = cliente;
		this.bevanda = bevanda;
		this.data = data;
		this.prezzo = prezzo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Bevanda getBevanda() {
		return bevanda;
	}

	public Date getData() {
		return data;
	}
	
	public float getPrezzo() {
		return prezzo;
	}

	@Override
	public int compareTo(Acquisto that) {
		return this.getData().compareTo(that.getData());
	}
}
