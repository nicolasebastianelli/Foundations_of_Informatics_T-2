package gattovolpe.holidays.persistence;

import gattovolpe.holidays.model.Pacchetto;

import java.util.List;

public interface PackageReader {
	
	List<Pacchetto> readPackages() throws InvalidPackageFormatException;

}
