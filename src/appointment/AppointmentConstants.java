package appointment;

import java.time.LocalTime;

/**
 * Constants regarding certain properties of appointments such as
 * the maximum allowed characters in an appointment title.
 */
public class AppointmentConstants {
    //Date/Time formats
    public static final String DATE_FORMAT = "YYYY.MM.dd";
    public static final String TIME_FORMAT = "HH:mm";

    //Business hours    TODO: should be EST, not local time
    public static final LocalTime OPEN_HOURS = LocalTime.of(8, 0);
    public static final LocalTime CLOSE_HOURS = LocalTime.of(22, 0);

    //Maximum allowed characters
    public static final int MAX_CHARS_TITLE = 50;
    public static final int MAX_CHARS_DESC = 50;
}
