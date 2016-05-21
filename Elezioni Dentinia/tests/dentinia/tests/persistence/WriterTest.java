package dentinia.tests.persistence;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import dentinia.model.Partito;
import dentinia.persistence.MySeggiWriter;

public class WriterTest {

	private MySeggiWriter writer;
	private List<Partito> list;
	
	@Before
	public void setUp() throws Exception {
		writer = new MySeggiWriter();
		list = new ArrayList<Partito>();
		list.add( new Partito("TG", 7460) );
		list.add( new Partito("GN", 2040) );
		list.add( new Partito("FB", 3482) );
		list.add( new Partito("FR", 8748) );
		list.add( new Partito("BV", 8922) );
		list.get(0).setSeggi(1);
		list.get(1).setSeggi(2);
		list.get(2).setSeggi(3);
		list.get(3).setSeggi(4);
		list.get(4).setSeggi(5);
	}

	@Test
	public void testStampaNomeSeggi() throws IOException {
		StringWriter result = new StringWriter();
		writer.stampaSeggi(list, result);
		
		String fileWritten = new String(result.getBuffer());
		for(Partito p : list) {
			Assert.assertTrue( fileWritten.contains(p.getNome()) );
		}
	}
	
	@Test
	public void testStampaNumeroSeggi() throws IOException {
		StringWriter result = new StringWriter();
		writer.stampaSeggi(list, result);
		
		String fileWritten = new String(result.getBuffer());
		for(Partito p : list) {
			Assert.assertTrue( fileWritten.contains("" + p.getSeggi() ));
		}
	}

}
