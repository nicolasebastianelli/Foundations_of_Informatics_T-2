package zannonia.model.routing;

import java.util.ArrayList;
import java.util.Iterator;

import zannonia.model.*;


public class Percorso implements Iterable<TrattaAutostradale>
{
	private ArrayList<TrattaAutostradale> trattePercorse = new ArrayList<TrattaAutostradale>();

	public Percorso()
	{
	}

	public Percorso(Percorso fromRoute)
	{
		trattePercorse.addAll(fromRoute.trattePercorse);
	}

	public void addTrattaAutostradale(Tratta tratta, Autostrada autostrada)
	{
		trattePercorse.add(new TrattaAutostradale(tratta, autostrada));
	}

	@Override
	public Iterator<TrattaAutostradale> iterator()
	{
		return trattePercorse.iterator();
	}

	public boolean isHighwayTakenButNotCurrent(Autostrada autostrada)
	{
		ArrayList<Autostrada> autostrade = new ArrayList<Autostrada>();
		Autostrada previous = null;
		
		for (TrattaAutostradale trattaAuto : trattePercorse)
		{
			Autostrada current = trattaAuto.getAutostrada();
			if (current != previous)
			{
				if (autostrade.contains(current))
					return true;
				autostrade.add(current);
			}
			previous = current;
		}
		return false;
	}
	
	public int size()
	{
		return trattePercorse.size();
	}
	
	public TrattaAutostradale getTrattaAutostradale(int index)
	{
		return trattePercorse.get(index);
	}
	
	public double getDistanza()
	{
		double total = 0;
		for (TrattaAutostradale current : this)
		{
			total += current.getTratta().getLunghezza();
		}
		return Math2.round(total, 1);
	}
	
	public double getCosto()
	{
		double total = 0.0;
		for (TrattaAutostradale current : this)
		{
			total += current.getTratta().getPedaggio();
		}
		return Math2.round(total, 1);		
	}
}
