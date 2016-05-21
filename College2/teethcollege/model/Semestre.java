package teethcollege.model;

public enum Semestre {
	PRIMO(1), SECONDO(2);

	private int ciclo;

	Semestre(int i) {
		ciclo = i;
	}

	int getValue() {
		return ciclo;
	}

	public String toString() {
		return this == PRIMO ? "1°" : "2°";
	}
}
