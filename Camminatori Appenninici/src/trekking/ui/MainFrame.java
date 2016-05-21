package trekking.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import trekking.controller.Controller;
import trekking.model.Difficulty;
import trekking.model.Trail;

@SuppressWarnings("rawtypes")
public class MainFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private PrintWriter pw;

	private Controller controller;
	
	private JPanel commandPanel;
	private JComboBox destinazioni;
	private JTextField dislivelloMax;
	private JTextField distanzaMax;
	private JTextField difficoltaMedia;
	
	private JButton aggiungiSentiero;
	private JButton controllaItinerario;
	private JButton resetItinerario, printItinerario;
	
	private JPanel criteriaPanel;
	private JComboBox difficoltaMax;
	
	private JTextArea descrizione;
	
	public MainFrame(Controller controller) {
		this.controller = controller;
		this.pw = null;
		initGUI();
		descrizione.setText(refreshedPath());
		setSize(500,350);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource() == aggiungiSentiero) {
			try {
			controller.addTrail((Trail)destinazioni.getSelectedItem());
			} catch(IllegalArgumentException e) {
				new SwingMessageManager().showMessage(e.getMessage());
			}
			descrizione.setText(refreshedPath());
		}
		else
			if(arg0.getSource() == controllaItinerario || arg0.getSource() == difficoltaMax
			|| arg0.getSource() == dislivelloMax || arg0.getSource() == distanzaMax || arg0.getSource() == difficoltaMedia) {
				if(controller.checkItinerary(
						Double.parseDouble(dislivelloMax.getText()),
						Double.parseDouble(distanzaMax.getText()),
						(Difficulty)difficoltaMax.getSelectedItem(),
						Double.parseDouble(difficoltaMedia.getText())))
					new SwingMessageManager().showMessage("Tutto OK!");

			}
			else
				if(arg0.getSource() == resetItinerario) {
					controller.reset();
					descrizione.setText(refreshedPath());
		}
		else
		if(arg0.getSource() == printItinerario) {
			File f = new File("Itinerari.txt");
			try {
				pw = new PrintWriter(new FileWriter(f,true),true);
			} catch (IOException e) {
				// should never occur here due to pre-condition check
			}
			pw.println(refreshedPath());
		}

	}


	private String refreshedPath() {
		StringBuilder sb = new StringBuilder("ITINERARIO ATTUALE:\n");
		for(Trail s : controller.getItinerary().getTrails()) {
			sb.append(s + "\n");
		}
		if(controller.getItinerary().getTrails().size()<1) return "Nessun sentiero inserito";
		sb.append("\n");
		sb.append("Dislivello max:   " + controller.getItinerary().calcMaxAltitudeDifference() + "\n");
		sb.append("Lunghezza totale: " + controller.getItinerary().calcTotalLength()+ "\n");
		sb.append("Difficolta max:   " + controller.getItinerary().calcMaxDifficulty()+ "\n");
		sb.append("\n");
		DecimalFormat df = new DecimalFormat("#.##");
		sb.append("Difficolta media: " + df.format(controller.getItinerary().calcAverageDifficulty()));
		sb.append("\n==========================================================================\n");
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	protected void initGUI() {
		
		setTitle("Sentieri di montagna");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		{ // pannello in alto
			commandPanel = new JPanel();
			getContentPane().add(commandPanel, BorderLayout.NORTH);
			GridLayout commandPanelLayout= new GridLayout(1, 1);
			commandPanelLayout.setHgap(5);
			commandPanelLayout.setVgap(5);
			commandPanel.setLayout(commandPanelLayout);
			
			{ //--- combo destinzioni 
				Set<Trail> tutte =
						new HashSet<Trail>();
				for(Trail s : controller.getAllTrails() ) {
					if(!tutte.contains(s))
						tutte.add(s);
				}
				
				destinazioni = new JComboBox(tutte.toArray());
				destinazioni.addActionListener(this);
				
				commandPanel.add(destinazioni);
			}
			
		}
		
		{ // pannello sx
			criteriaPanel = new JPanel();
			getContentPane().add(criteriaPanel, BorderLayout.WEST);
			GridLayout criteriaPanelLayout= new GridLayout(6, 2);
			criteriaPanelLayout.setHgap(5);
			criteriaPanelLayout.setVgap(5);
			criteriaPanel.setLayout(criteriaPanelLayout);
			
			{ // riga 1
				criteriaPanel.add(new JLabel("Difficolta Max"));
				
				difficoltaMax = new JComboBox( Difficulty.values());
				difficoltaMax.addActionListener(this);
				
				criteriaPanel.add(difficoltaMax);
			}
			{
				criteriaPanel.add(new JLabel("Dislivello Max"));
				
				dislivelloMax = 
						new JTextField("2000");
				dislivelloMax.addActionListener(this);
				
				criteriaPanel.add(dislivelloMax);
			}		
			{
				criteriaPanel.add(new JLabel("Lunghezza Max"));
				
				distanzaMax = 
						new JTextField("15");
				distanzaMax.addActionListener(this);
			
				criteriaPanel.add(distanzaMax);
			}
			{
				criteriaPanel.add(new JLabel("Difficolta media"));
				
				difficoltaMedia = 
						new JTextField("3");
				difficoltaMedia.addActionListener(this);
			
				criteriaPanel.add(difficoltaMedia);
			}
			{
				aggiungiSentiero = new JButton("Aggiungi");
				aggiungiSentiero.addActionListener(this);
				criteriaPanel.add(aggiungiSentiero);
				
				controllaItinerario = new JButton("Controlla");
				controllaItinerario.addActionListener(this);
				criteriaPanel.add(controllaItinerario);
			}
			{
				resetItinerario = new JButton("RESET");
				resetItinerario.addActionListener(this);
				criteriaPanel.add(resetItinerario);

				printItinerario = new JButton("Stampa");
				printItinerario.addActionListener(this);
				criteriaPanel.add(printItinerario);
			}
		}
		
		{
			descrizione = new JTextArea(5,20);
			getContentPane().add(new JScrollPane(descrizione), BorderLayout.CENTER);
		}

	}

}
