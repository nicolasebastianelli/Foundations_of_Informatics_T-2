package galliacapocciona.ui;

import galliacapocciona.model.Collegio;
import galliacapocciona.model.Partito;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalliaPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JComboBox<String> calcolatoreSeggiCombo;
	private JTextField numeroSeggiField;	
	private GalliaElectionPane electionTable;
	
	private List<Collegio> listaCollegi;
	private Map<String, Integer> mappaSeggi;
	private List<Partito> listaPartiti;

	private MyController controller;

	public GalliaPanel(MyController controller) {
		this.controller = controller;

		this.setBackground(Color.yellow);
		JLabel label = new JLabel("                Consiglio della Gallia Capocciona                ");
		label.setFont(new Font("Arial", Font.BOLD, 14));
		label.setForeground(Color.black);
		add(label);

		JLabel label2 = new JLabel("Metodo:");
		label2.setForeground(Color.black);
		add(label2);

		calcolatoreSeggiCombo = new JComboBox<String>(this.controller.getCalcolatoriSeggi());
		calcolatoreSeggiCombo.setPreferredSize(new java.awt.Dimension(200, 20));
		add(calcolatoreSeggiCombo);

		JButton actionButton = new JButton("Calcola");
		actionButton.addActionListener(this);
		add(actionButton);

		JLabel label3 = new JLabel("Seggi:");
		label3.setForeground(Color.black);
		add(label3);

		numeroSeggiField = new JTextField(3);
		add(numeroSeggiField);
		numeroSeggiField.addActionListener(this);

		listaCollegi = this.controller.getListaCollegi();
		numeroSeggiField.setText("" + this.controller.getSeggiMinimi());
		listaPartiti = Collegio.generaListaPartiti(listaCollegi);

		// inizializzazione mappaSeggi: tutti i partiti hanno 0 inizialmente
		// seggi
		mappaSeggi = new HashMap<String, Integer>();
		for (Partito p : listaPartiti) {
			mappaSeggi.put(p.getNome(), 0);
		}

		electionTable = new GalliaElectionPane(listaPartiti, mappaSeggi);
		add(electionTable);

	}

	public void actionPerformed(ActionEvent e) {
		String command = (String) calcolatoreSeggiCombo.getSelectedItem();
		
		int numeroSeggi;
		try {
			numeroSeggi = Integer.parseInt(numeroSeggiField.getText());
		}
		catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Numero seggi deve essere un numero!");
			return;
		}
		
		Map<String, Integer> mappaSeggi = controller.calcola(command, numeroSeggi);
		if (mappaSeggi != null) {
			electionTable.update(mappaSeggi);
		} else {
			numeroSeggiField.setText("" + this.controller.getSeggiMinimi());
		}
	}
}
