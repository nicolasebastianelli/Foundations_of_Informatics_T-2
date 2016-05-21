package trekking.controller;

import java.util.List;

import trekking.model.Difficulty;
import trekking.model.Itinerary;
import trekking.model.MyItinerary;
import trekking.model.Trail;
import trekking.model.Trekking;
import trekking.ui.MessageManager;

public class MyController implements Controller {

	private Itinerary itinerary;
	private Trekking trekking;
	private MessageManager manager;
	
	public MyController(Trekking trekking, MessageManager manager) {
		this.trekking = trekking;
		this.itinerary = new MyItinerary();
		this.manager = manager;
	}

	@Override
	public boolean checkItinerary(double dislivelloMax, 
			double distanzaMax,
			Difficulty difficoltaMax, 
			double difficoltaMedia) {
		String checkResult =
				itinerary.isValid(dislivelloMax, distanzaMax, difficoltaMax, difficoltaMedia);
		if(checkResult != null) {
			manager.showMessage(checkResult);
			return false;
		} else
			return true;
	}

	@Override
	public void addTrail(Trail s) throws IllegalArgumentException {
		this.itinerary.addTrail(s);
	}

	@Override
	public List<Trail> getAllTrails() {
		return trekking.getTrailList();
	}

	@Override
	public Itinerary getItinerary() {
		return this.itinerary;
	}

	@Override
	public void reset() {
		itinerary = new MyItinerary();
	}

}
