package appointment;

import java.time.LocalDateTime;

public class Appointment
{
    private String appointmentId;
    private String contactId;
    private String contactContactInfo;
    private String customerId;
    private String customerContactInfo;
    private String title;
    private AppointmentType type;
    private String description;
    private LocalDateTime startDateAndTime; //TODO: store as UTC
    private LocalDateTime endDateAndTime;   //TODO: store as UTC
    private Location location;

    public Appointment(
        String appointmentId,
        String contactId,
        String contactContactInfo,
        String customerId,
        String customerContactInfo,
        String title,
        AppointmentType type,
        String description,
        LocalDateTime start,
        LocalDateTime end,
        Location location)
    {
        this.appointmentId = appointmentId;
        this.contactId = contactId;
        this.contactContactInfo = contactContactInfo;
        this.customerId = customerId;
        this.customerContactInfo = customerContactInfo;
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
            LocalDateTime start,
            LocalDateTime end,
            Location location)
    {
        this.appointmentId = generateId();
        this.contactId = contactId;
        this.contactContactInfo = contactContactInfo;
        this.customerId = customerId;
        this.customerContactInfo = customerContactInfo;
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

    public String getAppointmentId()
    {
        return appointmentId;
    }

    public String getContactId()
    {
        return contactId;
    }

    public String getContactContactInfo()
    {
        return contactContactInfo;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public String getCustomerContactInfo()
    {
        return customerContactInfo;
    }

    public String getDescription()
    {
        return description;
    }

    public LocalDateTime getEndDateAndTime()
    {
        return endDateAndTime;
    }

    public Location getLocation()
    {
        return location;
    }

    public LocalDateTime getStartDateAndTime()
    {
        return startDateAndTime;
    }

    public String getTitle()
    {
        return title;
    }

    public AppointmentType getType()
    {
        return type;
    }
}
