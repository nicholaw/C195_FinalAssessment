package utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Country
{
    private int countryId;
    private String countryName;
    private Set<Division> firstLevelDivisions;

    public Country(int id, String name)
    {
        this.countryId = id;
        this.countryName = name;
        firstLevelDivisions = new LinkedHashSet<>();
    }//constructor

    /**
     * Adds a first-level division to this country.
     *
     * @param d the division to be added
     * @return  whether the division was successfully added
     */
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

    public Division getDivision(int divisionId) {
        for(Division d : firstLevelDivisions) {
            if(d.getDivisionId() == divisionId) {
                return d;
            }
        }
        return null;
    }//getDivision

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

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(!(obj instanceof Country))
            return false;
        if(!(((Country)obj).getCountryId() == this.getCountryId()))
            return false;
        return true;
    }
}//Country
