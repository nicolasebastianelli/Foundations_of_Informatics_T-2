package zannotaxi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zannotaxi.model.CorsaTaxi;
import zannotaxi.model.SimpleTime;
import zannotaxi.model.TariffaADistanza;
import zannotaxi.model.TariffaATempo;
import zannotaxi.model.Tassametro;
import zannotaxi.model.ZannoTassametro;
import zannotaxi.persistence.MyCorseTaxiReader;

public class ZannoUtils {

	static class MyCorseTaxiReaderWriter extends MyCorseTaxiReader {

		public void scriviCorse(List<CorsaTaxi> corse, OutputStream stream)
				throws IOException {

			ObjectOutputStream oos = new ObjectOutputStream(stream);

			oos.writeInt(corse.size());
			for (CorsaTaxi corsa : corse) {

				oos.writeInt(corsa.getOraPartenza().getOre());
				oos.writeInt(corsa.getOraPartenza().getMinuti());

				oos.writeObject(corsa.getDettagliCorsa());

				oos.writeObject(corsa.getRilevazioniDistanze());
			}
		}
		
		public void scriviCorseNonValide(List<CorsaTaxi> corse, OutputStream stream)
				throws IOException {

			ObjectOutputStream oos = new ObjectOutputStream(stream);

			oos.writeInt(corse.size());
			for (CorsaTaxi corsa : corse) {

				oos.writeObject(corsa.getOraPartenza().getOre());
				oos.writeObject(corsa.getOraPartenza().getMinuti());

				oos.writeObject(corsa.getRilevazioniDistanze());
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		TariffaATempo t0 = new TariffaATempo("T0", 0.15, 12);
		TariffaADistanza t1 = new TariffaADistanza("T1", 100, 0.25, 0, 10);
		TariffaADistanza t2 = new TariffaADistanza("T2", 85,  0.25, 10, 25);
		TariffaADistanza t3 = new TariffaADistanza("T3", 65,  0.15, 25, Double.MAX_VALUE);

		List<TariffaADistanza> tariffeADistanza = new ArrayList<TariffaADistanza>();
		tariffeADistanza.add(t1);
		tariffeADistanza.add(t2);
		tariffeADistanza.add(t3);

		Tassametro tassametro = new ZannoTassametro(27.0, 4.00, 6.00, t0, tariffeADistanza);
		System.out.println("DEBUG: " + tassametro);

		CorsaTaxi corsa1 = new CorsaTaxi("corsa1", new SimpleTime(9, 40),
				new double[] { 8.33, 16.67, 25, 33.33, 41.67, 50, 58.33 });
		System.out.println("DEBUG: " + corsa1 + " = Euro " + tassametro.calcolaCosto(corsa1)); 
		// Corsa1: 4.00 + 0.00 
		// (58.3 m @ 8.33m/s = 30km/h -> a distanza -> 1 scatto ogni 100m -> 0 scatti)
		CorsaTaxi corsa2 = new CorsaTaxi("corsa2", new SimpleTime(22, 40),
				new double[] { 5.56, 11.11, 16.67, 22.22, 27.78, 33.33, 38.89, 44.44 });
		System.out.println("DEBUG: " + corsa2 + " = Euro " + tassametro.calcolaCosto(corsa2));
		// Corsa2: 6.00 + 0.00
		// (44.4 m @ 5.56m/s = 20km/h -> a tempo -> 1 scatto ogni 12s -> 0 scatti)
		CorsaTaxi corsa3 = new CorsaTaxi("corsa3", new SimpleTime(9, 40), 15, 240);
		System.out.println("DEBUG: " + corsa3 + " = Euro " + tassametro.calcolaCosto(corsa3));
		// Corsa3: 4.00 + 3.00
		// (1 km @ 15km/h -> a tempo -> 1 scatto ogni 12s -> 20 scatti = 3 euro)
		CorsaTaxi corsa4 = new CorsaTaxi("corsa4", new SimpleTime(9, 40), 30, 120);
		System.out.println("DEBUG: " + corsa4 + " = Euro " + tassametro.calcolaCosto(corsa4));
		// Corsa4: 4.00 + 2.50
		// (1 km @ 30km/h -> a distanza -> 1 scatto ogni 100m -> 10 scatti = 2,50 euro)
		CorsaTaxi corsa5 = new CorsaTaxi("corsa5", new SimpleTime(9, 40), 60,60);
		System.out.println("DEBUG: " + corsa5 + " = Euro " + tassametro.calcolaCosto(corsa5));
		// Corsa5: 4.00 + 2.50
		// (1 km @ 60km/h -> a distanza -> 1 scatto ogni 100m -> 10 scatti = 2,50 euro)
		CorsaTaxi corsa6 = new CorsaTaxi("corsa6", new SimpleTime(9, 40), 60,120);
		System.out.println("DEBUG: " + corsa6 + " = Euro " + tassametro.calcolaCosto(corsa6));
		// Corsa6: 4.00 + 5.00
		// (2 km @ 60km/h -> a distanza -> 1 scatto ogni 100m -> 20 scatti = 5 euro)

		// esempio del testo: 500m a 30 km/h + 500m a 20km/h
		CorsaTaxi corsa7 = new CorsaTaxi("corsa7", new SimpleTime(9, 40), 30,
				60 /* 500m */, 20, 90 /* 500m */);
		System.out.println("DEBUG: " + corsa7 + " = Euro " + tassametro.calcolaCosto(corsa7));
		// Corsa7: 4.00 + 2.45
		// (0.5 km @ 30km/h  + 0.5 km @ 20km/h -> prima a distanza, poi a tempo
		//  prima -> 1 scatto ogni 100m -> 5 scatti = 1,25 euro
		//  poi --> 1 scatto ogni 12sec -> 8 scatti = 1,20 euro
		
		// 1 minuto a 120 km/h = 2 km totali
		CorsaTaxi corsa8 = new CorsaTaxi("corsa8", new SimpleTime(9, 40), 120, 60);
		System.out.println("DEBUG: " + corsa8 + " = Euro " + tassametro.calcolaCosto(corsa8));
		// Corsa8: 4.00 + 5.00
		// (2 km @ 120km/h -> a distanza -> 1 scatto ogni 100m -> 20 scatti = 5 euro)
		
		MyCorseTaxiReaderWriter rw = new MyCorseTaxiReaderWriter();

		rw.scriviCorse(Arrays.asList(corsa1, corsa2, corsa3, corsa4, corsa5,
				corsa6, corsa7, corsa8), new FileOutputStream("CORSE.BIN"));
		
		rw.scriviCorseNonValide(Arrays.asList(corsa1, corsa2), 
				new FileOutputStream("NONVALIDE.BIN"));
	}

}
