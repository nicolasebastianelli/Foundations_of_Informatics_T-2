package oroscopo.ui;

import javax.swing.*;

import oroscopo.controller.AbstractController;
import oroscopo.controller.Mese;
import oroscopo.model.Oroscopo;
import oroscopo.model.SegnoZodiacale;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class OroscopoFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;
	private JComboBox<SegnoZodiacale> segniZodiacali;
	private JTextArea output;
	private JButton stampa;

	private AbstractController controller;

	private void initGUI() {
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel);
		
		setTitle("Vedo, prevedo e stravedo");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.setBackground(Color.gray);
		output = new JTextArea(7, 25);

		// ---------------------
		mainPanel.add(new JLabel("Segno zodiacale:        "));
		segniZodiacali = new JComboBox<SegnoZodiacale>(controller.getSegni());
		segniZodiacali.setSelectedIndex(-1);
		mainPanel.add(segniZodiacali);

		// ---------------------
		mainPanel.add(new JLabel("Oroscopo mensile del segno: "));
		mainPanel.add(output);
		segniZodiacali.addActionListener(this);
		stampa = new JButton("Stampa annuale");
		stampa.addActionListener(this);
		mainPanel.add(stampa);
		
	}
	
	private int fortunaMin;

	public OroscopoFrame(AbstractController controller, int fortunaMin) {
		this.controller = controller;
		this.fortunaMin = fortunaMin;
		initGUI();
		setSize(350, 250);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() != stampa) {
			Oroscopo o = controller.generaOroscopoCasuale(segniZodiacali
					.getItemAt(segniZodiacali.getSelectedIndex()));
			output.setText(o.toString());
		}

		else { // pulsante Stampa Oroscopo Annuale

			try (PrintWriter pw = new PrintWriter("OroscopoAnnuale.txt")) {

				int fortuna = 0;
				int selectedIndex = segniZodiacali.getSelectedIndex();
				if (selectedIndex<0){
					output.setText("nessun segno selezionato");
					return;
				}
				
				SegnoZodiacale segnoSelezionato = segniZodiacali.getItemAt(selectedIndex);
				pw.println(segnoSelezionato);
				for (int i = 0; i < segnoSelezionato.toString().length(); i++)
					pw.print("-");
				pw.println();

				Oroscopo[] annuale = controller
						.generaOroscopoAnnuale(segnoSelezionato, fortunaMin);

				for (int i = 0; i < Mese.values().length; i++) {
					pw.println(Mese.values()[i]);
					pw.println(annuale[i]);
					fortuna += annuale[i].getFortuna();
				}

				pw.println("GRADO DI FORTUNA:\t" + fortuna / 12);
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

		}
	}
}