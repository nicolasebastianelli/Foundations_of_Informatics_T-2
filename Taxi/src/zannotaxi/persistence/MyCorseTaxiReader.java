package zannotaxi.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import zannotaxi.model.CorsaTaxi;
import zannotaxi.model.SimpleTime;

public class MyCorseTaxiReader implements CorseTaxiReader {
	
	boolean dataReady = false;
	
	private List<CorsaTaxi> corse;
	
	@Override
	public void leggiCorse(InputStream stream)
			throws BadFileFormatException, IOException {
		
		corse = new ArrayList<CorsaTaxi>();

		ObjectInputStream ois = new ObjectInputStream(stream);
		
		try {
			
			int nCorse = ois.readInt();
			
			for(int i=0; i<nCorse; i++) {

				int ora = ois.readInt();
				int minuti = ois.readInt();
				
				String descrizione = (String)ois.readObject();
				
				double[] campioni = (double[]) ois.readObject();

				corse.add(new CorsaTaxi(descrizione, new SimpleTime(ora, minuti), campioni));

			}
			
		} catch (Exception e) {
			dataReady = false;
			throw new BadFileFormatException(e);
		}
		
		dataReady = true;
	}

	@Override
	public List<CorsaTaxi> getCorse() throws DataNotReadyException {
		if(!dataReady) throw new DataNotReadyException("Dati non pronti");
		else
			return corse;
	}

}
