package ed.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ed.model.Bolletta;
import ed.model.Utente;
import ed.tests.TestData;

public class MainFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Controller controller;

	private JComboBox tariffeCombo;
	private JTextField cfField;
	private JTextField cognomeField;
	private JTextField consumoField;
	private JTextArea bollettaArea;
	private JButton calcButton;
	private JTextField meseField;
	private JTextField annoField;
	private JTextField nomeField;

	public MainFrame(Controller controller)
	{		
		this.controller = controller;

		createGui();
		bindData();
		setSize(640, 480);
	}

	private void bindData()
	{
		for (String tariffa : controller.getNomiTariffe())
		{
			tariffeCombo.addItem(tariffa);
		}
	}

	private void createGui()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		JPanel leftPanel = new JPanel();
		{
			leftPanel.setLayout(new BorderLayout());
		}
		getContentPane().add(leftPanel, BorderLayout.LINE_START);

		JPanel leftUpperPanel = new JPanel();
		{
			leftUpperPanel.setLayout(new BoxLayout(leftUpperPanel, BoxLayout.PAGE_AXIS));

			JLabel label = new JLabel("Codice Fiscale");
			leftUpperPanel.add(label);
			this.cfField = new JTextField();
			this.cfField.setText(TestData.CF);
			leftUpperPanel.add(this.cfField);

			label = new JLabel("Cognome");
			leftUpperPanel.add(label);
			this.cognomeField = new JTextField();
			this.cognomeField.setText(TestData.Cognome);
			leftUpperPanel.add(this.cognomeField);

			label = new JLabel("Nome");
			leftUpperPanel.add(label);
			this.nomeField = new JTextField();
			this.nomeField.setText(TestData.Nome);
			leftUpperPanel.add(this.nomeField);

			label = new JLabel("Mese");
			leftUpperPanel.add(label);
			this.meseField = new JTextField();
			this.meseField.setText("" + TestData.Mese);
			leftUpperPanel.add(this.meseField);

			label = new JLabel("Anno");
			leftUpperPanel.add(label);
			this.annoField = new JTextField();
			this.annoField.setText("" + TestData.Anno);
			leftUpperPanel.add(this.annoField);

			label = new JLabel("Tariffa");
			leftUpperPanel.add(label);
			tariffeCombo = new JComboBox();
			leftUpperPanel.add(tariffeCombo);

			label = new JLabel("Consumo (KWh)");
			leftUpperPanel.add(label);
			this.consumoField = new JTextField();
			leftUpperPanel.add(this.consumoField);

			this.calcButton = new JButton("Calcola");
			this.calcButton.addActionListener(this);
			leftUpperPanel.add(calcButton);
		}
		leftPanel.add(leftUpperPanel, BorderLayout.PAGE_START);

		this.bollettaArea = new JTextArea();
		this.bollettaArea.setEditable(false);
		getContentPane().add(bollettaArea, BorderLayout.CENTER);
		setBounds(200,200,600,400);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		double consumo;
		try
		{
			consumo = Double.parseDouble(this.consumoField.getText());
			if (consumo < 0)
			{
				JOptionPane.showMessageDialog(this, "Consumo deve essere un numero reale maggiore o uguale a zero");
				return;
			}
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Consumo deve essere un numero reale maggiore o uguale a zero");
			return;
		}

		String nomeTariffa = (String) this.tariffeCombo.getSelectedItem();
		if (nomeTariffa == null || nomeTariffa.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Selezionare una tariffa");
			return;
		}

		String cf = this.cfField.getText();
		if (cf.trim().length() != 16)
		{
			JOptionPane.showMessageDialog(this, "Il codice fiscale deve essere composto di 16 caratteri");
			return;
		}

		String cognome = this.cognomeField.getText();
		if (cognome.trim().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Il cognome non può essere vuoto");
			return;
		}

		String nome = this.nomeField.getText();
		if (nome.trim().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Il nome non può essere vuoto");
			return;
		}

		int anno;
		try
		{
			anno = Integer.parseInt(this.annoField.getText());
			if (2000 > anno || anno > 2050)
			{
				JOptionPane.showMessageDialog(this, "Anno deve essere compreso fra 2000 e 2050");
				return;
			}
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Anno deve essere compreso fra 2000 e 2050");
			return;
		}

		int mese;
		try
		{
			mese = Integer.parseInt(this.meseField.getText());
			if (1 > mese || mese > 12)
			{
				JOptionPane.showMessageDialog(this, "Mese deve essere compreso fra 1 e 12");
				return;
			}
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Mese deve essere compreso fra 1 e 12");
			return;
		}

		Utente utente = new Utente(cf, cognome, nome);
		Bolletta bolletta = controller.creaBolletta(nomeTariffa, utente, mese, anno, consumo);
		
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		bolletta.stampa(pw);
		pw.close();
		
		this.bollettaArea.setText(writer.toString());
	}

}
