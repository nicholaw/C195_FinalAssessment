package utils;

import java.time.LocalDate;

/**
 * Represents the week of the month by storing the starting date of the week and the ending date of the week
 */
public class WeekOfMonth {
    private LocalDate start;
    private LocalDate end;
    private int mapIndex;

    /**
     * Constructs this WeekOfMonth with the given start and end dates. Throws an IllegalArgumentException
     * if the start date is after the end date or the dates are more than seven days apart.
     * @param start -the date this week begins
     * @param end -the date this week ends
     */
    private WeekOfMonth(LocalDate start, LocalDate end, int mapIndex) {
        if(start.isAfter(end))
            throw new IllegalArgumentException();
        else if(end.isAfter(start.plusDays(10))) //TODO
            throw new IllegalArgumentException();
        this.start = start;
        this.end = end;
        this.mapIndex = mapIndex;
    }//constructor

    public int getMapIndex() {
        return mapIndex;
    }

    public LocalDate getStart() {
        return start;
    }//getStart

    public LocalDate getEnd() {
        return end;
    }//getEnd

    public static WeekOfMonth of(LocalDate start, LocalDate end, int mapIndex) {
        return new WeekOfMonth(start, end, mapIndex);
    }//WeekOfMonth

    @Override
    public String toString() {
        return (start.getMonth() + " "  + start.getDayOfMonth() + " - " + end.getMonth() + " " + end.getDayOfMonth());
    }
}//WeekOfMonth
