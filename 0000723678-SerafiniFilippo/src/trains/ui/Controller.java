package trains.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;

import trains.model.*;

public interface Controller {

	/**
	 * cerca i treni disponibili fra le due stazioni specificate;
	 * 
	 * @param station1
	 *            la prima stazione
	 * @param station2
	 *            la seconda stazione
	 * @return la lista di treni in servizio fra le due stazioni, in qualunque
	 *         direzione
	 */
	List<Train> trainServing(String station1, String station2);

	/**
	 * cerca i treni disponibili fra le due stazioni specificate;
	 * 
	 * @param Station1
	 *            la prima stazione
	 * @param Station2
	 *            la seconda stazione
	 * @param day
	 *            il giorno che interessa
	 * @return la lista di treni in servizio fra le due stazioni nel giorno
	 *         specificato, in qualunque direzione
	 */
	List<Train> trainServing(String station1, String station2, LocalDate day);

	/**
	 * restituisce l'insieme di tutte le stazioni ordinate alfabeticamente
	 * 
	 * @return l'insieme di tutte le stazioni
	 */
	SortedSet<String> getSortedStations();
}
