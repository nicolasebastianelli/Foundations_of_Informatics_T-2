package doc;

import doc.model.*;
import doc.persistence.*;
import doc.ui.*;

public class Program {

	public static void main(String[] args) {
		UserInteractor userInteractor = new SwingUserInteractor();

		Repository<Cliente> clienteRepository = null;
		try {
			clienteRepository = new TextClienteRepository("Clienti.txt");
		} catch (Exception e) {
			userInteractor.showMessage(e.getMessage());
			userInteractor.shutDownApplication();
		}

		Repository<Bevanda> bevandaRepository = null;
		try {
			bevandaRepository = new TextBevandaRepository("Bevande.txt");
		} catch (Exception e) {
			userInteractor.showMessage(e.getMessage());
			userInteractor.shutDownApplication();
		}

		Repository<DentoStrategia> strategiaRepository = null;
		try {
			strategiaRepository = new TextStrategieRepository("Strategie.txt");
		} catch (Exception e) {
			userInteractor.showMessage(e.getMessage());
			userInteractor.shutDownApplication();
		}

		AcquistoRepository acquistoRepository = null;
		try {
			acquistoRepository = new BinAcquistoRepository("Acquisti.dat");
		} catch (Exception e) {
			userInteractor.showMessage(e.getMessage());
			userInteractor.shutDownApplication();
		}

		MacchinaController controller = new MacchinaController(
				clienteRepository, 
				bevandaRepository, 
				strategiaRepository, 
				acquistoRepository,
				userInteractor);

		MainFrame frame = new MainFrame(controller);
		frame.setVisible(true);
	}

}
