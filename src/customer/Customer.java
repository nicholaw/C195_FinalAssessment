package customer;

import appointment.Appointment;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Country;
import utils.Division;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a customer in the application.
 */
public class Customer {
	private LongProperty customerId;
    private StringProperty name;
	private StringProperty phone;
	private StringProperty address;
	private StringProperty postCode;
	private StringProperty countryName;
	private StringProperty divisionName;
	private IntegerProperty scheduledAppointments;
	private ObservableList<Appointment> appointments;
    private Country country;
    private Division division;

	/**
	 * Constructs a Customer with the number of appointments the customer has scheduled, but does not actually
	 * populate the list of appointments for this customer.
	 * @param id		-this customer's id
	 * @param name		-this customer's name
	 * @param phone		-this customer's phone number
	 * @param address	-this customer's address
	 * @param postCode	-this customer's postal code
	 * @param country	-the customer's country
	 * @param div		-the region within the country this customer lives
	 * @param numAppointments	-the number of appointments this customer has scheduled
	 */
	public Customer(long id, String name, String phone, String address, String postCode, Country country, Division div, int numAppointments) {
    	this.customerId		=	new SimpleLongProperty(this, "customerId", id);
		this.name 			= 	new SimpleStringProperty(this, name);
		this.name 			= 	new SimpleStringProperty(this, "name", name);
		this.phone 			= 	new SimpleStringProperty(this, "phone", phone);
		this.address		= 	new SimpleStringProperty(this, "address", address);
		this.postCode 		= 	new SimpleStringProperty(this, "postCode", postCode);
		this.country		=	country;
		this.division		=	div;
		try {
			countryName = new SimpleStringProperty(this, "country", country.getCountryName());
			divisionName = new SimpleStringProperty(this, "division", division.getDivisionName());
		} catch(NullPointerException e) {
			countryName = new SimpleStringProperty(this, "country", "NULL");
			divisionName = new SimpleStringProperty(this, "division", "NULL");
			e.printStackTrace();
		}
		appointments = null;
		scheduledAppointments = new SimpleIntegerProperty(this, "appointments", numAppointments);
    }//constructor

	/**
	 * Constructs a Customer and populates the list of appointments this customer has scheduled.
	 * @param id		-this customer's id
	 * @param name		-this customer's name
	 * @param phone		-this customer's phone number
	 * @param address	-this customer's address
	 * @param postCode	-this customer's postal code
	 * @param country	-the customer's country
	 * @param div		-the region within the country this customer lives
	 * @param appointments	-collection of appointments this customer has scheduled
	 */
	public Customer(long id, String name, String phone, String address, String postCode, Country country,
					Division div, Collection<Appointment> appointments) {
		this.customerId		=	new SimpleLongProperty(this, "customerId", id);
		this.name 			= 	new SimpleStringProperty(this, name);
		this.name 			= 	new SimpleStringProperty(this, "name", name);
		this.phone 			= 	new SimpleStringProperty(this, "phone", phone);
		this.address		= 	new SimpleStringProperty(this, "address", address);
		this.postCode 		= 	new SimpleStringProperty(this, "postCode", postCode);
		this.country		=	country;
		this.division		=	div;
		try {
			countryName = new SimpleStringProperty(this, "country", country.getCountryName());
			divisionName = new SimpleStringProperty(this, "division", division.getDivisionName());
		} catch(NullPointerException e) {
			countryName = new SimpleStringProperty(this, "country", "NULL");
			divisionName = new SimpleStringProperty(this, "division", "NULL");
			e.printStackTrace();
		}
		if(appointments == null) {
			this.appointments = null;
			scheduledAppointments = new SimpleIntegerProperty(this, "appointments", 0);
		}
		else {
			this.appointments = FXCollections.observableArrayList(appointments);
			scheduledAppointments = new SimpleIntegerProperty(this, "appointments", appointments.size());
		}
	}//constructor

	/**
	 * Set the list of appointments this customer has scheduled and updates the IntegerProperty for
	 * displaying the information.
	 * @param coll -the collection of appointments to set
	 */
	public void setAppointments(Collection<appointment.Appointment> coll) {
		if(coll == null)
			appointments = null;
		else {
			appointments = FXCollections.observableArrayList(coll);
			scheduledAppointments.setValue(appointments.size());
		}
	}

	/**
	 * Adds an appointment to the list of appointments this customer has scheduled. Returns true
	 * if the appointment was successfully added and false otherwise.
	 * @param a -the appointment to add
	 * @return	-whether the appointment was added successfully
	 */
	public boolean addAppointment(appointment.Appointment a) {
		if(appointments.contains(a))
			return false;
		else {
			appointments.add(a);
			scheduledAppointments.setValue(appointments.size());
			return true;
		}
	}

	/**
	 * Returns the StringProperty for displaying this customer's address in a TableView.
	 * @return -StringProperty of this customer's address
	 */
	public StringProperty addressProperty() {
		return address;
	}

	/**
	 * Returns the StringProperty for displaying this customer's id in a TableView.
	 * @return -StringProperty of this customer's id
	 */
	public LongProperty customerIdProperty() {
		return customerId;
	}

	/**
	 * Returns the StringProperty for displaying this customer's name in a TableView.
	 * @return -StringProperty of this customer's name
	 */
	public StringProperty nameProperty() {
		return name;
	}

	/**
	 * Returns the StringProperty for displaying this customer's phone number in a TableView.
	 * @return -StringProperty of this customer's phone number
	 */
	public StringProperty phoneProperty() {
		return phone;
	}

