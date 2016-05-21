package mm;

import mm.model.MasterMindAlgorithm;
import mm.persistence.MyPartitaPersister;
import mm.persistence.MyPartitaWriter;
import mm.ui.AbstractController;
import mm.ui.MyController;
import mm.ui.MainFrame;

public class Program
{
	public static void main(String[] args)
	{
		AbstractController controller = new MyController(new MasterMindAlgorithm(), new MyPartitaPersister(), new MyPartitaWriter());
		MainFrame mainView = new MainFrame(controller);
		mainView.setVisible(true);
	}

}
