package dentinia.model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class CalcolatoreSeggiQuoziente extends CalcolatoreSeggi {

	Map<String, Partito> mappaPartiti;

	public CalcolatoreSeggiQuoziente() {
		mappaPartiti = new HashMap<String, Partito>();
	}

	public void assegnaSeggi(int seggiDaAssegnare, List<Partito> listaPartiti) {
		if (seggiDaAssegnare <= 0)
			throw new IllegalArgumentException(
					"seggi da assegnare nulli o negativi");
		// else
		int votiTotali = 0;
		for (Partito p : listaPartiti) {
			votiTotali += p.getVoti();
			mappaPartiti.put(p.getNome(), p);
		}
		int quoziente = votiTotali / seggiDaAssegnare;
		int seggiAssegnati = 0;
		List<ValorePartito> listaResti = new ArrayList<ValorePartito>();
		for (Partito p : listaPartiti) {
			int seggiAssegnatiAlPartito = p.getVoti() / quoziente;
			p.setSeggi(seggiAssegnatiAlPartito);
			seggiAssegnati += seggiAssegnatiAlPartito;
			listaResti.add(new ValorePartito(p.getVoti() % quoziente, p));
		}
		Collections.sort(listaResti);
		// System.out.println("Seggi assegnati direttamente: " + listaPartiti);
		// // DEBUG

		while (seggiAssegnati < seggiDaAssegnare) {
			for (int i = 0; i < listaResti.size()
					&& seggiAssegnati < seggiDaAssegnare; i++) {
				Partito daIncrementare = listaResti.get(i).getPartito();
				// System.out.println("Assegnato seggio extra al partito:  " +
				// daIncrementare.getNome()); // DEBUG
				daIncrementare.setSeggi(daIncrementare.getSeggi() + 1);
				seggiAssegnati++;
			}
		}
		// System.out.println("Seggi assegnati complessivi:  " + listaPartiti);
		// // DEBUG
	}

}
