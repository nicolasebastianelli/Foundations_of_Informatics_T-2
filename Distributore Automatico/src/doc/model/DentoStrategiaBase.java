package doc.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public abstract class DentoStrategiaBase  implements Serializable, DentoStrategia {

	private static final long serialVersionUID = 1L;
	
	private String nome;

	public DentoStrategiaBase(String nome) {
		if (nome == null || nome.trim().isEmpty())
			throw new IllegalArgumentException("nome");
		
		this.nome = nome;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public abstract float calcolaSconto(Collection<Acquisto> acquisti, Cliente cliente);

	protected Date getNow() {
		return new Date();
	}
}