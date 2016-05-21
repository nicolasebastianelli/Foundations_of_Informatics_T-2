package zannonia.model.routing;

import java.util.Comparator;


public class DistanceRouteComparator implements Comparator<Percorso>
{
	@Override
	public int compare(Percorso r1, Percorso r2)
	{
		return (int) (r1.getDistanza() * 100 - r2.getDistanza() * 100);
	}

}
