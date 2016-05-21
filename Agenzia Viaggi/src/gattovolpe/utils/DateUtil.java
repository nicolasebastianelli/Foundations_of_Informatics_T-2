package gattovolpe.utils;

import gattovolpe.holidays.persistence.InvalidPackageFormatException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	
	private static SimpleDateFormat format = 
			new SimpleDateFormat("dd/MM/yyyy");
	
	public static int getDateDifference(Date start, Date end) {
		GregorianCalendar startCalendar =
				new GregorianCalendar();
		startCalendar.setTime(start);
		setMidnight(startCalendar);
		
		GregorianCalendar endCalendar =
				new GregorianCalendar();
		endCalendar.setTime(end);
		setMidnight(endCalendar);
		
		long diff = 
				endCalendar.getTimeInMillis()-startCalendar.getTimeInMillis();
		
		return (int) (diff / (24 * 60 * 60 * 1000));
	}
	
	public static Date normalizeDate(Date in) {
		Calendar calendar =
				new GregorianCalendar();
		calendar.setTime(in);
		setMidnight(calendar);
		return calendar.getTime();
	}

	private static void setMidnight(Calendar gc) {
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
	}
	
	public static Date parseDate(String text) throws InvalidPackageFormatException {
		Date date = null;
		try {
			date = 
				format.parse( text );
		} catch( Exception e) {
			throw new InvalidPackageFormatException("Errore nel parse della data: " + text);
		}
		return normalizeDate(date);
	}
	
}
