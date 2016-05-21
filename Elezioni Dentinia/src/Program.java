

import java.io.BufferedReader;
import java.io.FileReader;

import dentinia.persistence.MySeggiWriter;
import dentinia.persistence.MyVotiReader;
import dentinia.ui.Controller;
import dentinia.ui.MainFrame;
import dentinia.ui.MyController;

public class Program
{
	public static void main(String[] args)
	{
		Controller controller = null;
		try {
			controller = 
					new MyController(new MyVotiReader(new BufferedReader(new FileReader("RisultatiElezioni.txt"))), new MySeggiWriter());
			
			MainFrame mainFrame = new MainFrame(controller);
			mainFrame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}