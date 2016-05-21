package rs.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import rs.model.Month;
import rs.model.StimaSintetica;
import rs.model.MyStimaSinteticaFactory;
import rs.model.StimaRischio;

public class FullStimaSinteticaFactoryTests {

	@Test(expected=IllegalArgumentException.class)
	public void testCrea_FailForNullParam() {
		MyStimaSinteticaFactory factory = new MyStimaSinteticaFactory();
		factory.create(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCrea_FailForEmptyCollection() {
		MyStimaSinteticaFactory factory = new MyStimaSinteticaFactory();
		factory.create(new ArrayList<StimaRischio>());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCrea_FailForMultipleLocalita() {
		MyStimaSinteticaFactory factory = new MyStimaSinteticaFactory();
		
		ArrayList<StimaRischio> stimeRischio = new ArrayList<StimaRischio>();
		stimeRischio.add(new StimaRischio("Uno", Month.APRIL, 2015, 20));
		stimeRischio.add(new StimaRischio("Uno", Month.MARCH, 2015, 20));
		stimeRischio.add(new StimaRischio("Due", Month.AUGUST, 2015, 20));
		
		factory.create(stimeRischio);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCrea_FailForDuplicatedMonth() {
		MyStimaSinteticaFactory factory = new MyStimaSinteticaFactory();
		
		ArrayList<StimaRischio> stimeRischio = new ArrayList<StimaRischio>();
		stimeRischio.add(new StimaRischio("Uno", Month.JANUARY, 2015, 20));
		stimeRischio.add(new StimaRischio("Uno", Month.MARCH, 2015, 20));
		stimeRischio.add(new StimaRischio("Uno", Month.JANUARY, 2015, 20));
		
		factory.create(stimeRischio);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCrea_FailForMultipleYears() {
		MyStimaSinteticaFactory factory = new MyStimaSinteticaFactory();
		
		ArrayList<StimaRischio> stimeRischio = new ArrayList<StimaRischio>();
		stimeRischio.add(new StimaRischio("Uno", Month.JANUARY, 2015, 20));
		stimeRischio.add(new StimaRischio("Uno", Month.FEBRUARY, 2015, 20));
		stimeRischio.add(new StimaRischio("Uno", Month.MARCH, 2016, 20));
		
		factory.create(stimeRischio);
	}
	
	@Test
	public void testCrea_PrevisioneSingola() {
		MyStimaSinteticaFactory factory = new MyStimaSinteticaFactory();
		
		ArrayList<StimaRischio> stimeRischio = new ArrayList<StimaRischio>();
		stimeRischio.add(new StimaRischio("Uno", Month.MARCH, 2015, 20));
		
		StimaSintetica stimaSintetica = factory.create(stimeRischio);

		assertEquals(2015, stimaSintetica.getAnno());
		assertEquals("Uno", stimaSintetica.getCitta());
		assertEquals(20, stimaSintetica.getRischio());
	}
	
	@Test
	public void testCrea_PrevisioneMultipla() {
		MyStimaSinteticaFactory factory = new MyStimaSinteticaFactory();
		
		ArrayList<StimaRischio> stimeRischio = new ArrayList<StimaRischio>();
		stimeRischio.add(new StimaRischio("Uno", Month.FEBRUARY, 2015, 20));
		stimeRischio.add(new StimaRischio("Uno", Month.JULY, 2015, 70));
		stimeRischio.add(new StimaRischio("Uno", Month.SEPTEMBER, 2015, 30));
		stimeRischio.add(new StimaRischio("Uno", Month.DECEMBER, 2015, 5));
		
		StimaSintetica stimaSintetica = factory.create(stimeRischio);

		assertEquals(2015, stimaSintetica.getAnno());
		assertEquals("Uno", stimaSintetica.getCitta());
		assertEquals(30, stimaSintetica.getRischio());		
	}

}
