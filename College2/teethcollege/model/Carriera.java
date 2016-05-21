package teethcollege.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Carriera {
	
	private PianoDiStudi pianoDiStudi;
	private Map<Long, List<Esame>> esami;
	
	public Carriera(PianoDiStudi pianoDiStudi) {
		this.pianoDiStudi = pianoDiStudi;
		this.esami = new TreeMap<Long, List<Esame>>();
	}

	public Carriera(PianoDiStudi pianoDiStudi, List<Esame> listaEsami) {
		this.pianoDiStudi = pianoDiStudi;
		this.esami = new TreeMap<Long, List<Esame>>();
		for (Esame e : listaEsami) addEsame(e);
	}

	public PianoDiStudi getPianoDiStudi() {
		return pianoDiStudi;
	}

	public List<Esame> getListaEsami(long codice) {
		return esami.get(codice);
	}

	public void addEsame(Esame esame){
		// chi deve verificare se non è già stato superato??		
		long key = esame.getCodice();
		if (!pianoDiStudi.getInsegnamenti().contains(esame.getInsegnamento())) {
			throw new IllegalArgumentException("Impossibile inserire un esame per un insegnamento non presente in carriera");
		}
		if (!esami.containsKey(key)){ // non ci sono ancora esami per questo insegnamento
			esami.put(key, new ArrayList<Esame>());
			esami.get(key).add(esame);
		}
		else { // ci sono già degli esami per questo insegnamento
			List<Esame> esamiPrecedenti = esami.get(key);
			for(Esame e: esamiPrecedenti){
				if (e.getValoreVoto()>=18) {
					throw new IllegalArgumentException("Impossibile inserire un esame già sostenuto con esito positivo");
				}
			}
			esami.get(key).add(esame);
		}
	}
	
	public List<Esame> getEsamiSostenuti() {
		List<Esame> tutti = new ArrayList<Esame>();
		for (long codice : esami.keySet()){
			tutti.addAll(esami.get(codice));
		}
		return tutti;
	}

	public List<Esame> getEsamiSuperati() {
		List<Esame> superati = new ArrayList<Esame>();
		for (long codice : esami.keySet()){
			List<Esame> esamiDiQuestoCorso = esami.get(codice);
			for (Esame e: esamiDiQuestoCorso) {
				if (e.getValoreVoto()>=18) superati.add(e);
			}
		}
		return superati;
	}
	
	public int getCreditiAcquisiti(){
		int sum = 0;
		for (Esame e: getEsamiSuperati()){
			sum += e.getInsegnamento().getCfu();
		}
		return sum;
	}

	public double getMediaPesata() {
		List<Esame> superati = getEsamiSuperati();
		if (superati.size() == 0) {
			return 0;
		}
//		
		float media = 0;
		for (Esame e : superati){
			media += e.getValoreVoto() * e.getInsegnamento().getCfu();
		}
		return Math.rint(100*media/getCreditiAcquisiti())/100;
	}	
	
	public String toString(){ // solo matricola cognome e nome
		return getPianoDiStudi().toString();
	}

	public String toFullString(boolean mostraDettagli) {
		StringBuilder sb = new StringBuilder();
		sb.append("Situazione esami per " + pianoDiStudi.getCognomeNome() + "\n");
		List<Esame> sostenuti = getEsamiSostenuti();
		List<Esame> superati = getEsamiSuperati();
		sb.append("Esami sostenuti:   " + sostenuti.size() + "\n");
		sb.append("Esami superati:    " + superati.size() + "\n");
		sb.append("Crediti acquisiti: " + getCreditiAcquisiti() + "\n");
		sb.append("Media pesata:      " + getMediaPesata() + "\n");
		if (mostraDettagli) {
			sb.append("Dettaglio esami sostenuti:\n");
			for (Esame e : sostenuti) {
				sb.append(e.toString() + "\n");
			}
			sb.append("Dettaglio esami superati:\n");
			for (Esame e : superati) {
				sb.append(e.toString() + "\n");
			}
		}
		return sb.toString();
	}
}
