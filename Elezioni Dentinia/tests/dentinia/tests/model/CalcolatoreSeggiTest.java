package dentinia.tests.model;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dentinia.model.CalcolatoreSeggi;
import dentinia.model.CalcolatoreSeggiDivisore;
import dentinia.model.CalcolatoreSeggiQuoziente;

public class CalcolatoreSeggiTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance1() throws NoSuchAlgorithmException {
		CalcolatoreSeggi cs = 
				CalcolatoreSeggi.getInstance("Metodo D'Hondt");
		
		Assert.assertEquals(cs.getClass(), CalcolatoreSeggiDivisore.class);
	}
	
	@Test
	public void testGetInstance2() {
		CalcolatoreSeggi cs = 
				CalcolatoreSeggi.getInstance("Metodo del quoziente");
		
		Assert.assertEquals(cs.getClass(), CalcolatoreSeggiQuoziente.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetInstanceFail() {
		
		CalcolatoreSeggi.getInstance("Metodo non esistente");
	}

	@Test
	public void testGetCalcolatoriSeggi() {
		
		Set<String> calcolatoriSeggi =
				CalcolatoreSeggi.getCalcolatoriSeggi();
		
		Assert.assertEquals(2, calcolatoriSeggi.size());
		Assert.assertTrue(calcolatoriSeggi.contains("Metodo del quoziente"));
		Assert.assertTrue(calcolatoriSeggi.contains("Metodo D'Hondt"));
	}

}
