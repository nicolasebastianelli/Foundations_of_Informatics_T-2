package compromeglio.tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import compromeglio.model.Categoria;
import compromeglio.model.Monitoraggio;
import compromeglio.model.NonRilevatoException;
import compromeglio.model.Rilevazione;
import compromeglio.model.Bene;


public class TestMonitoraggio {
	
	private static Bene[] beniDiProva;
	private static List<Rilevazione> rilevazioniDiProva;
	
	private static DateFormat df = 
			new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
	
	@BeforeClass
	public static void setup() throws ParseException {
		
		beniDiProva = new Bene[]{ 
				new Bene(11111, "Spaghetti Dentinia 3", Categoria.PASTA),
				new Bene(11112, "Spaghetti Dentinia 5", Categoria.PASTA),
				new Bene(11114, "Rigatoni Zannoni", Categoria.PASTA),
				new Bene(22222, "Pollo intero", Categoria.CARNE),
				new Bene(22223, "Mezzo pollo", Categoria.CARNE),
				new Bene(22224, "Pollo arrosto", Categoria.CARNE),
				new Bene(22225, "Petto di pollo", Categoria.CARNE),
				new Bene(22226, "Costata di manzo", Categoria.CARNE),
				new Bene(33333, "Filetti di merluzzo", Categoria.PESCE),
				new Bene(33334, "Trancio di pesce spada", Categoria.PESCE),
				new Bene(33335, "Orata", Categoria.PESCE)
				};
		
		rilevazioniDiProva = Arrays.asList( new Rilevazione[] {
				new Rilevazione(beniDiProva[0], "Market Dentinia" ,df.parse("18/01/2014"), 0.95f),
				new Rilevazione(beniDiProva[0], "Market Zannonia" ,df.parse("19/01/2014"), 1.05f),
				new Rilevazione(beniDiProva[0], "Market Sperduto" ,df.parse("19/01/2014"), 1.55f),
				new Rilevazione(beniDiProva[2], "Market Dentinia" ,df.parse("18/01/2014"), 1.30f),
				new Rilevazione(beniDiProva[2], "Market Zannonia" ,df.parse("19/01/2014"), 1.25f),
				new Rilevazione(beniDiProva[3], "Market Zannonia" ,df.parse("19/01/2014"), 3.25f),
				new Rilevazione(beniDiProva[3], "Market Sperduto" ,df.parse("19/01/2014"), 3.70f),
				new Rilevazione(beniDiProva[5], "Market Zannonia" ,df.parse("19/01/2014"), 4.25f),
				new Rilevazione(beniDiProva[5], "Market Sperduto" ,df.parse("19/01/2014"), 5.80f),
				new Rilevazione(beniDiProva[5], "Market Dentinia" ,df.parse("20/01/2014"), 4.10f),
				new Rilevazione(beniDiProva[8], "Market Dentinia" ,df.parse("20/01/2014"), 3.10f),
				new Rilevazione(beniDiProva[8], "Market Sperduto" ,df.parse("20/01/2014"), 4.80f)
		});
		
	}
	
	@Test
	public void testMonitoraggioCostruzioneArgomentoSingoloHaSuccesso() {
		new Monitoraggio(new HashSet<Bene>(Arrays.asList(beniDiProva)));
	}
	
	@Test
	public void testMonitoraggioCostruzioneArgomentoDoppioHaSuccesso() {
		new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
	}
	
