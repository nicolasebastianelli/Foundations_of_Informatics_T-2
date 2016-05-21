package teethcollege.esami.ui;

import javax.swing.*;

import teethcollege.model.Carriera;
import teethcollege.model.Esame;
import teethcollege.model.PianoDiStudi;
import teethcollege.model.Insegnamento;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class TeethCollegeExamPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Controller controller;

	private JComboBox<Insegnamento> comboCorsi;
	private JComboBox<String> comboVoti;
	private JComboBox<PianoDiStudi> comboStudenti;
	
	private JRadioButton mostraCarriera, mostraEsami;
	private ButtonGroup grp;
	
	private JTextArea areaDettagli;
	private JTextField dataEsame;
	private JButton bottoneAggiungiEsame;

	DateFormat formatter;
	PianoDiStudi prescelto = null;

	public TeethCollegeExamPanel(Controller controller) {
		this.controller = controller;
		// ---------------------------------------------------
		this.setBackground(Color.black);
		// ---------------------------------------------------
		JLabel label1 = new JLabel("Studenti");
		label1.setFont(new Font("Arial", Font.BOLD, 14));
		label1.setForeground(Color.white);
		add(label1);
		comboStudenti = new JComboBox<PianoDiStudi>();
		comboStudenti.setPreferredSize(new java.awt.Dimension(200, 20));
		comboStudenti.setBackground(Color.white);
		for (PianoDiStudi c : this.controller.getPianiDiStudi())
			comboStudenti.addItem(c);
		add(comboStudenti);
		comboStudenti.addActionListener(this);
		// ---------------------------------------------------
		JLabel label2 = new JLabel("  Mostra:");
		label2.setFont(new Font("Arial", Font.BOLD, 14));
		label2.setForeground(Color.white);
		add(label2);
		mostraCarriera = new JRadioButton("piano di studi"); mostraCarriera.setSelected(true);
		mostraCarriera.setBackground(Color.black); mostraCarriera.setForeground(Color.white);
		mostraEsami = new JRadioButton("esami");
		mostraEsami.setBackground(Color.black); mostraEsami.setForeground(Color.white);
		grp = new ButtonGroup();
		grp.add(mostraCarriera);
		grp.add(mostraEsami);
		mostraCarriera.addActionListener(this);
		mostraEsami.addActionListener(this);
		add(mostraCarriera);
		add(mostraEsami);
		// ---------------------------------------------------
		areaDettagli = new JTextArea(30, 65);
		areaDettagli.setEditable(false);
		add(areaDettagli);
		// ---------------------------------------------------
		JLabel label = new JLabel("Nuovo esame");
		label.setFont(new Font("Arial", Font.BOLD, 14));
		label.setForeground(Color.white);
		add(label);
		comboCorsi = new JComboBox<Insegnamento>();
		comboCorsi.setPreferredSize(new java.awt.Dimension(200, 20));
		for (Insegnamento i : controller.getInsegnamenti())
			comboCorsi.addItem(i);
		add(comboCorsi);
		
		JLabel labelVoto = new JLabel("voto");
		labelVoto.setFont(new Font("Arial", Font.BOLD, 14));
		labelVoto.setForeground(Color.white);
		add(labelVoto);
		comboVoti = new JComboBox<String>();
		comboVoti.setPreferredSize(new java.awt.Dimension(50, 20));
		for (String v : new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17",
				                     "18","19","20","21","22","23","24","25","26","27","28","29","30","30L"}) comboVoti.addItem(v);
		add(comboVoti);
		
		JLabel labelData = new JLabel("Data");
		labelData.setFont(new Font("Arial", Font.BOLD, 14));
		labelData.setForeground(Color.white);
		add(labelData);
		formatter = DateFormat.getDateInstance(DateFormat.SHORT);
		dataEsame = new JTextField(formatter.format(new Date()), 5);
		dataEsame.setFont(new Font("Arial", Font.BOLD, 12));
		add(dataEsame);
				
		bottoneAggiungiEsame = new JButton("INSERISCI");
		bottoneAggiungiEsame.setForeground(Color.black);
		bottoneAggiungiEsame.setBackground(Color.white);
		bottoneAggiungiEsame.setSize(new java.awt.Dimension(2,2));
		
		add(bottoneAggiungiEsame);
		bottoneAggiungiEsame.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		prescelto = (PianoDiStudi) comboStudenti.getSelectedItem();
		if (e.getSource() != bottoneAggiungiEsame) {
			if (mostraCarriera.isSelected()) {
				areaDettagli.setText(prescelto.toFullString());
			}
			else { // mostra esami
				List<Esame> esami = controller.getEsami(prescelto.getMatricola());
				Carriera c = new Carriera(prescelto, esami);
				areaDettagli.setText(c.toFullString(true));
			}
		} else { // bottoneAggiungiEsame: 
			Insegnamento corso = (Insegnamento) comboCorsi.getSelectedItem();
			String voto  = (String) comboVoti.getSelectedItem();
			String data = dataEsame.getText();
			Carriera c = controller.aggiungiEsame(corso, voto, data, prescelto);
			areaDettagli.setText(c.toFullString(true));
		}
	}

}
