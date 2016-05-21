package zannonia.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import zannonia.model.Autostrada;
import zannonia.model.Tratta;

public interface AutostradeReader
{
	List<Autostrada> readAutostrade(Reader autoReader, Map<String, Tratta> tratteMap) 
			throws IOException, MalformedFileException;
}
