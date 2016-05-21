package pharmame.persistence.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;

import org.junit.Test;

import pharmame.model.Farmaco;
import pharmame.persistence.AifaCSVFarmacoReader;
import pharmame.persistence.BadFileFormatException;

public class AifaCSVFarmacoReaderTests {

	@Test
	public void testReadFrom() throws BadFileFormatException, IOException {
		String toParse = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC;Codice Gruppo Equivalenza;X=in lista di trasparenza Aifa 15/01/14;Solo in lista di Regione:;Metri cubi ossigeno\n" + 
				         "Acarbosio;ACARBOSIO 100MG 40 UNITA' USO ORALE;ACARPHAGE*40 cpr 100 mg;5,63;MERCK SERONO SpA;038835144;H1A;X;;\n" +
	  					 "Ropinirolo;ROPINIROLO 0,5MG 21 UNITA' USO ORALE;ROPINIROLO*21 cpr riv 0,5 mg;4,50;EG SpA;038428823;E6B;X;;\n" +
						 "Esomeprazolo;ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE;NEXIUM*os grat gastrores 28 bust 10 mg;18,42;ASTRAZENECA SpA;034972556;CVC;;\"Lazio,Molise,Campania,Sicilia\";\n";
		
		AifaCSVFarmacoReader r = new AifaCSVFarmacoReader();
		StringReader textReader = new StringReader(toParse);
		Collection<Farmaco> coll = r.readFrom(textReader);
		
		assertEquals(3, coll.size());
		Farmaco[] ff = coll.toArray(new Farmaco[0]);
		
		Farmaco f = ff[0];
		assertEquals("Acarbosio", f.getPrincipioAttivo());
		assertEquals("ACARBOSIO 100MG 40 UNITA' USO ORALE", f.getGruppoEquivalenza());
		assertEquals("ACARPHAGE", f.getNome());
		assertEquals("40 cpr 100 mg", f.getConfezione());
		assertEquals(5.63, f.getPrezzo(), 0.001);
		assertEquals("MERCK SERONO SpA", f.getDitta());
		assertEquals(38835144, f.getCodice());
		
		f = ff[1];
		assertEquals("Ropinirolo", f.getPrincipioAttivo());
		assertEquals("ROPINIROLO 0,5MG 21 UNITA' USO ORALE", f.getGruppoEquivalenza());
		assertEquals("ROPINIROLO", f.getNome());
		assertEquals("21 cpr riv 0,5 mg", f.getConfezione());
		assertEquals(4.5, f.getPrezzo(), 0.001);
		assertEquals("EG SpA", f.getDitta());
		assertEquals(38428823, f.getCodice());
		
		f = ff[2];
		assertEquals("Esomeprazolo", f.getPrincipioAttivo());
		assertEquals("ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE", f.getGruppoEquivalenza());
		assertEquals("NEXIUM", f.getNome());
		assertEquals("os grat gastrores 28 bust 10 mg", f.getConfezione());
		assertEquals(18.42, f.getPrezzo(), 0.001);
		assertEquals("ASTRAZENECA SpA", f.getDitta());
		assertEquals(34972556, f.getCodice());		
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_MissingStarSeparator() throws BadFileFormatException, IOException {
		String toParse = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC;Codice Gruppo Equivalenza;X=in lista di trasparenza Aifa 15/01/14;Solo in lista di Regione:;Metri cubi ossigeno\n" + 
				         "Acarbosio;ACARBOSIO 100MG 40 UNITA' USO ORALE;ACARPHAGE40 cpr 100 mg;5,63;MERCK SERONO SpA;038835144;H1A;X;;\n" +
	  					 "Ropinirolo;ROPINIROLO 0,5MG 21 UNITA' USO ORALE;ROPINIROLO*21 cpr riv 0,5 mg;4,50;EG SpA;038428823;E6B;X;;\n" +
						 "Esomeprazolo;ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE;NEXIUM*os grat gastrores 28 bust 10 mg;18,42;ASTRAZENECA SpA;034972556;CVC;;\"Lazio,Molise,Campania,Sicilia\";\n";
		
		AifaCSVFarmacoReader r = new AifaCSVFarmacoReader();
		StringReader textReader = new StringReader(toParse);
		r.readFrom(textReader);
	}

	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_MissingPrincipioAttivo() throws BadFileFormatException, IOException {
		String toParse = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC;Codice Gruppo Equivalenza;X=in lista di trasparenza Aifa 15/01/14;Solo in lista di Regione:;Metri cubi ossigeno\n" + 
				         "Acarbosio;ACARBOSIO 100MG 40 UNITA' USO ORALE;ACARPHAGE*40 cpr 100 mg;5,63;MERCK SERONO SpA;038835144;H1A;X;;\n" +
	  					 "      ;ROPINIROLO 0,5MG 21 UNITA' USO ORALE;ROPINIROLO*21 cpr riv 0,5 mg;4,50;EG SpA;038428823;E6B;X;;\n" +
						 "Esomeprazolo;ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE;NEXIUM*os grat gastrores 28 bust 10 mg;18,42;ASTRAZENECA SpA;034972556;CVC;;\"Lazio,Molise,Campania,Sicilia\";\n";
		
		AifaCSVFarmacoReader r = new AifaCSVFarmacoReader();
		StringReader textReader = new StringReader(toParse);
		r.readFrom(textReader);
	}

	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_MissingGruppoEquivalenza() throws BadFileFormatException, IOException {
		String toParse = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC;Codice Gruppo Equivalenza;X=in lista di trasparenza Aifa 15/01/14;Solo in lista di Regione:;Metri cubi ossigeno\n" + 
				         "Acarbosio;     ;ACARPHAGE*40 cpr 100 mg;5,63;MERCK SERONO SpA;038835144;H1A;X;;\n" +
	  					 "Ropinirolo;ROPINIROLO 0,5MG 21 UNITA' USO ORALE;ROPINIROLO*21 cpr riv 0,5 mg;4,50;EG SpA;038428823;E6B;X;;\n" +
						 "Esomeprazolo;ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE;NEXIUM*os grat gastrores 28 bust 10 mg;18,42;ASTRAZENECA SpA;034972556;CVC;;\"Lazio,Molise,Campania,Sicilia\";\n";
		
		AifaCSVFarmacoReader r = new AifaCSVFarmacoReader();
		StringReader textReader = new StringReader(toParse);
		r.readFrom(textReader);
	}

	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_MissingPrezzo() throws BadFileFormatException, IOException {
		String toParse = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC;Codice Gruppo Equivalenza;X=in lista di trasparenza Aifa 15/01/14;Solo in lista di Regione:;Metri cubi ossigeno\n" + 
				         "Acarbosio;ACARBOSIO 100MG 40 UNITA' USO ORALE;ACARPHAGE*40 cpr 100 mg;    ;MERCK SERONO SpA;038835144;H1A;X;;\n" +
	  					 "Ropinirolo;ROPINIROLO 0,5MG 21 UNITA' USO ORALE;ROPINIROLO*21 cpr riv 0,5 mg;4,50;EG SpA;038428823;E6B;X;;\n" +
						 "Esomeprazolo;ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE;NEXIUM*os grat gastrores 28 bust 10 mg;18,42;ASTRAZENECA SpA;034972556;CVC;;\"Lazio,Molise,Campania,Sicilia\";\n";
		
		AifaCSVFarmacoReader r = new AifaCSVFarmacoReader();
		StringReader textReader = new StringReader(toParse);
		r.readFrom(textReader);
	}

	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_PrezzoMalformed() throws BadFileFormatException, IOException {
		String toParse = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC;Codice Gruppo Equivalenza;X=in lista di trasparenza Aifa 15/01/14;Solo in lista di Regione:;Metri cubi ossigeno\n" + 
				         "Acarbosio;ACARBOSIO 100MG 40 UNITA' USO ORALE;ACARPHAGE*40 cpr 100 mg;xxyy;MERCK SERONO SpA;038835144;H1A;X;;\n" +
	  					 "Ropinirolo;ROPINIROLO 0,5MG 21 UNITA' USO ORALE;ROPINIROLO*21 cpr riv 0,5 mg;4,50;EG SpA;038428823;E6B;X;;\n" +
						 "Esomeprazolo;ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE;NEXIUM*os grat gastrores 28 bust 10 mg;18,42;ASTRAZENECA SpA;034972556;CVC;;\"Lazio,Molise,Campania,Sicilia\";\n";
		
		AifaCSVFarmacoReader r = new AifaCSVFarmacoReader();
		StringReader textReader = new StringReader(toParse);
		r.readFrom(textReader);
	}

	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_MissingDitta() throws BadFileFormatException, IOException {
		String toParse = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC;Codice Gruppo Equivalenza;X=in lista di trasparenza Aifa 15/01/14;Solo in lista di Regione:;Metri cubi ossigeno\n" + 
				         "Acarbosio;ACARBOSIO 100MG 40 UNITA' USO ORALE;ACARPHAGE*40 cpr 100 mg;5,63;   ;038835144;H1A;X;;\n" +
	  					 "Ropinirolo;ROPINIROLO 0,5MG 21 UNITA' USO ORALE;ROPINIROLO*21 cpr riv 0,5 mg;4,50;EG SpA;038428823;E6B;X;;\n" +
						 "Esomeprazolo;ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE;NEXIUM*os grat gastrores 28 bust 10 mg;18,42;ASTRAZENECA SpA;034972556;CVC;;\"Lazio,Molise,Campania,Sicilia\";\n";
		
		AifaCSVFarmacoReader r = new AifaCSVFarmacoReader();
		StringReader textReader = new StringReader(toParse);
		r.readFrom(textReader);
	}

	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_MissingCodice() throws BadFileFormatException, IOException {
		String toParse = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC;Codice Gruppo Equivalenza;X=in lista di trasparenza Aifa 15/01/14;Solo in lista di Regione:;Metri cubi ossigeno\n" + 
				         "Acarbosio;ACARBOSIO 100MG 40 UNITA' USO ORALE;ACARPHAGE*40 cpr 100 mg;5,63;MERCK SERONO SpA;  ;H1A;X;;\n" +
	  					 "Ropinirolo;ROPINIROLO 0,5MG 21 UNITA' USO ORALE;ROPINIROLO*21 cpr riv 0,5 mg;4,50;EG SpA;038428823;E6B;X;;\n" +
						 "Esomeprazolo;ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE;NEXIUM*os grat gastrores 28 bust 10 mg;18,42;ASTRAZENECA SpA;034972556;CVC;;\"Lazio,Molise,Campania,Sicilia\";\n";
		
		AifaCSVFarmacoReader r = new AifaCSVFarmacoReader();
		StringReader textReader = new StringReader(toParse);
		r.readFrom(textReader);
	}

	@Test(expected=BadFileFormatException.class)
	public void testReadFrom_CodiceMalformed() throws BadFileFormatException, IOException {
		String toParse = "Principio Attivo;Descrizione Gruppo Equivalenza;Denominazione e Confezione;Prezzo al pubblico €;Ditta;Codice AIC;Codice Gruppo Equivalenza;X=in lista di trasparenza Aifa 15/01/14;Solo in lista di Regione:;Metri cubi ossigeno\n" + 
				         "Acarbosio;ACARBOSIO 100MG 40 UNITA' USO ORALE;ACARPHAGE*40 cpr 100 mg;5,63;MERCK SERONO SpA;xxyy;H1A;X;;\n" +
	  					 "Ropinirolo;ROPINIROLO 0,5MG 21 UNITA' USO ORALE;ROPINIROLO*21 cpr riv 0,5 mg;4,50;EG SpA;038428823;E6B;X;;\n" +
						 "Esomeprazolo;ESOMEPRAZOLO 10MG 28 UNITA' USO ORALE;NEXIUM*os grat gastrores 28 bust 10 mg;18,42;ASTRAZENECA SpA;034972556;CVC;;\"Lazio,Molise,Campania,Sicilia\";\n";
		
		AifaCSVFarmacoReader r = new AifaCSVFarmacoReader();
		StringReader textReader = new StringReader(toParse);
		r.readFrom(textReader);
	}

}
