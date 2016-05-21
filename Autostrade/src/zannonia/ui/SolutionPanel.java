package zannonia.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import zannonia.model.routing.*;


@SuppressWarnings("serial")
public class SolutionPanel extends JPanel
{
	private Percorso route;
	private String title;
	private double km;
	private double euro;

	private JLabel solutionIcon;
	private JLabel titleLabel;
	private JPanel titlePanel;
	private JLabel euroIcon;
	private JLabel euroLabel;
	private JLabel kmIcon;
	private JLabel kmLabel;
	private JPanel routePanel;

	public SolutionPanel(Percorso route, String title)
	{
		this.route = route;
		this.title = title;
		this.km = route.getDistanza();
		this.euro = route.getCosto();
		initGUI();
	}

	private void initGUI()
	{
		BorderLayout borderLayout = new BorderLayout();
		Dimension dim = new Dimension(600, 32 * route.size() + 100);
		setPreferredSize(dim);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setLayout(borderLayout);
		addHeader();
		addRoutes();
	}

	private void addRoutes()
	{
		routePanel = new JPanel();
		{
			BoxLayout boxLayout = new BoxLayout(routePanel, BoxLayout.Y_AXIS);
			routePanel.setLayout(boxLayout);
			TrattaAutostradale previous = null;
			for (TrattaAutostradale roadPoint : route)
			{
				if (previous == null)
				{
					routePanel.add(new HighwayPanel(roadPoint.getAutostrada()));
					routePanel.add(new RoadPointPanel(roadPoint));
				}
				else
				{
					if (previous.getAutostrada() != roadPoint.getAutostrada())
					{
						routePanel.add(new HighwayPanel(roadPoint.getAutostrada()));
					}
					routePanel.add(new RoadPointPanel(roadPoint));
				}
				previous = roadPoint;
			}
		}
		add(routePanel, BorderLayout.CENTER);
	}

	private void addHeader()
	{
		titlePanel = new JPanel();
		{
			FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
			titlePanel.setLayout(layout);
			solutionIcon = new JLabel();
			solutionIcon.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/Light.png")));
			titlePanel.add(solutionIcon);
			titleLabel = new JLabel(title);
			titleLabel.setFont(new Font("Times", Font.BOLD, 24));
			titlePanel.add(titleLabel);
			euroIcon = new JLabel();
			euroIcon.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/Euro.png")));
			titlePanel.add(euroIcon);
			euroLabel = new JLabel("" + euro);
			titlePanel.add(euroLabel);
			kmIcon = new JLabel();
			kmIcon.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/Distance.png")));
			titlePanel.add(kmIcon);
			kmLabel = new JLabel("" + Math.round(km * 100) / 100F);
			titlePanel.add(kmLabel);
		}
		add(titlePanel, BorderLayout.NORTH);
	}
}
