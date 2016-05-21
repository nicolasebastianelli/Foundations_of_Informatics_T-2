package ed.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import ed.model.Tariffa;

public interface TariffaReader
{
	Collection<Tariffa> leggiTariffe(Reader reader) throws IOException, BadFileFormatException;
}
