package rs.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;

import rs.model.StimaRischio;

public interface StimaRischioReader {
	Map<String, Collection<StimaRischio>> readFrom(Reader reader) throws IOException, BadFileFormatException;
}
