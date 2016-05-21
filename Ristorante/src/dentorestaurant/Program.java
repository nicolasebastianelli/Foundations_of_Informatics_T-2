package dentorestaurant;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import javax.swing.*;

import dentorestaurant.persistence.PortateReader;
import dentorestaurant.persistence.MyPortateReader;
import dentorestaurant.persistence.MyMenuReader;
import dentorestaurant.persistence.MenuReader;
import dentorestaurant.ui.MyController;
import dentorestaurant.ui.SwingUserInteractor;
import dentorestaurant.ui.RestaurantPanel;

public class Program {
	public static void main(String[] a) {
		final String nomeFilePortate = "Portate.txt";
		final String nomeFileMenu = "Menu.txt";

		Reader readerPortate = null;
		Reader readerMenu = null;
		try {
			readerPortate = new FileReader(nomeFilePortate);
			readerMenu = new FileReader(nomeFileMenu);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"File non trovato: " + e.getMessage());
			System.exit(1);
		}
		PortateReader myPortateReader = new MyPortateReader(readerPortate);
		MenuReader myMenuReader = new MyMenuReader(readerMenu);

		MyController controller = new MyController(myPortateReader,
				myMenuReader, new SwingUserInteractor());

		JFrame f = new JFrame("Dento's Restaurant");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(
				new RestaurantPanel(controller, "Cliente qualunque"));
		f.pack();
		f.setVisible(true);
	}

}
