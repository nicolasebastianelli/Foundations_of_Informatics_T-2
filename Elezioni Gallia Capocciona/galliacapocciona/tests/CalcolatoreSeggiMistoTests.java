package galliacapocciona.tests;

import static org.junit.Assert.*;
import galliacapocciona.model.CalcolatoreSeggi;
import galliacapocciona.model.Collegio;
import galliacapocciona.model.Partito;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class CalcolatoreSeggiMistoTests {
	
	private String[] nomiPartiti;
	private List<Collegio> listaCollegi;
	
	@Before
	public void setUp() {
		nomiPartiti = new String[] {"Gialli", "Neri", "Blu", "Rossi", "Verdi"};
		int[] votiPartiti = {7460, 2040, 3482, 8748, 8922};
		
		List<Partito> listaPartiti2 = new ArrayList<Partito>();
		for (int i=0; i< nomiPartiti.length; i++){
			listaPartiti2.add(new Partito(nomiPartiti[i], votiPartiti[i]/2));
		}
		
		// due collegi identici, ognuno con metà voti (vincono i Verdi in entrambi)

		listaCollegi = new ArrayList<Collegio>();
		listaCollegi.add(new Collegio("c1", new TreeSet<Partito>(listaPartiti2)));
		listaCollegi.add(new Collegio("c2", new TreeSet<Partito>(listaPartiti2)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAssegnaSeggi_MenoSeggiDiCollegi() throws NoSuchAlgorithmException {
		CalcolatoreSeggi cs4 = CalcolatoreSeggi.getInstance("misto");
		cs4.assegnaSeggi(listaCollegi.size() - 1, listaCollegi);
	}

	@Test
	public void testAssegnaSeggi_SeggiComeCollegi() throws NoSuchAlgorithmException {
		CalcolatoreSeggi cs4 = CalcolatoreSeggi.getInstance("misto");
		Map<String, Integer> mappaSeggi = cs4.assegnaSeggi(2, listaCollegi);
		// se i seggi da assegnare sono in numero esattamente uguale ai collegi, 
		// il sistema misto si comporta come maggioritario puro: la proporzionale non interviene
		for (int i = 0; i < nomiPartiti.length; i++) {
			assertTrue(mappaSeggi.containsKey(nomiPartiti[i]));
			int seggiAssegnati = mappaSeggi.get(nomiPartiti[i]);
			int seggiPrevisti = nomiPartiti[i].equals("Verdi") ? 2 : 0;
			assertEquals(seggiPrevisti, seggiAssegnati);
		}
	}
	
	@Test
	public void testAssegnaSeggi_PiuSeggiDicollegi() throws NoSuchAlgorithmException {
		int seggiOltreCollegi = 8;
		int seggiTotali = listaCollegi.size() + seggiOltreCollegi;
		
		CalcolatoreSeggi cs4 = CalcolatoreSeggi.getInstance("misto");
		Map<String, Integer> mappaSeggi = cs4.assegnaSeggi(seggiTotali, listaCollegi);
		// se, invece, i seggi da assegnare sono PIU' dei collegi, i seggi ulteriori sono 
		// assegnati con metodo proporzionale
		
		CalcolatoreSeggi cp = CalcolatoreSeggi.getInstance("proporzionale");
		Map<String, Integer> mappaSeggiProp = cp.assegnaSeggi(seggiOltreCollegi, listaCollegi);
		
		for (String nomePartito : nomiPartiti) {
			int seggiAssegnatiMisto = mappaSeggi.get(nomePartito);
			int seggiAssegnatiProp = mappaSeggiProp.get(nomePartito);
			int seggiAssegnatiMagg = seggiAssegnatiMisto - seggiAssegnatiProp;

			int seggiPrevistiMagg = nomePartito.equals("Verdi") ? 2 : 0;
			assertEquals(seggiPrevistiMagg, seggiAssegnatiMagg);
		}
	}

}
