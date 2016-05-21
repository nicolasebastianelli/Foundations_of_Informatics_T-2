package zannotaxi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import zannotaxi.persistence.MyCorseTaxiReader;
import zannotaxi.persistence.MyTariffaTaxiReader;
import zannotaxi.ui.Controller;
import zannotaxi.ui.MainFrame;
import zannotaxi.ui.MyController;

public class Program {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		ZannoUtils.main(new String[0]);
		
		Controller controller = null;
		try {
			
			controller = 
					new MyController(new MyTariffaTaxiReader(new FileReader("TARIFFE.TXT")), 
							new MyCorseTaxiReader(), new FileInputStream("CORSE.BIN"));
			
			MainFrame mainFrame = new MainFrame(controller);
			mainFrame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
