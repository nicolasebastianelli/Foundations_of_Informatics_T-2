package zannonia.model.routing;

import java.util.Comparator;


public class CostRouteComparator implements Comparator<Percorso>
{
	@Override
	public int compare(Percorso r1, Percorso r2)
	{
		return (int) (r1.getCosto() * 100 - r2.getCosto() * 100);
	}

}
