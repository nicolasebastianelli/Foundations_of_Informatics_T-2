package happybank.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SelezionaImporto extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private JTextField importoField;

	private Controller controller;

	public SelezionaImporto(Controller controller)
	{
		this.controller = controller;
		
		setLayout(new BorderLayout());
		
		JPanel innerPanel = new JPanel();
		{
			innerPanel.setLayout(new GridLayout(1, 2, 10, 10));
			innerPanel.add(new JLabel("Importo"));

			importoField = new JTextField();
			innerPanel.add(importoField);
		}
		add(innerPanel, BorderLayout.CENTER);
		
		OkCancelButtonPanel okCancelPanel = new OkCancelButtonPanel();
		okCancelPanel.addActionListener(this);
		add(okCancelPanel, BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		switch (evt.getActionCommand())
		{
			case "Ok":				
				try
				{
					int importo = Integer.parseInt(importoField.getText());
					controller.preleva(importo);
				}
				catch (NumberFormatException e)
				{
					controller.showMessage("Inserire un valore numerico");
				}
				break;
			default:
				controller.inizio();
				break;
		}
	}

}
