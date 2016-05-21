package doc.model;

import java.util.Collection;

public interface DentoStrategia {
	String getNome();
	float calcolaSconto(Collection<Acquisto> acquisti, Cliente cliente);
}
