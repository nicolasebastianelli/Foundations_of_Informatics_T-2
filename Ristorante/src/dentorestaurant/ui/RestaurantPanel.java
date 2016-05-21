package dentorestaurant.ui;

import javax.swing.*;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Ordine;
import dentorestaurant.model.Portata;
import dentorestaurant.model.Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JComboBox<Menu> comboMenu;
	private Map<Categoria, JComboBox<Portata>> comboCategorie;
	private JTextField prezzoField;
	private String nomeCliente;
	private Ordine ordine;
	private Menu m;

	public RestaurantPanel(Controller controller, String nomeCliente) {
		this.controller = controller;
		this.nomeCliente = nomeCliente;

		this.setPreferredSize(new Dimension(420, 280));
		this.setLayout(new BorderLayout());

		JPanel pageStartPanel = new JPanel();
//		pageStartPanel.setBackground(Color.cyan);
		pageStartPanel.setLayout(new FlowLayout());
		{
			JLabel label2 = new JLabel("Menu disponibili");
			label2.setFont(new Font("Arial", Font.BOLD, 14));
			label2.setForeground(Color.black);
			pageStartPanel.add(label2);

			comboMenu = new JComboBox<Menu>();
			comboMenu.setPreferredSize(new Dimension(200, 20));
			comboMenu.setBackground(Color.white);
			for (Menu c : this.controller.getMenus())
				comboMenu.addItem(c);
			comboMenu.addActionListener(this);
			pageStartPanel.add(comboMenu);
		}
		this.add(pageStartPanel, BorderLayout.PAGE_START);

		JPanel categoriePanel = new JPanel();
		categoriePanel.setLayout(new FlowLayout());
//		categoriePanel.setBackground(Color.green);
		{
			JPanel categorieInnerPanel = new JPanel();
			categorieInnerPanel.setLayout(new GridLayout(Categoria.values().length * 2, 1));
			{
				comboCategorie = new HashMap<>();
				for (Categoria c : Categoria.values()) {
					JComboBox<Portata> combo = new JComboBox<>();
					comboCategorie.put(c, combo);
					combo.setPreferredSize(new Dimension(200, 20));
					JLabel label = new JLabel(c.toString());
					label.setFont(new Font("Arial", Font.BOLD, 14));
					label.setForeground(Color.black);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					categorieInnerPanel.add(label);
					categorieInnerPanel.add(combo);
					combo.addActionListener(this);
				}
			}
			categoriePanel.add(categorieInnerPanel, BorderLayout.PAGE_START);
			
			JPanel prezzoPanel = new JPanel();
//			prezzoPanel.setBackground(Color.orange);
			prezzoPanel.setLayout(new FlowLayout());
			{
				JLabel labelPrezzo = new JLabel("Prezzo totale");
				labelPrezzo.setFont(new Font("Arial", Font.BOLD, 14));
				prezzoPanel.add(labelPrezzo);
				prezzoField = new JTextField("€ 0.00");
				prezzoField.setEditable(false);
				prezzoField.setHorizontalAlignment(JTextField.CENTER);
				prezzoField.setBackground(Color.white);
				prezzoField.setForeground(Color.red);
				prezzoField.setFont(new Font("Arial", Font.BOLD, 14));
				prezzoPanel.add(prezzoField);
			}
			categoriePanel.add(prezzoPanel, BorderLayout.LINE_END);
		}
		this.add(categoriePanel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		m = (Menu) comboMenu.getSelectedItem();
		prezzoField.setText("€ 0.00");
		if (e.getSource() == comboMenu) {
			// la sorgente dell'evento è la combo menu: ripopolo le combo delle
			// portate e creo un nuovo ordine
			for (Categoria c : Categoria.values()) {
				JComboBox<Portata> combo = comboCategorie.get(c);
				List<Portata> portatePerCategoria = m.getPortate(c);
				combo.setModel(new DefaultComboBoxModel<Portata>(
						portatePerCategoria.toArray(new Portata[0])));
			}
			ordine = controller.creaOrdine(m, nomeCliente);
		}
		// poi, in ogni caso (sia che la sorgente fosse la combo menu, sia che
		// fosse una delle combo Portate):
		// aggiusto l'ordine
		aggiornaOrdine(m);
	}

	private void aggiornaOrdine(Menu m) {
		for (Categoria c : Categoria.values()) {
			JComboBox<Portata> combo = comboCategorie.get(c);
			Portata selezionata = (Portata) combo.getSelectedItem();
			controller.sostituisciPortata(ordine, selezionata);
		}
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		prezzoField.setText(formatter.format(ordine.getPrezzoTotale()));
	}
}
