package oroscopo.controller;

import java.io.IOException;

import oroscopo.model.Oroscopo;
import oroscopo.model.OroscopoMensile;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.OroscopoRepository;

public class MyController extends AbstractController {
	
	private StrategiaSelezione strategiaSelezione;
	
	public MyController(OroscopoRepository myReader, StrategiaSelezione strategiaSelezione) 
			throws IOException {
		super(myReader);
		this.strategiaSelezione = strategiaSelezione;
	}

	@Override
	public SegnoZodiacale[] getSegni() {
		return SegnoZodiacale.values();
	}

	@Override
	public Oroscopo[] generaOroscopoAnnuale(SegnoZodiacale segno, int fortunaMin) {
		
		Oroscopo[] result = new Oroscopo[NUMERO_SEGNI];
		
		int fortunaTotale = 0;
		
		while(fortunaTotale<NUMERO_SEGNI*fortunaMin) {
			fortunaTotale = 0;
			for(int i=0; i<NUMERO_SEGNI; i++) {
				result[i] = generaOroscopoCasuale(segno);
				fortunaTotale += result[i].getFortuna();
			}
		}
		
		return result;
	}

	@Override
	public Oroscopo generaOroscopoCasuale(SegnoZodiacale segno) {
		
		Previsione amore = 
				strategiaSelezione.seleziona(myRepo.getPrevisioni("amore"), segno);
		
		Previsione lavoro = 
				strategiaSelezione.seleziona(myRepo.getPrevisioni("lavoro"), segno);
		
		Previsione salute = 
				strategiaSelezione.seleziona(myRepo.getPrevisioni("salute"), segno);
		
		return new OroscopoMensile(segno, amore, lavoro, salute);
	}

}
