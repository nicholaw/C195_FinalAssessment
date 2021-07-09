package customer;

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
import java.util.Collection;

public class Customer
{
	private LongProperty customerId;
    private StringProperty name;
	private StringProperty phone;
	private StringProperty address;
	//private StringProperty city;
	private StringProperty postCode;
	private StringProperty countryName;
	private StringProperty divisionName;
	private IntegerProperty scheduledAppointments;
	private ObservableList<appointment.Appointment> appointments;
    private Country country;
    private Division division;

	public Customer(long id, String name, String phone, String address, String postCode, Country country, Division div, int numAppointments) {
    	this.customerId		=	new SimpleLongProperty(this, "customerId", id);
		this.name 			= 	new SimpleStringProperty(this, name);
		this.name 			= 	new SimpleStringProperty(this, "name", name);
		this.phone 			= 	new SimpleStringProperty(this, "phone", phone);
		this.address		= 	new SimpleStringProperty(this, "address", address);
		//this.city 			= 	new SimpleStringProperty(this, "city", city);
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

	public Customer(long id, String name, String phone, String address, String postCode, Country country,
					Division div, Collection<appointment.Appointment> appointments) {
		this.customerId		=	new SimpleLongProperty(this, "customerId", id);
		this.name 			= 	new SimpleStringProperty(this, name);
		this.name 			= 	new SimpleStringProperty(this, "name", name);
		this.phone 			= 	new SimpleStringProperty(this, "phone", phone);
		this.address		= 	new SimpleStringProperty(this, "address", address);
		//this.city 			= 	new SimpleStringProperty(this, "city", city);
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

	public void setAppointments(Collection<appointment.Appointment> coll) {
		if(coll == null)
			appointments = null;
		else {
			appointments = FXCollections.observableArrayList(coll);
			scheduledAppointments.setValue(appointments.size());
		}
	}

	public boolean addAppointment(appointment.Appointment a) {
		if(appointments.contains(a))
			return false;
		else {
			appointments.add(a);
			scheduledAppointments.setValue(appointments.size());
			return true;
		}
	}

	public StringProperty addressProperty() {
		return address;
	}
	
	/*public StringProperty cityProperty() {
		return city;
	}*/
	
	public LongProperty customerIdProperty() {
		return customerId;
	}

	public StringProperty nameProperty() {
		return name;
	}
	
	public StringProperty phoneProperty() {
		return phone;
	}
	
	public StringProperty postCodeProperty() {
		return  postCode;
	}

	public StringProperty countryProperty() {
		return countryName;
	}

	public StringProperty divisionProperty() {
		return divisionName;
	}

	public IntegerProperty appointmentsProperty() {
		return scheduledAppointments;
	}

	public ObservableList<appointment.Appointment> getAppointments() {
		return appointments;
	}

	public int getNumAppointments() {
		return appointments.size();
	}
	
	public String getAddress()  {
		return address.get();
	}
	
	/*public String getCity() {
		return city.get();
	}*/
	
	public Country getCountry() {
		return country;
	}
	
	public long getCustomerId() {
		return customerId.get();
	}

	public Division getDivision() {
    	return division;
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getPhone() {
		return phone.get();
	}
	
	public String getPostCode() {
		return postCode.get();
	}

	public void removeAppointment(appointment.Appointment a) {
		appointments.remove(a);
		scheduledAppointments.setValue(appointments.size());
	}
	
	public void setAddress(String str) {
		address = new SimpleStringProperty(this, "address", str);
	}
	
	/*public void setCity(String str) {
		city = new SimpleStringProperty(this, "city", str);
	}*/
	
	private void setCountry(Country c) {
		country = c;
		countryName = new SimpleStringProperty(this, "country", c.getCountryName());
	}
	
	public void setDivision(Division d) {
		division = d;
		divisionName = new SimpleStringProperty(this, "division", d.getDivisionName());
		this.setCountry(d.getParentCountry());
	}
	
	public void setName(String str) {
		name = new SimpleStringProperty(this, "name", str);
	}
	
	public void setPhone(String str) {
		phone = new SimpleStringProperty(this, "phone", str);
	}
	
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