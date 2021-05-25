package appointment;

import java.time.LocalDateTime;

public class Appointment
{
    private int appointmentId;
    private String title;
    private String description;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointment(	String title, String description, String type,
                           LocalDateTime startDateTime, LocalDateTime endDateAndTime,
                           int customerId, int userId, int contactId)
    {
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateAndTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }//constructor

    public Appointment(	int appointmentId, String title, String description, String type,
                           LocalDateTime startDateTime, LocalDateTime endDateAndTime,
                           int customerId, int userId, int contactId)
    {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateAndTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }//constructor w/id

    public int getAppointmentId()
    {
        return appointmentId;
    }//getAppointmentId

    public int getContactId()
    {
        return contactId;
    }//getContactId

    public int getCustomerId()
    {
        return customerId;
    }//getCustomerId

    public String getDescription()
    {
        return description;
    }//getDescription

    public LocalDateTime getEndDateTime()
    {
        return endDateTime;
    }//getEndDateTime

    public LocalDateTime getStartDateTime()
    {
        return startDateTime;
    }//getStartDateTime

    public String getTitle()
    {
        return title;
    }//getTitle

    public String getType()
    {
        return type;
    }//getType

    public int getUserId()
    {
        return userId;
    }//getUserId
}//class Appointment
