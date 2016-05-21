package doc.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import doc.model.*;

public class DentoStrategiaConfigurabileTests {
	
	@Test
	public void testDentoStrategiaConfigurabile() {
		new DentoStrategiaConfigurabile("Pippo", new HashMap<String, Integer>(), 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDentoStrategiaConfigurabile_failNomeNull() {
		new DentoStrategiaConfigurabile(null, new HashMap<String, Integer>(), 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDentoStrategiaConfigurabile_failMapNull() {
		new DentoStrategiaConfigurabile("Pippo", null, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDentoStrategiaConfigurabile_failZeroHours() {
		new DentoStrategiaConfigurabile("Pippo", new HashMap<String, Integer>(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDentoStrategiaConfigurabile_failNegativeHours() {
		new DentoStrategiaConfigurabile("Pippo", new HashMap<String, Integer>(), -10);
	}

	@Test
	public void testAccessors() {
		HashMap<String, Integer> pb = new HashMap<>();
		pb.put("b1", 1);
		
		StrategiaUnderTest s = new StrategiaUnderTest("Test", pb, 1, AcquistoFactory.Now);
		assertEquals("Test", s.getNome());
		assertEquals(1, s.getPunteggiBevande().size());
		assertEquals(1, (int) s.getPunteggiBevande().get("b1"));
		assertEquals(1, s.getOre());
	}

	@Test
	public void testCalcolaSconto_UnaBevandaDueClienti_InTime() {
		Cliente c1 = new Cliente("c1", "C 1");
		Cliente c2 = new Cliente("c2", "C 2");
		Bevanda b1 = new Bevanda("b1", 10, 20, 30);
		
		HashMap<String, Integer> pb = new HashMap<>();
		pb.put("b1", 1);
		
		ArrayList<Acquisto> acq = new ArrayList<>();
		acq.add(AcquistoFactory.createAcq(c1, b1, 20, 0.3));
		acq.add(AcquistoFactory.createAcq(c1, b1, 20, 0.2));
		acq.add(AcquistoFactory.createAcq(c2, b1, 20, 0.1));
		
		StrategiaUnderTest s = new StrategiaUnderTest("Test", pb, 1, AcquistoFactory.Now);
		float sconto = s.calcolaSconto(acq, c1);
		assertEquals(1f, sconto, 0.01f);
	}

	@Test
	public void testCalcolaSconto_DueBevandeDueClienti_InTime() {
		Cliente c1 = new Cliente("c1", "C 1");
		Cliente c2 = new Cliente("c2", "C 2");
		Bevanda b1 = new Bevanda("b1", 10, 20, 30);
		Bevanda b2 = new Bevanda("b2", 10, 20, 30);
		
		HashMap<String, Integer> pb = new HashMap<>();
		pb.put("b1", 1);
		pb.put("b2", -2);
		
		ArrayList<Acquisto> acq = new ArrayList<>();
		acq.add(AcquistoFactory.createAcq(c1, b1, 20, 0.9));
		acq.add(AcquistoFactory.createAcq(c1, b1, 20, 0.8));
		acq.add(AcquistoFactory.createAcq(c2, b1, 20, 0.7));
		acq.add(AcquistoFactory.createAcq(c1, b2, 20, 0.6));
		acq.add(AcquistoFactory.createAcq(c1, b2, 20, 0.5));
		
		StrategiaUnderTest s = new StrategiaUnderTest("Test", pb, 1, AcquistoFactory.Now);
		float sconto = s.calcolaSconto(acq, c1);
		assertEquals(-0.5f, sconto, 0.01f);
	}

	@Test
	public void testCalcolaSconto_DueBevandeDueClienti_AlsoOutTime() {
		Cliente c1 = new Cliente("c1", "C 1");
		Cliente c2 = new Cliente("c2", "C 2");
		Bevanda b1 = new Bevanda("b1", 10, 20, 30);
		Bevanda b2 = new Bevanda("b2", 10, 20, 30);
		
		HashMap<String, Integer> pb = new HashMap<>();
		pb.put("b1", 1);
		pb.put("b2", -2);
		
		ArrayList<Acquisto> acq = new ArrayList<>();
		acq.add(AcquistoFactory.createAcq(c1, b1, 20, 2));
		acq.add(AcquistoFactory.createAcq(c1, b1, 20, 1.5));
		acq.add(AcquistoFactory.createAcq(c2, b1, 20, 0.7));
		acq.add(AcquistoFactory.createAcq(c1, b2, 20, 0.6));
		acq.add(AcquistoFactory.createAcq(c1, b2, 20, 0.5));
		
		StrategiaUnderTest s = new StrategiaUnderTest("Test", pb, 1, AcquistoFactory.Now);
		float sconto = s.calcolaSconto(acq, c1);
		assertEquals(-2f, sconto, 0.01f);
	}

	@Test
	public void testCalcolaSconto_DueBevandeDueClienti_AllOutTime() {
		Cliente c1 = new Cliente("c1", "C 1");
		Cliente c2 = new Cliente("c2", "C 2");
		Bevanda b1 = new Bevanda("b1", 10, 20, 30);
		Bevanda b2 = new Bevanda("b2", 10, 20, 30);
		
		HashMap<String, Integer> pb = new HashMap<>();
		pb.put("b1", 1);
		pb.put("b2", -2);
		
		ArrayList<Acquisto> acq = new ArrayList<>();
		acq.add(AcquistoFactory.createAcq(c1, b1, 20, 2));
		acq.add(AcquistoFactory.createAcq(c1, b1, 20, 1.5));
		acq.add(AcquistoFactory.createAcq(c2, b1, 20, 1.4));
		acq.add(AcquistoFactory.createAcq(c1, b2, 20, 1.3));
		acq.add(AcquistoFactory.createAcq(c1, b2, 20, 1.2));
		
		StrategiaUnderTest s = new StrategiaUnderTest("Test", pb, 1, AcquistoFactory.Now);
		float sconto = s.calcolaSconto(acq, c1);
		assertEquals(0f, sconto, 0.01f);
	}
}
