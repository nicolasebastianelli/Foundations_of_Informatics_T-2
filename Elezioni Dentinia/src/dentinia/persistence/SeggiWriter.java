package dentinia.persistence;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import dentinia.model.Partito;

public interface SeggiWriter {

	void stampaSeggi(List<Partito> partiti, Writer writer) throws IOException;
	
}
