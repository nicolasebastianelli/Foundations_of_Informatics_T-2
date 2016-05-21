package dentorestaurant.ui;

import java.util.Collection;

import dentorestaurant.model.Ordine;
import dentorestaurant.model.Portata;
import dentorestaurant.model.Menu;

public interface Controller {
	String sostituisciPortata(Ordine ordine, Portata daMettere);

	Collection<Menu> getMenus();
	// Collection<Portata> getPortate();

	Ordine creaOrdine(Menu m, String nomeCliente);
}
