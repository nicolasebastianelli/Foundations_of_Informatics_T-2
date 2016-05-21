package zannotaxi.model;

public class TariffaATempo implements TariffaTaxi {

	private double valoreScatto;
	private double durataScatto;
	private String nome;
	
	public TariffaATempo(String nome, double valoreScatto, double durataScatto) {
		this.valoreScatto = valoreScatto;
		this.durataScatto = durataScatto;
		this.nome = nome;
	}

	public double getValoreScattoInEuro() {
		return valoreScatto;
	}

	public String getNome() {
		return nome;
	}

	/* Restituisce la durata in secondi di uno scatto */
	public double getDurataScatto(){
		return durataScatto;
	}

	@Override
	public String toString() {
		return "TariffaATempo [valoreScatto=" + valoreScatto
				+ ", durataScatto=" + durataScatto + ", nome=" + nome + "]";
	}

	@Override
	public TipoTariffa getTipoTariffa() {
		return TipoTariffa.TEMPO;
	}
	
}
