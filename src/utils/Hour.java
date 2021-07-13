package utils;

/**
 * Represents an hour of the day. Used for displaying the hour with a leading
 * zero for the 24-hour clock.
 */
public enum Hour {
	TWENTY_FOUR	("00", 0),
	ONE			("01", 1),
	TWO			("02", 2),
	THREE		("03", 3),
	FOUR		("04", 4),
	FIVE		("05", 5),
	SIX			("06", 6),
	SEVEN		("07", 7),
	EIGHT		("08", 8),
	NINE		("09", 9),
	TEN			("10", 10),
	ELEVEN		("11", 11),
	TWELVE		("12", 12),
	THIRTEEN	("13", 13),
	FOURTEEN	("14", 14),
	FIFTEEN		("15", 15),
	SIXTEEN		("16", 16),
	SEVENTEEN	("17", 17),
	EIGHTEEN	("18", 18),
	NINETEEN	("19", 19),
	TWENTY		("20", 20),
	TWENTY_ONE	("21", 21),
	TWENTY_TWO	("22", 22),
	TWENTY_THREE("23", 23);
	
	private final String hour;
	private final int hourOfDay;

	/**
	 * Constructs this hour with the given name and hour-of-day.
	 * @param hour	-String name of the hour
	 * @param hourOfDay	-hour of the day
	 */
	Hour(String hour, int hourOfDay) {
		this.hour = hour;
		this.hourOfDay = hourOfDay;
	}

	/**
	 * Returns the name of the hour which matches the given hour-of-day
	 * @param hourOfDay -the hour of day
	 * @return	-name of the hour
	 */
	public static Hour getHour(int hourOfDay) {
		for(Hour h : Hour.values()) {
			if(h.getHourOfDay() == hourOfDay)
				return h;
		}
		return null;
	}

	/**
	 * Returns the hour of day.
	 * @return	-the hour of day
	 */
	public int getHourOfDay() {
		return this.hourOfDay;
	}
	
	@Override
	public String toString() {
		return this.hour;
	}
}//Hour