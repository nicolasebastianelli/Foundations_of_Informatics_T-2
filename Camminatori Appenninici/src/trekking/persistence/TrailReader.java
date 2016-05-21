package trekking.persistence;

import java.util.List;

import trekking.model.Trail;

public interface TrailReader {

	List<Trail> readTrails() throws InvalidTrailFormatException;

}
