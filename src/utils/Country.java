package utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a Country in which a customer can reside.
 */
public class Country {
    private int countryId;
    private String countryName;
    private Set<Division> firstLevelDivisions;

    /**
     * Constructs this country with the provided name and id.
     * @param id    -the country id
     * @param name  -the country name
     */
    public Country(int id, String name) {
        this.countryId = id;
        this.countryName = name;
        firstLevelDivisions = new LinkedHashSet<>();
    }//constructor

    /**
     * Adds a first-level division to this country.
     * @param d the division to be added
     * @return  whether the division was successfully added
     */
    public boolean addDivision(Division d)
    {
        return firstLevelDivisions.add(d);
    }//addDivision

    /**
     * Returns this county's id.
     * @return  -the country id
     */
    public int getCountryId()
    {
        return countryId;
    }

    /**
     * Returns this country's name.
     * @return  -the country name
     */
    public String getCountryName()
    {
        return countryName;
    }

    /**
     * Returns a collection of first-level-divisions within this country.
     * @return  -the collection of first-level-divisions.
     */
    public Collection<Division> getFirstLevelDivisions()
    {
        return firstLevelDivisions;
    }

    /**
     * Returns the division whose division id matches the provided id. Returns null if no
     * such division exists.
     * @param divisionId    -the division id to match
     * @return  -the division whose id matches
     */
    public Division getDivision(int divisionId) {
        for(Division d : firstLevelDivisions) {
            if(d.getDivisionId() == divisionId) {
                return d;
            }
        }
        return null;
    }//getDivision

    /**
     * Sets the divisions within this country to the provided collection of divisions.
     * @param divisions -the divisions to set
     */
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