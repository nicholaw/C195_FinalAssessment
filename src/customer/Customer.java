package customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer
{
    private int customerId;
    private StringProperty idProperty;
    private String name;
    private StringProperty nameProperty;
    private String phoneNum;
    private StringProperty phoneProperty;
    private String address;
    private String city;
    private int countryId;
    private int divisionId;
    private String postCode;
    private int scheduledAppointments = 0;

    public Customer(
            int id,
            String name,
            String phoneNum,
            String address,
            String city,
            int country,
            int div,
            String postCode)
    {
        customerId = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.city = city;
        this.countryId = country;
        this.divisionId = div;
        this.postCode = postCode;
    }//Constructor: id, name, phoneNum, address, city, country, div, postCode

    public Customer(
            String name,
            String phoneNum,
            String address,
            String city,
            int country,
            int div,
            String postCode)
    {
        this.name = name;
        this.nameProperty.setValue(name);
        this.phoneNum = phoneNum;
        this.address = address;
        this.city = city;
        this.countryId = country;
        this.divisionId = div;
        this.postCode = postCode;
        customerId = generateId();
    }//Constructor: name, phoneNum, address, city, country, div, postCode

    public Customer(int id, String name, int country, String phoneNum)
    {
        this.customerId = id;
        this.name = name;
        this.countryId = country;
        this.phoneNum = phoneNum;
    }//Constructor: id, name, country, phone

    private static int generateId()
    {
        return 0;
    }

    public String getAddress()
    {
        return address;
    }


    public int getCountryId()
    {
        return countryId;
    }

    public String getCity()
    {
        return city;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public String getName()
    {
        //return name;
        return nameProperty.get();
    }

    public StringProperty getNameProperty()
    {
        var nameProperty = new SimpleStringProperty(this, "name");
        nameProperty.setValue(this.name);
        return nameProperty;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public String getPostCode()
    {
        return postCode;
    }

    public int getDivisionId()
    {
        return divisionId;
    }

    public int getScheduledAppointments()
    {
        return scheduledAppointments;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(!(obj instanceof Customer))
            return false;
        if(this.customerId == ((Customer)obj).getCustomerId())
            return true;
        return false;
    }
}//Customer