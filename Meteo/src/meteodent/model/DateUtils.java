package meteodent.model;

import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtils {

	private DateUtils() {
	}
	
	public static Date getDayDate(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static boolean isSameDayDate(Date date1, Date date2) {
		GregorianCalendar cal = new GregorianCalendar();
		
		cal.setTime(date1);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		Date dayDate1 = cal.getTime();
		
		cal.setTime(date2);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		Date dayDate2 = cal.getTime();
		
		return dayDate1.equals(dayDate2);
	}
	
	public static Date setHourOfDay(Date date, int hourOfDay) {
		GregorianCalendar cal = new GregorianCalendar();
		
		cal.setTime(date);
		cal.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		
		return cal.getTime();		
	}
	
	public static Date createDate(int day, int month, int year, int hourOfDay) {
		GregorianCalendar cal = new GregorianCalendar();
		
		cal.set(GregorianCalendar.DAY_OF_MONTH, day);
		cal.set(GregorianCalendar.MONTH, month);
		cal.set(GregorianCalendar.YEAR, year);
		cal.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
}
