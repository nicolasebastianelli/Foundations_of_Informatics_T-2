package zannonia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tratta
{
	private String id;
	private double pedaggio;
	private double lunghezza;
	private List<Casello> caselli;
	private Set<Autostrada> autostrade;

	public Tratta(String id, double pedaggio, double lunghezza)
	{
		this.id = id;
		this.pedaggio = pedaggio;
		this.lunghezza = lunghezza;
		this.caselli = new ArrayList<Casello>();
		this.autostrade = new HashSet<Autostrada>();
	}
	
	public String getId()
	{
		return id;
	}
	
	public double getPedaggio()
	{
		return pedaggio;
	}
	
	public double getLunghezza()
	{
		return lunghezza;
	}
	
	public void addCasello(Casello casello)
	{
		if (caselli.contains(casello))
			throw new IllegalArgumentException("Casello già presente");
		
		caselli.add(casello);
		casello.setTratta(this);
	}
	
	public List<Casello> getCaselli()
	{
		return Collections.unmodifiableList(caselli);
	}
	
	public void addAutostrada(Autostrada autostrada)
	{
		if (autostrade.add(autostrada))
		{
			autostrada.addTratta(this);
		}
	}
	
	public Set<Autostrada> getAutostrade()
	{
		return Collections.unmodifiableSet(autostrade);
	}
	
	public boolean isTrattaInterscambio()
	{
		return autostrade.size() > 1;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < caselli.size(); ++i)
		{
			Casello casello = caselli.get(i);
			sb.append(casello);
			if (i < caselli.size() - 1)
			{
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}
