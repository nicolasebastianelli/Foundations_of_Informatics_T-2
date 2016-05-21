package meteodent.model;

import java.util.Collection;

public interface AnnuncioFactory {
	Annuncio crea(Collection<Previsione> previsioni);
}
