package doc.tests;

import java.util.Date;

import doc.model.Acquisto;
import doc.model.Bevanda;
import doc.model.Cliente;

public final class AcquistoFactory {


	public static final Date Now = new Date();
	
    public static Acquisto createAcq(Cliente c, Bevanda b, float p, double hoursAhead) {
		return new Acquisto(c, b, new Date(Now.getTime() - (long)(hoursAhead * 60 * 60 * 1000)), p);
	}
}
