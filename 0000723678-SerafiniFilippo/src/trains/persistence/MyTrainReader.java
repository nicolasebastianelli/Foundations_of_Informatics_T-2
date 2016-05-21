package trains.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import trains.model.Operator;
import trains.model.Station;
import trains.model.Stop;
import trains.model.Train;

public class MyTrainReader implements TrainReader {

	private Map<String, Station> stationMap;
	private BufferedReader br;

	public MyTrainReader(Map<String, Station> stationMap) {
		this.stationMap = stationMap;
	}

	@Override
	public Collection<Train> readTrains(Reader reader) throws IOException,
	BadFileFormatException {
		Collection<Train> trains = new ArrayList<>();
		this.br = new BufferedReader(reader);
		String line = null;
		Train train;

		while((line = br.readLine()) != null) {
			train = parseTrain(line.trim());
			trains.add(train);
		}
		return trains;
	}

	private Train parseTrain(String line) throws BadFileFormatException {
		if(line.isEmpty())
			throw new BadFileFormatException("Letta linea vuota");
		StringTokenizer tokenizer = new StringTokenizer(line, "\t\n\r");

		String trainId = tokenizer.nextToken().trim().toUpperCase();
		if(trainId.isEmpty())
			throw new BadFileFormatException("Id treno mancante");
		char op = trainId.charAt(0);

		Operator operator = getOperator(op);

		String trainNumber = trainId.substring(1);
		long number;
		try {
			number = Long.parseLong(trainNumber);
		} catch (NumberFormatException e) {
			throw new BadFileFormatException("Numero treno non numerico");
		}

		String daysOfWeek = tokenizer.nextToken().trim();
		if(daysOfWeek.isEmpty())
			throw new IllegalArgumentException("Giorni della settimana mancanti");

		DayOfWeek[] days = parseDaysOfWeek(daysOfWeek.toCharArray());

		String fermate = tokenizer.nextToken().trim();
		if(fermate.isEmpty())
			throw new IllegalArgumentException("Fermate mancanti");

		List<Stop> stops = parseStops(fermate);

		return new Train(operator, number, days, stops);
	}

	private Operator getOperator(char op) throws BadFileFormatException {
		switch(op) {
		case 'S' : return Operator.S;
		case 'Z' : return Operator.Z;
		default: throw new BadFileFormatException("Operatore mancante o non valido");
		}
	}

	private DayOfWeek[] parseDaysOfWeek(char[] ds) throws BadFileFormatException {
		DayOfWeek[] days = new DayOfWeek[ds.length];
		for(int i = 0; i < ds.length; i++) {
			int num = (int) ds[i] - 48;
			if(num < 1 || num > 7)
				throw new BadFileFormatException("Giorno della settimana non valido");
			days[i] = DayOfWeek.of(num);
		}
		return days;
	}	

	private List<Stop> parseStops(String line) throws BadFileFormatException {
		List<Stop> result = new ArrayList<Stop>();
		String[] fermate = line.split(",");
		Stop stop;
		for(String s : fermate) {
			stop = parseSingleStop(s.trim());
			result.add(stop);
		}
		return result;
	}

	private Stop parseSingleStop(String s) throws BadFileFormatException {
		StringTokenizer tokenizer = new StringTokenizer(s, "/");
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

		if(tokenizer.countTokens() != 3)
			throw new BadFileFormatException("Formato Stop non valido");

		String stationCode = tokenizer.nextToken().trim();
		Station station = stationMap.get(stationCode);
		if(station == null)
			throw new BadFileFormatException("Codice stazione non valido");

		try {
			String arrivalTime = tokenizer.nextToken().trim();
			Optional<LocalTime> arrival;
			LocalTime time;
			if(arrivalTime.equals("--"))
				arrival = Optional.empty();
			else {
				time = LocalTime.parse(arrivalTime, formatter);
				arrival = Optional.of(time);
			}

			String departureTime = tokenizer.nextToken().trim();
			Optional<LocalTime> departure;
			if(departureTime.equals("--"))
				departure = Optional.empty();
			else {
				time = LocalTime.parse(departureTime, formatter);
				departure = Optional.of(time);
			}
			
			return new Stop(station, arrival, departure);
			
		}catch (DateTimeParseException e) {
			throw new BadFileFormatException("Formato orari non valido");
		}
	}
}
