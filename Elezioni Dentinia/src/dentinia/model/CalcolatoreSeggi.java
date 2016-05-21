package dentinia.model;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


public abstract class CalcolatoreSeggi {
	
	public static CalcolatoreSeggi getInstance(String name) {
		switch (name.toLowerCase()){
			case "metodo del quoziente":  
				return new CalcolatoreSeggiQuoziente();
			case "metodo d'hondt":  
					return new CalcolatoreSeggiDivisore();
			default: throw new IllegalArgumentException(name);
		}
	}
	
	public static Set<String> getCalcolatoriSeggi() {
		Set<String> risultato = new HashSet<String>();
		risultato.add("Metodo del quoziente");
		risultato.add("Metodo D'Hondt");
		
		return risultato;
	}
	
	public abstract void assegnaSeggi(int seggi, List<Partito> listaPartiti);
}
