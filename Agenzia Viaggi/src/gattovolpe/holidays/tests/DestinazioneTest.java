package gattovolpe.holidays.tests;

import static org.junit.Assert.*;
import gattovolpe.holidays.model.Destinazione;

import org.junit.BeforeClass;
import org.junit.Test;

public class DestinazioneTest {

	private static Destinazione destinazioneTest1, destinazioneTest2, destinazioneTest3;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		destinazioneTest1 = new Destinazione();
		destinazioneTest1.setStato("STATO1");
		destinazioneTest1.setLuogo("LUOGO1");
		
		destinazioneTest2 = new Destinazione();
		destinazioneTest2.setStato("STATO2");
		destinazioneTest2.setLuogo("LUOGO2");
		
		destinazioneTest3 = new Destinazione();
		destinazioneTest3.setStato("stato2");
		destinazioneTest3.setLuogo("luogo2");
	}

	@Test
	public void test_due_destinazioni_equivalenti_hanno_stesso_hash() {
		assertEquals(destinazioneTest2, destinazioneTest3);
		assertEquals(destinazioneTest2.hashCode(), destinazioneTest3.hashCode());
	}
	
	@Test
	public void test_due_destinazioni_diverse_ritornano_equals_false() {
		assertFalse(destinazioneTest1.equals(destinazioneTest3));
	}
	
	@Test
	public void testGetStato() {
		assertEquals("STATO2", destinazioneTest2.getStato());
	}

	@Test
	public void testSetStato() {
		Destinazione test = new Destinazione();
		test.setStato("TEST");
		assertEquals("TEST", test.getStato());
	}

	@Test
	public void testGetLuogo() {
		assertEquals("LUOGO2", destinazioneTest2.getLuogo());
	}

	@Test
	public void testSetLuogo() {
		Destinazione test = new Destinazione();
		test.setLuogo("TEST");
		assertEquals("TEST", test.getLuogo());
	}

}
