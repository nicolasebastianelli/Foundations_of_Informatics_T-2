package dentinia.tests.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import dentinia.model.CalcolatoreSeggiQuoziente;
import dentinia.model.Partito;

public class QuozienteTest {
	
	private CalcolatoreSeggiQuoziente qCalc;
	private List<Partito> list;
	
	@Before
	public void setUp() throws Exception {
		qCalc = new CalcolatoreSeggiQuoziente();
		list = new ArrayList<Partito>();
		list.add( new Partito("TG", 7460) );
		list.add( new Partito("GN", 2040) );
		list.add( new Partito("FB", 3482) );
		list.add( new Partito("FR", 8748) );
		list.add( new Partito("BV", 8922) );
	}

	@Test
	public void testAssegnaSeggi() {
		qCalc.assegnaSeggi(15, list);
		Assert.assertEquals(5, list.size());
		Assert.assertEquals(4, list.get(0).getSeggi());
		Assert.assertEquals(1, list.get(1).getSeggi());
		Assert.assertEquals(2, list.get(2).getSeggi());
		Assert.assertEquals(4, list.get(3).getSeggi());
		Assert.assertEquals(4, list.get(4).getSeggi());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssegnaZeroSeggi() {
		qCalc.assegnaSeggi(0, list);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAssegnaSeggiNegativi() {
		qCalc.assegnaSeggi(-15, list);
	}

	@Test
	public void testCostruttoreCalcolatoreSeggiQuoziente() {
		qCalc = new CalcolatoreSeggiQuoziente();
		Assert.assertNotNull(qCalc);
	}

}
