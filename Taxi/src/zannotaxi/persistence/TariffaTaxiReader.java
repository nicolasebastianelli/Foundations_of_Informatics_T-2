package zannotaxi.persistence;

import java.util.List;

import zannotaxi.model.TariffaTaxi;

public interface TariffaTaxiReader {

	List<TariffaTaxi> leggiTariffe();
	
}
