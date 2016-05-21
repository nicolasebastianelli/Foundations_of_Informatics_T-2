package dentorestaurant.model;

import java.util.HashMap;
import java.util.Map;

public class Ordine {

	private String nomeCliente;
	private Menu menu;
	private Map<Categoria, Portata> elencoPortate;

	public Ordine(Menu menu, String nomeCliente) {
		if (menu == null)
			throw new IllegalArgumentException("menu");
		if (nomeCliente == null || nomeCliente.trim().isEmpty())
			throw new IllegalArgumentException("nome cliente mancante");
		this.nomeCliente = nomeCliente;
		this.menu = menu;
		this.elencoPortate = new HashMap<Categoria, Portata>();
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public Menu getMenu() {
		return menu;
	}

	public void aggiungiPortata(Portata portata) {
		if (elencoPortate.get(portata.getCategoria()) != null)
			throw new IllegalArgumentException(
					"La categoria ha già una portata");

		elencoPortate.put(portata.getCategoria(), portata);
	}

	public boolean isValid() {
		for (Categoria c : Categoria.values())
			if (elencoPortate.get(c) == null)
				return false;
		return true;
	}

	public Map<Categoria, Portata> getElencoPortate() {
		return elencoPortate;
	}

	public double getPrezzoTotale() {
		double somma = 0;
		for (Portata i : elencoPortate.values())
			somma += i.getPrezzo();
		return somma;
	}

	public String toString() {
		return menu.toString() + " PER " + nomeCliente;
	}

	public String toFullString() {
		StringBuilder sb = new StringBuilder();
		sb.append(toString() + System.getProperty("line.separator"));
		for (Categoria c : Categoria.values())
			sb.append(c + ": " + elencoPortate.get(c));
		sb.append("Prezzo totale: € " + this.getPrezzoTotale());
		return sb.toString();
	}

	public void sostituisciPortata(Portata corrente, Portata futuro)
			throws IllegalArgumentException {
		if (!elencoPortate.get(corrente.getCategoria()).equals(corrente))
			throw new IllegalArgumentException(
					"Portata non presente nell'ordine attuale");
		if (corrente.getCategoria() != futuro.getCategoria())
			throw new IllegalArgumentException(
					"Impossibile inserire una portata di categoria diversa");
		if (!menu.getPortate(corrente.getCategoria()).contains(futuro))
			throw new IllegalArgumentException(
					"Portata non presente nel menù di riferimento");
		elencoPortate.put(corrente.getCategoria(), futuro);
	}
}
