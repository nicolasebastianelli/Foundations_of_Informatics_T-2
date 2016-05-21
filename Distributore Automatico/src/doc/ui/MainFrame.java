package doc.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.*;

import doc.model.*;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private MacchinaController controller;
	private JComboBox<Cliente> clienteComboBox;
	private JComboBox<Bevanda> bevandaComboBox;
	private JLabel prezzoLabel;
	private JButton acquistaButton;

	public MainFrame(MacchinaController controller) {
		if (controller == null)
			throw new IllegalArgumentException();

		this.controller = controller;

		initGUI();
		calcolaPrezzo();
		pack();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 2));
		{
			JLabel label = new JLabel("Cliente:");
			mainPanel.add(label);

			clienteComboBox = new JComboBox<Cliente>(controller.getClienti().toArray(new Cliente[0]));
			clienteComboBox.addActionListener(this);
			mainPanel.add(clienteComboBox);

			label = new JLabel("Bevanda:");
			mainPanel.add(label);

			bevandaComboBox = new JComboBox<Bevanda>(controller.getBevande().toArray(new Bevanda[0]));
			bevandaComboBox.addActionListener(this);
			mainPanel.add(bevandaComboBox);

			label = new JLabel("Prezzo:");
			mainPanel.add(label);

			prezzoLabel = new JLabel();
			mainPanel.add(prezzoLabel);

			acquistaButton = new JButton("Acquista");
			acquistaButton.addActionListener(this);
			mainPanel.add(acquistaButton);
		}
		getContentPane().add(mainPanel, BorderLayout.PAGE_START);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == acquistaButton) {
			acquista();
		}
		calcolaPrezzo();
	}

	private void acquista() {
		Bevanda bevanda = (Bevanda) bevandaComboBox.getSelectedItem();
		Cliente cliente = (Cliente) clienteComboBox.getSelectedItem();
		controller.acquistaBevanda(bevanda, cliente);
	}

	private void calcolaPrezzo() {
		Bevanda bevanda = (Bevanda) bevandaComboBox.getSelectedItem();
		Cliente cliente = (Cliente) clienteComboBox.getSelectedItem();
		float prezzo = controller.getPrezzoBevanda(bevanda, cliente);
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
		prezzoLabel.setText(formatter.format(prezzo));
	}

}
