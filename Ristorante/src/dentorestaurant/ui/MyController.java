package dentorestaurant.ui;

import java.io.IOException;
import dentorestaurant.persistence.MalformedFileException;
//import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Ordine;
import dentorestaurant.model.Portata;
import dentorestaurant.model.Menu;
import dentorestaurant.persistence.PortateReader;
import dentorestaurant.persistence.MenuReader;

public class MyController implements Controller {

	private Map<Categoria, List<Portata>> mappaPortate;
	private Collection<Menu> elencoMenu;

	public MyController(PortateReader portateReader, MenuReader menuReader,
			UserInteractor userInteractor) {
		try {
			mappaPortate = portateReader.caricaPortate();
			portateReader.close();
			elencoMenu = menuReader.caricaMenu(mappaPortate);
			menuReader.close();
		} catch (IOException | MalformedFileException e) {
			userInteractor.showMessage("Errore nella lettura del file: "
					+ e.getMessage());
			userInteractor.shutDownApplication();
		}
	}

	@Override
	public String sostituisciPortata(Ordine ordine, Portata daMettere) {
		try {
			Portata daTogliere = ordine.getElencoPortate().get(
					daMettere.getCategoria());
			if (daTogliere != null) {
				if (!daTogliere.equals(daMettere)) {
					ordine.sostituisciPortata(daTogliere, daMettere);
				}
			} else {
				ordine.aggiungiPortata(daMettere);
			}
			return null;
		} catch (IllegalArgumentException ex) {
			return ex.getMessage();
		}
	}

	@Override
	public Collection<Menu> getMenus() {
		return elencoMenu;
	}

	@Override
	public Ordine creaOrdine(Menu m, String nomeCliente) {
		return new Ordine(m, nomeCliente);
	}

	/*
	 * @Override public Collection<Portata> getPortate() { List<Portata> portate
	 * = new ArrayList<>(); mappaPortate.values().forEach(portate::addAll); //
	 * Java 8 only (altrimenti serve loop) return portate; }
	 */

}
