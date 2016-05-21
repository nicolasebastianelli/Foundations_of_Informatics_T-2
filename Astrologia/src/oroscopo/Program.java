package oroscopo;

import java.io.FileReader;

import oroscopo.controller.AbstractController;
import oroscopo.controller.MyController;
import oroscopo.controller.MyStrategiaSelezione;
import oroscopo.persistence.TextFileOroscopoRepository;
import oroscopo.ui.OroscopoFrame;

public class Program
{
	public static void main(String[] args)
	{
		AbstractController controller = null;
		try {
			controller = 
					new MyController(new TextFileOroscopoRepository(new FileReader("FrasiOroscopo.txt")),
							new MyStrategiaSelezione());
					
			OroscopoFrame mainFrame = new OroscopoFrame(controller, 6);
			mainFrame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}