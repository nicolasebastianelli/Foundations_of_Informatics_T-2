package doc.persistence;

import java.io.IOException;

import doc.model.Acquisto;

public interface AcquistoRepository extends Repository<Acquisto> {
	void add(Acquisto acquisto) throws IOException;
}
