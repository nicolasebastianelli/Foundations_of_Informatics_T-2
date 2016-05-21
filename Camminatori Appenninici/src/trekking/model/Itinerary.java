package trekking.model;

import java.util.List;

public interface Itinerary {

	void addTrail(Trail s) throws IllegalArgumentException;

	List<Trail> getTrails();

	String isValid(double dislivelloMax, double distanzaMax,
			Difficulty difficoltaMax, double difficoltaMedia);

	double calcMaxAltitudeDifference();

	double calcTotalLength();

	Difficulty calcMaxDifficulty();

	double calcAverageDifficulty();

}