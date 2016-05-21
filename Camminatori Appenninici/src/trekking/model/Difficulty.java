package trekking.model;

public enum Difficulty {
	
	LOW(1),
	MEDIUM(2),
	HIGH(3),
	EXTREME(4);
	
	private int value;
	private Difficulty(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
