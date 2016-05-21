package rs.model;

import java.util.GregorianCalendar;

public enum Month {
	JANUARY(GregorianCalendar.JANUARY, 1), FEBRUARY(GregorianCalendar.FEBRUARY,
			2), MARCH(GregorianCalendar.MARCH, 3), APRIL(
			GregorianCalendar.APRIL, 4), MAY(GregorianCalendar.MAY, 5), JUNE(
			GregorianCalendar.JUNE, 6), JULY(GregorianCalendar.JULY, 7), AUGUST(
			GregorianCalendar.AUGUST, 8), SEPTEMBER(
			GregorianCalendar.SEPTEMBER, 9), OCTOBER(GregorianCalendar.OCTOBER,
			10), NOVEMBER(GregorianCalendar.NOVEMBER, 11), DECEMBER(
			GregorianCalendar.DECEMBER, 12);

	private int gregorianCalendarMonth;
	private int conventionalMonth;

	private Month(int gregorianCalendarMonth, int conventionalMonth) {
		this.gregorianCalendarMonth = gregorianCalendarMonth;
		this.conventionalMonth = conventionalMonth;
	}

	public boolean isContainedIn(Month[] months) {
		for (Month month : months) {
			if (month == this) {
				return true;
			}
		}
		return false;
	}

	public int toGregorianCalendarMonth() {
		return gregorianCalendarMonth;
	}

	public int toConventionalMonth() {
		return conventionalMonth;
	}

	public static Month fromGregorianCalendarMonth(int gregorianCalendarMonth) {
		for (Month month : Month.values()) {
			if (month.gregorianCalendarMonth == gregorianCalendarMonth) {
				return month;
			}
		}
		return null;
	}

	public static Month fromConventionalMonth(int conventionalMonth) {
		for (Month month : Month.values()) {
			if (month.conventionalMonth == conventionalMonth) {
				return month;
			}
		}
		return null;
	}
}
