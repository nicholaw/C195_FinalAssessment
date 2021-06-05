package customer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer
{
	private IntegerProperty customerId;
    private StringProperty name;
	private StringProperty phone;
	private StringProperty address;
	private StringProperty city;
	private StringProperty postCode;
    private Country country;
    private Division division;
	private IntegerProperty appointments;

    public Customer(int id, String name, String phone, String address,
            String city, String postCode, Country country, Division div) {
        this.customerId 	=	new SimpleIntegerProperty(this, id);
		this.name 			= 	new SimpleStringProperty(this, name);
		this.phone 			= 	new SimpleStringProperty(this, phone);
		this.address		= 	new SimpleStringProperty(this, address);
		this.city 			= 	new SimpleStringProperty(this, city);
		this.postCode 		= 	new SimpleStringProperty(this, postCode);
		this.country		=	country;
		this.division		=	division;
		this.appointments	=	new SimpleIntegerProperty(this, 0);
    }//constructor
	
	public Customer(String name, String phone, String address,
            String city, String postCode, Country country, Division div) {
		this.name 			= 	new SimpleStringProperty(this, name);
		this.phone 			= 	new SimpleStringProperty(this, phone);
		this.address		= 	new SimpleStringProperty(this, address);
		this.city 			= 	new SimpleStringProperty(this, city);
		this.postCode 		= 	new SimpleStringProperty(this, postCode);
		this.country		=	country;
		this.division		=	division;
		this.appointments	=	new SimpleIntegerProperty(this, 0);
    }//constructor

    public Customer(int id, String name, int country, String phoneNum)
    {
        this.customerId 	=	new SimpleIntegerProperty(this, id);
		this.name 			= 	new SimpleStringProperty(this, name);
		this.phone 			= 	new SimpleStringProperty(this, phone);
        this.country		=	country;
		this.appointments	=	new SimpleIntegerProperty(this, 0);
    }//constructor

	public StringProperty addressProperty() {
		return address;
	}
	
	public StringProperty cityProperty() {
		return city;
	}
	
	public IntegerProperty customerIdProperty() {
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
		return appointments.intValue();
	}
	
	public String getAddress()  {
		return address.get();
	}
	
	public String getCity() {
		return city.get();
	}
	
	public int getCountryId() {
		return country.getCountryId();
	}
	
	public int getCustomerId() {
		return customerId.intValue();
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
		appointments = new SimpleIntegerProperty(this, i);
	}

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(!(obj instanceof Customer))
            return false;
        if(this.id.get() == ((Customer)obj).getId())
            return true;
        return false;
    }
}//Customer