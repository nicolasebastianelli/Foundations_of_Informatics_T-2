package pharmame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyFarmacoFilterFactory implements FarmacoFilterFactory {

	private final String PrincipioAttivo = "Principio Attivo";
	private final String GruppoEquivalenza = "Gruppo Equivalenza";
	private final String Nome = "Nome";

	private final List<String> filterNames = Arrays.asList(PrincipioAttivo, GruppoEquivalenza, Nome);

	@Override
	public Filter<Farmaco> get(String name, String searchKey) {
		switch (name) {
		case PrincipioAttivo:
			return new PrincipioAttivoFilter(searchKey);
		case GruppoEquivalenza:
			return new GruppoEquivalenzaFilter(searchKey);
		case Nome:
			return new NomeFilter(searchKey);
		default:
			throw new IllegalArgumentException("name");
		}
	}

	@Override
	public Collection<String> getNames() {
		return new ArrayList<>(filterNames);
	}

}
