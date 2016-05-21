package doc.model;

import java.io.Serializable;
import java.util.Collection;

public class DefaultDentoStrategia implements Serializable, DentoStrategia {
 
	private static final long serialVersionUID = 1L;

	@Override
	public String getNome() {
		return "";
	}
	
	@Override
	public float calcolaSconto(Collection<Acquisto> acquisti, Cliente cliente) {
		return 0;
	}

}
