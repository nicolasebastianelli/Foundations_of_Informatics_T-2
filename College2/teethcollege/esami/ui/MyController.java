package teethcollege.esami.ui;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import teethcollege.model.*;
import teethcollege.persistence.EsamiManager;

public class MyController extends Controller {

	private EsamiManager esamiManager;

	public MyController(String nomeFileInsegnamenti, String nomeFileCarriere, UserInteractor userInteractor,
			EsamiManager esamiManager) {
		super(nomeFileInsegnamenti, nomeFileCarriere, userInteractor);
		this.esamiManager = esamiManager;
	}

	@Override
	public List<Esame> getEsami(String matricola) {
		String filename = matricola + ".txt";
		File file = new File(filename);
		if (!file.exists()) {
			// JOptionPane.showMessageDialog(null, "Lo studente " + matricola +
			// " non ha ancora sostenuto esami");
			return new ArrayList<Esame>(); // restituisco una lista vuota
		}
		// else
		try {
			List<Esame> listaEsamiStudente = esamiManager.caricaEsami(new FileReader(filename), getMappaInsegnamenti());
			return listaEsamiStudente;
		} catch (IOException e1) {
			// non dovrebbe mai accadere...!
			getUserInteractor().showMessage("Impossibile caricare esami per lo studente " + matricola);
			return null;
		}
	}

	@Override
	protected void salvaEsami(String matricola, List<Esame> esami) {
		String filename = matricola + ".txt";
		try {
			esamiManager.salvaEsami(new FileWriter(filename), esami); // NO
																		// ppend
																		// mode
		} catch (IOException e1) {
			// non dovrebbe mai accadere...!
			getUserInteractor().showMessage("Impossibile salvare esami per lo studente " + matricola);
		}
	}

	public Esame getNuovoEsame(Insegnamento corso, String votoScelto, String dataScelta) {
		int voto = votoScelto.equals("30L") ? 31 : Integer.parseInt(votoScelto);
		Date dataEsame = null;
		try {
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
			dataEsame = formatter.parse(dataScelta);
			Esame esame = new Esame(corso, voto, dataEsame);
			return esame;

		} catch (ParseException e1) {
			getUserInteractor().showMessage("Data esame non valida: " + dataScelta);
			return null;
		}
	}

	@Override
	public Carriera aggiungiEsame(Insegnamento corso, String votoScelto, String dataScelta, PianoDiStudi prescelto) {
		List<Esame> esami = getEsami(prescelto.getMatricola());
		Carriera c = new Carriera(prescelto, esami);
		Esame nuovoEsame = getNuovoEsame(corso, votoScelto, dataScelta);
		if (nuovoEsame != null) {
			try {
				c.addEsame(nuovoEsame);
				salvaEsami(prescelto.getMatricola(), c.getEsamiSostenuti());
			} catch (IllegalArgumentException e2) {
				getUserInteractor().showMessage(e2.getMessage());
			}
		}
		return c;
	}

}
