package trains.tests;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import trains.model.Operator;
import trains.model.Station;
import trains.model.Stop;
import trains.model.Train;

public final class TestData {

	public static Collection<Station> getStationCollection() {
		List<Station> stations = Arrays.asList(new Station("FIRENZE", "S.M.N.",
				"FI01"), new Station("FIRENZE", "Campo Marte", "FI02"),
				new Station("FIRENZE", "Rifredi", "FI03"), new Station(
						"VENEZIA", "S.Lucia", "VE01"), new Station("VENEZIA",
						"Mestre", "VE02"), new Station("BOLOGNA", "Centrale",
						"BO00"), new Station("TORINO", "P.N.", "TO00"),
				new Station("TORINO", "P.S.", "TO01"), new Station(
						"REGGIO EMILIA", "C.le", "RE00"), new Station(
						"REGGIO EMILIA", "AV Mediopadana", "RE01"),
				new Station("MILANO", "C.le", "MI00"), new Station("MILANO",
						"Porta Garibaldi", "MI01"), new Station("MILANO",
						"Rogoredo", "MI02"), new Station("MILANO", "Lambrate",
						"MI03"),
				new Station("MILANO", "Rho Fiera Expo", "MI04"));
		return stations;
	}
	
	public static Map<String, Station> getStationMap() {
		Map<String, Station> map = TestData.getStationCollection().stream().collect(
				toMap(s -> s.getId(), identity()));

		return map;
	}
	
	public static Collection<Train> getTrainCollection() {
		DayOfWeek[] onlyWeekEnds = new DayOfWeek[] {DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
		DayOfWeek[] onlyMondays = new DayOfWeek[] {DayOfWeek.MONDAY};
		DayOfWeek[] allDays = DayOfWeek.values();
		
		Map<String, Station> stationMap = getStationMap();
		
		List<Train> trains = Arrays.asList(
				new Train(Operator.Z, 12345, allDays, 
						Arrays.asList(
								new Stop(stationMap.get("FI01"), 
										Optional.empty(),
										Optional.of(LocalTime.of(8, 0))),
								new Stop(stationMap.get("BO00"), 
												Optional.of(LocalTime.of(8, 59)),
												Optional.of(LocalTime.of(9, 0))),
								new Stop(stationMap.get("MI00"), 
												Optional.of(LocalTime.of(9, 59)),
												Optional.of(LocalTime.of(10, 0))),
								new Stop(stationMap.get("TO00"), 
												Optional.of(LocalTime.of(10, 59)),
												Optional.empty())
								)
						),
				new Train(Operator.Z, 65432, allDays, 
						Arrays.asList(
								new Stop(stationMap.get("TO00"), 
										Optional.empty(),
										Optional.of(LocalTime.of(8, 30))),
								new Stop(stationMap.get("MI00"), 
												Optional.of(LocalTime.of(9, 29)),
												Optional.of(LocalTime.of(9, 30))),
								new Stop(stationMap.get("BO00"), 
												Optional.of(LocalTime.of(10, 29)),
												Optional.of(LocalTime.of(10, 30))),
								new Stop(stationMap.get("FI01"), 
												Optional.of(LocalTime.of(11, 29)),
												Optional.empty())
								)
						),
				new Train(Operator.Z, 112345, onlyMondays, 
						Arrays.asList(
								new Stop(stationMap.get("FI01"), 
										Optional.empty(),
										Optional.of(LocalTime.of(8, 0))),
								new Stop(stationMap.get("BO00"), 
												Optional.of(LocalTime.of(8, 59)),
												Optional.of(LocalTime.of(9, 0))),
								new Stop(stationMap.get("MI00"), 
												Optional.of(LocalTime.of(9, 59)),
												Optional.of(LocalTime.of(10, 0))),
								new Stop(stationMap.get("TO00"), 
												Optional.of(LocalTime.of(10, 59)),
												Optional.empty())
								)
						),
				new Train(Operator.Z, 165432, onlyWeekEnds, 
						Arrays.asList(
								new Stop(stationMap.get("TO00"), 
										Optional.empty(),
										Optional.of(LocalTime.of(8, 30))),
								new Stop(stationMap.get("MI00"), 
												Optional.of(LocalTime.of(9, 29)),
												Optional.of(LocalTime.of(9, 30))),
								new Stop(stationMap.get("BO00"), 
												Optional.of(LocalTime.of(10, 29)),
												Optional.of(LocalTime.of(10, 30))),
								new Stop(stationMap.get("FI01"), 
												Optional.of(LocalTime.of(11, 29)),
												Optional.empty())
								)
						),
				new Train(Operator.Z, 165432, allDays, 
						Arrays.asList(
								new Stop(stationMap.get("FI02"), 
										Optional.empty(),
										Optional.of(LocalTime.of(8, 30))),
								new Stop(stationMap.get("MI01"), 
												Optional.of(LocalTime.of(9, 29)),
												Optional.of(LocalTime.of(9, 30)))
								)
						)
			);
		
		return trains;
	}
}
