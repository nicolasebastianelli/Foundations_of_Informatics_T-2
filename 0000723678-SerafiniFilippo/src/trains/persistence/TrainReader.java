package trains.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import trains.model.*;

public interface TrainReader {
	/**
	 * associa l'ID di un treno al treno stesso
	 */
	public Collection<Train> readTrains(Reader reader) throws IOException,
			BadFileFormatException;

}
