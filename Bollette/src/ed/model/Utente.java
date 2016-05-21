package ed.model;

public class Utente
{
	private String codfisc, cognome, nome;

	public Utente(String codfisc, String cognome, String nome)
	{
		this.codfisc = codfisc;
		this.cognome = cognome;
		this.nome = nome;
	}

	String getCodFisc()
	{
		return codfisc;
	}

	String getCognome()
	{
		return cognome;
	}

	String getNome()
	{
		return nome;
	}

	public String toString()
	{
		return cognome + " " + nome + " (" + codfisc + ")";
	}
}
