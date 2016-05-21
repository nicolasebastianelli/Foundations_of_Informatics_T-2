package compromeglio.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Monitoraggio {
	
	private Map<Long, List<Rilevazione>> mappaRilevazioni;
	private Set<Bene> beni;
	
	public Monitoraggio(Set<Bene> beni) {
		
		this.beni = beni;
		this.mappaRilevazioni = new HashMap<Long, List<Rilevazione>>();
	}

	public Monitoraggio(Set<Bene> beni, List<Rilevazione> rilevazioni) {
		
		this(beni);
		for (Rilevazione r : rilevazioni) addRilevazione(r);
	}

	public Bene getBene(long codice){
		for(Bene b : beni) {
			if(b.getCodice()==codice) return b;
		}
		return null;
	}
	
	public List<Rilevazione> getRilevazioni(long codice) {
		return mappaRilevazioni.get(codice);
	}

	public void addRilevazione(Rilevazione rilevazione) {
		
		Long key = rilevazione.getCodiceBene();
		
		//--- controllo sovrabbondante, in teoria.. se la specifica e rispettata in toto		
		if (!mappaRilevazioni.containsKey(key)){ // non ci sono ancora rilevazioni per questo bene
			mappaRilevazioni.put(key, new ArrayList<Rilevazione>());
			mappaRilevazioni.get(key).add(rilevazione);
		}
		else 
		{ // ci sono gia rilevazioni per questo bene
			List<Rilevazione> listaRilevazioni = mappaRilevazioni.get(key);
			if (listaRilevazioni.contains(rilevazione)) {
					throw new IllegalArgumentException("Identica rilevazione gia presente");
			}
			mappaRilevazioni.get(key).add(rilevazione);
		}
	}
	
	public Rilevazione getMigliorRilevazione(long codiceBene) throws NonRilevatoException {
		
		List<Rilevazione> rilevazioniDiQuestoBene = mappaRilevazioni.get(codiceBene);
		
		if(rilevazioniDiQuestoBene==null || rilevazioniDiQuestoBene.size()<1) 
			throw new NonRilevatoException("Codice non trovato: " + codiceBene);
		
		Rilevazione rilevazioneMigliore = null;
		Float prezzoMigliore = Float.MAX_VALUE;
		for (Rilevazione r : rilevazioniDiQuestoBene){
			if (r.getPrezzo() < prezzoMigliore) {
				rilevazioneMigliore = r; 
				prezzoMigliore = r.getPrezzo();
			}
		}
		return rilevazioneMigliore;
	}	
	

	public float getPrezzoMedio(long codiceBene) throws NonRilevatoException {
		
		List<Rilevazione> rilevazioniDiQuestoBene = mappaRilevazioni.get(codiceBene);
		if(rilevazioniDiQuestoBene==null || rilevazioniDiQuestoBene.size()<1) {
			throw new NonRilevatoException("non rilevato ID: "+ codiceBene);
		}
		float somma = 0;
		for (Rilevazione r : rilevazioniDiQuestoBene){
			somma += r.getPrezzo();
		}
		return somma/rilevazioniDiQuestoBene.size();
	}	

	public float getPrezzoMedio(Categoria c) throws NonRilevatoException {
		
		float somma = 0;
		int   n = 0;
		for (Long codice : mappaRilevazioni.keySet()) {
			Categoria categoriaDelBene = this.getBene(codice).getCategoria();
			if (categoriaDelBene==c){
				List<Rilevazione> rilevazioniDiQuestoBene = mappaRilevazioni.get(codice);
				n += rilevazioniDiQuestoBene.size();
				for (Rilevazione r : rilevazioniDiQuestoBene){
					somma += r.getPrezzo();
				}
			}
		}
		
		if(n==0) throw new NonRilevatoException("Categoria mai rilevata");
		else
			return somma/n;
	}	
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for (Long codice : mappaRilevazioni.keySet()) {
			Bene b = this.getBene(codice);
			sb.append(b.toString()+"\n");
		}
		return sb.toString();
	}

	public String toFullString() {
		
		StringBuilder sb = new StringBuilder();
		for (Long codice : mappaRilevazioni.keySet()) {
			Bene b = this.getBene(codice);
			sb.append(b.toString()+"\n");
			List<Rilevazione> rilevazioniDiQuestoBene = mappaRilevazioni.get(codice);
			for (Rilevazione r:rilevazioniDiQuestoBene){
				sb.append("\t"+ r.toString()+"\n");
			}
		}
		return sb.toString();
	}

}
