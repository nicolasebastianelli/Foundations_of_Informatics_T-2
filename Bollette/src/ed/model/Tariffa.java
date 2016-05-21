package ed.model;

public abstract class Tariffa
{
	public static final double VALORE_ACCISA = 2.27 / 100; // 2.27 cents = 0.0227 E
	public static final double SOGLIA_ACCISA = 150;
	public static final double STIMA_ALTRI_ONERI = 50;
	public static final double ALIQUOTA_IVA = 10;

	private String nome;

	public Tariffa(String nome)
	{
		this.nome = nome;
	}

	public String getNome()
	{
		return nome;
	}
	
	public String toString()
	{
		return getNome();
	}

	protected double calcAccise(double consumo)
	{
		return consumo > Tariffa.SOGLIA_ACCISA ? Tariffa.VALORE_ACCISA * (consumo - Tariffa.SOGLIA_ACCISA) : 0;
	}

	protected double calcIVA(double costo)
	{
		return Tariffa.ALIQUOTA_IVA / 100 * costo;
	}

	public abstract Bolletta creaBolletta(Utente utente, int mese, int anno, double consumo);
}
