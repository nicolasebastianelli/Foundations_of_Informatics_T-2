package trekking.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyItinerary implements Itinerary {

	private List<Trail> sentieri;
	public MyItinerary() {
		sentieri = new ArrayList<Trail>();
	}
	
	@Override
	public void addTrail(Trail s) throws IllegalArgumentException {
		//--- inizio nello stesso luogo dell'ultimo
		if(sentieri.size()>0) {
			if(!sentieri.get(sentieri.size()-1).getTrailEnd().equals(s.getTrailHead()))
				throw new IllegalArgumentException("Stazione di partenza invalida");
		}
		sentieri.add(s);
	}
	
	@Override
	public List<Trail> getTrails()
	{
		return sentieri;
	}

	@Override
	public String isValid(double dislivelloMax, double distanzaMax, Difficulty difficoltaMax, double difficoltaMedia) {
		DecimalFormat df = new DecimalFormat("#.##");
		if(calcMaxAltitudeDifference()>dislivelloMax) 
			return "Dislivello max superato: " + df.format(calcMaxAltitudeDifference());
		if(calcTotalLength()>distanzaMax)
			return "Distanza tot superata: " + df.format(calcTotalLength());
		if(calcMaxDifficulty().getValue()>difficoltaMax.getValue())
			return "Difficolta max superata: " + calcMaxDifficulty();
		if(calcAverageDifficulty()>difficoltaMedia)
			return "Difficolta avg superata: " + df.format(calcAverageDifficulty());
		return null;
	}
	
	@Override
	public double calcMaxAltitudeDifference() {
		if(sentieri.size()<1)
			return 0.0;
		double quotaMin = sentieri.get(0).getMinAltitude();
		double quotaMax = sentieri.get(0).getMaxAltitude();
		for( Trail se : sentieri ) {
			if(se.getMinAltitude()<quotaMin)
				quotaMin = se.getMinAltitude();
			if(se.getMaxAltitude()>quotaMax)
				quotaMax = se.getMaxAltitude();
		}
		return quotaMax - quotaMin;
	}
	
	@Override
	public double calcTotalLength() {
		double lunghezzaTotale = 0.0;
		for(Trail se : sentieri) {
			lunghezzaTotale += se.getLength();
		}
		return lunghezzaTotale;
	}
	
	@Override
	public Difficulty calcMaxDifficulty() {
		Difficulty max = Difficulty.LOW;
		for(Trail se : sentieri) {
			if(se.getDifficulty().getValue()>max.getValue())
				max = se.getDifficulty();
		}
		return max;
	}
	
	@Override
	public double calcAverageDifficulty() {
		double valore = 0.0;
		double pesoTotale = 0.0;
		for(Trail s : sentieri){
			double peso =  s.getLength() * s.getAltitudeDifference();
			valore += s.getDifficulty().getValue() * peso;
			pesoTotale += peso;
		}
		if(pesoTotale>0) {
			return valore / pesoTotale;
		} else return 0.0;
	}
}
