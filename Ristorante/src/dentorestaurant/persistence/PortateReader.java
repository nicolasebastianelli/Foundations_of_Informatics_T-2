package dentorestaurant.persistence;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Portata;

public interface PortateReader {
	public Map<Categoria, List<Portata>> caricaPortate()
			throws MalformedFileException, IOException;

	public void close();
}
