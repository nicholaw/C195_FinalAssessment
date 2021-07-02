package utils;

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

    Month(String name, int monthOfYear, int numDays) {
        this.name 		 = name;
		this.monthOfYear = monthOfYear;
        this.numDays 	 = numDays;
    }

    /**
     *
     * @param monthOfYear
     * @return
     */
    public static Month getMonth(int monthOfYear) {
        for(Month m : Month.values()) {
            if(m.getMonthOfYear() == monthOfYear)
                return m;
        }
        return null;
    }
	
    public String getName() {
        return this.name;
    }
	
	public int getMonthOfYear() {
		return this.monthOfYear;
	}
	
    public int getNumDays() {
        return this.numDays;
    }
	
    @Override
    public String toString() {
        return this.name;
    }
}