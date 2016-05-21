package teethcollege.pianidistudi;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import javax.swing.*;

import teethcollege.persistence.InsegnamentiReader;
import teethcollege.persistence.MyInsegnamentiReader;
import teethcollege.persistence.MyPianiReader;
import teethcollege.persistence.PianiReader;
import teethcollege.pianidistudi.ui.MyController;
import teethcollege.pianidistudi.ui.SwingUserInteractor;
import teethcollege.pianidistudi.ui.TeethCollegePanel;

public class Program {
	public static void main(String[] a)
	{
		final String nomeFileInsegnamenti = "Insegnamenti.txt";
		final String nomeFileCarriere = "PianiDiStudi.txt";
		
		Reader readerInsegnamenti = null;
		Reader readerPiani = null;
		try {
			readerInsegnamenti = new FileReader(nomeFileInsegnamenti);
			readerPiani = new FileReader(nomeFileCarriere);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File non trovato: " + e.getMessage());
			System.exit(1);
		}
		InsegnamentiReader myInsReader = new MyInsegnamentiReader(readerInsegnamenti);		
		PianiReader myPianiReader = new MyPianiReader(readerPiani);
		
		MyController controller = new MyController(myInsReader, myPianiReader, new SwingUserInteractor());
		
		JFrame f = new JFrame("TeethCollege");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new TeethCollegePanel(controller));
		f.setSize(800, 630);
		f.setVisible(true);
	}

}
