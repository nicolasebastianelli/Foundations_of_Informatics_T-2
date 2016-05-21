package mm.persistence;

import java.io.IOException;
import java.io.PrintWriter;

import mm.model.Partita;

public interface PartitaWriter
{
	void write(Partita partita, PrintWriter dest) throws IOException;
}
