package utils;

/**
 * Represents the minute of the hour. Used to display minutes with a
 * leading zero.
 */
public enum Minute {
	ZERO		("00", 0),
	FIVE		("05", 5),
	TEN			("10", 10),
	FIFTEEN		("15", 15),
	TWENTY		("20", 20),
	TWENTY_FIVE	("25", 25),
	THIRTY		("30", 30),
	THIRTY_FIVE	("35", 35),
	FORTY		("40", 40),
	FORTY_FIVE	("45", 45),
	FIFTY		("50", 50),
	FIFTY_FIVE	("55", 55);
	
	private final String minute;
	private final int minuteOfHour;

	/**
	 * Constructs this minute with the provided minute-of-hour and name.
	 * @param minute	-name of this minute
	 * @param minuteOfHour	-minute of hour
	 */
	Minute(String minute, int minuteOfHour) {
		this.minute = minute;
		this.minuteOfHour = minuteOfHour;
	}

	/**
	 * Returns the nearest fifth minute rounding up to the provided minute
	 * of the hour.
	 * @param minuteOfHour	-the minute of hour
	 * @return	-the nearest fifth minute rounding up
	 */
	public static Minute getMinute(int minuteOfHour) {
		int t;
		for(Minute m : Minute.values()) {
			t = m.getMinuteOfHour();
			for(int i = t; i >= (t - 4); i--) {
				if(i == minuteOfHour) {
					return m;
				}
			}
		}
		return Minute.ZERO;
	}

	/**
	 * Returns this minutes minute of the hour.
	 * @return	-the minute of the hour
	 */
	public int getMinuteOfHour() {
		return this.minuteOfHour;
	}
	
	@Override
	public String toString() {
		return this.minute;
	}
}