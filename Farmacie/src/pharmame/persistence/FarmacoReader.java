package pharmame.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import pharmame.model.Farmaco;

public interface FarmacoReader {
	Collection<Farmaco> readFrom(Reader inputReader) throws BadFileFormatException, IOException;
}
