package trains.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class LocalDateHelper {
	public static LocalDate getLocalDateWith(DayOfWeek day) {
		LocalDate date = LocalDate.now();
		int distanceFromPreviousRequestedDay = 
				date.getDayOfWeek().getValue() - day.getValue();
		int newDayOfMonth = date.getDayOfMonth() - distanceFromPreviousRequestedDay;
		if (newDayOfMonth <= 0) {
			newDayOfMonth = date.getDayOfMonth() + 
					(DayOfWeek.values().length - distanceFromPreviousRequestedDay);
		}
		date = date.withDayOfMonth(newDayOfMonth);
		return date;
	}
}
