package utils;

/**
 * Represent a Month of the year. Used to track how many days in each month.
 */
public enum Month
{
    JAN("Jan",	1,	31),
    FEB("Feb",	2,	28),
    MAR("Mar",	3,	31),
    APR("Apr",	4,	30),
    MAY("May",	5,	31),
    JUN("June",	6,	30),
    JUL("July",	7,	31),
    AUG("Aug",	8,	31),
    SEP("Sept",	9,	30),
    OCT("Oct",	10,	31),
    NOV("Nov",	11,	30),
    DEC("Dec",	12,	31);

    private final String name;
	private final int monthOfYear;
    private final int numDays;

    /**
     * Constructs this Month with the provided name, month-of-year, and number of days.
     * @param name  -name of this month
     * @param monthOfYear   -month of year
     * @param numDays   -number of days in this month
     */
    Month(String name, int monthOfYear, int numDays) {
        this.name 		 = name;
		this.monthOfYear = monthOfYear;
        this.numDays 	 = numDays;
    }

    /**
     * Returns the month which matches the provided month of the year. Returns null if no
     * such month exists.
     * @param monthOfYear   -month of the year
     * @return  -the matching month
     */
    public static Month getMonth(int monthOfYear) {
        for(Month m : Month.values()) {
            if(m.getMonthOfYear() == monthOfYear)
                return m;
        }
        return null;
    }//getMonth

    /**
     * Returns the name of this month.
     * @return  -the name of this month.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the month of the year of this month.
     * @return  -the month-of-year
     */
	public int getMonthOfYear() {
		return this.monthOfYear;
	}

    /**
     * Returns the number of days in this month.
     * @return  -the number of days
     */
    public int getNumDays() {
        return this.numDays;
    }
	
    @Override
    public String toString() {
        return this.name;
    }
}