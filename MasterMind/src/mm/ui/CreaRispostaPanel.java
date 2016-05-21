package mm.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mm.model.Codice;
import mm.model.Colore;
import mm.model.Configuration;

public class CreaRispostaPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private ColoreComboBox[] colorBoxes;

	private AbstractController controller;

	public CreaRispostaPanel(AbstractController controller)
	{
		this.controller = controller;
		initGUI();
	}

	private void initGUI()
	{
		setBorder(new EmptyBorder(3, 3, 3, 3));
		setLayout(new GridLayout(1, Configuration.LunghezzaCodice));
		colorBoxes = new ColoreComboBox[Configuration.LunghezzaCodice];

		for (int i = 0; i < Configuration.LunghezzaCodice; i++)
		{
			colorBoxes[i] = new ColoreComboBox();
			add(colorBoxes[i]);
		}

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(this);
		add(okButton, BorderLayout.PAGE_END);
	}

	public Codice getCodice()
	{
		Colore[] colori = new Colore[Configuration.LunghezzaCodice];
		for (int i = 0; i < colori.length; i++)
		{
			colori[i] = (Colore) colorBoxes[i].getSelectedItem();
		}
		Codice codice = new Codice(colori);
		return codice;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		controller.addTentativo(getCodice());
	}
}
