package galliacapocciona.model;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class CalcolatoreSeggiProporzionale extends CalcolatoreSeggi {

	private Map<String,Integer> mappaSeggi; 
	
	public CalcolatoreSeggiProporzionale() {
		mappaSeggi = new HashMap<String, Integer>();
	}

	public Map<String,Integer> assegnaSeggi(int seggiDaAssegnare, List<Collegio> listaCollegi) {
		return assegnaSeggiGlobale(seggiDaAssegnare, Collegio.generaListaPartiti(listaCollegi));
	}
	
	public Map<String,Integer> assegnaSeggiGlobale(int seggiDaAssegnare, List<Partito> listaPartiti) {
		if (seggiDaAssegnare <= 0) throw new IllegalArgumentException("seggi da assegnare nulli o negativi");
		//else
		int votiTotali = 0;
		for (Partito p: listaPartiti){
			votiTotali += p.getVoti();
			mappaSeggi.put(p.getNome(), 0);
		}
		int quoziente = votiTotali / seggiDaAssegnare;
		int seggiAssegnati = 0;
		List<Partito> listaResti = new ArrayList<Partito>();
		for (Partito p: listaPartiti){
			int seggiAssegnatiAlPartito = p.getVoti()/quoziente;
			mappaSeggi.put(p.getNome(), seggiAssegnatiAlPartito);
			seggiAssegnati += seggiAssegnatiAlPartito;
			listaResti.add(new Partito(p.getNome(), p.getVoti() % quoziente));
			Collections.sort(listaResti);
		}
		// fine fase assegnazione diretta
		// inizio fase assegnazione seggi residui ai resti
		while (seggiAssegnati < seggiDaAssegnare){
			String daPremiare = listaResti.remove(0).getNome();
			mappaSeggi.put(daPremiare, mappaSeggi.get(daPremiare)+1); // assegnazione seggio extra
			seggiAssegnati++;
		}
		return mappaSeggi;
	}

}
