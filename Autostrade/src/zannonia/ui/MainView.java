package zannonia.ui;

import java.util.List;

import zannonia.model.Autostrada;
import zannonia.model.routing.Percorso;

public interface MainView
{
	void setController(MainController controller);
	void setModel(List<Autostrada> model);
	void mostraPercorsi(List<Percorso> percorsi);
}
