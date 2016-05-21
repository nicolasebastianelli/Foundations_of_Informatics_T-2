package ed.model;

public class TariffaAConsumo extends Tariffa
{
	private double sogliaMensile, prezzoKWh, prezzoKWhExtra;

	public TariffaAConsumo(String nome, double prezzoKWh, double sogliaMensile, double prezzoKWhExtra)
	{
		super(nome);
		this.prezzoKWh = prezzoKWh;
		this.prezzoKWhExtra = prezzoKWhExtra;
		this.sogliaMensile = sogliaMensile;
	}

	public TariffaAConsumo(String nome, double prezzoKWh)
	{
		this(nome, prezzoKWh, Double.POSITIVE_INFINITY, 0);
	}

	public boolean hasSogliaMensile()
	{
		return sogliaMensile != Double.POSITIVE_INFINITY;
	}

	public double getSogliaMensile()
	{
		if (!hasSogliaMensile())
			throw new IllegalStateException();

		return sogliaMensile;
	}

	public double getPrezzoKWh()
	{
		return prezzoKWh;
	}

	public double getPrezzoKWhExtra()
	{
		return prezzoKWhExtra;
	}

	@Override
	public Bolletta creaBolletta(Utente utente, int mese, int anno, double consumo)
	{
		Bolletta bolletta = new Bolletta(utente, mese, anno, getNome(), consumo);

		double costo;
		if (hasSogliaMensile())
		{
			double costoInSoglia = prezzoKWh * Math.min(sogliaMensile, consumo) * (1 + STIMA_ALTRI_ONERI / 100);
			double consumoExtraSoglia = consumo - sogliaMensile;
			if (consumoExtraSoglia < 0)
			{
				consumoExtraSoglia = 0;
			}
			double costoExtraSoglia = prezzoKWhExtra * consumoExtraSoglia * (1 + STIMA_ALTRI_ONERI / 100);
			costo = (costoInSoglia + costoExtraSoglia);
			bolletta.addLineaBolletta("Costo energia entro soglia", costoInSoglia);
			bolletta.addLineaBolletta("Costo energia extra soglia", costoExtraSoglia);
		}
		else
		{
			costo = prezzoKWh * consumo * (1 + STIMA_ALTRI_ONERI / 100);
			bolletta.addLineaBolletta("Costo energia", costo);
		}

		double accise = calcAccise(consumo);
		double iva = calcIVA(costo + accise);
		bolletta.addLineaBolletta("Corrispettivo per accise", accise);		
		bolletta.addLineaBolletta("Corrispettivo per IVA", iva);
		bolletta.addLineaBolletta("Totale Bolletta", costo + accise + iva);

		return bolletta;
	}
}
