package appointment;

import javafx.beans.property.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import utils.Contact;
import utils.Location;
import utils.Type;

/**
 * Represents a customer appointment which can be scheduled and updated
 * by the application.
 */
public class Appointment {
	private LongProperty	appointmentId;
	private StringProperty	title;
	private StringProperty	description;
	private ObjectProperty<Type> type;
	private LocalDateTime	startDateTime;
	private LocalDateTime	endDateTime;
	private StringProperty	date;
	private StringProperty	start;
	private StringProperty	end;
	private LongProperty	customerId;
	private Contact			contact;
	private Location		location;

	/**
	 * Constructor for an appointment.
	 * @param appointmentId	-the unique id of this appointment
	 * @param title	-the title of this appointment
	 * @param description -a brief description of this appointment
	 * @param type -the category of this appointment
	 * @param startDateTime -the starting date and time of this appointment
	 * @param endDateTime -the ending date and time of this appointment
	 * @param customerId -the id of the customer to whom this appointment belongs
	 * @param contact -the employee contact assigned to this appointment
	 * @param location -the location of this appointment
	 */
    public Appointment(long appointmentId, String title, String description, Type type,
						LocalDateTime startDateTime, LocalDateTime endDateTime,
						long customerId, Contact contact, Location location) {
        this.appointmentId	=	new SimpleLongProperty(this, "appointmentId", appointmentId);
		this.title 			= 	new SimpleStringProperty(this, "title", title);
		this.description 	= 	new SimpleStringProperty(this, "description", description);
		this.type 			= 	new SimpleObjectProperty<>(this, "type", type);
		this.startDateTime	= 	startDateTime;
		this.date 			= 	new SimpleStringProperty(this, "date", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.start	 		= 	new SimpleStringProperty(this, "start", startDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
		this.endDateTime 	= 	endDateTime;
		this.end	 		= 	new SimpleStringProperty(this, "end", endDateTime.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
		this.customerId 	= 	new SimpleLongProperty(this, "customerId", customerId);
		this.contact 		= 	contact;
		this.location		=	location;
    }//constructor

	/**
	 * Returns a LongProperty for displaying this appointment's id in a TableView.
	 * @return -LongProperty of this appointment's id
	 */
	public LongProperty appointmentIdProperty() {
		return appointmentId;
	}

	/**
	 * Returns a StringProperty for displaying the title of this appointment in a TableView.
	 * @return -StringProperty of this appointment's title
	 */
	public StringProperty titleProperty() {
		return title;
	}

	/**
	 * Returns a StringProperty for displaying the description of this appointment in a TableView.
	 * @return -StringProperty of this appointment's description
	 */
	public StringProperty descriptionProperty() {
		return description;
	}

	/**
	 * Returns an ObjectProperty for displaying the type of appointment in a TableView.
	 * @return -ObjectProperty of this appointment's type
	 */
	public ObjectProperty typeProperty() {
		return type;
	}

	/**
	 * Returns a StringProperty for displaying the date of this appointment in a TableView.
	 * @return -StringProperty of the date of this appointment
	 */
	public StringProperty dateProperty() {
		return date;
	}

	/**
	 * Returns a StringProperty for displaying the starting time of this appointment in a TableView.
	 * @return -StringProperty of this appointment's starting time
	 */
	public StringProperty startProperty() {
		return start;
	}

	/**
	 * Returns a StringProperty for displaying the ending time of this appointment in a TableView.
	 * @return -StringProperty of this appointment's end time
	 */
	public StringProperty endProperty() {
		return end;
	}

	/**
	 * Returns a LongProperty for displaying this appointment's customer id in a TableView.
	 * @return -LongProperty of this appointment's customer id
	 */
	public LongProperty customerIdProperty() {
		return customerId;
	}

	/**
	 * Returns a LongProperty for displaying this appointment's contact id in a TableView.
	 * @return -LongProperty of this appointment's contact id
	 */
	public LongProperty contactIdProperty() {
		return new SimpleLongProperty(this, "contactId", contact.getId());
	}

	/**
	 * Returns a StringProperty for displaying this appointment's contact name in a TableView.
	 * @return -StringProperty of this appointment's contact name
	 */
	public StringProperty contactNameProperty() {
		return new SimpleStringProperty(this, "contactName", contact.getName());
	}

	/**
	 * Returns a StringProperty for displaying the contact email address for this appointment in a TableView.
	 * @return -StringProperty of this appointment's contact email address
	 */
	public StringProperty contactEmailProperty() {
		return new SimpleStringProperty(this, "contactEmail", contact.getEmail());
	}

	/**
	 * Returns the id of this appointment.
	 * @return	-this appointment's id
	 */
    public long getAppointmentId() {
        return appointmentId.get();
    }//getAppointmentId

	/**
	 * Returns the contact id of this appointment.
	 * @return	-this appointment's contact id
	 */
    public long getContactId() {
        return contact.getId();
    }//getContactId

	/**
	 * Returns the customer id of this appointment.
	 * @return	-this appointment's customer id
	 */
    public long getCustomerId() {
        return customerId.get();
    }//getCustomerId

	/**
	 * Returns the description of this appointment.
	 * @return	-this appointment's description
	 */
    public String getDescription() {
        return description.get();
    }//getDescription

	/**
	 * Returns the end date and time of this appointment.
	 * @return	-this appointment's end date and time
	 */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }//getEndDateTime

	/**
	 * Returns the location of this appointment.
	 * @return	-this appointment's location
	 */
	public Location getLocation() {
    	return location;
	}

	/**
	 * Returns the starting date and time of this appointment.
	 * @return	-this appointment's starting date and time
	 */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }//getStartDateTime

	/**
	 * Returns the title of this appointment.
	 * @return	-this appointment's title
	 */
    public String getTitle() {
        return title.get();
    }//getTitle

	/**
	 * Returns the type of this appointment (e.g. ORIENTATION).
	 * @return	-this appointment's type
	 */
    public Type getType() {
        return type.get();
    }//getType

	/**
	 * Returns the contact for this appointment.
	 * @return	-this appointment's contact
	 */
	public Contact getContact() {
		return contact;
	}//getContact

	/**
	 * Checks if this appointment's start and end times overlap the times of the given appointment. Returns
	 * ture if the times overlap and false otherwise.
	 * @param a -the appointment to be checked against
	 * @return	-whether this and the given appointment overlap
	 */
	public boolean overlaps(Appointment a) {
		if(a != null) {
			return this.overlaps(a.getStartDateTime(), a.getEndDateTime());
		}
		return false;
	}//overlaps

	/**
	 * Sets the title of this appointment to the given String.
	 * @param title -the title to be set
	 */
	public void setTitle(String title) {
    	this.title.setValue(title);
	}//setTitle

	/**
	 * Set the description of this appointment to the given String.
	 * @param description -the description to be set
	 */
	public void setDescription(String description) {
    	this.description.setValue(description);
	}//setDescription

	/**
	 * Sets the starting date and time of this appointment to the given LocalDateTime.
	 * @param start -the starting date and time to set
	 */
	public void setStartDateTime(LocalDateTime start) {
    	this.startDateTime = start;
    	date.setValue(start.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
		this.start.setValue(start.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
	}//setStartDateTime

	/**
	 * Sets the ending date and time of this appointment to the given LocalDateTime.
	 * @param end -the end date and time to set
	 */
	public void setEndDateTime(LocalDateTime end) {
		this.endDateTime = end;
		this.end.setValue(end.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
	}//setEndDateTime

	/**
	 * Sets the location of this appointment to the given Location.
	 * @param l -the location to be set
	 */
	public void setLocation(Location l) {
    	this.location = l;
	}//setLocation

	/**
	 * Set the contact for this appointment to the given Contact.
	 * @param c -the contact to be set
	 */
	public void setContact(Contact c) {
    	this.contact = c;
	}//setContact

	/**
	 * Sets the type of this appointment to the given Type.
	 * @param t	-the type to be set
	 */
	public void setType(Type t) {
    	this.type.setValue(t);
	}//setType

	/**
	 * Checks if this appointment's start and end times overlap the given start and end times. Returns
	 * ture if the times overlap and false otherwise.
	 * @param start -the start time to be checked against
	 * @param end	-the end time to be checked against
	 * @return	-whether this appointment overlaps the given start and end times
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
	}//overlaps

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Appointment))
			return false;
		return (this.getAppointmentId() == ((Appointment) obj).getAppointmentId());
	}//equals
}//class Appointment