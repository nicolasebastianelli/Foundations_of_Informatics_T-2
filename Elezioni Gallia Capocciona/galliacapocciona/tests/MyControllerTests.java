package galliacapocciona.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import galliacapocciona.model.CalcolatoreSeggi;
import galliacapocciona.model.Collegio;
import galliacapocciona.ui.MyController;
import galliacapocciona.ui.UserInteractor;

import org.junit.Test;

public class MyControllerTests {

	@SuppressWarnings("unchecked")
	@Test
	public void testCalcola() {
		UserInteractor ui = mock(UserInteractor.class);		
		MyController ctrl = new MyControllerUnderTest(ui);
				
		Map<String, Integer> result = new HashMap<String, Integer>();
		CalcolatoreSeggi calcolatoreSeggiMock = mock(CalcolatoreSeggi.class);
		when(calcolatoreSeggiMock.assegnaSeggi(anyInt(), (List<Collegio>) anyObject()))
			.thenReturn(result);
		CalcolatoreSeggi.addCalcolatoreSeggi("test1", calcolatoreSeggiMock);
		
		Map<String, Integer> actualResult = ctrl.calcola("test1", ctrl.getSeggiMinimi());
		assertSame(result, actualResult);
		
		verify(calcolatoreSeggiMock, times(1)).assegnaSeggi(anyInt(),(List<Collegio>) anyObject());
		verify(ui, never()).showMessage(anyString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCalcola_seggiDaAssegnareMinoriDiSeggiMinimi() {
		UserInteractor ui = mock(UserInteractor.class);		
		MyController ctrl = new MyControllerUnderTest(ui);
				
		CalcolatoreSeggi calcolatoreSeggiMock = mock(CalcolatoreSeggi.class);
		CalcolatoreSeggi.addCalcolatoreSeggi("test2", calcolatoreSeggiMock);
		
		Map<String, Integer> actualResult = ctrl.calcola("test2", ctrl.getSeggiMinimi() - 1);
		assertNull(actualResult);
		
		verify(calcolatoreSeggiMock, never()).assegnaSeggi(anyInt(),(List<Collegio>) anyObject());
		verify(ui, times(1)).showMessage(anyString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCalcola_calcolatoreSeggiNonValido() {
		UserInteractor ui = mock(UserInteractor.class);		
		MyController ctrl = new MyControllerUnderTest(ui);
				
		CalcolatoreSeggi calcolatoreSeggiMock = mock(CalcolatoreSeggi.class);
		CalcolatoreSeggi.addCalcolatoreSeggi("test3", calcolatoreSeggiMock);
		
		Map<String, Integer> actualResult = ctrl.calcola("test3XXX", ctrl.getSeggiMinimi());
		assertNull(actualResult);
		
		verify(calcolatoreSeggiMock, never()).assegnaSeggi(anyInt(),(List<Collegio>) anyObject());
		verify(ui, times(1)).showMessage(anyString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCalcola_calcolatoreLanciaEccezione() {
		UserInteractor ui = mock(UserInteractor.class);		
		MyController ctrl = new MyControllerUnderTest(ui);
				
		CalcolatoreSeggi calcolatoreSeggiMock = mock(CalcolatoreSeggi.class);
		when(calcolatoreSeggiMock.assegnaSeggi(anyInt(), (List<Collegio>) anyObject()))
			.thenThrow(IllegalArgumentException.class);
		CalcolatoreSeggi.addCalcolatoreSeggi("test4", calcolatoreSeggiMock);
		
		Map<String, Integer> actualResult = ctrl.calcola("test4", ctrl.getSeggiMinimi());
		assertNull(actualResult);
		
		verify(calcolatoreSeggiMock, times(1)).assegnaSeggi(anyInt(),(List<Collegio>) anyObject());
		verify(ui, times(1)).showMessage(anyString());
	}


}
