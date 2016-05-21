package dentinia.tests.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import dentinia.model.CalcolatoreSeggiDivisore;
import dentinia.model.Partito;

public class DivisoreTest {

	private CalcolatoreSeggiDivisore hCalc;
	private List<Partito> list;
	
	@Before
	public void setUp() throws Exception {
		hCalc = new CalcolatoreSeggiDivisore();
		list = new ArrayList<Partito>();
		list.add( new Partito("TG", 7460) );
		list.add( new Partito("GN", 2040) );
		list.add( new Partito("FB", 3482) );
		list.add( new Partito("FR", 8748) );
		list.add( new Partito("BV", 8922) );
	}

	@Test
	public void testAssegnaSeggi() {
		hCalc.assegnaSeggi(15, list);
		Assert.assertEquals(5, list.size());
		Assert.assertEquals(4, list.get(0).getSeggi());
		Assert.assertEquals(1, list.get(1).getSeggi());
		Assert.assertEquals(1, list.get(2).getSeggi());
		Assert.assertEquals(4, list.get(3).getSeggi());
		Assert.assertEquals(5, list.get(4).getSeggi());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAssegnaZeroSeggi() {
		hCalc.assegnaSeggi(0, list);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAssegnaSeggiNegativi() {
		hCalc.assegnaSeggi(-15, list);
	}

	@Test
	public void testCostruttoreCalcolatoreSeggiDivisore() {
		hCalc = new CalcolatoreSeggiDivisore();
		Assert.assertNotNull(hCalc);
	}

}
