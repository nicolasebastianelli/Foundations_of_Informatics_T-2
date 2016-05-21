package compromeglio;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.*;

import compromeglio.persistence.BeniReader;
import compromeglio.persistence.MyBeniReader;
import compromeglio.persistence.MyRilevazioniReader;
import compromeglio.persistence.RilevazioniReader;
import compromeglio.ui.MyController;
import compromeglio.ui.SwingUserInteractor;
import compromeglio.ui.ComproMeglioPanel;

public class Program {
	
	public static void main(String[] a) throws FileNotFoundException {
		
		BeniReader mbr = new MyBeniReader(new FileReader("BeniDiConsumo.txt"));
		RilevazioniReader mrr = new MyRilevazioniReader(new FileReader("Rilevazioni.txt"));
		
		MyController controller = new MyController(mbr, mrr, new SwingUserInteractor());
		
		JFrame f = new JFrame("Compro Meglio!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new ComproMeglioPanel(controller));
		f.setSize(750, 630);
		f.setVisible(true);

	}

}
