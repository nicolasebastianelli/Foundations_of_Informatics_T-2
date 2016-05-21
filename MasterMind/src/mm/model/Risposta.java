package mm.model;

import java.io.Serializable;

public class Risposta implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int rightPlaceCount;
	private int presentButWrongPlaceCount;

	public Risposta(int rightPlaceCount, int presentButWrongPlaceCount)
	{		
		this.rightPlaceCount = rightPlaceCount;
		this.presentButWrongPlaceCount = presentButWrongPlaceCount;
	}
	
	public int getCount()
	{
		return rightPlaceCount + presentButWrongPlaceCount;
	}
	
	public ColoreRisposta getColoreRisposta(int index)
	{
		if (index < rightPlaceCount)
		{
			return ColoreRisposta.Nero;
		}
		if (index < rightPlaceCount + presentButWrongPlaceCount)
		{
			return ColoreRisposta.Bianco;
		}
		throw new ArrayIndexOutOfBoundsException();
	}
	
	public boolean allMatch()
	{
		return rightPlaceCount == Configuration.LunghezzaCodice;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Risposta))
			return false;
		
		Risposta other = (Risposta) o;
		
		return rightPlaceCount == other.rightPlaceCount && 
			presentButWrongPlaceCount == other.presentButWrongPlaceCount;
	}
}
