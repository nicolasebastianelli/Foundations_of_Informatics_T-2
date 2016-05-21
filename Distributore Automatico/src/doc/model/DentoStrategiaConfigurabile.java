package doc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class DentoStrategiaConfigurabile extends DentoStrategiaBase{

	private static final long serialVersionUID = 1L;
	
	private Map<String, Integer> punteggiBevande;
	private int ore;
	private long totalMillis;

	public DentoStrategiaConfigurabile(String nome, Map<String, Integer> punteggiBevande, int ore) {
		super(nome);
		if (punteggiBevande == null)
			throw new IllegalArgumentException("punteggiBevande");
		if (ore <= 0)
			throw new IllegalArgumentException("ore");
		
		this.punteggiBevande = punteggiBevande;
		this.ore = ore;
		this.totalMillis = ore * 60 * 60 * 1000;
	}
	
	public Map<String, Integer> getPunteggiBevande() {
		return Collections.unmodifiableMap(punteggiBevande);
	}
	
	public int getOre() {
		return ore;
	}

	@Override
	public float calcolaSconto(Collection<Acquisto> acquisti, Cliente cliente) {
		float sconto = 0;
		Collection<Acquisto> filteredAcquisti = filtraAcquisti(acquisti, cliente);
		if (filteredAcquisti.size() > 0) {
			float punteggioTotale = 0;
			for (Acquisto acquisto : filteredAcquisti) {
				Integer punteggio = punteggiBevande.get(acquisto.getBevanda().getNome());
				if (punteggio != null) {
					punteggioTotale += punteggio;
				}
			}
			sconto = punteggioTotale / filteredAcquisti.size();
		}
		return sconto;
	}

	private Collection<Acquisto> filtraAcquisti(Collection<Acquisto> acquisti, Cliente cliente) {
		ArrayList<Acquisto> filteredAcquisti = new ArrayList<>();
		long nowMillis = getNow().getTime();
		for (Acquisto acquisto : acquisti) {
			if ((nowMillis - acquisto.getData().getTime()) < totalMillis && acquisto.getCliente().equals(cliente)) {
				filteredAcquisti.add(acquisto);
			}
		}
		return filteredAcquisti;
	}

}
