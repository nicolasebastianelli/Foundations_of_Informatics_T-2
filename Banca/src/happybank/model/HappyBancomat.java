package happybank.model;

public class HappyBancomat extends AbstractBancomat
{
	@Override
	public ImportoErogato erogaImporto(TesseraBancomat tessera, int importo, int taglio1, int taglio2) 
			throws ImportoNonErogabileException
	{		
		if (!tessera.checkMaxPrelevabile(importo))
			throw new ImportoNonErogabileException("La somma richiesta è superiore al massimo prelevabile: " + 
					tessera.getMaxPrelevabile());
		if (!tessera.checkPrelevamentoDalConto(importo))
			throw new ImportoNonErogabileException("La somma richiesta non è non è disponibile sul conto: " + 
					tessera.getContoCorrente().getSaldo());

		int numTaglio2 = 0, numTaglio1 = 0;
		int sommaTemp = importo;
		numTaglio1 = sommaTemp / taglio1;
		sommaTemp = sommaTemp % taglio1;
		while (sommaTemp != 0 && numTaglio1 >= 0)
		{
			numTaglio2 = sommaTemp / taglio2;
			sommaTemp  = sommaTemp % taglio2;

			if (sommaTemp > 0)
			{
				numTaglio1--;
				sommaTemp = importo - numTaglio1 * taglio1;
			}
		}
		
		if (sommaTemp != 0)
			throw new ImportoNonErogabileException("La somma richiesta non è componibile con i tagli presenti");
				
		tessera.prelievo(importo);
		
		return taglio1 < taglio2
				? erogaImporto(numTaglio1, numTaglio2)
				: erogaImporto(numTaglio2, numTaglio1);
	}
	
	private ImportoErogato erogaImporto(int numPiccoloTaglio, int numGrandeTaglio)
		throws ImportoNonErogabileException
	{
		if (numPiccoloTaglio > getNumPiccoloTaglio())
			throw new ImportoNonErogabileException("Il numero di banconote di piccolo taglio presenti non consente di soddisfare la richiesta");
	
		if (numGrandeTaglio > getNumGrandeTaglio())
			throw new ImportoNonErogabileException("Il numero di banconote di grande taglio presenti non consente di soddisfare la richiesta");		

		setNumPiccoloTaglio(getNumPiccoloTaglio() - numPiccoloTaglio);
		setNumGrandeTaglio(getNumGrandeTaglio() - numGrandeTaglio);		
		return new ImportoErogato(getPiccoloTaglio(), numPiccoloTaglio, getGrandeTaglio(), numGrandeTaglio);
	}

}
