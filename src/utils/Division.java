package utils;

public class Division
{
    private int divisionId;
    private String divisionName;

    public Division(int id, String name)
    {
        this.divisionId = id;
        this.divisionName = name;
    }//constructor

    public int getDivisionId()
    {
        return divisionId;
    }

    public String getDivisionName()
    {
        return divisionName;
    }

    @Override
    public String toString()
    {
        return divisionName;
    }
}//Division