package pharmame.model;

public class GruppoEquivalenzaFilter extends FarmacoFilterBase {

	public GruppoEquivalenzaFilter(String key) {
		super(key);
	}

	@Override
	protected String getValue(Farmaco f) {
		return f.getGruppoEquivalenza();
	}

}
