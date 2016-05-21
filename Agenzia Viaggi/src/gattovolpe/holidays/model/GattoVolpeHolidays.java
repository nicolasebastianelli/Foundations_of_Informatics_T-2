package gattovolpe.holidays.model;

import gattovolpe.holidays.persistence.InvalidPackageFormatException;
import gattovolpe.holidays.persistence.PackageReader;

import java.util.List;

public class GattoVolpeHolidays {
	
	private List<Pacchetto> elencoPacchetti;
	public List<Pacchetto> getElencoPacchetti() {
		return elencoPacchetti;
	}
	
	public void caricaPacchetti(PackageReader reader) throws InvalidPackageFormatException {
		elencoPacchetti = 
				reader.readPackages();
	}

}
