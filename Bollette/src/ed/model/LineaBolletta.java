package ed.model;

public class LineaBolletta
{
	private String voce;
	private double valore;

	public LineaBolletta(String voce, double valore)
	{
		this.voce = voce;
		this.valore = valore;
	}

	public String getVoce()
	{
		return voce;
	}

	public double getValore()
	{
		return valore;
	}
}
