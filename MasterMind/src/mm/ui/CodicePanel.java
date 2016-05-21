package mm.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import mm.model.Codice;
import mm.model.Configuration;

public class CodicePanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public CodicePanel()
	{
		initGUI();
	}

	private void initGUI()
	{
		setLayout(new GridLayout(1, Configuration.LunghezzaCodice));
	}

	public void setCodice(Codice codice)
	{
		removeAll();
		if (codice != null)
		{
			for (int i = 0; i < codice.getCount(); i++)
			{
				ColorPanel colorPanel = new ColorPanel(codice.getColore(i).getInnerColor());
				add(colorPanel);
			}
		}
		revalidate();
	}
}