	/**
	 * Returns the StringProperty for displaying this customer's postal code in a TableView.
	 * @return -StringProperty of this customer's postal code
	 */
	public StringProperty postCodeProperty() {
		return  postCode;
	}

	/**
	 * Returns the StringProperty for displaying this customer's country in a TableView.
	 * @return -StringProperty of this customer's country
	 */
	public StringProperty countryProperty() {
		return countryName;
	}

	/**
	 * Returns the StringProperty for displaying this customer's first-level-division in a TableView.
	 * @return -StringProperty of this customer's first-level-division
	 */
	public StringProperty divisionProperty() {
		return divisionName;
	}

	/**
	 * Returns the IntegerProperty for displaying the number of appointment this customer has
	 * scheduled in a tableview.
	 * @return -IntegerProperty of the number of appointments scheduled by this customer
	 */
	public IntegerProperty appointmentsProperty() {
		return scheduledAppointments;
	}

	/**
	 * Returns an ObservableList of the appointments this customer has scheduled.
	 * @return	-the list of appointments
	 */
	public ObservableList<Appointment> getAppointments() {
		return appointments;
	}

	/**
	 * Returns an ObservableList of the appointments this customer has scheduled for the given month of
	 * the given year.
	 * @param month -the month to match
	 * @param year -the year to match
	 * @return -the appointments during the given month and year
	 */
	public ObservableList<Appointment> getAppointmentsByMonth(Month month, int year) {
		var list = new ArrayList<Appointment>();
		for(Appointment a : appointments) {
			if(a.getLocalStartDateTime().getMonth().equals(month) && a.getLocalStartDateTime().getYear() == year)
				list.add(a);
		}
		return FXCollections.observableArrayList(list);
	}//getAppointmentsByMonth

	/**
	 * Returns an ObservableList of appointments this customer has scheduled within the provided range of
	 * dates (inclusive).
	 * @param rangeBegin -the first date of the range
	 * @param rangeEnd -the last date of the range
	 * @return -list of appointments which fall within the range
	 */
	public ObservableList<Appointment> getAppointmentsByRange(LocalDate rangeBegin, LocalDate rangeEnd) {
		var list = new ArrayList<Appointment>();
		if(rangeBegin == null || rangeEnd == null)
			return FXCollections.observableArrayList(list);
		if(rangeBegin.isAfter(rangeEnd))
			return FXCollections.observableArrayList(list);
		for(Appointment a : appointments) {
			if(a.getLocalStartDateTime().toLocalDate().isAfter(rangeBegin) &&
					a.getLocalStartDateTime().toLocalDate().isBefore(rangeEnd))
				list.add(a);
			else if(a.getLocalStartDateTime().toLocalDate().isEqual(rangeBegin) ||
					a.getLocalStartDateTime().toLocalDate().isEqual(rangeEnd))
				list.add(a);
		}
		return FXCollections.observableArrayList(list);
	}//getAppointmentsByRange

	/**
	 * Returns this customer's address.
	 * @return	-this customer' address
	 */
	public String getAddress()  {
		return address.get();
	}

	/**
	 * Returns this customer's country.
	 * @return	-this customer's country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Returns this customer's id.
	 * @return	-this customer's id
	 */
	public long getCustomerId() {
		return customerId.get();
	}

	/**
	 * Returns this customer's first-level-division.
	 * @return	-this customer's first-level-division
	 */
	public Division getDivision() {
    	return division;
	}

	/**
	 * Return this customer's name.
	 * @return	-this customer's name
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * Returns this customer's phone number.
	 * @return	-this customer's phone number
	 */
	public String getPhone() {
		return phone.get();
	}

	/**
	 * Returns this customer's postal code.
	 * @return	-this customer's postal code
	 */
	public String getPostCode() {
		return postCode.get();
	}

	/**
	 * Removes the given appointment from this customer's list of scheduled appointments.
	 * @param a	-the appointment to remove
	 */
	public void removeAppointment(appointment.Appointment a) {
		appointments.remove(a);
		scheduledAppointments.setValue(appointments.size());
	}

	/**
	 * Sets this customer's address to the given String.
	 * @param str	-the address to set
	 */
	public void setAddress(String str) {
		address = new SimpleStringProperty(this, "address", str);
	}

	/**
	 * Sets this customer's country to the given Country.
	 * @param c	-the country to set
	 */
	private void setCountry(Country c) {
		country = c;
		countryName = new SimpleStringProperty(this, "country", c.getCountryName());
	}

	/**
	 * Sets this customer's first-level-division to the given division.
	 * @param d	-the division to set
	 */
	public void setDivision(Division d) {
		division = d;
		divisionName = new SimpleStringProperty(this, "division", d.getDivisionName());
		this.setCountry(d.getParentCountry());
	}

	/**
	 * Sets this customer's name to the given String.
	 * @param str	-the name to set
	 */
	public void setName(String str) {
		name = new SimpleStringProperty(this, "name", str);
	}

	/**
	 * Sets this customer's phone number to the given String.
	 * @param str	-the phone number to set
	 */
	public void setPhone(String str) {
		phone = new SimpleStringProperty(this, "phone", str);
	}

	/**
	 * Sets this customer's postal code to the given String.
	 * @param str	-the postal code to set
	 */
	public void setPostCode(String str) {
		postCode = new SimpleStringProperty(this, "postCode", str);
	}

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(!(obj instanceof Customer))
            return false;
        if(this.customerId.get() == ((Customer)obj).getCustomerId())
            return true;
        return false;
    }
	
	@Override
	public String toString() {
		return this.getName();
	}
}//Customer