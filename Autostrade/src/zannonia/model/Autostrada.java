package zannonia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Autostrada
{
	private String nome;
	private List<Tratta> tratte;

	public Autostrada(String nome)
	{
		this.nome = nome;
		this.tratte = new ArrayList<Tratta>();
	}

	public String getNome()
	{
		return nome;
	}

	public void addTratta(Tratta tratta)
	{
		if (!tratte.contains(tratta))
		{
			tratte.add(tratta);
			tratta.addAutostrada(this);
		}
	}

	public Tratta getNext(Tratta current, boolean ascending)
	{
		int index = tratte.indexOf(current);
		if (index < 0)
			throw new IllegalArgumentException("tratta non presente");
		
		index += ascending ? 1 : -1;
		if (index >= 0 && index < tratte.size())
		{
			return tratte.get(index);
		}
		return null;
	}
	
	public List<Tratta> getTratte()
	{
		return Collections.unmodifiableList(tratte);
	}
	
	@Override
	public String toString()
	{
		return getNome();
	}

}
