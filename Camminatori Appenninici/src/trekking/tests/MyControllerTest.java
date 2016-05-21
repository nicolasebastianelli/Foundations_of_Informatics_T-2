package trekking.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import trekking.controller.MyController;
import trekking.model.Difficulty;
import trekking.model.Trail;
import trekking.model.TrailHead;
import trekking.model.Trekking;
import trekking.persistence.InvalidTrailFormatException;
import trekking.persistence.TrailReader;
import trekking.ui.MessageManager;

public class MyControllerTest {

	@Test
	public void test_aggiunta_primo_sentiero() throws InvalidTrailFormatException {
		
		Trekking trekking = new Trekking();
		trekking.loadTrails(new MockReader());
		
		MyController testController =
				new MyController(trekking, new MockMessageManager());
		
		testController.addTrail(trekking.getTrailList().get(0));
			
		assertEquals(1, testController.getItinerary().getTrails().size());
	}
	
	@Test
	public void test_funzionalita_base() throws InvalidTrailFormatException {
		
		Trekking trekking = new Trekking();
		trekking.loadTrails(new MockReader());
		MockMessageManager manager = new MockMessageManager();
		
		MyController testController =
				new MyController(trekking, manager);
		
		testController.addTrail(trekking.getTrailList().get(0));
		testController.addTrail(trekking.getTrailList().get(1));
		
		assertEquals(2, testController.getItinerary().getTrails().size());
		assertEquals(0, manager.getMessageCount());
		testController.checkItinerary(1200, 12, Difficulty.HIGH, 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_aggiunta_sentieri_non_consecutivi() throws InvalidTrailFormatException {
		
		Trekking trekking = new Trekking();
		trekking.loadTrails(new MockReader());
		
		MyController testController =
				new MyController(trekking, new MockMessageManager());
		
		testController.addTrail(trekking.getTrailList().get(1));
		testController.addTrail(trekking.getTrailList().get(0));
	}
	
	@Test
	public void test_aggiunta_sentieri_consecutivi_superamento_lunghezza_max() throws InvalidTrailFormatException {
		
		Trekking trekking = new Trekking();
		trekking.loadTrails(new MockReader());
		MockMessageManager manager = new MockMessageManager();
		
		MyController testController =
				new MyController(trekking, manager);
		
		testController.addTrail(trekking.getTrailList().get(0));
		testController.addTrail(trekking.getTrailList().get(2));
		
		testController.checkItinerary(1200, 12, Difficulty.HIGH, 3);
		assertEquals(1, manager.getMessageCount());
	}
	
	@Test
	public void test_aggiunta_sentieri_consecutivi_superamento_Difficulty_max() throws InvalidTrailFormatException {
		
		Trekking trekking = new Trekking();
		trekking.loadTrails(new MockReader());
		MockMessageManager manager = new MockMessageManager();
		
		MyController testController =
				new MyController(trekking, manager);
		
		testController.addTrail(trekking.getTrailList().get(0));
		testController.addTrail(trekking.getTrailList().get(4));
		
		testController.checkItinerary(1200, 12, Difficulty.MEDIUM, 3);
		assertEquals(1, manager.getMessageCount());
	}
	
	@Test
	public void test_Difficulty_MEDIUM_calcolata_correttamente() throws InvalidTrailFormatException {
		
		Trekking trekking = new Trekking();
		trekking.loadTrails(new MockReader());
		MockMessageManager manager = new MockMessageManager();
		
		MyController testController =
				new MyController(trekking, manager);
		
		testController.addTrail(trekking.getTrailList().get(0));
		testController.addTrail(trekking.getTrailList().get(1));
		
		testController.checkItinerary(1200, 12, Difficulty.HIGH, 1.9804); // 1.98039 limit
		assertEquals(0, manager.getMessageCount());
		
		testController.checkItinerary(1200, 12, Difficulty.HIGH, 1.9803);
		assertEquals(1, manager.getMessageCount());
	}
	
	@Test
	public void test_get_sentieri_disponibili_restituisce_tutti() throws InvalidTrailFormatException {
		
		Trekking trekking = new Trekking();
		trekking.loadTrails(new MockReader());
		
		MyController testController =
				new MyController(trekking, new MockMessageManager());
		
		testController.addTrail(trekking.getTrailList().get(0));
		testController.addTrail(trekking.getTrailList().get(1));
		
		assertEquals(5, testController.getAllTrails().size());
	}
	
}

class MockReader implements TrailReader {

	@Override
	public List<Trail> readTrails() throws InvalidTrailFormatException {
		List<Trail> sentieri = new ArrayList<Trail>();
		
		TrailHead A = new TrailHead();
		A.setName("PARTENZA");
		A.setAltitude(0);
		
		TrailHead B = new TrailHead();
		B.setName("INTERMEDIO");
		B.setAltitude(1000);
		
		TrailHead C = new TrailHead();
		C.setName("FINALE");
		C.setAltitude(1100);
		
		Trail s = new Trail(A,B,Difficulty.MEDIUM);
		s.setName("ELEMENTO 0");
		s.setLength(10);

		sentieri.add(s);
		
		s = new Trail(B,C,Difficulty.LOW);
		s.setName("ELEMENTO 1");
		s.setLength(2);

		sentieri.add(s);
		
		s = new Trail(B,C,Difficulty.LOW);
		s.setName("ELEMENTO 2");
		s.setLength(2.1);

		sentieri.add(s);
		
		s = new Trail(B,C,Difficulty.LOW);
		s.setName("ELEMENTO 3");
		s.setLength(2);
		
		sentieri.add(s);
		
		s = new Trail(B,C,Difficulty.HIGH);
		s.setName("ELEMENTO 4");
		s.setLength(2);

		sentieri.add(s);
		
		return sentieri;
	}
}

class MockMessageManager implements MessageManager {

	List<String> messages;
	public int getMessageCount() {
		return messages.size();
	}
	
	public MockMessageManager() {
		messages = new ArrayList<String>();
	}
	
	@Override
	public void showMessage(String message) {
		messages.add(message);
	}
	
}