package teethcollege.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import teethcollege.model.*;

public class CarrieraTests {

	@Test
	public void testCarrieraPianoDiStudi() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");
		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		pds.aggiungiInsegnamento(ins1);
		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		pds.aggiungiInsegnamento(ins2);
		Insegnamento ins3 = TestUtils.createObbligatorio(3);
		pds.aggiungiInsegnamento(ins3);

		Carriera c = new Carriera(pds);

		assertSame(pds, c.getPianoDiStudi());
		assertNull(c.getListaEsami(1));
	}

	@Test
	public void testCarrieraPianoDiStudiListOfEsame() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");

		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		pds.aggiungiInsegnamento(ins1);
		Esame e1_ins1 = new Esame(ins1, 10, new Date());
		Esame e2_ins1 = new Esame(ins1, 20, new Date());

		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		pds.aggiungiInsegnamento(ins2);
		Esame e1_ins2 = new Esame(ins2, 30, new Date());

		Insegnamento ins3 = TestUtils.createObbligatorio(3);
		pds.aggiungiInsegnamento(ins3);

		ArrayList<Esame> listaEsami = new ArrayList<Esame>();
		listaEsami.add(e1_ins1);
		listaEsami.add(e2_ins1);
		listaEsami.add(e1_ins2);
		Carriera c = new Carriera(pds, listaEsami);

		assertSame(pds, c.getPianoDiStudi());

		List<Esame> readListaEsami = c.getListaEsami(1);
		assertNotNull(readListaEsami);
		assertEquals(2, readListaEsami.size());
		assertTrue(readListaEsami.contains(e1_ins1));
		assertTrue(readListaEsami.contains(e2_ins1));

		readListaEsami = c.getListaEsami(2);
		assertNotNull(readListaEsami);
		assertEquals(1, readListaEsami.size());
		assertTrue(readListaEsami.contains(e1_ins2));

		readListaEsami = c.getListaEsami(3);
		assertNull(readListaEsami);
	}

	@Test
	public void testAddEsame() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");
		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		pds.aggiungiInsegnamento(ins1);
		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		pds.aggiungiInsegnamento(ins2);

		Carriera c = new Carriera(pds);

		Esame eIns1 = new Esame(ins1, 10, new Date());
		c.addEsame(eIns1);

		assertNotNull(c.getEsamiSostenuti());
		assertEquals(1, c.getEsamiSostenuti().size());
		assertSame(eIns1, c.getEsamiSostenuti().get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEsame_fail_esameRiferitoAdInsegnamentoNonPresenteInPianoDiStudi() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");
		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		pds.aggiungiInsegnamento(ins1);
		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		pds.aggiungiInsegnamento(ins2);
		Insegnamento ins3 = TestUtils.createObbligatorio(3);

		Carriera c = new Carriera(pds);

		Esame eIns1 = new Esame(ins1, 10, new Date());
		c.addEsame(eIns1);
		Esame eIns3 = new Esame(ins3, 20, new Date());
		c.addEsame(eIns3);		
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEsame_fail_esameGiaSostenuto() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");
		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		pds.aggiungiInsegnamento(ins1);
		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		pds.aggiungiInsegnamento(ins2);

		Carriera c = new Carriera(pds);

		Esame e1Ins1 = new Esame(ins1, 20, new Date());
		c.addEsame(e1Ins1);
		Esame e2Ins1 = new Esame(ins1, 31, new Date());
		c.addEsame(e2Ins1);		
	}


	@Test
	public void testGetEsamiSostenuti() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");
		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		pds.aggiungiInsegnamento(ins1);
		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		pds.aggiungiInsegnamento(ins2);

		Carriera c = new Carriera(pds);

		assertNotNull(c.getEsamiSostenuti());
		assertEquals(0, c.getEsamiSostenuti().size());

		Esame e1Ins1 = new Esame(ins1, 10, new Date());
		c.addEsame(e1Ins1);
		Esame e2Ins1 = new Esame(ins1, 11, new Date());
		c.addEsame(e2Ins1);

		assertNotNull(c.getEsamiSostenuti());
		assertEquals(2, c.getEsamiSostenuti().size());
		assertTrue(c.getEsamiSostenuti().contains(e1Ins1));
		assertTrue(c.getEsamiSostenuti().contains(e2Ins1));
	}

	@Test
	public void testGetEsamiSuperati() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");
		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		pds.aggiungiInsegnamento(ins1);
		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		pds.aggiungiInsegnamento(ins2);

		Carriera c = new Carriera(pds);

		assertNotNull(c.getEsamiSostenuti());
		assertEquals(0, c.getEsamiSostenuti().size());
		assertNotNull(c.getEsamiSuperati());
		assertEquals(0, c.getEsamiSuperati().size());

		Esame e1Ins1 = new Esame(ins1, 10, new Date());
		c.addEsame(e1Ins1);
		Esame e2Ins1 = new Esame(ins1, 18, new Date());
		c.addEsame(e2Ins1);

		assertNotNull(c.getEsamiSostenuti());
		assertEquals(2, c.getEsamiSostenuti().size());
		assertTrue(c.getEsamiSostenuti().contains(e1Ins1));
		assertTrue(c.getEsamiSostenuti().contains(e2Ins1));
		assertNotNull(c.getEsamiSuperati());
		assertEquals(1, c.getEsamiSuperati().size());
		assertTrue(c.getEsamiSuperati().contains(e2Ins1));
	}

	@Test
	public void testGetCreditiAcquisiti() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");
		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		pds.aggiungiInsegnamento(ins1);
		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		pds.aggiungiInsegnamento(ins2);

		Carriera c = new Carriera(pds);
		assertEquals(0, c.getCreditiAcquisiti());

		Esame e1Ins1 = new Esame(ins1, 10, new Date());
		c.addEsame(e1Ins1);
		assertEquals(0, c.getCreditiAcquisiti());
		
		Esame e2Ins1 = new Esame(ins1, 18, new Date());
		c.addEsame(e2Ins1);
		assertEquals(e2Ins1.getInsegnamento().getCfu(), c.getCreditiAcquisiti());	

		Esame e1Ins2 = new Esame(ins2, 18, new Date());
		c.addEsame(e1Ins2);
		assertEquals(e2Ins1.getInsegnamento().getCfu() + e1Ins2.getInsegnamento().getCfu(), 
				c.getCreditiAcquisiti());	
	}

	@Test
	public void testGetMediaPesata_Zero() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");
		Insegnamento ins1 = TestUtils.createObbligatorio(1);
		pds.aggiungiInsegnamento(ins1);
		Insegnamento ins2 = TestUtils.createObbligatorio(2);
		pds.aggiungiInsegnamento(ins2);

		Carriera c = new Carriera(pds);
		assertEquals(0, c.getMediaPesata(), 0.001);
		
		Esame e1Ins1 = new Esame(ins1, 10, new Date());
		c.addEsame(e1Ins1);
		assertEquals(0, c.getMediaPesata(), 0.001);
	}

	@Test
	public void testGetMediaPesata_ValoreValido() {
		PianoDiStudi pds = new PianoDiStudi("Pinco", "Pallino", "123456");
		Insegnamento ins1 = TestUtils.createObbligatorio(1, 6);
		pds.aggiungiInsegnamento(ins1);
		Insegnamento ins2 = TestUtils.createObbligatorio(2, 9);
		pds.aggiungiInsegnamento(ins2);
		Insegnamento ins3 = TestUtils.createObbligatorio(3, 9);
		pds.aggiungiInsegnamento(ins3);

		Carriera c = new Carriera(pds);
		Esame e1Ins1 = new Esame(ins1, 20, new Date());
		c.addEsame(e1Ins1);
		assertEquals(20, c.getMediaPesata(), 0.001);
		
		Esame e1Ins2 = new Esame(ins2, 24, new Date());
		c.addEsame(e1Ins2);
		assertEquals(22.4, c.getMediaPesata(), 0.001);	
		
		Esame e1Ins3 = new Esame(ins3, 26, new Date());
		c.addEsame(e1Ins3);
		assertEquals(23.75,  c.getMediaPesata(), 0.001);		
	}

}
