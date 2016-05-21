package gattovolpe.holidays.view;

import gattovolpe.holidays.controller.Controller;
import gattovolpe.holidays.controller.CostoComparator;
import gattovolpe.holidays.controller.DurataComparator;
import gattovolpe.holidays.controller.InizioComparator;
import gattovolpe.holidays.model.Destinazione;
import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("rawtypes")
public class MainFrame extends JFrame implements ActionListener, ChangeListener//, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	private static final SimpleDateFormat myDateFormat = 
			new SimpleDateFormat("dd/MM/yyyy");
	
	private Controller controller;
	
	private JPanel commandPanel;
	private JComboBox destinazioni;
	private JTextField costo;
	private JSpinner dataInizio;
	private JSpinner dataFine;
	private JTextField durataRichiesta;
	
	private JPanel criteriaPanel;
	private JComboBox tipoPacchetto;
	
	private JRadioButton ordinaCosto;
	private JRadioButton ordinaInizio;
	private JRadioButton ordinaDurata;
	
	private JTextArea descrizione;
	
	public MainFrame(Controller controller) {
		this.controller = controller;
		initGUI();
		pack();
		refreshList();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		refreshList();
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		refreshList();
	}
	
	private void refreshList() {
		
		Comparator<Pacchetto> comparator = null;
		
		if(ordinaCosto.isSelected())
			comparator = new CostoComparator();
		else if(ordinaInizio.isSelected())
			comparator = new InizioComparator((Date)dataInizio.getValue());
		else if(ordinaDurata.isSelected()) {
			comparator = new DurataComparator( Integer.parseInt(durataRichiesta.getText()) );
		}
		
		List<Pacchetto> lista = 
				controller.evalPackage(comparator, 
						TipoPacchetto.valueOf( tipoPacchetto.getSelectedItem().toString()),
						destinazioni.getSelectedItem().toString(),
						Double.parseDouble(costo.getText()),
						(Date)dataInizio.getValue(),
						(Date)dataFine.getValue());
		
		StringBuilder sb =
				new StringBuilder();
		for(Pacchetto p : lista) {
			sb.append(p.getNome());
			sb.append("; ");
			sb.append("Dest. :" + p.getDestinazione().getLuogo());
			sb.append("; ");
			sb.append("Costo: " + p.getCosto());
			sb.append("; ");
			sb.append("Inizio: " + myDateFormat.format(p.getDataInizio().getTime() ));
			sb.append("; ");
			sb.append("Durata: " + p.getDurataGiorni() + " giorni");
			sb.append( System.getProperty("line.separator") );
		}
		descrizione.setText(sb.toString());
	}

	@SuppressWarnings("unchecked")
	protected void initGUI() {
		
		setTitle("Il gatto e la volpe holidays");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		{ // pannello in alto
			commandPanel = new JPanel();
			getContentPane().add(commandPanel, BorderLayout.NORTH);
			GridLayout commandPanelLayout= new GridLayout(1, 4);
			commandPanelLayout.setColumns(4);
			commandPanelLayout.setHgap(5);
			commandPanelLayout.setVgap(5);
			commandPanelLayout.setRows(1);
			commandPanel.setLayout(commandPanelLayout);
			
			{ //--- combo destinzioni 
				Set<String> tutte =
						new HashSet<String>();
				for(Destinazione d : controller.getDestinazioni() ) {
					if(!tutte.contains(d.getStato()))
						tutte.add(d.getStato());
				}
				
				destinazioni = new JComboBox(tutte.toArray());
				destinazioni.addActionListener(this);
				
				commandPanel.add(destinazioni);
			}
			
			{ // textfield costo
				costo = new JTextField("1000");
				costo.addActionListener(this);
				
				commandPanel.add(costo);
			}
			
			{ // jspinner data inizio
				
				dataInizio = new JSpinner( new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH) );
				dataInizio.setEditor( new JSpinner.DateEditor(dataInizio, "dd/MM/yyyy") );
				dataInizio.addChangeListener(this);
				
				commandPanel.add(dataInizio);
			}
			
			{ // jspinner durata
				Date initEndDate = new Date();
				GregorianCalendar initEndCalendar = new GregorianCalendar();
				initEndCalendar.setTime(initEndDate);
				initEndCalendar.add(Calendar.MONTH, 3);
				dataFine = new JSpinner( new SpinnerDateModel( initEndCalendar.getTime(), null, null, Calendar.DAY_OF_MONTH) );
				dataFine.setEditor( new JSpinner.DateEditor(dataFine, "dd/MM/yyyy") );
				dataFine.addChangeListener(this);
				
				commandPanel.add(dataFine);
			}
			
		}
		
		{ // pannello sx
			criteriaPanel = new JPanel();
			getContentPane().add(criteriaPanel, BorderLayout.WEST);
			GridLayout criteriaPanelLayout= new GridLayout(4, 2);
			criteriaPanelLayout.setColumns(2);
			criteriaPanelLayout.setHgap(5);
			criteriaPanelLayout.setVgap(5);
			criteriaPanelLayout.setRows(4);
			criteriaPanel.setLayout(criteriaPanelLayout);
			
			{ // riga 1
				criteriaPanel.add(new JLabel("Tipo"));
				
				tipoPacchetto = new JComboBox( TipoPacchetto.values() );
				tipoPacchetto.addActionListener(this);
				
				criteriaPanel.add(tipoPacchetto);
			}
			{
				criteriaPanel.add(new JLabel("Basso costo"));
				
				ordinaCosto = new JRadioButton();
				ordinaCosto.setSelected(true);
				ordinaCosto.addActionListener(this);
				
				criteriaPanel.add(ordinaCosto);
			}		
			{
				criteriaPanel.add(new JLabel("Inizio vicino"));
				
				ordinaInizio = new JRadioButton();
				ordinaInizio.addActionListener(this);
				
				criteriaPanel.add(ordinaInizio);				
			}
			{
				criteriaPanel.add(new JLabel("Durata simile"));
	
				JPanel durataPanel = new JPanel();
				durataPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				
				ordinaDurata = new JRadioButton();
				ordinaDurata.addActionListener(this);
				
				durataRichiesta = new JTextField("7");
				durataRichiesta.addActionListener(this);
				
				durataPanel.add(ordinaDurata);
				durataPanel.add(durataRichiesta);
				
				criteriaPanel.add(durataPanel);
			}
			
			{ // group radio
				 ButtonGroup group = new ButtonGroup();
				 group.add(ordinaCosto);
				 group.add(ordinaInizio);
				 group.add(ordinaDurata);
			}
			
		}
		
		{
			descrizione = new JTextArea(5,20);
			getContentPane().add(new JScrollPane(descrizione), BorderLayout.CENTER);
		}

	}

}
