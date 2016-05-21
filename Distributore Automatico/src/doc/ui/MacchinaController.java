package doc.ui;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import doc.model.Acquisto;
import doc.model.Bevanda;
import doc.model.Cliente;
import doc.model.DentoStrategia;
import doc.persistence.AcquistoRepository;
import doc.persistence.Repository;

public class MacchinaController {
	private Repository<Cliente> clienteRepository;
	private Repository<Bevanda> bevandaRepository;
	private AcquistoRepository acquistoRepository;
	private UserInteractor userInteractor;
	
	public MacchinaController(
			Repository<Cliente> clienteRepository, 
			Repository<Bevanda> bevandaRepository,
			Repository<DentoStrategia> strategiaRepository,
			AcquistoRepository acquistoRepository,
			UserInteractor userInteractor) {
		
		if (clienteRepository == null || 
				bevandaRepository == null ||
				strategiaRepository == null ||
				acquistoRepository == null ||
				userInteractor == null)
			throw new IllegalArgumentException("null args");
				
		Map<String, DentoStrategia> strategiaMap = new HashMap<>();
		for (DentoStrategia strategia : strategiaRepository.getAll()) {
			strategiaMap.put(strategia.getNome(), strategia);
		}
		
		for (Bevanda bevanda : bevandaRepository.getAll()) {
			DentoStrategia strategia = strategiaMap.get(bevanda.getNome());
			bevanda.setStrategia(strategia);
		}
		
		this.clienteRepository = clienteRepository;
		this.bevandaRepository = bevandaRepository;
		this.acquistoRepository = acquistoRepository;
		this.userInteractor = userInteractor;
	}
	
	public Collection<Cliente> getClienti() {
		return clienteRepository.getAll();
	}
	
	public Collection<Bevanda> getBevande() {
		return bevandaRepository.getAll();
	}
	
	public float getPrezzoBevanda(Bevanda bevanda, Cliente cliente) {
		if (bevanda == null || cliente == null)
			throw new IllegalArgumentException("null args");
		
		return bevanda.getPrezzo(acquistoRepository.getAll(), cliente);
	}
	
	public void acquistaBevanda(Bevanda bevanda, Cliente cliente) {
		if (bevanda == null || cliente == null)
			throw new IllegalArgumentException("null args");
		
		Acquisto acquisto = new Acquisto(cliente, bevanda, getNow(), getPrezzoBevanda(bevanda, cliente));
		try {
			acquistoRepository.add(acquisto);
			userInteractor.showMessage("Bevanda erogata");
		} catch (Exception e) {
			userInteractor.showMessage("Errore durante l'esecuzione dell'acquisto");
		}
	}
	
	protected Date getNow() {
		return new Date();
	}
}
