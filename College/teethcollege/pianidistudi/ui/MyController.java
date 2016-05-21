package teethcollege.pianidistudi.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import teethcollege.model.PianoDiStudi;
import teethcollege.model.Insegnamento;
import teethcollege.persistence.InsegnamentiReader;
import teethcollege.persistence.PianiReader;

public class MyController implements Controller {

	private Map<Long, Insegnamento> insegnamenti;
	private Collection<PianoDiStudi> pianiDiStudi;

	public MyController(InsegnamentiReader insegnamentiReader,
			PianiReader pianiReader, UserInteractor userInteractor) {
		try {
			insegnamenti = insegnamentiReader.caricaInsegnamenti();
			insegnamentiReader.close();

			pianiDiStudi = pianiReader.caricaPianiDiStudi(insegnamenti);
			pianiReader.close();
		} catch (IOException e) {
			userInteractor.showMessage("Errore nella lettura del file: " + e.getMessage());
			userInteractor.shutDownApplication();
		}

		ArrayList<PianoDiStudi> pianiScartati = new ArrayList<PianoDiStudi>();
		for (PianoDiStudi c : pianiDiStudi) {
			if (!c.isValid()) {
				pianiScartati.add(c);
			}
		}
		for (PianoDiStudi c : pianiScartati) {
			pianiDiStudi.remove(c);
		}
		if (pianiScartati.size() > 0) {
			userInteractor.showMessage("Piani di studio scartati: "
					+ pianiScartati);
		}
	}

	@Override
	public String sostituisci(PianoDiStudi piano, Insegnamento daTogliere,
			Insegnamento daMettere) {
		try {
			piano.sostituisciInsegnamento(daTogliere, daMettere);
			return null;
		} catch (IllegalArgumentException ex) {
			return ex.getMessage();
		}
	}

	@Override
	public Collection<PianoDiStudi> getPianiDiStudi() {
		return pianiDiStudi;
	}

	@Override
	public Collection<Insegnamento> getInsegnamenti() {
		return insegnamenti.values();
	}

}
