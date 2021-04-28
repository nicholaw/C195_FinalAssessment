package appointment;

public enum Location
{
    LONDON("London, UK"),
    NEW_YORK("New York, US"),
    QUEBEC("Quebec, CA");

    private final String name;

    Location(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
