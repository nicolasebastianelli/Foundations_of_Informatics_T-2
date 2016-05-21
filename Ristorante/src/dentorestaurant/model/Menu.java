package dentorestaurant.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Menu {

	private String nome;
	private Map<Categoria, List<Portata>> mappaPortate;

	public Menu(String nome) {
		if (nome == null || nome.isEmpty())
			throw new IllegalArgumentException("nome menù vuoto");
		this.nome = nome;
		this.mappaPortate = new HashMap<Categoria, List<Portata>>();
		for (Categoria c : Categoria.values())
			mappaPortate.put(c, new ArrayList<Portata>());
	}

	public List<Portata> getPortate(Categoria c) {
		return mappaPortate.get(c);
	}

	public final Map<Categoria, List<Portata>> getPortate() {
		return mappaPortate;
	}

	public String getNome() {
		return nome;
	}

	public String toString() {
		return "MENU " + getNome();
	}

	public String toFullString() {
		StringBuilder sb = new StringBuilder();
		sb.append(toString() + System.getProperty("line.separator"));
		for (Categoria c : mappaPortate.keySet()) {
			sb.append(c.toString() + "\t");
			for (Portata p : getPortate(c))
				sb.append(p.toString() + System.getProperty("line.separator"));
		}
		return sb.toString();
	}

}
