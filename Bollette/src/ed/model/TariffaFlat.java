package ed.model;

public class TariffaFlat extends Tariffa
{
	private double sogliaMensile, quotaFissaMensile, prezzoKWhExtra;
	
	public TariffaFlat(String nome, double quotaFissaMensile, double sogliaMensile, double prezzoKWhExtra)
	{
		super(nome);
		this.quotaFissaMensile = quotaFissaMensile;
		this.sogliaMensile = sogliaMensile;
		this.prezzoKWhExtra = prezzoKWhExtra;
	}

	public double getSogliaMensile()
	{
		return sogliaMensile;
	}

	public double getQuotaFissaMensile()
	{
		return quotaFissaMensile;
	}

	public double getPrezzoKWhExtra()
	{
		return prezzoKWhExtra;
	}

	@Override
	public Bolletta creaBolletta(Utente utente, int mese, int anno, double consumo)
	{	
		double costoInSoglia = getQuotaFissaMensile();
		double consumoExtraSoglia = consumo - sogliaMensile;
		if (consumoExtraSoglia < 0)
		{
			consumoExtraSoglia = 0;
		}
		double costoExtraSoglia = getPrezzoKWhExtra() * consumoExtraSoglia;
		double costo = costoInSoglia + costoExtraSoglia;
		
		Bolletta bolletta = new Bolletta(utente, mese, anno, getNome(), consumo);
		bolletta.addLineaBolletta("Quota fissa mensile", costoInSoglia);
		bolletta.addLineaBolletta("Costo energia extra soglia", costoExtraSoglia);
		double accise = calcAccise(consumo);
		bolletta.addLineaBolletta("Corrispettivo per accise", accise);		
		double iva = calcIVA(costo + accise);
		bolletta.addLineaBolletta("Corrispettivo per IVA", iva);
		bolletta.addLineaBolletta("Totale Bolletta", costo + accise + iva);
		
		return bolletta;
	}

}
