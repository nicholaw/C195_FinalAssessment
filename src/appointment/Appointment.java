package appointment;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import utils.Contact;
import utils.Location;

public class Appointment
{
	private IntegerProperty	appointmentId;
	private StringProperty	title;
	private StringProperty	description;
	private StringProperty	type;
	private LocalDateTime	startDateTime;
	private StringProperty	startDate;
	private StringProperty	startTime;
	private LocalDateTime	endDateTime;
	private StringProperty	endDate;
	private StringProperty	endTime;
	private LongProperty	customerId;
	private Contact			contact;
	private Location		location;

    public Appointment(String title, String description, String type, LocalDateTime startDateTime, 
						LocalDateTime endDateTime, int customerId, Contact contact, Location location)
    {
        this.title 			= 	new SimpleStringProperty(this, "title", title);
        this.description 	= 	new SimpleStringProperty(this, "description", description);
        this.type 			= 	new SimpleStringProperty(this, "type", type);
        this.startDateTime	= 	startDateTime;
		this.startDate 		= 	new SimpleStringProperty(this, "startDate", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.startTime 		= 	new SimpleStringProperty(this, "startTime", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
        this.endDateTime 	= 	endDateTime;
		this.endDate 		= 	new SimpleStringProperty(this, "endDate", endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.endTime 		= 	new SimpleStringProperty(this, "endTime", endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
        this.customerId 	= 	new SimpleLongProperty(this, "customerId", customerId);
		this.contact 		= 	contact;
		this.location		=	location;
    }//constructor

    public Appointment(int appointmentId, String title, String description, String type, 
						LocalDateTime startDateTime, LocalDateTime endDateTime,
						long customerId, Contact contact, Location location)
    {
        this.appointmentId	=	new SimpleIntegerProperty(this, "appointmentId", appointmentId);
		this.title 			= 	new SimpleStringProperty(this, "title", title);
		this.description 	= 	new SimpleStringProperty(this, "description", description);
		this.type 			= 	new SimpleStringProperty(this, "type", type);
		this.startDateTime	= 	startDateTime;
		this.startDate 		= 	new SimpleStringProperty(this, "startDate", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.startTime 		= 	new SimpleStringProperty(this, "startTime", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
		this.endDateTime 	= 	endDateTime;
		this.endDate 		= 	new SimpleStringProperty(this, "endDate", endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.endTime 		= 	new SimpleStringProperty(this, "endTime", endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
		this.customerId 	= 	new SimpleLongProperty(this, "customerId", customerId);
		this.contact 		= 	contact;
		this.location		=	location;
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
	
	public LongProperty customerIdProperty() {
		return customerId;
	}
	
	public LongProperty contactIdProperty() {
		return new SimpleLongProperty(this, "contactId", contact.getId());
	}
	
	public StringProperty contactNameProperty() {
		return new SimpleStringProperty(this, contact.getName());
	}
	
	public StringProperty contactEmailProperty() {
		return new SimpleStringProperty(this, contact.getEmail());
	}

    public int getAppointmentId() {
        return appointmentId.get();
    }//getAppointmentId

    public long getContactId() {
        return contact.getId();
    }//getContactId

    public long getCustomerId() {
        return customerId.get();
    }//getCustomerId

    public String getDescription() {
        return description.get();
    }//getDescription

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }//getEndDateTime

	public Location getLocation() {
    	return location;
	}

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }//getStartDateTime

    public String getTitle() {
        return title.get();
    }//getTitle

    public String getType() {
        return type.get();
    }//getType
	
	public Contact getContact() {
		return contact;
	}//getContact

	public boolean overlaps(Appointment a) {
    	if(a != null) {
			LocalDateTime start = a.getStartDateTime();
			return (start.isAfter(this.startDateTime) && start.isBefore(this.endDateTime));
		} else
			return false;
	}

	/**
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean overlaps(LocalDateTime start, LocalDateTime end) {
		if(start != null && end != null) {
			if(start.isAfter(startDateTime) && start.isBefore(endDateTime))
				return true;
			if(end.isAfter(startDateTime) && end.isBefore(endDateTime))
				return true;
			if(start.isBefore(startDateTime) && end.isAfter(endDateTime))
				return true;
		}
		return false;
	}
}//class Appointment