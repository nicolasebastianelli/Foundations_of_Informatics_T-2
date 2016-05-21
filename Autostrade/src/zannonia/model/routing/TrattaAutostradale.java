package zannonia.model.routing;

import zannonia.model.Autostrada;
import zannonia.model.Tratta;

public class TrattaAutostradale
{
	private Tratta tratta;
	private Autostrada autostrada;

	public TrattaAutostradale(Tratta tratta, Autostrada autostrada)
	{
		this.tratta = tratta;
		this.autostrada = autostrada;
	}
	
	public Tratta getTratta()
	{
		return tratta;
	}
	
	public Autostrada getAutostrada()
	{
		return autostrada;
	}
}