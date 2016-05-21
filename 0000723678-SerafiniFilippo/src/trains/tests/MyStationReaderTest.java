package trains.tests;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.Test;

import trains.model.Station;
import trains.persistence.BadFileFormatException;
import trains.persistence.MyStationReader;
import trains.persistence.StationReader;

public class MyStationReaderTest {

	@Test
	public void testReadAll() throws IOException, BadFileFormatException {
		String dati = "FIRENZE	S.M.N./FI01, Campo Marte/FI02, Rifredi/FI03"
				+ System.lineSeparator()
				+ "VENEZIA	S.Lucia/VE01, Mestre/VE02"
				+ System.lineSeparator()
				+ "BOLOGNA	Centrale/BO00"
				+ System.lineSeparator()
				+ "REGGIO EMILIA	C.le/RE00, AV Mediopadana/RE01"
				+ System.lineSeparator()
				+ "TORINO	P.N./TO00, P.S./TO01"
				+ System.lineSeparator()
				+ "MILANO	C.le/MI00, Porta Garibaldi/MI01, Rogoredo/MI02, Lambrate/MI03, Rho Fiera Expo/MI04";

		StationReader rdr = new MyStationReader();
		Map<String, Station> stationMap = rdr.readStations(new StringReader(
				dati));

		assertEquals(15, stationMap.size());

		Station s;

		s = stationMap.get("FI01");
		assertNotNull(s);
		assertEquals("FI01", s.getId());
		assertEquals("FIRENZE", s.getCityName());
		assertEquals("S.M.N.", s.getStationName());

		s = stationMap.get("FI02");
		assertNotNull(s);
		assertEquals("FI02", s.getId());
		assertEquals("FIRENZE", s.getCityName());
		assertEquals("Campo Marte", s.getStationName());

		s = stationMap.get("FI03");
		assertNotNull(s);
		assertEquals("FI03", s.getId());
		assertEquals("FIRENZE", s.getCityName());
		assertEquals("Rifredi", s.getStationName());

		s = stationMap.get("MI00");
		assertNotNull(s);
		assertEquals("MI00", s.getId());
		assertEquals("MILANO", s.getCityName());
		assertEquals("C.le", s.getStationName());

		s = stationMap.get("MI01");
		assertNotNull(s);
		assertEquals("MI01", s.getId());
		assertEquals("MILANO", s.getCityName());
		assertEquals("Porta Garibaldi", s.getStationName());

		s = stationMap.get("MI02");
		assertNotNull(s);
		assertEquals("MI02", s.getId());
		assertEquals("MILANO", s.getCityName());
		assertEquals("Rogoredo", s.getStationName());

		s = stationMap.get("MI03");
		assertNotNull(s);
		assertEquals("MI03", s.getId());
		assertEquals("MILANO", s.getCityName());
		assertEquals("Lambrate", s.getStationName());

		s = stationMap.get("MI04");
		assertNotNull(s);
		assertEquals("MI04", s.getId());
		assertEquals("MILANO", s.getCityName());
		assertEquals("Rho Fiera Expo", s.getStationName());
	}
}
