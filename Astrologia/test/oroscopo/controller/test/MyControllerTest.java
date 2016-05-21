package oroscopo.controller.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;  
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import oroscopo.controller.AbstractController;
import oroscopo.controller.MyController;
import oroscopo.controller.StrategiaSelezione;
import oroscopo.model.Oroscopo;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.OroscopoRepository;

public class MyControllerTest {
	
	private OroscopoRepository reader;
	private StrategiaSelezione strategia;
	
	@Before
	public void setUp() {
		reader = mock(OroscopoRepository.class);
		strategia = mock(StrategiaSelezione.class);
	}

	@Test
	public void testMyController() throws IOException {
		new MyController( reader, strategia );
	}

	@Test
	public void testGetSegni() throws IOException {
		
		AbstractController underTest = 
				new MyController( reader, strategia );
		
		SegnoZodiacale[] segni = underTest.getSegni();
		
		assertEquals(AbstractController.NUMERO_SEGNI, segni.length);
		
		assertEquals(segni[0], SegnoZodiacale.ARIETE);
		assertEquals(segni[1], SegnoZodiacale.TORO);
		assertEquals(segni[2], SegnoZodiacale.GEMELLI);
		assertEquals(segni[3], SegnoZodiacale.CANCRO);
		assertEquals(segni[4], SegnoZodiacale.LEONE);
		assertEquals(segni[5], SegnoZodiacale.VERGINE);
		assertEquals(segni[6], SegnoZodiacale.BILANCIA);
		assertEquals(segni[7], SegnoZodiacale.SCORPIONE);
		assertEquals(segni[8], SegnoZodiacale.SAGITTARIO);
		assertEquals(segni[9], SegnoZodiacale.CAPRICORNO);
		assertEquals(segni[10], SegnoZodiacale.ACQUARIO);
		assertEquals(segni[11], SegnoZodiacale.PESCI);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGeneraOroscopoCasuale() throws IOException {
		
		AbstractController underTest = 
				new MyController( reader, strategia );
		
		when(reader.getPrevisioni("amore")).thenReturn( Arrays.asList( new Previsione[] { 
				new Previsione("testo previsione amore", 5)
		}));
		
		when(reader.getPrevisioni("lavoro")).thenReturn( Arrays.asList( new Previsione[] { 
				new Previsione("testo previsione lavoro", 5)
		}));
		
		when(reader.getPrevisioni("salute")).thenReturn( Arrays.asList( new Previsione[] { 
				new Previsione("testo previsione salute", 5)
		}));
		
		when(strategia.seleziona(any(List.class), any(SegnoZodiacale.class)))
			.thenAnswer( new Answer<Previsione>() {
				@Override
				public Previsione answer(InvocationOnMock invocation)
						throws Throwable {
					List<?> p = (List<?>)invocation.getArguments()[0];
					return (Previsione)p.get(0);
				}
			});
			
		Oroscopo risultato =
				underTest.generaOroscopoCasuale(SegnoZodiacale.ARIETE);
		
		assertEquals("testo previsione amore", risultato.getPrevisioneAmore().getPrevisione());
		assertEquals("testo previsione lavoro", risultato.getPrevisioneLavoro().getPrevisione());
		assertEquals("testo previsione salute", risultato.getPrevisioneSalute().getPrevisione());
		
		assertEquals(5, risultato.getPrevisioneAmore().getValore());
		assertEquals(5, risultato.getPrevisioneLavoro().getValore());
		assertEquals(5, risultato.getPrevisioneSalute().getValore());
		
		verify(reader, atLeast(3)).getPrevisioni(any(String.class));
		verify(strategia, atLeast(3)).seleziona(any(List.class), eq(SegnoZodiacale.ARIETE));
		
		assertEquals(SegnoZodiacale.ARIETE, risultato.getSegnoZodiacale());
		
		assertEquals(5, risultato.getFortuna());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGeneraOroscopoAnnuale() throws IOException {
		
		AbstractController underTest = 
				new MyController( reader, strategia );
		
		when(reader.getPrevisioni("amore")).thenReturn( Arrays.asList( new Previsione[] { 
				new Previsione("testo previsione amore", 5)
		}));
		
		when(reader.getPrevisioni("lavoro")).thenReturn( Arrays.asList( new Previsione[] { 
				new Previsione("testo previsione lavoro", 5)
		}));
		
		when(reader.getPrevisioni("salute")).thenReturn( Arrays.asList( new Previsione[] { 
				new Previsione("testo previsione salute", 5)
		}));
		
		when(strategia.seleziona(any(List.class), any(SegnoZodiacale.class)))
			.thenAnswer( new Answer<Previsione>() {
				@Override
				public Previsione answer(InvocationOnMock invocation)
						throws Throwable {
					List<?> p = (List<?>)invocation.getArguments()[0];
					return (Previsione)p.get(0);
				}
			});
			
		Oroscopo[] risultato =
				underTest.generaOroscopoAnnuale(SegnoZodiacale.ARIETE, 5);
		
		assertEquals(12, risultato.length);
		
		int fortuna = 0;
		for(Oroscopo o : risultato) {
			fortuna += o.getFortuna();
		}
		
		assertEquals(5, fortuna/12);
		
		verify(reader, atLeast(12*3)).getPrevisioni(any(String.class));
		verify(strategia, atLeast(12*3)).seleziona(any(List.class), eq(SegnoZodiacale.ARIETE));
	}
}
