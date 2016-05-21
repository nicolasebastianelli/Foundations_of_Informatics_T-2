package zannonia.model.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import zannonia.model.*;

public class CalcolatorePercorsi
{
	private ArrayList<Percorso> percorsoList;
	private Tratta trattaUscita;

	private static Comparator<Percorso> defaultComparator = new CostRouteComparator();

	public CalcolatorePercorsi()
	{
	}

	public List<Percorso> calcola(Casello caselloEntrata, Casello caselloUscita)
	{
		this.percorsoList = new ArrayList<Percorso>();

		Tratta trattaEntrata = caselloEntrata.getTratta();
		Set<Autostrada> autostradeEntrata = trattaEntrata.getAutostrade();
		trattaUscita = caselloUscita.getTratta();

		if (trattaEntrata == trattaUscita)
		{
			Percorso percorsoSingolaTratta = new Percorso();
			percorsoSingolaTratta.addTrattaAutostradale(trattaEntrata, autostradeEntrata.toArray(new Autostrada[0])[0]);
			percorsoList.add(percorsoSingolaTratta);
		}
		else
		{
			for (Autostrada autostradaEntrata : autostradeEntrata)
			{
				cloneAndGo(null, autostradaEntrata, trattaEntrata, true);
				cloneAndGo(null, autostradaEntrata, trattaEntrata, false);
			}

			Collections.sort(percorsoList, defaultComparator);
		}
		return percorsoList;
	}

	private void popolaPercorso(Percorso percorso, Autostrada autostradaCorrente, Tratta trattaCorrente, boolean ascending)
	{
		Tratta next = autostradaCorrente.getNext(trattaCorrente, ascending);
		if (next != null)
		{
			if (percorso.isHighwayTakenButNotCurrent(autostradaCorrente))
			{
				percorsoList.remove(percorso);
			}
			else
			{
				percorso.addTrattaAutostradale(next, autostradaCorrente);
				if (!next.equals(trattaUscita))
				{
					if (next.isTrattaInterscambio())
					{
						for (Autostrada attraversata : next.getAutostrade())
						{
							if (attraversata != autostradaCorrente)
							{
								cloneAndGo(percorso, attraversata, next, true);
								cloneAndGo(percorso, attraversata, next, false);
							}
						}
					}
					popolaPercorso(percorso, autostradaCorrente, next, ascending);
				}
			}
		}
		else
		{
			percorsoList.remove(percorso);
		}
	}

	private void cloneAndGo(Percorso route, Autostrada autostrada, Tratta tratta, boolean asc)
	{
		Percorso cloned = null;
		if (route == null)
		{
			cloned = new Percorso();
			cloned.addTrattaAutostradale(tratta, autostrada);
		}
		else
		{
			cloned = new Percorso(route);
		}
		percorsoList.add(cloned);
		popolaPercorso(cloned, autostrada, tratta, asc);
	}

	public static void printPercorso(Percorso percorso)
	{
		System.out.println("####");
		for (TrattaAutostradale ls : percorso)
		{
			System.out.println(ls.getAutostrada().getNome() + " -- " + ls.getTratta().getId());
		}
	}
}
