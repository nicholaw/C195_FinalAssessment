package Utils;

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
	
	Hour(String hour, int hourOfDay) {
		this.hour = hour;
		this.hourOfDay = hourOfDay;
	}
	
	public getHour() {
		return this.hour;
	}
	
	public getHourOfDay() {
		return this.hourOfDay;
	}
	
	@Override
	public static String toString() {
		return this.hour;
	}
}