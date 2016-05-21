package oroscopo.model;

import java.util.HashSet;
import java.util.Set;

public class Previsione {
	
	String previsione;
	int valore;
	Set<SegnoZodiacale> segni;
	
	public Previsione(String previsione, int valore){
		this.previsione=previsione;
		this.valore=valore;
		this.segni = new HashSet<>();
		for(SegnoZodiacale s : SegnoZodiacale.values()) {
			segni.add(s);
		}
	}
	
	public Previsione(String previsione, int valore, Set<SegnoZodiacale> segni){
		this.previsione=previsione;
		this.valore=valore;
		this.segni = segni;
	}
	
	public String getPrevisione(){
		return previsione;
	}
	
	public int getValore(){
		return valore;
	}
	
	public boolean validaPerSegno(SegnoZodiacale segno) {
		return segni.contains(segno);
	}

}
