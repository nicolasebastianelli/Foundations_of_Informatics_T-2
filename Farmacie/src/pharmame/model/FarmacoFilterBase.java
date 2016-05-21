package pharmame.model;

public abstract class FarmacoFilterBase implements Filter<Farmaco> {

	private String key;

	public FarmacoFilterBase(String key) {
		this.key = key.toUpperCase();
	}

	@Override
	public boolean filter(Farmaco f) {
		return getValue(f)
				.toUpperCase()
				.contains(key);
	}

	protected abstract String getValue(Farmaco f);

}
