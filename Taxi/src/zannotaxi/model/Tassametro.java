package zannotaxi.model;

import java.util.Arrays;
import java.util.List;

public abstract class Tassametro {

	private TariffaATempo tariffaATempo;
	private List<TariffaADistanza> tariffeADistanza;
	private double velocitaLimite;
	private double[] scattoIniziale;

	public Tassametro(double velocitaLimite, double scattoInizialeGiorno, double scattoInizialeNotte,
			TariffaATempo tariffaATempo, List<TariffaADistanza> tariffeADistanza) {

		this.velocitaLimite = velocitaLimite;
		this.tariffaATempo = tariffaATempo;
		this.tariffeADistanza = tariffeADistanza;
		this.scattoIniziale = new double[] { scattoInizialeGiorno, scattoInizialeNotte };
		
		if (!adiacenti(tariffeADistanza))
			throw new IllegalArgumentException(
					"tariffe a distanza non adiacenti");
	}

	private boolean adiacenti(List<TariffaADistanza> tariffeADistanza) {

		if (tariffeADistanza.size() < 2)
			return true;

		TariffaADistanza prev = tariffeADistanza.get(0);
		for (int i = 1; i < tariffeADistanza.size(); i++) {
			TariffaADistanza succ = tariffeADistanza.get(i);
			if (succ.getLimiteMinimoApplicabilitaEuro() != prev
					.getLimiteMassimoApplicabilitaEuro())
				return false;
			else
				prev = succ;
		}
		return true;
	}

	public double getScattoIniziale(FasciaOraria giornoONotte) {
		return scattoIniziale[giornoONotte.ordinal()];
	}

	public TariffaATempo getTariffaATempo() {
		return tariffaATempo;
	}

	public List<TariffaADistanza> getTariffeADistanza() {
		return tariffeADistanza;
	}

	public double getVelocitaLimite() {
		return velocitaLimite;
	}

	public String toString() {
		return "Tassametro [tariffaATempo=" + tariffaATempo
				+ ", tariffeADistanza=" + tariffeADistanza
				+ ", velocitaLimite=" + velocitaLimite + ", scattoIniziale="
				+ Arrays.toString(scattoIniziale) + "]";
	}

	public double calcolaCosto(CorsaTaxi corsa) {
		double scattoIniziale = getScattoIniziale(FasciaOraria
				.getFasciaOraria(corsa.getOraPartenza()));
		if (corsa.getRilevazioniDistanze().length < 1)
			return scattoIniziale;
		else
			return scattoIniziale + calcolaCostoVariabile(corsa);
	}

	public abstract double calcolaCostoVariabile(CorsaTaxi corsa);

}
