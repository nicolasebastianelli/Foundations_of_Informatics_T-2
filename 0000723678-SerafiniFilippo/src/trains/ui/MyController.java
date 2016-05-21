package trains.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import trains.model.Station;
import trains.model.Stop;
import trains.model.Train;

public class MyController implements Controller {
	
	private Collection<Station> stations;
	private Collection<Train> trains;
 
	public MyController(Collection<Station> stations, Collection<Train> trains) {
		if(stations == null || trains == null)
			throw new IllegalArgumentException("Argomenti passati nulli");
		
		this.stations = stations;
		this.trains = trains;
	}

	@Override
	public List<Train> trainServing(String station1, String station2) {
		List<Train> result = new ArrayList<Train>();
		
		for(Train t : trains) {
			Optional<Stop> stop1 = Stop.searchByFullStationName(t.getStops(), station1);
			if(stop1.isPresent()) {
				Optional<Stop> stop2 = Stop.searchByFullStationName(t.getStops(), station2);
				if(stop2.isPresent())
					result.add(t);
			}
		}
		return result;
	}

	@Override
	public List<Train> trainServing(String station1, String station2, LocalDate day) {
		List<Train> result = new ArrayList<Train>();
		DayOfWeek dayOfWeek;
		for(Train t : trainServing(station1, station2)) {
			dayOfWeek = day.getDayOfWeek();
			for(int i = 0; i < t.getDays().length; i++) {
				if(t.getDays()[i].equals(dayOfWeek)) {
					result.add(t);
					break;
				}
			}
		}
		return result;
	}

	@Override
	public SortedSet<String> getSortedStations() {
		SortedSet<String> result = new TreeSet<>();
		
		for(Station s : stations){
			result.add(s.toString());
		}
		
		return result;
	}

}
