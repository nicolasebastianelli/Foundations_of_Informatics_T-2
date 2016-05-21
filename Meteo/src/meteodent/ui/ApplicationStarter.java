package meteodent.ui;

import meteodent.model.MyAnnuncioFactory;
import meteodent.persistence.MyPrevisioneReader;

public class ApplicationStarter {

	public static void main(String[] args) {
		SwingUserInteractor userInteractor = new SwingUserInteractor();
		MyPrevisioneReader previsioneReader = new MyPrevisioneReader();
		MyAnnuncioFactory annuncioFactory = new MyAnnuncioFactory();
		
		MyController controller = new MyController(userInteractor, previsioneReader, annuncioFactory);
		controller.start();
		new MyMainView(controller);
	}

}
