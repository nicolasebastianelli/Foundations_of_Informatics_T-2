package meteodent.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;

import meteodent.model.Previsione;

public interface PrevisioneReader {
	Map<String, Collection<Previsione>> readFrom(Reader reader) throws IOException, BadFileFormatException;
}
