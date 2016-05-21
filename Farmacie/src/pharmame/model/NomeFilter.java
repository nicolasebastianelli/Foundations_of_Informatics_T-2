package pharmame.model;

public class NomeFilter extends FarmacoFilterBase {

	public NomeFilter(String key) {
		super(key);
	}

	@Override
	protected String getValue(Farmaco f) {
		return f.getNome();
	}

}
