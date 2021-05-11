package utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Country
{
    private int countryId;
    private String countryName;
    private Set firstLevelDivisions;

    public Country(int id, String name)
    {
        this.countryId = id;
        this.countryName = name;
        firstLevelDivisions = new LinkedHashSet<Division>();
    }//constructor

    public boolean addDivision(Division d)
    {
        return firstLevelDivisions.add(d);
    }//addDivision

    public int getCountryId()
    {
        return countryId;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public Collection<Division> getFirstLevelDivisions()
    {
        return firstLevelDivisions;
    }

    public void setDivisions(Collection<Division> divisions)
    {
        for(Division d : divisions)
            firstLevelDivisions.add(d);
    }//setDivisions

    @Override
    public String toString()
    {
        return countryName;
    }
}//Country
