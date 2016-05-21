package dentorestaurant.persistence;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Portata;
import dentorestaurant.model.Menu;

public interface MenuReader {
	public List<Menu> caricaMenu(Map<Categoria, List<Portata>> mappaPortate)
			throws IOException, MalformedFileException;

	public void close();
}
