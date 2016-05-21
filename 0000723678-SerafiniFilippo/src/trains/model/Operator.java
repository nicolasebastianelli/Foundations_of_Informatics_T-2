package trains.model;

public enum Operator {
	S("Sferraglia"), Z("ZannoRails");

	private String fullName;

	private Operator(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}
}
