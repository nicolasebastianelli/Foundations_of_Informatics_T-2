package zannotaxi.model;

public class TariffaADistanza implements TariffaTaxi { 

	private double valoreScatto;
	private double distanzaDiScatto;
	private String nome;
	private double applicabileDaEuro;
	private double applicabileFinoAEuro;

	
	public TariffaADistanza(String nome, double distanzaDiScatto, double valoreScatto,
			double applicabileDaEuro, double applicabileFinoAEuro) {
		
		if (applicabileDaEuro >= applicabileFinoAEuro) 
			throw new IllegalArgumentException("intervallo applicabilita impossibile");
		
		// else
		this.nome = nome;
		this.distanzaDiScatto = distanzaDiScatto;
		this.valoreScatto = valoreScatto;
		this.applicabileDaEuro = applicabileDaEuro;
		this.applicabileFinoAEuro = applicabileFinoAEuro;
	}

	public double getValoreScattoInEuro() {
		return valoreScatto;
	}

	public String getNome() {
		return nome;
	}

	/* Restituisce la distanza in metri che corrisponde a uno scatto */
	public double getDistanzaDiScatto(){
		return distanzaDiScatto;
	}

	/* Restituisce il limite minimo di spesa per che rende applicabile questa tariffa */
	public double getLimiteMinimoApplicabilitaEuro(){
		return applicabileDaEuro;
	}

	/* Restituisce il limite massimo di spesa per che rende applicabile questa tariffa */
	public double getLimiteMassimoApplicabilitaEuro(){
		return applicabileFinoAEuro;
	}

	public String toString() {
		return "TariffaADistanza [valoreScatto=" + valoreScatto
				+ ", distanzaDiScatto=" + distanzaDiScatto + ", nome=" + nome
				+ ", applicabileDaEuro=" + applicabileDaEuro
				+ ", applicabileFinoAEuro=" + applicabileFinoAEuro + "]";
	}

	@Override
	public TipoTariffa getTipoTariffa() {
		return TipoTariffa.DISTANZA;
	}
	
	
}
