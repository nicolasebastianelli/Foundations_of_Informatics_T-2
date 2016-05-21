package oroscopo.controller;

import java.util.List;
import java.util.Random;

import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyStrategiaSelezione implements StrategiaSelezione {

	private Random r = new Random();
	
	@Override
	public Previsione seleziona(List<Previsione> previsioni, SegnoZodiacale segno) {
		
		Previsione risultato = null;
		
		while( !(risultato = previsioni.get(r.nextInt(previsioni.size())) ).validaPerSegno(segno) ) ;
		
		return risultato;
	}

}
