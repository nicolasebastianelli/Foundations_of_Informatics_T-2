package happybank.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class SelezionaBancomatControllaPin extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private Controller controller;

	private JComboBox<String> bancomatCombo;
	private JPasswordField passwordField;

	public SelezionaBancomatControllaPin(Controller controller)
	{
		this.controller = controller;
		initGUI();
	}

	private void initGUI()
	{
		setLayout(new BorderLayout());
		
		JPanel innerPanel = new JPanel();
		{
			innerPanel.setLayout(new GridLayout(2, 2, 10, 10));

			innerPanel.add(new JLabel("Bancomat"));
			
			String[] tessere = controller.getIdTessereBancomat();
			bancomatCombo = new JComboBox<String>(tessere);
			innerPanel.add(bancomatCombo);
			
			innerPanel.add(new JLabel("Pin"));
			
			passwordField = new JPasswordField();
			innerPanel.add(passwordField);
		}
		add(innerPanel, BorderLayout.CENTER);
		
		OkCancelButtonPanel buttonsPanel = new OkCancelButtonPanel();
		buttonsPanel.addActionListener(this);
		add(buttonsPanel, BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		switch(evt.getActionCommand())
		{
			case "Ok":
				controller.checkPin((String)bancomatCombo.getSelectedItem(),
						new String(passwordField.getPassword()));
				break;
			default:
				controller.inizio();
				break;				
		}
	}

}
