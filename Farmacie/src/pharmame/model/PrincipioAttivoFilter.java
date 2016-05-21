package pharmame.model;

public class PrincipioAttivoFilter extends FarmacoFilterBase {
	
	public PrincipioAttivoFilter(String key) {
		super(key);
	}

	@Override
	protected String getValue(Farmaco f) {
		return f.getPrincipioAttivo();
	}

}
