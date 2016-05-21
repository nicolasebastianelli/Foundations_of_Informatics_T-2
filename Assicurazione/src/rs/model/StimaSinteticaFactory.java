package rs.model;

import java.util.Collection;

public interface StimaSinteticaFactory {
	StimaSintetica create(Collection<StimaRischio> previsioni);
}
