package trains.tests;

import java.io.IOException;
import java.io.StringReader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;
import static org.junit.Assert.*;

import org.junit.Test;

import trains.model.Operator;
import trains.model.Station;
import trains.model.Train;
import trains.persistence.BadFileFormatException;
import trains.persistence.MyTrainReader;
import trains.persistence.TrainReader;

public class MyTrainReaderTest {
	
	@Test
	public void testReadAll() throws IOException, BadFileFormatException {
		Map<String, Station> stationMap = TestData.getStationMap();

		String treni = "Z9902	12345	BO00/--/6.18,RE01/6.41/6.43,MI02/7.21/7.23,MI01/7.41/7.44,MI04/7.53/7.55,TO01/8.33/--"
				+ System.lineSeparator()
				+ "S9570	123456	BO00/--/11.23,RE01/11.42/11.44,MI02/12.22/12.24,MI01/12.43/12.45,MI04/12.55/12.57,TO01/13.35/13.37,TO00/13.45/--";

		TrainReader trainReader = new MyTrainReader(stationMap);
		Collection<Train> trains = trainReader.readTrains(new StringReader(
				treni));

		Map<Long, Train> trainMap = trains.stream().collect(
				toMap(s -> s.getNumber(), identity()));
		
		assertEquals(2, trainMap.size());

		assertTrue(trainMap.keySet().contains(9902L));
		assertTrue(trainMap.keySet().contains(9570L));

		Train t1 = trainMap.get(9902L);
		int t1stops = t1.getStops().size();
		Train t2 = trainMap.get(9570L);
		int t2stops = t2.getStops().size();
		DayOfWeek[] weekdays = new DayOfWeek[] { DayOfWeek.MONDAY,
				DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
				DayOfWeek.FRIDAY };
		DayOfWeek[] feriali = new DayOfWeek[] { DayOfWeek.MONDAY,
				DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
				DayOfWeek.FRIDAY, DayOfWeek.SATURDAY };

		assertEquals(Operator.Z, t1.getOperator());
		assertEquals(Operator.S, t2.getOperator());
		assertArrayEquals(weekdays, t1.getDays());
		assertArrayEquals(feriali, t2.getDays());

		assertEquals(6, t1stops);
		assertEquals(7, t2stops);

		assertTrue(t1.getStops().get(0).getStation().getCityName()
				.equalsIgnoreCase("Bologna"));
		assertTrue(t1.getStops().get(0).getStation().getStationName()
				.equalsIgnoreCase("Centrale"));
		assertTrue(t2.getStops().get(0).getStation().getCityName()
				.equalsIgnoreCase("Bologna"));
		assertTrue(t2.getStops().get(0).getStation().getStationName()
				.equalsIgnoreCase("Centrale"));
		assertTrue(t1.getStops().get(t1stops - 1).getStation().getCityName()
				.equalsIgnoreCase("Torino"));
		assertTrue(t1.getStops().get(t1stops - 1).getStation().getStationName()
				.equalsIgnoreCase("P.S."));
		assertTrue(t2.getStops().get(t2stops - 1).getStation().getCityName()
				.equalsIgnoreCase("Torino"));
		assertTrue(t2.getStops().get(t2stops - 1).getStation().getStationName()
				.equalsIgnoreCase("P.N."));

		assertEquals(LocalTime.of(8, 33), t1.getStops().get(t1stops - 1)
				.getArrivalTime().get());
		assertEquals(LocalTime.of(13, 45), t2.getStops().get(t2stops - 1)
				.getArrivalTime().get());
	}

	// @Test(expected = BadFileFormatException.class)
	// public void testReadAll_Proto() throws IOException,
	// BadFileFormatException {
	// SortedMap<String, List<Station>> stationMap = getCityStationMap();
	//
	// String treni =
	// "Z9902	12345	BO00/--/6.18,RE01/6.41/6.43,MI02/7.21/7.23,MI01/7.41/7.44,MI04/7.53/7.55,TO01/8.33/--"
	// + System.lineSeparator() +
	// "S9570	123456	BO00/--/11.23,RE01/11.42/11.44,MI02/12.22/12.24,MI01/12.43/12.45,MI04/12.55/12.57,TO01/13.35/13.37,TO00/13.45/--";
	//
	// TrainReader trainReader = new
	// MyTrainReader(Program.getStationSet(stationMap));
	// trainReader.readTrains(new StringReader(treni));
	// }

