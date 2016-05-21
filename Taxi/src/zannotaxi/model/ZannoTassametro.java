package zannotaxi.model;

import java.util.List;

public class ZannoTassametro extends Tassametro {

	public ZannoTassametro(double velocitaLimite, double scattoInizialeGiorno, double scattoInizialeNotte,
			TariffaATempo tariffaATempo, List<TariffaADistanza> tariffeADistanza) {
		super(velocitaLimite, scattoInizialeGiorno, scattoInizialeNotte, tariffaATempo, tariffeADistanza);
	}

	@Override
	public double calcolaCostoVariabile(CorsaTaxi corsa) {
		double costo = 0;
		double[] rilevazioniDistanze = corsa.getRilevazioniDistanze();

		int tempoTrascorsoDaUltimoScatto = 0;
		double spazioPercorsoDaUltimoScatto = 0;

		for (int i = 1; i < rilevazioniDistanze.length; i++) {
			spazioPercorsoDaUltimoScatto += rilevazioniDistanze[i] - rilevazioniDistanze[i - 1];
			tempoTrascorsoDaUltimoScatto++;
			
			double velocitaMedia = 3.6 * spazioPercorsoDaUltimoScatto / tempoTrascorsoDaUltimoScatto;
			if (velocitaMedia >= getVelocitaLimite()) {
				TariffaADistanza tariffaCorrente = getTariffaCorrente(costo);
				if (Math.round(spazioPercorsoDaUltimoScatto) >= tariffaCorrente.getDistanzaDiScatto()) {
					costo += tariffaCorrente.getValoreScattoInEuro();
					tempoTrascorsoDaUltimoScatto = 0;
					spazioPercorsoDaUltimoScatto = 0;
				}
			} else {
				if (tempoTrascorsoDaUltimoScatto >= getTariffaATempo().getDurataScatto()) {
					costo += getTariffaATempo().getValoreScattoInEuro();
					tempoTrascorsoDaUltimoScatto = 0;
					spazioPercorsoDaUltimoScatto = 0;
				}
			}
		}

		return costo;
	}

	private TariffaADistanza getTariffaCorrente(double costo) {
		for (TariffaADistanza t : getTariffeADistanza()) {
			if (t.getLimiteMinimoApplicabilitaEuro() <= costo && costo < t.getLimiteMassimoApplicabilitaEuro()) {
				return t;
			}
		}
		throw new IllegalStateException("Quale tariffa ti do?");
	}

}
