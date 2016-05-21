package galliacapocciona.model;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


public class CalcolatoreSeggiMaggioritario extends CalcolatoreSeggi {

	public Map<String,Integer> assegnaSeggi(int seggiDaAssegnare, List<Collegio> listaCollegi) {
		// l'argomento seggiDaAssegnare è usato solo per verificare la cardinalità della listaCollegi
		if (listaCollegi.size()!=seggiDaAssegnare) { 
			throw new IllegalArgumentException("Impossibile assegnare "+ seggiDaAssegnare + " seggi in presenza di " + listaCollegi.size() + " collegi");
		}
		// else
		// generazione elenco nomi partiti (senza duplicazioni perché è un Set) 
		Set<String> nomiPartiti = new HashSet<String>();
		for (Partito p : listaCollegi.get(0).getListaPartiti()){
			nomiPartiti.add(p.getNome());
		}
		// inizializzazione mappaseggi: tutti i partiti hanno inizialmente 0 seggi
		Map<String,Integer> mappaSeggi = new HashMap<String, Integer>();
		for(String nomePartito : nomiPartiti){
			mappaSeggi.put(nomePartito, 0);
		}
		// inizio calcolo
		for (Collegio c : listaCollegi){
			// tutti i partiti sono presenti nella mappa, al più con 0 seggi; la get non può fallire
			Integer seggiPartito = mappaSeggi.get(c.getVincitore().getNome());
			mappaSeggi.put(c.getVincitore().getNome(), seggiPartito+1);
		}
		return mappaSeggi;
	}

}
