package Utils;

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
	
	Minute(String minute, int minuteOfHour) {
		this.minute = minute;
		this.minuteOfHour = minuteOfHour;
	}
	
	public getMinute() {
		return this.minute;
	}
	
	public getMinuteOfHour() {
		return this.minuteOfHour;
	}
	
	@Override
	public static String toString() {
		return this.minute;
	}
}