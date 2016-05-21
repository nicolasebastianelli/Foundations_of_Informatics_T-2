package zannonia.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import zannonia.model.*;
import zannonia.model.routing.*;

public class MainFrame extends JFrame implements ActionListener, MainView
{
	private static final long serialVersionUID = 1L;

	private MainController controller;

	private JPanel centerPanel;

	private JLabel departureLabel;
	private JLabel arrivalLabel;
	private JComboBox<Autostrada> departureHighway;
	private JComboBox<Casello> departureExit;
	private JComboBox<Autostrada> arrivalHighway;
	private JComboBox<Casello> arrivalExit;
	private JButton calcButton;

	public MainFrame()
	{
		initGUI();
		pack();
	}

	@Override
	public void setController(MainController controller)
	{
		this.controller = controller;
	}

	private void initGUI()
	{
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(900, 600));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		{
			JPanel comboPanel = new JPanel();
			comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.PAGE_AXIS));
			{
				initDeparture(comboPanel);
				initArrival(comboPanel);

				calcButton = new JButton("Calcola Percorso");
				calcButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				calcButton.addActionListener(this);
				comboPanel.add(calcButton);
			}
			leftPanel.add(comboPanel, BorderLayout.PAGE_START);
		}
		this.getContentPane().add(leftPanel, BorderLayout.LINE_START);

		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		this.getContentPane().add(new JScrollPane(centerPanel), BorderLayout.CENTER);
	}

	private void initArrival(JPanel comboPanel)
	{
		arrivalLabel = new JLabel("ARRIVO");
		arrivalLabel.setFont(new Font("Times", Font.BOLD, 16));
		arrivalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboPanel.add(arrivalLabel);
		arrivalHighway = new JComboBox<Autostrada>();
		arrivalHighway.addActionListener(this);
		arrivalHighway.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboPanel.add(arrivalHighway);
		arrivalExit = new JComboBox<Casello>();
		arrivalExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboPanel.add(arrivalExit);
	}

	private void initDeparture(JPanel comboPanel)
	{
		departureLabel = new JLabel("PARTENZA");
		departureLabel.setFont(new Font("Times", Font.BOLD, 16));
		departureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboPanel.add(departureLabel);
		departureHighway = new JComboBox<Autostrada>();
		departureHighway.addActionListener(this);
		departureHighway.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboPanel.add(departureHighway);
		departureExit = new JComboBox<Casello>();
		departureExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboPanel.add(departureExit);
	}

	@Override
	public void setModel(List<Autostrada> model)
	{
		for (Autostrada highway : model)
		{
			arrivalHighway.addItem(highway);
			departureHighway.addItem(highway);
		}
	}

	@Override
	public void mostraPercorsi(List<Percorso> percorsi)
	{
		centerPanel.removeAll();
		int i = 1;
		for (Percorso percorso : percorsi)
		{
			SolutionPanel solutionPanel = new SolutionPanel(percorso, "Soluzione " + i);
			centerPanel.add(solutionPanel);
			i++;
		}
		repaint();
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Object source = arg0.getSource();
		if (source == calcButton)
		{
			if (departureExit.getSelectedItem() != null && arrivalExit.getSelectedItem() != null)
			{
				controller
						.calcolaPercorsi(this, (Casello) departureExit.getSelectedItem(), (Casello) arrivalExit.getSelectedItem());
			}
		}
		if (source == departureHighway)
		{
			refreshCombo(departureHighway, departureExit);
		}
		if (source == arrivalHighway)
		{
			refreshCombo(arrivalHighway, arrivalExit);
		}
	}

	private void refreshCombo(JComboBox<Autostrada> currentComboBox, JComboBox<Casello> updateComboBox)
	{
		((DefaultComboBoxModel<Casello>) updateComboBox.getModel()).removeAllElements();
		Autostrada highway = (Autostrada) currentComboBox.getSelectedItem();
		if (highway != null)
		{
			for (Tratta tratta : highway.getTratte())
			{
				for (Casello casello : tratta.getCaselli())
				{
					updateComboBox.addItem(casello);
				}
			}
		}
	}
}
