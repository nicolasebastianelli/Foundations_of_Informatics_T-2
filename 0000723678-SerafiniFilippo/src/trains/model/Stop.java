package trains.model;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

public class Stop {
	private Station station = null;
	private Optional<LocalTime> arrivalTime = null;
	private Optional<LocalTime> departureTime = null;

	public Stop(Station station, Optional<LocalTime> arrival,
			Optional<LocalTime> departure) {
		if (station == null || arrival == null || departure == null) {
			throw new IllegalArgumentException(
					"Null arguments in Stop constructor");
		}
		if (!arrival.isPresent() && !departure.isPresent()) {
			throw new IllegalArgumentException(
					"At least one time needed in Stop constructor");
		}
		this.station = station;
		this.arrivalTime = arrival;
		this.departureTime = departure;
	}

	public Station getStation() {
		return station;
	}

	public Optional<LocalTime> getArrivalTime() {
		return arrivalTime;
	}

	public Optional<LocalTime> getDepartureTime() {
		return departureTime;
	}

	@Override
	public String toString() {
		return station.getStationName()
				+ ", "
				+ (arrivalTime.isPresent() ? arrivalTime.get().toString()
						+ ", " : "--")
				+ (departureTime.isPresent() ? departureTime.get().toString()
						+ ", " : "--") + System.getProperty("line.separator");
	}
	
	public static Optional<Stop> searchByStation(Collection<Stop> stops, Station station) {
		for (Stop stop : stops) {
			if (stop.getStation().equals(station)) {
				return Optional.of(stop);
			}
		}
		return Optional.empty();
	}

	public static Optional<Stop> searchByFullStationName(Collection<Stop> stops,
			String name) {
		for (Stop stop : stops) {
			if (stop.getStation().toString().equalsIgnoreCase(name))
				return Optional.of(stop);
		}
		return Optional.empty();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Stop)) {
			return false;
		}
		Stop that = (Stop) o;
		return this.getArrivalTime().equals(that.getArrivalTime())
				&& this.getDepartureTime().equals(that.getDepartureTime())
				&& this.getStation().equals(that.getStation());
	}
}
