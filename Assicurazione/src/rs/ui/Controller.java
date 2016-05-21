package rs.ui;

import java.util.Collection;

import rs.model.StimaSintetica;
import rs.model.StimaRischio;

public interface Controller {
	Collection<String> getCitta();
	Collection<Integer> getAnniPerCitta(String localita);
	Collection<StimaRischio> getStimeRischio(String localita, int anno);
	StimaSintetica getStimaSintetica(String localita, int anno);
}
