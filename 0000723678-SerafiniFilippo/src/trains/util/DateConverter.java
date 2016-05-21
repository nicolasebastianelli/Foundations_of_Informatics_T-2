package trains.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateConverter {
	public static LocalDate asLocalDate(java.util.Date date) {
		return asLocalDate(date, ZoneId.systemDefault());
	}

	public static LocalDate asLocalDate(java.util.Date date, ZoneId zone) {
		if (date == null)
			return null;

		if (date instanceof java.sql.Date)
			return ((java.sql.Date) date).toLocalDate();
		else
			return Instant.ofEpochMilli(date.getTime()).atZone(zone)
					.toLocalDate();
	}
}
