package zannotaxi.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import zannotaxi.model.CorsaTaxi;

public interface CorseTaxiReader {
	
	 List<CorsaTaxi> getCorse() throws DataNotReadyException;
	
	 void leggiCorse(InputStream stream) throws BadFileFormatException, IOException;

}
