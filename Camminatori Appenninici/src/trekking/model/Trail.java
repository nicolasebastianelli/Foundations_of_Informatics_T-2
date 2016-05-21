package trekking.model;

public class Trail {

	private TrailHead head;
	
	private TrailHead end;
	
	private String name;
	
	private double length;
	
	private Difficulty difficulty;
	
	public Trail(TrailHead partenza, TrailHead arrivo, Difficulty difficolta) {
		this.head = partenza;
		this.end = arrivo;
		this.difficulty = difficolta;
	}

	public TrailHead getTrailHead() {
		return head;
	}

	public TrailHead getTrailEnd() {
		return end;
	}
	
	public double getAltitudeDifference() {
		return getMaxAltitude() - getMinAltitude();	
	}
	
	public double getMinAltitude() {
		return end.getAltitude() >= head.getAltitude() ?
				head.getAltitude() : end.getAltitude();
	}
	
	public double getMaxAltitude() {
		return end.getAltitude() >= head.getAltitude() ?
				end.getAltitude() : head.getAltitude();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double lunghezza) {
		this.length = lunghezza;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Trail))
			return false;
		Trail altro = (Trail)o;
		boolean uguali = true;
		uguali = uguali &&
				head.equals(altro.getTrailHead());
		uguali = uguali &&
				end.equals(altro.getTrailEnd());
		uguali = uguali &&
				name.equals(altro.getName());
		uguali = uguali &&
				head.equals(altro.getTrailHead());
		uguali = uguali &&
				Math.abs(length-altro.getLength())<0.01;
		uguali = uguali &&
				difficulty == altro.getDifficulty();
		return uguali;
	}
	
	@Override
	public String toString() {
		return this.getName() + "; " + this.getTrailHead() + "; " + this.getTrailEnd();
	}
}
