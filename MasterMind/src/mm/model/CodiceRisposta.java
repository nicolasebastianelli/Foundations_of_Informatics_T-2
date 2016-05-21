package mm.model;

import java.io.Serializable;

public class CodiceRisposta implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Codice tentativo;
	private Risposta risposta;
	
	public CodiceRisposta(Codice tentativo, Risposta risposta)
	{
		this.tentativo = tentativo;
		this.risposta = risposta;
	}
	
	public Codice getTentativo()
	{
		return tentativo;
	}
	
	public Risposta getRisposta()
	{
		return risposta;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof CodiceRisposta))
				return false;
		
		CodiceRisposta other = (CodiceRisposta) o;
		
		return getTentativo().equals(other.getTentativo()) && getRisposta().equals(other.getRisposta());
	}
}
