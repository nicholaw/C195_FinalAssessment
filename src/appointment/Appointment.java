package appointment;

import javafx.beans.property.*;

import java.time.*;
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
	private ZonedDateTime 	startDateTime;
	private ZonedDateTime	endDateTime;
	private StringProperty	date;
	private StringProperty	start;
	private StringProperty	end;
	private LongProperty	customerId;
	private Contact			contact;
	private Location		location;
	private StringProperty locationProperty;

	/**
	 * Constructs this appointment from two provided LocalDateTimes representing the start and end of this appointment.
	 * @param appointmentId    -the unique id of this appointment
	 * @param title    -the title of this appointment
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
		this.customerId 	= 	new SimpleLongProperty(this, "customerId", customerId);
		this.contact 		= 	contact;
		this.location		=	location;
		this.locationProperty = new SimpleStringProperty(this, "location", location.toString());
		date = new SimpleStringProperty(this, "date", "");
		start = new SimpleStringProperty(this, "start", "");
		end = new SimpleStringProperty(this, "end", "");
		setStartDateTime(startDateTime);
		setEndDateTime(endDateTime);
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
	 * Returns the local end date and time of this appointment.
	 * @return	-this appointment's end date and time
	 */
    public LocalDateTime getLocalEndDateTime() {
        return LocalDateTime.ofInstant(endDateTime.toInstant(), ZoneId.systemDefault());
    }//getLocalEndDateTime

	/**
	 * Returns the UTC end date and time of this appointment.
	 * @return	-this appointment's end date and time in UTC
	 */
	public ZonedDateTime getUTCEndDateTime() {
    	return endDateTime;
	}//getUTCEndDateTime

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
    public LocalDateTime getLocalStartDateTime() {
        return LocalDateTime.ofInstant(startDateTime.toInstant(), ZoneId.systemDefault());
    }//getLocalStartDateTime

	/**
	 * Returns the UTC start date and time of this appointment.
	 * @return	-this appointment's start date and time in UTC
	 */
	public ZonedDateTime getUTCStartDateTime() {
		return startDateTime;
	}//getUTCStartDateTime

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
	 * Sets the starting date and time of this appointment to the given LocalDateTime converted to UTC.
	 * @param start -the starting date and time to set
	 */
	public void setStartDateTime(LocalDateTime start) {
		startDateTime = ZonedDateTime.ofInstant(start.atZone(ZoneId.systemDefault()).toInstant(), AppointmentConstants.ZONE_UTC);
		setStartProperty(start.toLocalTime());
		setDateProperty(start.toLocalDate());
	}//setStartDateTime

	/**
	 * Sets the value of the StringProperty which displays the local start time of this appointment in a TableView
	 */
	private void setStartProperty(LocalTime time) {
		start.setValue(time.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
	}//setStartProperty

	/**
	 * Sets the value of the StringProperty which displays the local date of this appointment in a TableView
	 */
	private void setDateProperty(LocalDate date) {
		this.date.setValue(date.format(DateTimeFormatter.ofPattern(AppointmentConstants.DATE_FORMAT)));
	}//setDateProperty

	/**
	 * Sets the ending date and time of this appointment to the given LocalDateTime converted to UTC.
	 * @param end -the end date and time to set
	 */
	public void setEndDateTime(LocalDateTime end) {
		endDateTime = ZonedDateTime.ofInstant(end.atZone(ZoneId.systemDefault()).toInstant(), AppointmentConstants.ZONE_UTC);
		setEndProperty(end.toLocalTime());
	}//setEndDateTime

	/**
	 * Sets the value of the StringProperty which displays the local end time of this appointment in a TableView
	 */
	private void setEndProperty(LocalTime time) {
		end.setValue(time.format(DateTimeFormatter.ofPattern(AppointmentConstants.TIME_FORMAT)));
	}//setEndProperty

	/**
	 * Sets the location of this appointment to the given Location.
	 * @param l -the location to be set
	 */
	public void setLocation(Location l) {
    	this.location = l;
    	this.locationProperty.setValue(l.toString());
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
	 * false if the times do not overlap and true otherwise.
	 * @param startDateTime -the start time to be checked against
	 * @param endDateTime	-the end time to be checked against
	 * @return	-whether this appointment overlaps the given start and end times
	 */
	public boolean overlaps(ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
		//check arguments are not null
		if(startDateTime == null || endDateTime == null)
			return false;

		//check arguments are in UTC and convert to UTC if necessary
		ZonedDateTime convertedStartDateTime;
		ZonedDateTime convertedEndDateTime;
		if(startDateTime.getZone().equals(AppointmentConstants.ZONE_UTC))
			convertedStartDateTime = startDateTime;
		else
			convertedStartDateTime = ZonedDateTime.ofInstant(startDateTime.toInstant(), AppointmentConstants.ZONE_UTC);
		if(endDateTime.getZone().equals(AppointmentConstants.ZONE_UTC))
			convertedEndDateTime = endDateTime;
		else
			convertedEndDateTime = ZonedDateTime.ofInstant(endDateTime.toInstant(), AppointmentConstants.ZONE_UTC);

		//check for overlap
		if(convertedStartDateTime.isAfter(this.endDateTime) && convertedEndDateTime.isAfter(this.endDateTime)) {
			return false;
		} else if(convertedStartDateTime.isBefore(this.startDateTime) && convertedEndDateTime.isBefore(this.startDateTime)) {
			return false;
		} else
			return true;
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