package appointment;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment
{
	private IntegerProperty	appointmentId;
	private StringProperty	title;
	private StringProperty	description;
	private StringProperty	type;
	private LocalDateTime	startDateTime;
	private StringProperty	startDate;
	private StringProperty	startTime;
	private LocalDateTime	endDateAndTime;
	private StringProperty	endDate;
	private StringProperty	endTime;
	private IntegerProperty	customerId;
	private IntegerProperty	contactId;

    public Appointment(String title, String description, String type, LocalDateTime startDateTime, 
						LocalDateTime endDateAndTime, int customerId, int contactId)
    {
        this.title 			= 	new SimpleStringProperty(this, title);
        this.description 	= 	new SimpleStringProperty(this, description);
        this.type 			= 	new SimpleStringProperty(this, type);
        this.startDateTime	= 	startDateTime;
		this.startDate 		= 	new SimpleStringProperty(this, startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.startTime 		= 	new SimpleStringProperty(this, startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
        this.endDateTime 	= 	endDateTime;
		this.endDate 		= 	new SimpleStringProperty(this, endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.endTime 		= 	new SimpleStringProperty(this, endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
        this.customerId 	= 	new SimpleIntegerProperty(this, customerId);
        this.contactId 		= 	new SimpleIntegerProperty(this, contactId);
    }//constructor

    public Appointment(int appointmentId, String title, String description, String type, 
						LocalDateTime startDateTime, LocalDateTime endDateAndTime, 
						int customerId, int userId, int contactId)
    {
        this.appointmentId	=	new SimpleIntegerProperty(this, appointmentId);
		this.title 			= 	new SimpleStringProperty(this, title);
        this.description 	= 	new SimpleStringProperty(this, description);
        this.type 			= 	new SimpleStringProperty(this, type);
        this.startDateTime	= 	startDateTime;
		this.startDate 		= 	new SimpleStringProperty(this, startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.startTime 		= 	new SimpleStringProperty(this, startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
        this.endDateTime 	= 	endDateTime;
		this.endDate 		= 	new SimpleStringProperty(this, endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.endTime 		= 	new SimpleStringProperty(this, endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
        this.customerId 	= 	new SimpleIntegerProperty(this, customerId);
        this.contactId 		= 	new SimpleIntegerProperty(this, contactId);
    }//constructor

	public IntegerProperty appointmentIdProperty() {
		return appointmentId;
	}
	
	public StringProperty titleProperty() {
		return title;
	}
	
	public StringProperty descriptionProperty() {
		return description;
	}
	
	public StringProperty typeProperty() {
		return type;
	}
	
	public StringProperty startDateProperty() {
		return startDate;
	}
	
	public StringProperty startTimeProperty() {
		return startTime;
	}
	
	public StringProperty endDateProperty() {
		return endDate;
	}
	
	public StringProperty endTimeProperty() {
		return endTime;
	}
	
	public IntegerProperty customerIdProperty() {
		return customerId;
	}
	
	public IntegerProperty contactIdProperty() {
		return contactId;
	}

    public int getAppointmentId() {
        return appointmentId.get();
    }//getAppointmentId

    public int getContactId() {
        return contactId.get();
    }//getContactId

    public int getCustomerId() {
        return customerId.get();
    }//getCustomerId

    public String getDescription() {
        return description.get();
    }//getDescription

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }//getEndDateTime

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }//getStartDateTime

    public String getTitle() {
        return title.get();
    }//getTitle

    public String getType() {
        return type.get();
    }//getType
}//class Appointment