package compromeglio.ui;

import javax.swing.*;

import compromeglio.model.Categoria;
import compromeglio.model.Bene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComproMeglioPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private IController controller;

	private JComboBox<Categoria> comboCategorie;
	private JComboBox<Bene> comboBeni;
	
	private JTextField prezzoMedioCategoria;
	private JTextField prezzoMiglioreBene;
	private JTextField prezzoMedioBene;
	
	private JTextArea migliorRilevazioneBene;
	
	private Categoria categoriaSelezionata;

	public ComproMeglioPanel(IController controller) {
		
		this.controller = controller;
		// ---------------------------------------------------
		this.setBackground(Color.lightGray);
		this.setLayout(new BorderLayout(5,5));
		
		// ---------------------------------------------------
		
		JPanel combo = new JPanel(new GridLayout(2,2));
		
		combo.add(new JLabel("Categorie"));
		
		comboCategorie = new JComboBox<Categoria>();
		comboCategorie.setPreferredSize(new java.awt.Dimension(200, 20));
		comboCategorie.setBackground(Color.white);
		
		for (Categoria c : this.controller.getCategorie())
			comboCategorie.addItem(c);
		comboCategorie.setSelectedIndex(-1);
		combo.add(comboCategorie);
		comboCategorie.addActionListener(this);
		
		combo.add(new JLabel("Beni"));
		comboBeni = new JComboBox<Bene>();
		comboBeni.setPreferredSize(new java.awt.Dimension(200, 20));
		comboBeni.setBackground(Color.white);
		combo.add(comboBeni);
		comboBeni.addActionListener(this);
		
		add(combo, BorderLayout.NORTH);
		
		// ---------------------------------------------------
		
		JPanel summary = new JPanel(new GridLayout(3,2));
		
		summary.add(new JLabel("Prezzo medio categoria"));
		
		prezzoMedioCategoria = new JTextField();
		prezzoMedioCategoria.setEditable(false);
		summary.add(prezzoMedioCategoria);
		
		// ---------------------------------------------------
		
		summary.add(new JLabel("Prezzo migliore bene"));
		
		prezzoMiglioreBene = new JTextField();
		prezzoMiglioreBene.setEditable(false);
		summary.add(prezzoMiglioreBene);
		
		// ---------------------------------------------------
		
		summary.add(new JLabel("Prezzo medio bene"));
		
		prezzoMedioBene = new JTextField();
		prezzoMedioBene.setEditable(false);
		summary.add(prezzoMedioBene);
		
		add(summary, BorderLayout.WEST);
		
		// ---------------------------------------------------
		migliorRilevazioneBene = new JTextArea();
		migliorRilevazioneBene.setEditable(false);
		add(migliorRilevazioneBene, BorderLayout.CENTER);
		
		// ---------------------------------------------------
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == comboCategorie) {
			categoriaSelezionata = (Categoria) comboCategorie.getSelectedItem();
			//--- popola combo beni
			comboBeni.removeAllItems();
			for (Bene b : this.controller.getBeniPerCategoria(categoriaSelezionata))
				comboBeni.addItem(b);
			comboBeni.setSelectedIndex(-1);
			prezzoMedioCategoria
				.setText(controller.getPrezzoMedioPerCategoria(categoriaSelezionata));

			cancellaDatiBene();
		}
		
		if (e.getSource() == comboBeni) {
			if(comboBeni.getSelectedItem()!=null) {
				mostraDatiBene((Bene)comboBeni.getSelectedItem());
			}
		}
	}
	
	private void cancellaDatiBene() {
		
		prezzoMiglioreBene.setText("");
		prezzoMedioBene.setText("");
		migliorRilevazioneBene.setText("");
	}
	
	private void mostraDatiBene(Bene b) {
		
		prezzoMiglioreBene
			.setText(controller.getMigliorPrezzoPerBene(b));
		prezzoMedioBene
			.setText(controller.getPrezzoMedioPerBene(b));
		migliorRilevazioneBene
			.setText(controller.getMigliorRilevazionePerBene(b));
	}
 
}
