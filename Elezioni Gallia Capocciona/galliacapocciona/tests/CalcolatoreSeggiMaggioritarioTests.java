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

public class CalcolatoreSeggiMaggioritarioTests {
	
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

	@Test
	public void testAssegnaSeggi() throws NoSuchAlgorithmException {
		CalcolatoreSeggi cs3 = CalcolatoreSeggi.getInstance("maggioritario");
		Map<String, Integer> mappaSeggi = cs3.assegnaSeggi(listaCollegi.size(), listaCollegi);
		
		for (int i = 0; i < nomiPartiti.length; i++) {
			assertTrue(mappaSeggi.containsKey(nomiPartiti[i]));
			int seggiAssegnati = mappaSeggi.get(nomiPartiti[i]);
			int seggiPrevisti = nomiPartiti[i].equals("Verdi") ? 2 : 0;
			assertEquals(seggiPrevisti, seggiAssegnati);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssegnaSeggi_MenoSeggiDiCollegi() throws NoSuchAlgorithmException {
		CalcolatoreSeggi cs3 = CalcolatoreSeggi.getInstance("maggioritario");
		cs3.assegnaSeggi(listaCollegi.size() - 1, listaCollegi);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssegnaSeggi_PiuSeggiDiCollegi() throws NoSuchAlgorithmException {
		CalcolatoreSeggi cs3 = CalcolatoreSeggi.getInstance("maggioritario");
		cs3.assegnaSeggi(listaCollegi.size() + 2, listaCollegi);
	}

}
