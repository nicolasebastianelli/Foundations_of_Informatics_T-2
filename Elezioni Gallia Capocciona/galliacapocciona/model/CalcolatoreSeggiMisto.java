package galliacapocciona.model;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class CalcolatoreSeggiMisto extends CalcolatoreSeggi {

	private Map<String,Integer> mappaSeggi; 
	
	public CalcolatoreSeggiMisto() {
		mappaSeggi = new HashMap<String, Integer>();
	}

	public Map<String,Integer> assegnaSeggi(int seggiDaAssegnare, List<Collegio> listaCollegi) {
		// l'argomento seggiDaAssegnare è usato solo per verificare la cardinalità della listaCollegi
		if (listaCollegi.size()>seggiDaAssegnare) { 
			throw new IllegalArgumentException("Impossibile assegnare meno seggi ("+ seggiDaAssegnare + ") che collegi (" + listaCollegi.size() + ")");
		}
		// altrimenti, prima assegna tanti seggi col maggioritario quanti sono i collegi..
		CalcolatoreSeggiMaggioritario csm = new CalcolatoreSeggiMaggioritario();
		Map<String,Integer> mappaSeggiMaggioritario = csm.assegnaSeggi(listaCollegi.size(), listaCollegi);
		// .. e se non ce ne sono altri da assegnare, termina lì
		if (listaCollegi.size()==seggiDaAssegnare) {
			return mappaSeggiMaggioritario;
		}
		// altrimenti, se ce ne sono altri, li assegna col proporzionale
		CalcolatoreSeggiProporzionale csp = new CalcolatoreSeggiProporzionale();
		Map<String,Integer> mappaSeggiProporzionale = csp.assegnaSeggi(seggiDaAssegnare-listaCollegi.size(), listaCollegi); 
		// infine, genera la mappa finale sommando ordinatamente i due contributi
		for(String nomePartito : mappaSeggiMaggioritario.keySet()){
			mappaSeggi.put(nomePartito, mappaSeggiMaggioritario.get(nomePartito) + mappaSeggiProporzionale.get(nomePartito));
		}
		return mappaSeggi;
	}

}
