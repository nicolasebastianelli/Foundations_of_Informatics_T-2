package gattovolpe;
import java.io.FileNotFoundException;
import java.io.FileReader;

import gattovolpe.holidays.controller.MyController;
import gattovolpe.holidays.model.GattoVolpeHolidays;
import gattovolpe.holidays.persistence.InvalidPackageFormatException;
import gattovolpe.holidays.persistence.MyPackageReader;
import gattovolpe.holidays.view.MainFrame;

public class Program
{
	public static void main(String[] args)
	{
		MyController controller = null;
		try {
			GattoVolpeHolidays gvh = 
					new GattoVolpeHolidays();
			gvh.caricaPacchetti( new MyPackageReader( new FileReader("PACCHETTI.TXT" )) );
			
			controller = new MyController( gvh );
			
			MainFrame mainFrame = new MainFrame(controller);
			mainFrame.setVisible(true);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidPackageFormatException e) {
			e.printStackTrace();
		}
	}
}
