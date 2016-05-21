package galliacapocciona.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;


public class Collegio {

	private Partito vincitore;
	private SortedSet<Partito> partiti;
	private String denominazione;
	
	public Collegio(String denominazione, SortedSet<Partito> partiti) {
		this.denominazione=denominazione;
		this.partiti = partiti;
		this.vincitore = partiti.first();
	}
	
	public Partito getVincitore() {
		return vincitore;
	}

	public SortedSet<Partito> getListaPartiti() {
		return partiti;
	}

	public String getDenominazione() {
		return denominazione;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Collegio ");  sb.append(denominazione); sb.append(": ");
		for (Partito p : partiti) sb.append(p.toString());
		return sb.toString();
	}
	
	public static List<Partito> generaListaPartiti(List<Collegio> listaCollegi){
		Map<String,Integer> mappaVoti = new HashMap<String,Integer>(); 
		for (Collegio c : listaCollegi){
			for (Partito p : c.partiti) {
				Integer votiPartito = mappaVoti.get(p.getNome());
				mappaVoti.put(p.getNome(), p.getVoti()+(votiPartito==null ? 0 : votiPartito));
			}
		}
		List<Partito> lista = new ArrayList<Partito>();
		for (String nomePartito : mappaVoti.keySet()){
			lista.add(new Partito(nomePartito, mappaVoti.get(nomePartito)));
		}
		return lista;
		
	}

}
