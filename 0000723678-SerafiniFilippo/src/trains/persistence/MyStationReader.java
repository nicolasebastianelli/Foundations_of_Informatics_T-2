package trains.persistence;

import trains.model.*;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MyStationReader implements StationReader {
	public Map<String, Station> readStations(Reader reader) throws IOException,
			BadFileFormatException {
		// Ogni riga contiene il nome della città (che può includere spazi), una
		// tabulazione e poi l’elenco delle stazioni associate
		// (che possono includere spazi ma non ripetono il nome della città),
		// separate da virgole
		// Ogni Station è anche associata a un identificativo univoco, separato
		// dal nome della Station da una barra
		BufferedReader bufferedReader = new BufferedReader(reader);
		HashMap<String, Station> map = new HashMap<>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "\t,\n\r");
			String city = st.nextToken().trim().toUpperCase();
			if (city.isEmpty())
				throw new BadFileFormatException("Nome città vuoto");
			List<Station> stations = extractStations(city, st);
			for (Station station : stations) {
				map.put(station.getId(), station);
			}
		}
		bufferedReader.close();
		return map;
	}

	private List<Station> extractStations(String city, StringTokenizer st)
			throws BadFileFormatException {
		List<Station> stations = new ArrayList<Station>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken().trim();
			String[] items = token.split("/");
			if (items.length != 2 || items[0].isEmpty() || items[1].isEmpty())
				throw new BadFileFormatException("Formato stazione/id errato");
			stations.add(new Station(city, items[0], items[1]));
		}
		return stations;
	}

}