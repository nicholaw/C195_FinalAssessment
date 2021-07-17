package appointment;

import java.time.*;

/**
 * Constants regarding certain properties of appointments such as
 * the maximum allowed characters in an appointment title.
 */
public class AppointmentConstants {
    //Date and time formats
    public static final String DATE_FORMAT = "YYYY.MM.dd";
    public static final String TIME_FORMAT = "HH:mm";

    //Time zone constants
    public static final ZoneId ZONE_UTC = ZoneId.of("UTC");
    public static final ZoneId ZONE_EST = ZoneId.of("US/Eastern");

    //Business hours
    public static final ZonedDateTime OPEN_HOURS =
            ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)), ZONE_EST);
    public static final ZonedDateTime CLOSE_HOURS =
            ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0)), ZONE_EST);

    //Maximum allowed characters
    public static final int MAX_CHARS_TITLE = 50;
    public static final int MAX_CHARS_DESC = 50;
}
