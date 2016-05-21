package zannonia.ui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import zannonia.model.Autostrada;


@SuppressWarnings("serial")
public class HighwayPanel extends javax.swing.JPanel
{
	private JLabel iconLabel;
	private JLabel nameLabel;
	private Autostrada autostrada;

	public HighwayPanel(Autostrada autostrada)
	{
		super();
		if (autostrada == null)
			throw new IllegalArgumentException("autostrada == null");
		this.autostrada = autostrada;
		initGUI();
	}

	private void initGUI()
	{
		GridBagLayout thisLayout = new GridBagLayout();
		GridBagConstraints constraint = new GridBagConstraints();
		this.setLayout(thisLayout);
		{
			iconLabel = new JLabel();
			iconLabel.setPreferredSize(new Dimension(32, 32));
			iconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/Highway.png")));
			constraint.gridx = 0;
			constraint.gridy = 0;
			constraint.weightx = 0.1;
			constraint.anchor = GridBagConstraints.WEST;
			this.add(iconLabel, constraint);
			
			nameLabel = new JLabel();
			nameLabel.setText(autostrada.getNome());
			nameLabel.setFont(new Font("Times", Font.BOLD, 16));
			constraint.gridx = 1;
			constraint.gridy = 0;
			constraint.anchor = GridBagConstraints.CENTER;
			constraint.weightx = 0.9;
			this.add(nameLabel, constraint);
		}

	}

}
