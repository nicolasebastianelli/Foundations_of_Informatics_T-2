package compromeglio.ui;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import compromeglio.model.Bene;
import compromeglio.model.Categoria;
import compromeglio.model.Monitoraggio;
import compromeglio.persistence.BeniReader;
import compromeglio.persistence.RilevazioniReader;

public class MyController extends Controller {

	public MyController(BeniReader readerBeni, RilevazioniReader readerRilevazioni, UserInteractor userInteractor) {
		
		super(userInteractor);
		
		try {
			
			beni = readerBeni.caricaBeni();
			rilevazioni = readerRilevazioni.caricaRilevazioni(getMappaBeni());
			monitoraggio = new Monitoraggio(beni, rilevazioni);
			
		} catch (IOException e) {
			userInteractor.showMessage(e.getMessage());
		}
	}
	
	@Override
	public Set<Categoria> getCategorie() {
		
		Set<Categoria> result = new HashSet<Categoria>();
		for(Bene b : beni) {
			result.add(b.getCategoria());
		}
		return result;
	}
	
	@Override
	public Set<Bene> getBeniPerCategoria(Categoria c) {
		
		Set<Bene> result = new HashSet<Bene>();
		for(Bene b : beni) {
			if(b.getCategoria() == c) {
				result.add(b);
			}
		}
		return result;
	}
	

}
