package mm.model;

import java.util.ArrayList;

public class MasterMindAlgorithm implements MatchingAlgorithm
{
	private static final long serialVersionUID = 1L;

	@Override
	public Risposta match(Codice segreto, Codice tentativo)
	{
		if (segreto.getCount() != tentativo.getCount())
			throw new IllegalArgumentException();
		
		ArrayList<Colore> coloreList = new ArrayList<Colore>();
		for (int i = 0; i < tentativo.getCount(); i++)
		{
			coloreList.add(tentativo.getColore(i));
		}
		
		int rightPlaceCount = 0;
		int presentCount = 0;
		for (int i = 0; i < segreto.getCount(); i++)
		{
			Colore coloreSegreto = segreto.getColore(i);
			if (coloreSegreto == tentativo.getColore(i))
			{
				rightPlaceCount++;
			}
			
			int idx = coloreList.indexOf(coloreSegreto);
			if (idx >= 0)
			{
				presentCount++;
				coloreList.remove(idx);
			}
		}
		
		return new Risposta(rightPlaceCount, presentCount - rightPlaceCount);
	}

}
