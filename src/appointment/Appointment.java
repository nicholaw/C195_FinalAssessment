package appointment;

import java.util.Date;

public class Appointment
{
    private String appointmentId;
    private String contactInfo;
    private String customerId;
    private String title;
    private AppointmentType type;
    private String description;
    private Date startDateAndTime;
    private Date endDateAndTime;
    private Location location;

    public Appointment(
        String appointmentId,
        String contactInfo,
        String customerId,
        String title,
        AppointmentType type,
        String description,
        Date start,
        Date end,
        Location location)
    {
        this.appointmentId = appointmentId;
        this.contactInfo = contactInfo;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDateAndTime = start;
        this.endDateAndTime = end;
        this.location = location;
    }

    public Appointment(
            String contactInfo,
            String customerId,
            String title,
            AppointmentType type,
            String description,
            Date start,
            Date end,
            Location location)
    {
        this.appointmentId = generateId();
        this.contactInfo = contactInfo;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDateAndTime = start;
        this.endDateAndTime = end;
        this.location = location;
    }

    private static String generateId()
    {
        return "";
    }
}
