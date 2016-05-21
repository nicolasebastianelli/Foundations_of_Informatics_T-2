package ed.model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class Bolletta
{
	private final static String[] NomiMesi = { "nil", "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
		"Settembre", "Ottobre", "Novembre", "Dicembre" };
	
	private Utente utente;
	private int mese, anno;
	private String nomeTariffa;
	private double consumo;
	private ArrayList<LineaBolletta> lineeBolletta = new ArrayList<LineaBolletta>(); 

	public Bolletta(Utente utente, int mese, int anno, String nomeTariffa, double consumo)
	{
		this.utente = utente;
		this.nomeTariffa = nomeTariffa;
		this.mese = mese;
		this.anno = anno;
		this.consumo = consumo;
	}
	
	public Utente getUtente()
	{
		return utente;
	}
	
	public String getNomeTariffa()
	{
		return nomeTariffa;
	}
	
	public int getMese()
	{
		return mese;
	}
	
	public int getAnno()
	{
		return anno;
	}
	
	public double getConsumo()
	{
		return consumo;
	}
	
	public void addLineaBolletta(LineaBolletta lineaBolletta)
	{
		lineeBolletta.add(lineaBolletta);
	}
	
	public void addLineaBolletta(String voce, double valore)
	{
		lineeBolletta.add(new LineaBolletta(voce, valore));
	}
	
	public List<LineaBolletta> getLineeBolletta()
	{
		return new ArrayList<LineaBolletta>(lineeBolletta);
	}
	
	private String linea = "----------------------------------------------------------";
	private String doppialinea = "==========================================================";

	public String toString()
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		stampa(pw);
		pw.close();
		return sw.toString();
	}

	private String format(double valore)
	{
		return String.format("%5.2f", valore);
	}

	public void stampa(PrintWriter pw)
	{
		pw.println(doppialinea);
		pw.println("ElettroDent SpA – Energia che illumina");

		pw.print("Sig. ");
		pw.println(utente);
		
		pw.print("Bolletta di ");
		pw.print(NomiMesi[mese]);
		pw.print(" ");
		pw.println(anno);
		
		pw.println(linea);
		
		pw.print("Tariffa ");
		pw.print(nomeTariffa);
		pw.print(", Consumo KWh ");
		pw.println(consumo);
		
		pw.println(linea);
		pw.println("Dettaglio importi:");
		for (LineaBolletta lineaBolletta : lineeBolletta)
		{
			pw.println(lineaBolletta.getVoce() + " € " + format(lineaBolletta.getValore()));
		}
		
//		pw.println("Corrispettivo per consumi o quota fissa mensile:   € " + format(calcCostoEnergia()));
//		// pw.println("Corrispettivo per consumi oltre quota (se previsti): € "
//		// + calcCostoEnergia());
//		pw.println("Corrispettivo per accise:                          € " + format(calcAccise()));
//		pw.println("IVA " + Tariffa.ALIQUOTA_IVA + "% sul subtotale di € " + format(calcSubTotale()) + ":                € "
//				+ format(calcIVA()));
		pw.println(linea);
	}

}
