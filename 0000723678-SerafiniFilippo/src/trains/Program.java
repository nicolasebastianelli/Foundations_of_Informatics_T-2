package trains;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.swing.JOptionPane;

import trains.model.Station;
import trains.model.Train;
import trains.persistence.BadFileFormatException;
import trains.persistence.MyStationReader;
import trains.persistence.MyTrainReader;
import trains.persistence.StationReader;
import trains.persistence.TrainReader;
import trains.ui.Controller;
import trains.ui.MyController;
import trains.ui.TrainFrame;

public class Program {

	public static void main(String[] args) {

		try {
			StationReader rdr = new MyStationReader();
			Map<String, Station> stationMap = rdr.readStations(new FileReader("Stazioni.txt"));
			System.out.println(stationMap);
			TrainReader trd = new MyTrainReader(stationMap);
			Collection<Train> trains = trd.readTrains(new FileReader("Treni.txt"));
			System.out.println(trains);
			for (Train t : trains)
				System.out.println(t.toFullString());
			Controller controller = new MyController(stationMap.values(), trains);
			List<Train> treniToRe = controller.trainServing("TORINO P.S.","REGGIO EMILIA AV Mediopadana");
			System.out.println(treniToRe);
			List<Train> treniToReLun = controller.trainServing("TORINO P.S.","REGGIO EMILIA AV Mediopadana", LocalDate.of(2015, 5, 25));// lun
			System.out.println(treniToReLun);
			List<Train> treniToReSab = controller.trainServing("TORINO P.S.","REGGIO EMILIA AV Mediopadana", LocalDate.of(2015, 5, 23));// sab
			System.out.println(treniToReSab);
			TrainFrame frame = new TrainFrame(controller);
			frame.setVisible(true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					null,
					"Errore di I/O - impossibile leggere i dati"
							+ e.getMessage());
			// e.printStackTrace();
		} catch (BadFileFormatException e) {
			JOptionPane.showMessageDialog(null,
					"Formato del file di testo errato - impossibile leggere i dati"
							+ e.getMessage());
			// e.printStackTrace();
		}
	}

	public static Set<Station> getStationSet(
			SortedMap<String, List<Station>> mappaCitt‡Stazioni) {
		Set<Station> stationSet = new HashSet<>();
		for (List<Station> list : mappaCitt‡Stazioni.values()) {
			for (Station s : list) {
				stationSet.add(s);
			}
		}
		return stationSet;
	}

}
