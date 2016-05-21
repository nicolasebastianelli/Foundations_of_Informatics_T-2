package trains.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

public class Train {

	private Operator operator;
	private long number;
	private DayOfWeek[] days;
	private List<Stop> stops;

	public Train(Operator operator, long number, DayOfWeek[] days, List<Stop> stops) {
		if(operator == null)
			throw new IllegalArgumentException("Operator");
		if(number < 0)
			throw new IllegalArgumentException("Number");
		if(days == null || days.length <= 0)
			throw new IllegalArgumentException("Days");
		if(stops == null ||stops.isEmpty())
			throw new IllegalArgumentException("Stops");
		this.operator = operator;
		this.number = number;
		this.days = days;
		this.stops = stops;
	}

	public Operator getOperator() {
		return operator;
	}

	public long getNumber() {
		return number;
	}

	public DayOfWeek[] getDays() {
		return days;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public List<Station> getServedStations() {
		List<Station> result = new ArrayList<>();

		for(Stop s : stops) {
			result.add(s.getStation());
		}
		return result;
	}

	public SortedSet<String> getServedCities() {
		SortedSet<String> result = new TreeSet<>();

		for(Station s : getServedStations()) {
			if(!result.contains(s.getCityName()))
				result.add(s.getCityName());
		}		
		return result;		
	}

	public Duration travelDuration(Station departureStation, Station arrivalStation) {
		if(departureStation == null || arrivalStation == null)
			throw new IllegalArgumentException("Null Stations");
		if(!getServedStations().contains(departureStation))
			throw new IllegalArgumentException("Invalid Departure Station");
		if(!getServedStations().contains(arrivalStation))
			throw new IllegalArgumentException("Invalid Arrival Station");
		Optional<LocalTime> departureTime = null;
		LocalTime departure;
		Optional<LocalTime> arrivalTime = null;
		LocalTime arrival;
		for(Stop s : stops) {
			if(departureStation.equals(s.getStation()))
				departureTime = s.getDepartureTime();
			if(arrivalStation.equals(s.getStation()))
				arrivalTime = s.getArrivalTime();
		}
		if(!departureTime.isPresent())
			throw new IllegalArgumentException("departureStation");
		else
			departure = departureTime.get();

		if(!arrivalTime.isPresent())
			throw new IllegalArgumentException("arrivalTime");
		else
			arrival = arrivalTime.get();

		if(!departure.isBefore(arrival))
			throw new IllegalArgumentException(
					"Orario di arrivo prima dell'orario di partenza");


		return Duration.between(departure, arrival);
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

		text.append(operator.getFullName() + " ");
		text.append(number + " ");

		Stop departure = stops.get(0);
		Stop arrival = stops.get(stops.size() - 1);
		
		String departureTime;
		if(departure.getDepartureTime().isPresent())
			departureTime = formatter.format(departure.getDepartureTime().get());
		else
			departureTime = "--";
		String arrivalTime;
		if(arrival.getArrivalTime().isPresent())
			arrivalTime = formatter.format(arrival.getArrivalTime().get());
		else
			arrivalTime = "--";

		text.append(departure.getStation().toString() + " ");
		text.append("(" + departureTime + ")");

		text.append("/ " + arrival.getStation().toString());
		text.append(" (" + arrivalTime + ")");

		return text.toString();		
	}

	public String toFullString() {
		StringBuilder text = new StringBuilder();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

		text.append(operator.getFullName() + " ");
		text.append(number + " da ");

		Stop departure = stops.get(0);
		Stop arrival = stops.get(stops.size() - 1);
		
		String departureTime;
		if(departure.getDepartureTime().isPresent())
			departureTime = formatter.format(departure.getDepartureTime().get());
		else
			departureTime = "--";
		String arrivalTime;
		if(arrival.getArrivalTime().isPresent())
			arrivalTime = formatter.format(arrival.getArrivalTime().get());
		else
			arrivalTime = "--";

		text.append(departure.getStation().toString() + " ");
		text.append("(" + departureTime + ")");

		text.append(" a " + arrival.getStation().toString());
		text.append(" (" + arrivalTime + ")");
		
		text.append(", circola " + printDayOfWeek() + "\n");
		
		text.append("ferma a: " + printFermate());

		return text.toString();		
	}

	private String printDayOfWeek() {
		StringBuilder text = new StringBuilder();
		DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.ITALY);
		for(DayOfWeek d : days) {
			text.append(dayOfWeekFormatter.format(d));
			if(!d.equals(days[days.length - 1]))
				text.append("-");
		}
		return text.toString();
	}
	
	private String printFermate() {
		StringBuilder text = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		
		for(Stop s : stops) {
			text.append(s.getStation().toString() + " (");
			String departureTime;
			if(s.getDepartureTime().isPresent())
				departureTime = formatter.format(s.getDepartureTime().get());
			else
				departureTime = "--";
			String arrivalTime;
			if(s.getArrivalTime().isPresent())
				arrivalTime = formatter.format(s.getArrivalTime().get());
			else
				arrivalTime = "--";
			text.append(arrivalTime + "/");
			text.append(departureTime + ")");
			if(!s.equals(stops.get(stops.size() - 1)))
				text.append(", ");
		}
		return text.toString();
	}
}
