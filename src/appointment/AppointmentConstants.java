package appointment;

import java.time.LocalTime;

public class AppointmentConstants {
    public static final String DATE_FORMAT = "YYYY.MM.dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final LocalTime OPEN_HOURS = LocalTime.of(8, 0);
    public static final LocalTime CLOSE_HOURS = LocalTime.of(22, 0);
}
