package trains.tests;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import trains.model.Operator;
import trains.model.Station;
import trains.model.Stop;
import trains.model.Train;

public class TrainTest {
	private static Station boCle;
	private static Station reAV;
	private static Station miCle;
	private static Station miRog;
	private static Station miGar;
	private static Station miRho;
	@SuppressWarnings("unused")
	private static Station toPN;
	private static Station toPS;

	@BeforeClass
	public static void initialSetUp() {
		boCle = new Station("Bologna", "Centrale", "BO00");
		reAV = new Station("Reggio Emilia", "Mediopadana", "RE01");
		miCle = new Station("Milano", "Centrale", "MI00");
		miGar = new Station("Milano", "Porta Garibaldi", "MI01");
		miRog = new Station("Milano", "Rogoredo", "MI02");
		miRho = new Station("Milano", "Rho Fiera", "MI04");
		toPN = new Station("Torino", "Porta Nuova", "TO00");
		toPS = new Station("Torino", "Porta Susa", "TO01");
	}

	private List<Stop> getStops() {
		return Arrays.asList(new Stop[] {
				new Stop(boCle, Optional.<LocalTime> empty(), Optional
						.of(LocalTime.of(6, 18))),
				new Stop(reAV, Optional.of(LocalTime.of(6, 41)), Optional
						.of(LocalTime.of(6, 43))),
				new Stop(miRog, Optional.of(LocalTime.of(7, 21)), Optional
						.of(LocalTime.of(7, 23))),
				new Stop(miGar, Optional.of(LocalTime.of(7, 41)), Optional
						.of(LocalTime.of(7, 44))),
				new Stop(miRho, Optional.of(LocalTime.of(7, 53)), Optional
						.of(LocalTime.of(7, 55))),
				new Stop(toPS, Optional.of(LocalTime.of(8, 33)), Optional
						.<LocalTime> empty()) });
	}

	private void checkWrongTrackException(Train train, Station s1, Station s2) {
		boolean ok = false;
		try {
			train.travelDuration(s1, s2);
		} catch (IllegalArgumentException e) {
			ok = true;
		}
		Assert.assertTrue(ok);
	}

	@Test
	public void testTravelDuration() {
		Train train = new Train(Operator.valueOf("Z"), 9902L,
				java.time.DayOfWeek.values(), getStops());

		assertEquals(Duration.ofMinutes(38), train.travelDuration(reAV, miRog));

		checkWrongTrackException(train, reAV, null);
		checkWrongTrackException(train, null, miRog);
		checkWrongTrackException(train, reAV, miCle);
	}

	@Test
	public void testGetType() {
		Train train = new Train(Operator.valueOf("Z"), 9902L,
				java.time.DayOfWeek.values(), getStops());
		assertEquals(Operator.valueOf("Z"), train.getOperator());
	}

	@Test
	public void testGetNumber() {
		Train train = new Train(Operator.valueOf("Z"), 9902L,
				java.time.DayOfWeek.values(), getStops());
		assertEquals(9902l, train.getNumber());
	}

	@Test
	public void testGetSetDays() {
		Train train = new Train(Operator.valueOf("Z"), 9902L,
				java.time.DayOfWeek.values(), getStops());
		assertEquals(7, train.getDays().length);
		assertEquals(DayOfWeek.MONDAY, train.getDays()[0]);
		assertEquals(DayOfWeek.WEDNESDAY, train.getDays()[2]);
		assertEquals(DayOfWeek.FRIDAY, train.getDays()[4]);
	}

	@Test
	public void testGetSetStops() {
		Train train = new Train(Operator.valueOf("Z"), 9902L,
				java.time.DayOfWeek.values(), getStops());
		assertEquals(6, train.getStops().size());
	}

	@Test
	public void testGetServedStations() {
		/*
		 * boCle = new Station("Bologna", "Centrale", "BO00"); reAV = new
		 * Station("Reggio Emilia", "Mediopadana", "RE01"); miCle = new
		 * Station("Milano", "Centrale", "MI00"); miGar = new Station("Milano",
		 * "Porta Garibaldi", "MI01"); miRog = new Station("Milano", "Rogoredo",
		 * "MI02"); miRho = new Station("Milano", "Rho Fiera", "MI04"); toPN =
		 * new Station("Torino", "Porta Nuova", "TO00"); toPS = new
		 * Station("Torino", "Porta Susa", "TO01"); new Stop(boCle,
		 * Optional.<LocalTime>empty(), Optional.of(LocalTime.of(6,18)) ), new
		 * Stop(reAV, Optional.of(LocalTime.of(6,41)),
		 * Optional.of(LocalTime.of(6,43)) ), new Stop(miRog,
		 * Optional.of(LocalTime.of(7,21)), Optional.of(LocalTime.of(7,23)) ),
		 * new Stop(miGar, Optional.of(LocalTime.of(7,41)),
		 * Optional.of(LocalTime.of(7,44)) ), new Stop(miRho,
		 * Optional.of(LocalTime.of(7,53)), Optional.of(LocalTime.of(7,55)) ),
		 * new Stop(toPS, Optional.of(LocalTime.of(8,33)),
		 * Optional.<LocalTime>empty() )
		 */

		Train train = new Train(Operator.valueOf("Z"), 9902L,
				java.time.DayOfWeek.values(), getStops());
		List<Station> stations = Arrays.asList(boCle, reAV, miRog, miGar,
				miRho, toPS);
		assertEquals(stations, train.getServedStations());
	}

	@Test
	public void testGetServedCities() {
		/*
		 * boCle = new Station("Bologna", "Centrale", "BO00"); reAV = new
		 * Station("Reggio Emilia", "Mediopadana", "RE01"); miCle = new
		 * Station("Milano", "Centrale", "MI00"); miGar = new Station("Milano",
		 * "Porta Garibaldi", "MI01"); miRog = new Station("Milano", "Rogoredo",
		 * "MI02"); miRho = new Station("Milano", "Rho Fiera", "MI04"); toPN =
		 * new Station("Torino", "Porta Nuova", "TO00"); toPS = new
		 * Station("Torino", "Porta Susa", "TO01");
		 */
		// List<String> cityList = Arrays.asList("Bologna", "Reggio Emilia",
		// "Milano", "Torino");
		List<String> cityList = Arrays.asList("Bologna", "Milano",
				"Reggio Emilia", "Torino");
		SortedSet<String> cities = new TreeSet<>();
		cities.addAll(cityList);

		Train train = new Train(Operator.valueOf("Z"), 9902L,
				java.time.DayOfWeek.values(), getStops());

		assertEquals(cities, train.getServedCities());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCtor_NullStops() {
		new Train(Operator.Z, 9999L, java.time.DayOfWeek.values(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCtor_NoStops() {
		new Train(Operator.Z, 9999L, java.time.DayOfWeek.values(),
				new ArrayList<Stop>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCtor_DayOfWeekNull() {
		new Train(Operator.Z, 9999L, null, getStops());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCtor_DayOfWeekEmpty() {
		new Train(Operator.Z, 9999L, new DayOfWeek[0], getStops());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCtor_WrongCode() {
		new Train(Operator.Z, -1, java.time.DayOfWeek.values(), getStops());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCtor_OperatorNull() {
		new Train(null, 9999L, java.time.DayOfWeek.values(), getStops());
	}

}
