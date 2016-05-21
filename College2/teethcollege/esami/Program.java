package teethcollege.esami;

import javax.swing.*;

import teethcollege.esami.ui.MyController;
import teethcollege.esami.ui.SwingUserInteractor;
import teethcollege.esami.ui.TeethCollegeExamPanel;
import teethcollege.persistence.MyEsamiManager;

public class Program {
	public static void main(String[] a)
	{
		MyController controller = new MyController("Insegnamenti.txt", "PianiDiStudi.txt", 
				new SwingUserInteractor(), new MyEsamiManager());
		
		JFrame f = new JFrame("TeethCollege Esami");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new TeethCollegeExamPanel(controller));
		f.setSize(750, 630);
		f.setVisible(true);
	}

}
