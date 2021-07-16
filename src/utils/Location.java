package utils;

/**
 * Represents a location where an appointment can take place.
 */
public enum Location {
    LONDON("London, UK"),
    NEW_YORK("New York City, US"),
    PHOENIX("Phoenix, USA"),
    QUEBEC("Montréal, CA");

    private final String location;

    /**
     * Constructs this Location with the provided name.
     * @param location  -the name of this location
     */
    Location(String location) {
        this.location = location;
    }

    /**
     * Returns the name of this location.
     * @return
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Returns the Location whose name matches the given String. Returns null if
     * no such location exists.
     * @param str   -the String to match
     * @return  -the matching Location
     */
    public static Location getLocation(String str) {
        for(Location l : Location.values()) {
            if(str.equals(l.getLocation())) {
                return l;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.location;
    }
}//location
