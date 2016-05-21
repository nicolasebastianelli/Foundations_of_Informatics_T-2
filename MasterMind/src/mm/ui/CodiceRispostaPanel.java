package mm.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import mm.model.Codice;
import mm.model.CodiceRisposta;
import mm.model.Configuration;
import mm.model.Risposta;

public class CodiceRispostaPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private CodiceRisposta codiceRisposta;

	public CodiceRispostaPanel(CodiceRisposta codiceRisposta)
	{
		this.codiceRisposta = codiceRisposta;
		
		initGUI();
	}
	
	private void initGUI()
	{
//		setBorder(new EmptyBorder(3, 3, 3, 3));		
		Codice tentativo = codiceRisposta.getTentativo();
		setLayout(new GridLayout(1, tentativo.getCount() + 1));
		
		for (int i = 0; i < tentativo.getCount(); i++)
		{
			ColorPanel colorPanel = new ColorPanel(tentativo.getColore(i).getInnerColor());
			add(colorPanel);
		}
		
		Risposta risposta = codiceRisposta.getRisposta();
		JPanel rispostaPanel = new JPanel();
		{
			rispostaPanel.setLayout(new GridLayout(1, Configuration.LunghezzaCodice));
			for (int i = 0; i < Configuration.LunghezzaCodice; i++)
			{
				ColorPanel panel = i < risposta.getCount()
						? new ColorPanel(risposta.getColoreRisposta(i).getInnerColor())
						: new ColorPanel();
				rispostaPanel.add(panel);
			}			
		}
		add(rispostaPanel);
	}
	
	public CodiceRisposta getCodiceRisposta()
	{
		return codiceRisposta;
	}
}
