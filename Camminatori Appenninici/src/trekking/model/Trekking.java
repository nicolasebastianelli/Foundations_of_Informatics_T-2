package trekking.model;

import java.util.List;

import trekking.persistence.InvalidTrailFormatException;
import trekking.persistence.TrailReader;

public class Trekking {
	
	private List<Trail> trailList;
	public List<Trail> getTrailList() {
		return trailList;
	}
	
	public void loadTrails(TrailReader reader) throws InvalidTrailFormatException {
		trailList = reader.readTrails();
	}

}
