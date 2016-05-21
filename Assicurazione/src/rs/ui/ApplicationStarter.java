package rs.ui;

import rs.model.MyStimaSinteticaFactory;
import rs.persistence.MyStimaRischioReader;

public class ApplicationStarter {

	public static void main(String[] args) {
		SwingUserInteractor userInteractor = new SwingUserInteractor();
		MyStimaRischioReader stimaRischioReader = new MyStimaRischioReader();
		MyStimaSinteticaFactory stimaSinteticaFactory = new MyStimaSinteticaFactory();
		
		MyController controller = new MyController(userInteractor, stimaRischioReader, stimaSinteticaFactory);
		controller.start();
		new MyMainView(controller);
	}

}
