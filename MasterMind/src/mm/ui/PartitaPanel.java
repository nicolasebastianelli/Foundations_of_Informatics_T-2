package mm.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import mm.model.CodiceRisposta;
import mm.model.Configuration;

public class PartitaPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private CreaRispostaPanel creaRispostaView;

	public PartitaPanel(AbstractController controller)
	{
		setLayout(new GridLayout(Configuration.GiocateMassime + 1, 1));

		creaRispostaView = new CreaRispostaPanel(controller);
	}
	
	public void addCodiceRisposta(CodiceRisposta codiceRisposta)
	{
		int insertIndex = getComponentCount() - 1;
		add(new CodiceRispostaPanel(codiceRisposta), insertIndex);
		revalidate();		
	}
	
	public void reset()
	{		
		removeAll();
		add(creaRispostaView);
		revalidate();
	}
}
