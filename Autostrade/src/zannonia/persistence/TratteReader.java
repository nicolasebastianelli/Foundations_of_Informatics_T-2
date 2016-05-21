package zannonia.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import zannonia.model.Tratta;

public interface TratteReader
{
	Map<String, Tratta> readTratte(Reader tratteReader) throws IOException, MalformedFileException;
}
