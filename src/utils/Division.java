package utils;

/**
 * Represents a first-level-division (such as a state, provence, or canton) in which
 * a customer can reside.
 */
public class Division {
    private int divisionId;
    private String divisionName;
    private Country parentCountry;

    /**
     * Constructs this division with the provided is, name, and parent country.
     * @param id    -the division id
     * @param name  -the division name
     * @param parent    -the parent country
     */
    public Division(int id, String name, Country parent) {
        this.divisionId = id;
        this.divisionName = name;
        this.parentCountry = parent;
    }//constructor

    /**
     * Returns the id of this division.
     * @return  -the division id
     */
    public int getDivisionId()
    {
        return divisionId;
    }

    /**
     * Returns the name of this division.
     * @return  -the division name
     */
    public String getDivisionName()
    {
        return divisionName;
    }

    /**
     * Returns the parent country of this division.
     * @return  -the parent country
     */
    public Country getParentCountry() {
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