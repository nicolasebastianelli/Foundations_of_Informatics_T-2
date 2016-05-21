package mm.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mm.model.Codice;
import mm.model.CodiceRisposta;

public class MainFrame extends JFrame implements ActionListener, MainView
{
	private static final long serialVersionUID = 1L;
	private AbstractController controller;
	private CodicePanel codicePanel;
	private PartitaPanel partitaPanel;
	private JScrollPane scrollPane;

	private JButton caricaButton;
	private JButton salvaButton;
	private JButton salvaPartitaButton;
	private JButton nuovaPartitaButton;

	public MainFrame(AbstractController controller)
	{
		this.controller = controller;

		initGUI();
		
		this.controller.setMainView(this);
		updateButtonsStatus();
	}

	private void initGUI()
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Container contentPane = getContentPane();

		JPanel buttonsPanel = new JPanel();
		{
			buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));

			caricaButton = new JButton("Carica");
			caricaButton.addActionListener(this);
			buttonsPanel.add(caricaButton);

			salvaButton = new JButton("Salva");
			salvaButton.addActionListener(this);
			buttonsPanel.add(salvaButton);

			salvaPartitaButton = new JButton("Salva Partita");
			salvaPartitaButton.addActionListener(this);
			buttonsPanel.add(salvaPartitaButton);

			nuovaPartitaButton = new JButton("Nuova Partita");
			nuovaPartitaButton.addActionListener(this);
			buttonsPanel.add(nuovaPartitaButton);
		}
		contentPane.add(buttonsPanel, BorderLayout.LINE_END);

		codicePanel = new CodicePanel();
		contentPane.add(codicePanel, BorderLayout.PAGE_END);

		partitaPanel = new PartitaPanel(controller);
		scrollPane = new JScrollPane(partitaPanel);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		setSize(new Dimension(800, 600));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case "Carica":
				controller.carica();
				break;
			case "Salva":
				controller.salva();
				break;
			case "Salva Partita":
				controller.salvaPartita();
				break;
			case "Nuova Partita":
				controller.nuovaPartita();
				break;
			default:
				throw new IllegalArgumentException();
		}
		updateButtonsStatus();
	}

	private void updateButtonsStatus()
	{
		boolean partitaChiusa = controller.isPartitaChiusa();
		salvaButton.setEnabled(!partitaChiusa);
		salvaPartitaButton.setEnabled(partitaChiusa);
	}

	@Override
	public void addCodiceRisposta(CodiceRisposta codiceRisposta)
	{
		partitaPanel.addCodiceRisposta(codiceRisposta);
		updateButtonsStatus();
	}

	@Override
	public void showCodiceSegreto(Codice segreto)
	{
		codicePanel.setCodice(segreto);
	}

	@Override
	public void reset()
	{
		codicePanel.setCodice(null);
		partitaPanel.reset();
	}

	@Override
	public void showMessage(String msg)
	{
		JOptionPane.showMessageDialog(this, msg);
	}
}
