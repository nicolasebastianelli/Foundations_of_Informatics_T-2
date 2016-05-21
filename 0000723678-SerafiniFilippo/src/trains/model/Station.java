package trains.model;

public class Station {
	private String name;
	private String city;
	private String id;

	public Station(String city, String name, String id) {
		this.name = name;
		this.city = city;
		this.id = id;
	}

	public String getStationName() {
		return name;
	}

	public String getCityName() {
		return city;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return getCityName() + " " + getStationName();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Station)) {
			return false;
		}
		Station that = (Station) o;
		return this.getId().equalsIgnoreCase(that.getId());
	}

}
