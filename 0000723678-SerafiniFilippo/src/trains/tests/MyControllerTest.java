package trains.tests;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import trains.model.Station;
import trains.model.Train;
import trains.ui.MyController;
import trains.util.LocalDateHelper;

public class MyControllerTest {
	private Map<String, Station> stationMap = TestData.getStationMap();
		
	private Station getStation(String code) {
		return stationMap.get(code);
	}
	
	@Test
	public void ctorShouldNotFailWithValidParams() {
		new MyController(TestData.getStationCollection(), TestData.getTrainCollection());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ctorShouldFailWithNullStations() {
		new MyController(null, TestData.getTrainCollection());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ctorShouldFailWithNullTrains() {
		new MyController(TestData.getStationCollection(), null);
	}
	
	@Test
	public void trainServing2FromFI01toBO00ShouldGet4Trains() {
		MyController c = new MyController(TestData.getStationCollection(), TestData.getTrainCollection());
		String stationName1 = getStation("FI01").toString();
		String stationName2 = getStation("BO00").toString();
		
		List<Train> trains = c.trainServing(stationName1, stationName2);
		assertEquals(4, trains.size());
	}
	
	@Test
	public void trainServing2FromFI02toMI01ShouldGet1Train() {
		MyController c = new MyController(TestData.getStationCollection(), TestData.getTrainCollection());
		String stationName1 = getStation("FI02").toString();
		String stationName2 = getStation("MI01").toString();
		
		List<Train> trains = c.trainServing(stationName1, stationName2);
		assertEquals(1, trains.size());
	}
	
	@Test
	public void trainServing2FromTO01toMI02ShouldGetNoTrains() {
		MyController c = new MyController(TestData.getStationCollection(), TestData.getTrainCollection());
		String stationName1 = getStation("TO01").toString();
		String stationName2 = getStation("MI02").toString();
		
		List<Train> trains = c.trainServing(stationName1, stationName2);
		assertEquals(0, trains.size());
	}
	
	@Test
	public void trainServing3FromFI01toBO00ForWednesdaysShouldGet2Trains() {
		MyController c = new MyController(TestData.getStationCollection(), TestData.getTrainCollection());
		String stationName1 = getStation("FI01").toString();
		String stationName2 = getStation("BO00").toString();
		
		List<Train> trains = c.trainServing(stationName1, stationName2, LocalDateHelper.getLocalDateWith(DayOfWeek.WEDNESDAY));
		assertEquals(2, trains.size());
	}
	
	@Test
	public void trainServing3FromFI02toMI01ForWednesdaysShouldGet1Train() {
		MyController c = new MyController(TestData.getStationCollection(), TestData.getTrainCollection());
		String stationName1 = getStation("FI02").toString();
		String stationName2 = getStation("MI01").toString();
		
		List<Train> trains = c.trainServing(stationName1, stationName2, LocalDateHelper.getLocalDateWith(DayOfWeek.WEDNESDAY));
		assertEquals(1, trains.size());
	}
	
	@Test
	public void trainServing3FromTO01toMI02ForWednesdaysShouldGetNoTrains() {
		MyController c = new MyController(TestData.getStationCollection(), TestData.getTrainCollection());
		String stationName1 = getStation("TO01").toString();
		String stationName2 = getStation("MI02").toString();
		
		List<Train> trains = c.trainServing(stationName1, stationName2, LocalDateHelper.getLocalDateWith(DayOfWeek.WEDNESDAY));
		assertEquals(0, trains.size());
	}
	
}
