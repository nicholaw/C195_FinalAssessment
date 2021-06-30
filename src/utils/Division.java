package utils;

public class Division
{
    private int divisionId;
    private String divisionName;
    private Country parentCountry;

    public Division(int id, String name, Country parent) {
        this.divisionId = id;
        this.divisionName = name;
        this.parentCountry = parent;
    }//constructor

    public int getDivisionId()
    {
        return divisionId;
    }

    public String getDivisionName()
    {
        return divisionName;
    }

    public Country getParentCountry()
    {
        return parentCountry;
    }

    @Override
    public String toString()
    {
        return divisionName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(!(obj instanceof Division))
            return false;
        if(!(((Division)obj).getDivisionId() == this.getDivisionId()))
            return false;
        return true;
    }
}//Division