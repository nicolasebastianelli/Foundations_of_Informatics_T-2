package trekking.model;

public class TrailHead {

	private String name;
	
	private double altitude;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitudine) {
		this.altitude = altitudine;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof TrailHead))
			return false;
		else {
			TrailHead altra = (TrailHead)o;
			return altra.getAltitude() == this.getAltitude() &&
					altra.getName().equals(this.getName());
		}
	}
	
	@Override
	public String toString()
	{
		return this.getName() + " (" + this.getAltitude() + ")"; 
	}
}
