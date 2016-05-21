package teethcollege.pianidistudi.ui;

import javax.swing.*;

import teethcollege.model.PianoDiStudi;
import teethcollege.model.Insegnamento;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeethCollegePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Controller controller;

	private JComboBox<Insegnamento> comboCorsi1, comboCorsi2;
	private JComboBox<PianoDiStudi> comboStudenti;
	private JTextArea areaDettagli;
	private JTextField esitoOperazione;
	private JButton bottoneSostituisci;

	public TeethCollegePanel(Controller controller) {
		this.controller = controller;

		// ---------------------------------------------------
		this.setBackground(Color.cyan);
		// ---------------------------------------------------
		JLabel label2 = new JLabel("Studenti");
		label2.setFont(new Font("Arial", Font.BOLD, 14));
		label2.setForeground(Color.black);
		add(label2);

		comboStudenti = new JComboBox<PianoDiStudi>();
		comboStudenti.setPreferredSize(new java.awt.Dimension(200, 20));
		comboStudenti.setBackground(Color.white);
		for (PianoDiStudi c : controller.getPianiDiStudi())
			comboStudenti.addItem(c);
		add(comboStudenti);
		comboStudenti.addActionListener(this);
		// ---------------------------------------------------
		areaDettagli = new JTextArea(30, 70);
		areaDettagli.setEditable(false);
		add(areaDettagli);
		// ---------------------------------------------------
		JLabel label = new JLabel("Cambia insegnamento");
		label.setFont(new Font("Arial", Font.BOLD, 14));
		label.setForeground(Color.black);
		add(label);
		comboCorsi1 = new JComboBox<Insegnamento>();
		comboCorsi1.setPreferredSize(new java.awt.Dimension(200, 20));
		for (Insegnamento i : controller.getInsegnamenti())
			comboCorsi1.addItem(i);
		add(comboCorsi1);
		JLabel labelCon = new JLabel("con");
		labelCon.setFont(new Font("Arial", Font.BOLD, 14));
		labelCon.setForeground(Color.black);
		add(labelCon);
		comboCorsi2 = new JComboBox<Insegnamento>();
		comboCorsi2.setPreferredSize(new java.awt.Dimension(200, 20));
		for (Insegnamento i : controller.getInsegnamenti())
			comboCorsi2.addItem(i);
		add(comboCorsi2);
		bottoneSostituisci = new JButton("SOSTITUISCI");
		add(bottoneSostituisci);
		bottoneSostituisci.addActionListener(this);
		JLabel labelEsito = new JLabel("Esito operazione:");
		labelEsito.setFont(new Font("Arial", Font.BOLD, 14));
		labelEsito.setForeground(Color.black);
		add(labelEsito);
		esitoOperazione = new JTextField(30);
		add(esitoOperazione);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == comboStudenti) {
			areaDettagli.setText(((PianoDiStudi) comboStudenti.getSelectedItem())
					.toFullString());
		} else if (e.getSource() == bottoneSostituisci) {
			PianoDiStudi c = (PianoDiStudi) comboStudenti.getSelectedItem();
			Insegnamento daTogliere = (Insegnamento) comboCorsi1.getSelectedItem();
			Insegnamento daMettere = (Insegnamento) comboCorsi2.getSelectedItem();

			String result = controller.sostituisci(c, daTogliere, daMettere);
			if (result == null) {
				esitoOperazione.setForeground(Color.green);
				esitoOperazione.setText("Insegnamento correttamente sostituito");
				areaDettagli.setText(c.toFullString());
			} else {
				esitoOperazione.setForeground(Color.red);
				esitoOperazione.setText(result);
			}
		}
	}

}
