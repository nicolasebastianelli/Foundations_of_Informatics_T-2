package doc.model;

import java.io.Serializable;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nome;

	public Cliente(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Cliente) {
			Cliente otherCliente = (Cliente) other;
			return getId().equals(otherCliente.getId()) && getNome().equals(otherCliente.getNome());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", id, nome);
	}
}
