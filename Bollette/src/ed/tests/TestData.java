package ed.tests;

import ed.model.Utente;

public class TestData
{
	public final static String Nome = "Mario";
	public final static String Cognome = "Rossi";
	public final static String CF = "MRARSS76H12A944I";
	
	public final static Utente UtenteTest = new Utente(CF, Cognome, Nome);
	
	public final static int Mese = 05;
	public final static int Anno = 2012;
}
