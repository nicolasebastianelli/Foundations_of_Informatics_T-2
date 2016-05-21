package trains.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import trains.model.*;

/**
 * Returns the Map<String, List<Station>> that associates each id to its station
 */
public interface StationReader {
	public Map<String, Station> readStations(Reader reader) throws IOException,
			BadFileFormatException;
}
