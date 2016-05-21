package mm.ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ColorPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JPanel innerColorPanel;
	
	public ColorPanel()
	{
		setLayout(new GridLayout(1, 1));
		setBorder(new EmptyBorder(3, 3, 3, 3));
		
		innerColorPanel = new JPanel();
		add(innerColorPanel);
	}

	public ColorPanel(Color color)
	{
		this();
		innerColorPanel.setBackground(color);
	}
}
