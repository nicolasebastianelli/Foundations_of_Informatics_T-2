package mm.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mm.model.Partita;

public interface PartitaPersister
{
	Partita read(InputStream source) throws IOException, BadFileFormatException;
	void write(Partita partita, OutputStream dest) throws IOException;
}
