package zannotaxi.ui;

import java.util.ArrayList;
import java.util.List;

import zannotaxi.model.TariffaADistanza;
import zannotaxi.model.TariffaATempo;
import zannotaxi.model.TariffaTaxi;
import zannotaxi.model.Tassametro;
import zannotaxi.model.ZannoTassametro;
import zannotaxi.persistence.BadFileFormatException;
import zannotaxi.persistence.CorseTaxiReader;
import zannotaxi.persistence.TariffaTaxiReader;

public abstract class AbstractController implements Controller {

	protected TariffaTaxiReader tariffaReader;
	protected List<TariffaTaxi> listaTariffe;
	protected CorseTaxiReader corseReader;
	protected Tassametro tassametro;

	public void setTassametro(Tassametro tassametro) {
		this.tassametro = tassametro;
	}

	public AbstractController(TariffaTaxiReader tariffaReader,
			CorseTaxiReader corseReader) throws BadFileFormatException {
		
		this.tariffaReader = tariffaReader;
		this.corseReader = corseReader;
		listaTariffe = tariffaReader.leggiTariffe();
		
		TariffaATempo aTempo = null;
		List<TariffaADistanza> aDistanza = new ArrayList<TariffaADistanza>();
		for(TariffaTaxi t : listaTariffe) {
			if(t instanceof TariffaATempo) {
				if(aTempo==null) {
					aTempo = (TariffaATempo)t;
				} else {
					throw new BadFileFormatException("Una sola tariffa a tempo ammessa");
				}
			}
			if(t instanceof TariffaADistanza) {
				aDistanza.add((TariffaADistanza)t);
			}
		}
		
		tassametro = new ZannoTassametro(27, 0.50, 2.0, aTempo, aDistanza);
	}
	
	public AbstractController(CorseTaxiReader corseReader, Tassametro tassametro) throws BadFileFormatException {
	
		this.corseReader = corseReader;
		this.tassametro = tassametro;
	}

}
