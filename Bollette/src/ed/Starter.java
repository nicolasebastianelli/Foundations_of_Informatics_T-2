package ed;

import java.io.FileNotFoundException;
import java.io.FileReader;

import ed.persistence.MyTariffaReader;
import ed.ui.MainFrame;
import ed.ui.MyController;
import ed.ui.SwingMessageManager;

public class Starter
{

	public static void main(String[] args) throws FileNotFoundException
	{
		MyController controller = new MyController(new MyTariffaReader(), new SwingMessageManager());
		FileReader fileReader = new FileReader("Tariffe.txt");
		controller.leggiTariffe(fileReader);
		
		MainFrame mainFrame = new MainFrame(controller);
		mainFrame.setVisible(true);
	}

}
