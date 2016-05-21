package zannotaxi.ui;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

import zannotaxi.model.CorsaTaxi;
import zannotaxi.model.Tassametro;
import zannotaxi.persistence.BadFileFormatException;
import zannotaxi.persistence.CorseTaxiReader;
import zannotaxi.persistence.DataNotReadyException;
import zannotaxi.persistence.TariffaTaxiReader;

public class MyController extends AbstractController {
	
	public MyController(TariffaTaxiReader tariffeReader, 
			CorseTaxiReader corseReader, 
			InputStream corseStream) throws BadFileFormatException, IOException {
		
		super(tariffeReader, corseReader);
		corseReader.leggiCorse(corseStream);
	}
	
	public MyController(CorseTaxiReader corseReader, 
			Tassametro tassametro) throws BadFileFormatException {
		
		super(corseReader, tassametro);
	}

	@Override
	public String[] getDescrizioniCorse() {
		
		List<CorsaTaxi> corse = null;
		try {
			corse = corseReader.getCorse();
		} catch (DataNotReadyException e) {
			return new String[0];
		}
		String[] risultato = new String[corse.size()];
		for(int i=0; i<risultato.length; i++) {
			risultato[i] = corse.get(i).getDettagliCorsa();
		}
		return risultato;
	}

	@Override
	public CorsaTaxi getCorsaPerDescrizione(String descrizioneCorsa) {
		List<CorsaTaxi> corse = null;
		try {
			corse = corseReader.getCorse();
		} catch (DataNotReadyException e) {
			return null;
		}
		for(CorsaTaxi ct : corse) {
			if(ct.getDettagliCorsa().equalsIgnoreCase(descrizioneCorsa))
				return ct;
		}
		return null;
	}

	@Override
	public String[][] getLineeDiCosto(CorsaTaxi corsa) {

		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		
		String dettaglio = corsa.getDettagliCorsa();
		String costo = numberFormat.format(tassametro.calcolaCosto(corsa));
		String distanza = numberFormat
			.format(corsa.getRilevazioniDistanze()[corsa.getRilevazioniDistanze().length-1] / 1000.0);
		String orario = corsa.getOraPartenza().toString();
		
		return new String[][] 
				{ {"Corsa", dettaglio }, { "Orario", orario }, { "Distanza km", distanza }, { "Costo", costo } };
	}

}
