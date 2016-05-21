package dentinia.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import java.util.Map;
//import java.util.HashMap;
//import java.util.NavigableMap;
//import java.util.TreeMap;

public class CalcolatoreSeggiDivisore extends CalcolatoreSeggi {

//	Map<String, Partito> mappaPartiti;
//	TreeMap<Integer,String> mappaQuozienti;

	public CalcolatoreSeggiDivisore() {
//		mappaPartiti   = new HashMap<String, Partito>();
//		mappaQuozienti = new TreeMap<Integer, String>();
	}

	public void assegnaSeggi(int seggiDaAssegnare, List<Partito> listaPartiti) {
		if (seggiDaAssegnare <= 0)
			throw new IllegalArgumentException("seggi da assegnare nulli o negativi");
		// else
//		for (Partito p : listaPartiti) {
//			p.setSeggi(0);
//			mappaPartiti.put(p.getNome(), p);
//			for (int i = 1; i <= seggiDaAssegnare;i++){
//				mappaQuozienti.put(p.getVoti() / i, p.getNome());
//			}
//		}
//		NavigableMap<Integer,String> mappaQuozientiReversed = mappaQuozienti.descendingMap();
//		// System.out.println(mappaQuozientiReversed.values()); // DEBUG		
//		
//		int seggiAssegnati = 0;
//		for (String nomePartito : mappaQuozientiReversed.values()){
//			if (seggiAssegnati >= seggiDaAssegnare) break;
//			Partito daIncrementare = mappaPartiti.get(nomePartito);
//			daIncrementare.setSeggi(daIncrementare.getSeggi()+1);
//			seggiAssegnati++;
//		}
//		// System.out.println("Seggi assegnati complessivi:  " + listaPartiti); // DEBUG
		
		ArrayList<ValorePartito> divisori = new ArrayList<ValorePartito>();
		for (Partito p : listaPartiti) {
			p.setSeggi(0);
			for (int i = 1; i <= seggiDaAssegnare; i++) {
				divisori.add(new ValorePartito(p.getVoti() / i, p));
			}
		}
		Collections.sort(divisori);
		
		int seggiAssegnati = 0;
		for (int i = 0; i < divisori.size() && seggiAssegnati < seggiDaAssegnare; i++) {
			ValorePartito dp = divisori.get(i);
			Partito daIncrementare = dp.getPartito();
			daIncrementare.setSeggi(daIncrementare.getSeggi() + 1);
			seggiAssegnati++;			
		}
	}

}
