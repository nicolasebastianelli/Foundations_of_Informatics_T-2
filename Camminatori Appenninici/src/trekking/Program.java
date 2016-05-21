package trekking;
import java.io.FileReader;

import trekking.controller.MyController;
import trekking.model.Trekking;
import trekking.persistence.MyTrailReader;
import trekking.ui.MainFrame;
import trekking.ui.SwingMessageManager;

public class Program
{
	public static void main(String[] args)
	{
		MyController controller = null;
		try {
			Trekking trekking = new Trekking();
			trekking.loadTrails( new MyTrailReader( new FileReader("Trails.TXT" )));
			
			controller = 
					new MyController(trekking, new SwingMessageManager());
			
			MainFrame mainFrame = new MainFrame(controller);
			mainFrame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}