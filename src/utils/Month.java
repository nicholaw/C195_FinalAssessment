package utils;

public enum Month
{
    JAN("Jan", 31),
    FEB("Feb", 28),
    MAR("Mar", 31),
    APR("Apr", 30),
    MAY("May", 31),
    JUN("June", 30),
    JUL("July", 31),
    AUG("Aug", 31),
    SEP("Sept", 30),
    OCT("Oct", 31),
    NOV("Nov", 30),
    DEC("Dec", 31);

    private final String name;
    private final int numDays;

    private Month(String name, int numDays)
    {
        this.name = name;
        this.numDays = numDays;
    }
    public String getName()
    {
        return this.name;
    }
    public int getNumDays()
    {
        return this.numDays;
    }
    @Override
    public String toString()
    {
        return this.name;
    }
}