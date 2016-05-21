package pharmame;

import java.io.FileNotFoundException;
import java.io.IOException;

import pharmame.model.FarmacoFilterFactory;
import pharmame.model.MyFarmacoFilterFactory;
import pharmame.persistence.AifaCSVFarmacoReader;
import pharmame.persistence.BadFileFormatException;
import pharmame.persistence.FarmacoReader;
import pharmame.ui.Controller;
import pharmame.ui.MainView;
import pharmame.ui.MyController;
import pharmame.ui.MyMainView;

public class ProgramStarter {

	public static void main(String[] args) throws FileNotFoundException, BadFileFormatException, IOException {
		
		FarmacoReader reader = new AifaCSVFarmacoReader();
		MainView view = new MyMainView();
		FarmacoFilterFactory filterFactory = new MyFarmacoFilterFactory();
		Controller controller = new MyController(reader, view, filterFactory);
		controller.start();
	}

}
