package teethcollege.tests;

import teethcollege.model.Categoria;
import teethcollege.model.Insegnamento;
import teethcollege.model.Semestre;

public final class TestUtils {
	
	private TestUtils() {		
	}

	public static Insegnamento createObbligatorio(long codice) {
		return new Insegnamento(codice, Long.toString(codice), "SSDXXX", 6, Semestre.PRIMO, Categoria.OBBLIGATORIO);
	}


	public static Insegnamento createObbligatorio(long codice, int cfu) {
		return new Insegnamento(codice, Long.toString(codice), "SSDXXX", cfu, Semestre.PRIMO, Categoria.OBBLIGATORIO);
	}


	public static Insegnamento createAScelta(long codice) {
		return new Insegnamento(codice, Long.toString(codice), "SSDXXX", 6, Semestre.PRIMO, Categoria.A_SCELTA);
	}

}