	@Test(expected = BadFileFormatException.class)
	public void testReadAll_NotEnoughTokensInStation() throws IOException,
			BadFileFormatException {
		Map<String, Station> stationMap = TestData.getStationMap();

		String treni = "Z9902	12345	BO00/--/6.18,RE01/6.41,MI02/7.21/7.23,MI01/7.41/7.44,MI04/7.53/7.55,TO01/8.33/--"
				+ System.lineSeparator()
				+ "S9570	123456	BO00/--/11.23,RE01/11.42/11.44,MI02/12.22/12.24,MI01/12.43/12.45,MI04/12.55/12.57,TO01/13.35/13.37,TO00/13.45/--";

		TrainReader trainReader = new MyTrainReader(stationMap);
		trainReader.readTrains(new StringReader(treni));
	}

	@Test(expected = BadFileFormatException.class)
	public void testReadAll_WrongTime() throws IOException,
			BadFileFormatException {
		Map<String, Station> stationMap = TestData.getStationMap();

		String treni = "Z9902	12345	BO00/--/6.18,RE01/6.X1/6.43,MI02/7.21/7.23,MI01/7.41/7.44,MI04/7.53/7.55,TO01/8.33/--"
				+ System.lineSeparator()
				+ "S9570	123456	BO00/--/11.23,RE01/11.42/11.44,MI02/12.22/12.24,MI01/12.43/12.45,MI04/12.55/12.57,TO01/13.35/13.37,TO00/13.45/--";

		TrainReader trainReader = new MyTrainReader(stationMap);
		trainReader.readTrains(new StringReader(treni));
	}

	@Test(expected = BadFileFormatException.class)
	public void testReadAll_WrongTimeFormat() throws IOException,
			BadFileFormatException {
		Map<String, Station> stationMap = TestData.getStationMap();

		String treni = "Z9902	12345	BO00/--/6.18,RE01/6:41/6.43,MI02/7.21/7.23,MI01/7.41/7.44,MI04/7.53/7.55,TO01/8.33/--"
				+ System.lineSeparator()
				+ "S9570	123456	BO00/--/11.23,RE01/11.42/11.44,MI02/12.22/12.24,MI01/12.43/12.45,MI04/12.55/12.57,TO01/13.35/13.37,TO00/13.45/--";

		TrainReader trainReader = new MyTrainReader(stationMap);
		trainReader.readTrains(new StringReader(treni));
	}

	@Test(expected = BadFileFormatException.class)
	public void testReadAll_NonExistingStation() throws IOException,
			BadFileFormatException {
		Map<String, Station> stationMap = TestData.getStationMap();

		String treni = "Z9902	12345	XX00/--/6.18,RE01/6.41/6.43,MI02/7.21/7.23,MI01/7.41/7.44,MI04/7.53/7.55,TO01/8.33/--"
				+ System.lineSeparator()
				+ "S9570	123456	BO00/--/11.23,RE01/11.42/11.44,MI02/12.22/12.24,MI01/12.43/12.45,MI04/12.55/12.57,TO01/13.35/13.37,TO00/13.45/--";

		TrainReader trainReader = new MyTrainReader(stationMap);
		trainReader.readTrains(new StringReader(treni));
	}

	@Test(expected = BadFileFormatException.class)
	public void testReadAll_WrongDays() throws IOException,
			BadFileFormatException {
		Map<String, Station> stationMap = TestData.getStationMap();

		String treni = "Z9902	12345	BO00/--/6.18,RE01/6.41/6.43,MI02/7.21/7.23,MI01/7.41/7.44,MI04/7.53/7.55,TO01/8.33/--"
				+ System.lineSeparator()
				+ "S9570	990456	BO00/--/11.23,RE01/11.42/11.44,MI02/12.22/12.24,MI01/12.43/12.45,MI04/12.55/12.57,TO01/13.35/13.37,TO00/13.45/--";

		TrainReader trainReader = new MyTrainReader(stationMap);
		trainReader.readTrains(new StringReader(treni));
	}

	@Test(expected = BadFileFormatException.class)
	public void testReadAll_MissingOperator() throws IOException,
			BadFileFormatException {
		Map<String, Station> stationMap = TestData.getStationMap();

		String treni = "Z9902	12345	BO00/--/6.18,RE01/6.41/6.43,MI02/7.21/7.23,MI01/7.41/7.44,MI04/7.53/7.55,TO01/8.33/--"
				+ System.lineSeparator()
				+ "9570	123456	BO00/--/11.23,RE01/11.42/11.44,MI02/12.22/12.24,MI01/12.43/12.45,MI04/12.55/12.57,TO01/13.35/13.37,TO00/13.45/--";

		TrainReader trainReader = new MyTrainReader(stationMap);
		trainReader.readTrains(new StringReader(treni));
	}

}
