package trains.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import trains.model.Station;
import trains.model.Stop;
import trains.model.Train;

public class TrainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JComboBox<String> departureCombo;
	private JComboBox<String> arrivalCombo;
	private TrainDateSpinner dateSpinner;
	private JButton button;
	private JTextArea textArea;

	public TrainFrame(Controller controller) {
		super("Ferrovie Di Dentinia");
		this.controller = controller;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {

		setSize(700, 300);

		JPanel leftPanel = new JPanel();
		{
			JPanel leftUpperPanel = new JPanel();
			{
				leftUpperPanel.setLayout(new BoxLayout(leftUpperPanel,
						BoxLayout.PAGE_AXIS));

				leftUpperPanel.add(new JLabel("Stazione di Partenza:"));

				departureCombo = new JComboBox<String>(controller
						.getSortedStations().toArray(new String[0]));
				leftUpperPanel.add(departureCombo);

				leftUpperPanel.add(new JLabel("Stazione di Arrivo:"));

				arrivalCombo = new JComboBox<String>(controller
						.getSortedStations().toArray(new String[0]));
				leftUpperPanel.add(arrivalCombo);

				leftUpperPanel.add(new JLabel("Data del Viaggio:"));

				dateSpinner = new TrainDateSpinner();
				leftUpperPanel.add(dateSpinner);

				button = new JButton("Cerca");
				button.addActionListener(this);
				leftUpperPanel.add(button);
			}

			leftPanel.add(leftUpperPanel, BorderLayout.PAGE_START);

		}

		JPanel rightPanel = new JPanel();
		{
			rightPanel.add(new JLabel("Soluzioni di Viaggio"),
					BorderLayout.PAGE_START);

			textArea = new JTextArea(15, 60);
			textArea.setEditable(false);
			textArea.setFont(new Font("Courier New", Font.BOLD, 12));
			rightPanel.add(textArea, BorderLayout.CENTER);

		}

		getContentPane().add(leftPanel, BorderLayout.LINE_START);
		getContentPane().add(rightPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String departure = (String) departureCombo.getSelectedItem();
		String arrival = (String) arrivalCombo.getSelectedItem();
		LocalDate date = dateSpinner.getDateValue();

		List<Train> treni = controller.trainServing(departure, arrival, date);
		List<Train> filteredTrains = new ArrayList<Train>();

		for (Train t : treni) {
			Stop firstStop = t.getStops().get(0);
			Stop lastStop = t.getStops().get(t.getStops().size() - 1);
			LocalTime partenza = firstStop.getDepartureTime().get();
			LocalTime arrivo = lastStop.getArrivalTime().get();
			if (partenza.isBefore(arrivo))
				filteredTrains.add(t);
		}

		textArea.setText(printSolutions(filteredTrains, departure, arrival));
	}

	private String printSolutions(List<Train> trains, String departure, String arrival) {
		if (trains.isEmpty())
			return "Non ci sono altre soluzioni";
		else {
			StringBuilder sb = new StringBuilder();
			DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
			boolean noOtherSolutions = false;
			for (Train t : trains) {

				LocalTime orarioPartenza = null;
				Station departureStation = null;
				LocalTime orarioArrivo = null;
				Station arrivalStation = null;

				for (Stop s : t.getStops()) {
					if (s.getStation().toString().equals(departure)) {
						departureStation = s.getStation();
						if (s.getDepartureTime().isPresent())
							orarioPartenza = s.getDepartureTime().get();
					}

					if (s.getStation().toString().equals(arrival)) {
						arrivalStation = s.getStation();
						if (s.getArrivalTime().isPresent())
							orarioArrivo = s.getArrivalTime().get();
					}
				}

				if (orarioPartenza == null || departureStation == null
						|| orarioArrivo == null || arrivalStation == null) {
					noOtherSolutions = true;
					continue;
				}

				Duration duration = null;
				try {
					duration = t.travelDuration(departureStation,
							arrivalStation);
				} catch (IllegalArgumentException e) {
					noOtherSolutions = true;
					continue;
				}

				sb.append(t.getOperator() + " " + t.getNumber());
				sb.append(System.lineSeparator());
				sb.append("Da:\t" + departure + "\t" + formatter.format(orarioPartenza));
				sb.append(System.lineSeparator());
				sb.append("A:\t" + arrival + "\t" + formatter.format(orarioArrivo));
				sb.append(System.lineSeparator());
				sb.append("Durata : " + duration.toMinutes() + " minuti");
				sb.append(System.lineSeparator());
				sb.append("-------------------------------------------");
				sb.append(System.lineSeparator());
			}
			
			if(noOtherSolutions)
				sb.append("Non ci sono altre soluzioni");

			return sb.toString();
		}
	}

}
