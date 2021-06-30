package utils;

public enum Location {
    LONDON("London, UK"),
    NEW_YORK("New York City, US"),
    QUEBEC("Quebec, CA");

    private final String location;

    Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        return this.location;
    }
}
