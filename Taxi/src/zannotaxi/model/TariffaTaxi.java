package zannotaxi.model;

public interface TariffaTaxi {
	
	public enum TipoTariffa {
		TEMPO, DISTANZA
	}
	
	double getValoreScattoInEuro();
	
	String getNome();
	
	TipoTariffa getTipoTariffa();
	
}
