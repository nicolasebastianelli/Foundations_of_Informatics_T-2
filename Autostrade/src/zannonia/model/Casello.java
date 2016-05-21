package zannonia.model;

public class Casello
{
	private String nome;
	private Tratta tratta;

	public Casello(String nome)
	{
		this.nome = nome;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public void setTratta(Tratta tratta)
	{
		this.tratta = tratta;
	}
	
	public Tratta getTratta()
	{
		return tratta;
	}
	
	@Override
	public String toString()
	{
		return nome;
	}
	
}
