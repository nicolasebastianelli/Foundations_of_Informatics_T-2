package galliacapocciona;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import galliacapocciona.persistence.MyCollegiReader;
import galliacapocciona.ui.MyController;
import galliacapocciona.ui.GalliaPanel;
import galliacapocciona.ui.SwingUserInteractor;
import galliacapocciona.ui.UserInteractor;

import javax.swing.*;

public class Main {

	public static void main(String[] a) {
		UserInteractor userInteractor = new SwingUserInteractor();
		try {
			MyController ctrl = new MyController(userInteractor);
			FileReader reader = new FileReader("RisultatiGallia.txt");
			ctrl.loadData(reader, new MyCollegiReader());
			reader.close();
			JFrame f = new JFrame("Simulatore seggi");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(new GalliaPanel(ctrl));
			f.setSize(380, 300);
			f.setVisible(true);
		} catch (FileNotFoundException e) {
			userInteractor.showMessage("File non trovato.");
			e.printStackTrace();
		} catch (IOException e) {
			userInteractor.showMessage("Errore durante la chiusura del file.");
			e.printStackTrace();
		}
	}

}
