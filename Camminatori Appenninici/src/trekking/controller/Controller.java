package trekking.controller;

import java.util.List;

import trekking.model.Difficulty;
import trekking.model.Itinerary;
import trekking.model.Trail;

public interface Controller {
	
	List<Trail> getAllTrails();
	
	Itinerary getItinerary();
	
	boolean checkItinerary(double dislivelloMax,
			double distanzaMax,
			Difficulty difficoltaMax,
			double difficoltaMedia);
	
	void addTrail(Trail s)
			throws IllegalArgumentException;
	
	void reset();
}
