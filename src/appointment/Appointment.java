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
	private LocalDateTime	endDateTime;
	private StringProperty	date;
	private StringProperty	start;
	private StringProperty	end;
	private LongProperty	customerId;
	private Contact			contact;
	private Location		location;

    public Appointment(String title, String description, String type, LocalDateTime startDateTime, 
						LocalDateTime endDateTime, int customerId, Contact contact, Location location) {
        this.title 			= 	new SimpleStringProperty(this, "title", title);
        this.description 	= 	new SimpleStringProperty(this, "description", description);
        this.type 			= 	new SimpleStringProperty(this, "type", type);
        this.startDateTime	= 	startDateTime;
		this.date 			= 	new SimpleStringProperty(this, "date", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.start	 		= 	new SimpleStringProperty(this, "start", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
        this.endDateTime 	= 	endDateTime;
		this.end	 		= 	new SimpleStringProperty(this, "end", endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
        this.customerId 	= 	new SimpleLongProperty(this, "customerId", customerId);
		this.contact 		= 	contact;
		this.location		=	location;
    }//constructor

    public Appointment(int appointmentId, String title, String description, String type, 
						LocalDateTime startDateTime, LocalDateTime endDateTime,
						long customerId, Contact contact, Location location) {
        this.appointmentId	=	new SimpleIntegerProperty(this, "appointmentId", appointmentId);
		this.title 			= 	new SimpleStringProperty(this, "title", title);
		this.description 	= 	new SimpleStringProperty(this, "description", description);
		this.type 			= 	new SimpleStringProperty(this, "type", type);
		this.startDateTime	= 	startDateTime;
		this.date 			= 	new SimpleStringProperty(this, "date", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.start	 		= 	new SimpleStringProperty(this, "start", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
		this.endDateTime 	= 	endDateTime;
		this.end	 		= 	new SimpleStringProperty(this, "end", endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
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
	
	public StringProperty dateProperty() {
		return date;
	}
	
	public StringProperty startProperty() {
		return start;
	}
	
	public StringProperty endProperty() {
		return end;
	}
	
	public LongProperty customerIdProperty() {
		return customerId;
	}
	
	public LongProperty contactIdProperty() {
		return new SimpleLongProperty(this, "contactId", contact.getId());
	}
	
	public StringProperty contactNameProperty() {
		return new SimpleStringProperty(this, "contactName", contact.getName());
	}
	
	public StringProperty contactEmailProperty() {
		return new SimpleStringProperty(this, "contactEmail", contact.getEmail());
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

	public void setTitle(String title) {
    	this.title.setValue(title);
	}

	public void setDescription(String description) {
    	this.description.setValue(description);
	}

	public void setStartDateTime(LocalDateTime start) {
    	this.startDateTime = start;
    	date.setValue(start.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.start.setValue(start.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
	}

	public void setEndDateTime(LocalDateTime end) {
		this.endDateTime = end;
		this.end.setValue(end.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
	}

	public void setLocation(Location l) {
    	this.location = l;
	}

	public void setContact(Contact c) {
    	this.contact = c;
	}

	public void setType(AppointmentType t) {
    	this.type.setValue(t.getType());
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

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Appointment))
			return false;
		return (this.getAppointmentId() == ((Appointment) obj).getAppointmentId());
	}
}//class Appointment