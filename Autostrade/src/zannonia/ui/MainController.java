package zannonia.ui;

import java.util.List;

import zannonia.model.*;
import zannonia.model.routing.*;

public class MainController
{
	public void start(List<Autostrada> autostrade)
	{
		MainFrame mainView = new MainFrame();
		mainView.setModel(autostrade);
		mainView.setController(this);

		mainView.setVisible(true);
	}

	public void calcolaPercorsi(MainView mainView, Casello departure, Casello arrival)
	{
		CalcolatorePercorsi router = new CalcolatorePercorsi();
		List<Percorso> percorsi = router.calcola(departure, arrival);
		mainView.mostraPercorsi(percorsi);
	}
}
