package zannotaxi.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import zannotaxi.model.CorsaTaxi;

public class MainFrame extends JFrame implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JPanel mainPanel;
	private JList<String> corsa;
	
	JCorsaTaxiPane corsaTable;

	public MainFrame(Controller controller) {
		this.controller = controller;
		initGUI();
		setSize(500, 250);
	}

	private void initGUI() {

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		getContentPane().add(mainPanel);

		setTitle("Zann-O-Taxi");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JLabel label = new JLabel("Corsa taxi, simulazione costi");
		mainPanel.add(label, BorderLayout.NORTH);

		corsaTable = new JCorsaTaxiPane();
		mainPanel.add(corsaTable, BorderLayout.SOUTH);

		JPanel commandPanel = new JPanel();
		GridLayout commandPanelLayout = new GridLayout(1, 3);
		commandPanelLayout.setHgap(5);
		commandPanelLayout.setVgap(5);
		commandPanel.setLayout(commandPanelLayout);

		JLabel label2 = new JLabel("Seleziona corsa: ");
		commandPanel.add(label2);

		corsa = new JList<String>(controller.getDescrizioniCorse());
		corsa.addListSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(corsa);
		commandPanel.add(scrollPane);

		mainPanel.add(commandPanel, BorderLayout.CENTER);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		String corsaSelezionata = corsa.getSelectedValue();
		CorsaTaxi corsa = 
				controller.getCorsaPerDescrizione(corsaSelezionata);
		
		corsaTable.update(controller.getLineeDiCosto(corsa));
	}
}