	@Test
	public void testMonitoraggioGetBeneValidoResituisceCorrettoBene() {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)) );
		Bene b = m.getBene(22225);
		Assert.assertEquals(beniDiProva[6], b);
	}
	
	@Test
	public void testMonitoraggioGetBeneNonEsistenteRestituisceNull() {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)) );
		Bene b = m.getBene(99999);
		Assert.assertNull(b);
	}
	
	@Test
	public void testGetRilevazioniPerBeneValidoRestituisceListaCorretta() throws ParseException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		List<Rilevazione> rilevazioni = m.getRilevazioni(22222);
		Assert.assertEquals(Arrays.asList( new Rilevazione[] {
				new Rilevazione(beniDiProva[3], "Market Zannonia" ,df.parse("19/01/2014"), 3.25f),
				new Rilevazione(beniDiProva[3], "Market Sperduto" ,df.parse("19/01/2014"), 3.70f) 
		} ), rilevazioni);
	}
	
	@Test
	public void testGetRilevazioniPerBeneValidoSenzaRilevazioniRestituisceNull() throws ParseException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		List<Rilevazione> rilevazioni = m.getRilevazioni(11112);
		Assert.assertNull(rilevazioni);
	}
	
	@Test
	public void testGetRilevazioniPerBeneNonValidoRestituisceNull() {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		List<Rilevazione> rilevazioni = m.getRilevazioni(99999);
		Assert.assertNull(rilevazioni);
	}
	
	@Test
	public void testAggiungiRilevazionePerBeneGiaRilevato() throws ParseException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		List<Rilevazione> rilevazioni = m.getRilevazioni(22222);
		Assert.assertEquals(Arrays.asList( new Rilevazione[] {
				new Rilevazione(beniDiProva[3], "Market Zannonia" ,df.parse("19/01/2014"), 3.25f),
				new Rilevazione(beniDiProva[3], "Market Sperduto" ,df.parse("19/01/2014"), 3.70f) 
		} ), rilevazioni);
		
		m.addRilevazione( new Rilevazione(beniDiProva[3], "Market Pineto" ,df.parse("01/02/2014"), 7.77f) );
		
		rilevazioni = m.getRilevazioni(22222);
		Assert.assertEquals(Arrays.asList( new Rilevazione[] {
				new Rilevazione(beniDiProva[3], "Market Zannonia" ,df.parse("19/01/2014"), 3.25f),
				new Rilevazione(beniDiProva[3], "Market Sperduto" ,df.parse("19/01/2014"), 3.70f),
				new Rilevazione(beniDiProva[3], "Market Pineto" ,df.parse("01/02/2014"), 7.77f)
		} ), rilevazioni);
	}
	
	@Test
	public void testAggiungiRilevazionePerBeneNonGiaRilevato() throws ParseException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		
		m.addRilevazione( new Rilevazione(beniDiProva[1], "Market Pineto" ,df.parse("01/02/2014"), 1.11f) );
		
		List<Rilevazione> rilevazioni = m.getRilevazioni(11112);
		Assert.assertEquals(Arrays.asList( new Rilevazione[] {
				new Rilevazione(beniDiProva[1], "Market Pineto" ,df.parse("01/02/2014"), 1.11f)
		} ), rilevazioni);
	}
	
	@Test
	public void testGetMigliorRilevazioneDiBeneRilevatoPiuVolte() throws ParseException, NonRilevatoException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		
		Rilevazione r =
				m.getMigliorRilevazione(beniDiProva[5].getCodice());
		
		Assert.assertEquals(new Rilevazione(beniDiProva[5], "Market Dentinia" ,df.parse("20/01/2014"), 4.10f), r);
	}
	
	@Test
	public void testGetMigliorRilevazioneDiBeneRilevatoUnaVolta() throws ParseException, NonRilevatoException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		m.addRilevazione( new Rilevazione(beniDiProva[1], "Market Pineto" ,df.parse("01/02/2014"), 1.11f) );
		
		Rilevazione r =
				m.getMigliorRilevazione(beniDiProva[1].getCodice());
		
		Assert.assertEquals(new Rilevazione(beniDiProva[1], "Market Pineto" ,df.parse("01/02/2014"), 1.11f), r);
	}
	
	@Test(expected=NonRilevatoException.class)
	public void testGetMigliorRilevazioneDiBeneNonRilevato() throws ParseException, NonRilevatoException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		
		m.getMigliorRilevazione(beniDiProva[1].getCodice());
	}
	
	@Test
	public void testGetPrezzoMedioDiCategoriaRilevata() throws NonRilevatoException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		
		Assert.assertEquals(4.22f, m.getPrezzoMedio( Categoria.CARNE ), 0.001f);
		Assert.assertEquals(1.22f, m.getPrezzoMedio( Categoria.PASTA ), 0.001f);
		Assert.assertEquals(3.95f, m.getPrezzoMedio( Categoria.PESCE ), 0.001f);
	}
	
	@Test(expected=NonRilevatoException.class)
	public void testGetPrezzoMedioDiCategoriaNonRilevata() throws NonRilevatoException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		
		m.getPrezzoMedio( Categoria.ACQUA );
	}
	
	@Test
	public void testGetPrezzoMedioBeneRilevato() throws NonRilevatoException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		
		Assert.assertEquals(1.183f, m.getPrezzoMedio(beniDiProva[0].getCodice()), 0.001f);
		Assert.assertEquals(1.275f, m.getPrezzoMedio(beniDiProva[2].getCodice()), 0.001f);
		Assert.assertEquals(3.475f, m.getPrezzoMedio(beniDiProva[3].getCodice()), 0.001f);
		Assert.assertEquals(4.716f, m.getPrezzoMedio(beniDiProva[5].getCodice()), 0.001f);
		Assert.assertEquals(3.950f, m.getPrezzoMedio(beniDiProva[8].getCodice()), 0.001f);
	}
	
	@Test(expected=NonRilevatoException.class)
	public void testGetPrezzoMedioBeneNonRilevatoLanciaEccezione() throws NonRilevatoException {
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		
		m.getPrezzoMedio(beniDiProva[1].getCodice());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMonitoraggio() {
		
		Monitoraggio c = new Monitoraggio(new HashSet<Bene>(Arrays.asList(beniDiProva)));
		
		Date d = new Date();
		c.addRilevazione(new Rilevazione(beniDiProva[0], "Market Dentinia", d, 0.95F));
		c.addRilevazione(new Rilevazione(beniDiProva[0], "Market Zannonia", d, 1.05F));
 
		// ora tento di inserire un rilevazione gia presente
		c.addRilevazione(new Rilevazione(beniDiProva[0], "Market Dentinia", d, 0.95F));
	}
	
	@Test
	public void testToStringContieneElencoBeni() throws NonRilevatoException {
		
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		
		String elencoBeni = m.toString();
		for(Bene b : beniDiProva) {
			if(m.getRilevazioni(b.getCodice())!=null)
				Assert.assertTrue(elencoBeni.contains(b.toString()));
		}
	}
	
	@Test
	public void testToFullStringContieneElencoRilevazioni() throws NonRilevatoException {
		
		Monitoraggio m = 
				new Monitoraggio( new HashSet<Bene>(Arrays.asList(beniDiProva)), rilevazioniDiProva );
		
		String elencoBeni = m.toFullString();
		for(Rilevazione r : rilevazioniDiProva) {
			Assert.assertTrue(elencoBeni.contains(r.toString()));
		}
	}
	
}
