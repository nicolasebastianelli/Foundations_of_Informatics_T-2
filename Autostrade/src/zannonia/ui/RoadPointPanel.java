package zannonia.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import zannonia.model.Autostrada;
import zannonia.model.routing.TrattaAutostradale;


@SuppressWarnings("serial")
public class RoadPointPanel extends javax.swing.JPanel
{
	private JLabel imageLabel;
	private JLabel nameLabel;
	private JLabel kmLabel;
	private TrattaAutostradale trattaAutostradale;

	public RoadPointPanel(TrattaAutostradale tratta)
	{
		super();
		if (tratta == null)
			throw new IllegalArgumentException("tratta == null");
		this.trattaAutostradale = tratta;
		initGUI();
	}

	private void initGUI()
	{
		GridBagLayout thisLayout = new GridBagLayout();
		GridBagConstraints constraint = new GridBagConstraints();
		this.setLayout(thisLayout);
		{
			imageLabel = new JLabel();
			imageLabel.setPreferredSize(new java.awt.Dimension(32, 32));
			nameLabel = new JLabel();
			nameLabel.setFont(new Font("Times", Font.PLAIN, 14));
			kmLabel = new JLabel();
			this.add(imageLabel, 0);
			if (trattaAutostradale.getTratta().isTrattaInterscambio())
			{
				imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/JunctionX.png")));
				StringBuilder sb = new StringBuilder();
				sb.append("Interscambio fra ");
				for (Autostrada a : trattaAutostradale.getTratta().getAutostrade())
				{
					sb.append(a);
					sb.append(" ");
				}
				nameLabel.setText(sb.toString());
			}
			else
			{
				imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/Exit.png")));
				nameLabel.setText("Tratta: " + trattaAutostradale.getTratta());
			}
			constraint.gridx = 0;
			constraint.gridy = 0;
			constraint.weightx = 0.1;
			constraint.anchor = GridBagConstraints.WEST;
			this.add(imageLabel, constraint);
			
			constraint.gridx = 1;
			constraint.gridy = 0;
			constraint.weightx = 0.7;
			constraint.anchor = GridBagConstraints.WEST;
			this.add(nameLabel, constraint);
			
			constraint.gridx = 2;
			constraint.gridy = 0;
			constraint.weightx = 0.2;
			constraint.anchor = GridBagConstraints.EAST;
			this.add(kmLabel, constraint);
			
		}
	}

}
