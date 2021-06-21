package customer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.Country;
import utils.Division;

public class Customer
{
	private LongProperty customerId;
    private StringProperty name;
	private StringProperty phone;
	private StringProperty address;
	private StringProperty city;
	private StringProperty postCode;
    private Country country;
    private Division division;
	private IntegerProperty appointments;

	public Customer(long id, String name, String phone, String address,
            String city, String postCode, Country country, int divId) {
    	this.customerId		=	new SimpleLongProperty(this, "customerId", id);
		this.name 			= 	new SimpleStringProperty(this, name);
		this.name 			= 	new SimpleStringProperty(this, "name", name);
		this.phone 			= 	new SimpleStringProperty(this, "phone", phone);
		this.address		= 	new SimpleStringProperty(this, "address", address);
		this.city 			= 	new SimpleStringProperty(this, "city", city);
		this.postCode 		= 	new SimpleStringProperty(this, "postCode", postCode);
		this.country		=	country;
		this.division		=	country.getDivision(divId);
		this.appointments	=	new SimpleIntegerProperty(this, "appointments", 0);
    }//constructor

	public StringProperty addressProperty() {
		return address;
	}
	
	public StringProperty cityProperty() {
		return city;
	}
	
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
	
	public int getAppointments() {
		return appointments.get();
	}
	
	public String getAddress()  {
		return address.get();
	}
	
	public String getCity() {
		return city.get();
	}
	
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
	
	public void setAppointments(int i) {
		appointments = new SimpleIntegerProperty(this, "appointments", i);
	}
	
	public void setAddress(String str) {
		address = new SimpleStringProperty(this, "address", str);
	}
	
	public void setCity(String str) {
		city = new SimpleStringProperty(this, "city", str);
	}
	
	public void setCountry(Country c) {
		country = c;
	}
	
	public void setDivision(Division d) {
		division = d;
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