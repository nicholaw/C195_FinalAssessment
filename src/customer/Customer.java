package customer;

import utils.Country;
import utils.Division;

public class Customer
{
    private int customerId;
    private String name;
    private String phoneNum;
    private String address;
    private String city;
    private Country country;
    private Division division;
    private String postCode;
    private int scheduledAppointments = 0;

    public Customer(
            int id,
            String name,
            String phoneNum,
            String address,
            String city,
            Country country,
            Division div,
            String postCode)
    {
        customerId = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.city = city;
        this.country = country;
        this.division = div;
        this.postCode = postCode;
    }//Constructor: id, name, phoneNum, address, city, country, div, postCode

    public Customer(
            String name,
            String phoneNum,
            String address,
            String city,
            Country country,
            Division div,
            String postCode)
    {
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.city = city;
        this.country = country;
        this.division = div;
        this.postCode = postCode;
        customerId = generateId();
    }//Constructor: name, phoneNum, address, city, country, div, postCode

    public Customer(int id, String name, Country country, Division div)
    {
        this.customerId = id;
        this.name = name;
        this.country = country;
        this.division = div;
    }//Constructor: id, name, country, div

    private static int generateId()
    {
        return 0;
    }

    public String getAddress()
    {
        return address;
    }

    public Country getCountry()
    {
        return country;
    }

    public int getCountryId()
    {
        return country.getCountryId();
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
        return name;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public String getPostCode()
    {
        return postCode;
    }

    public Division getFirstLevelDivision()
    {
        return division;
    }

    public int getDivisionId()
    {
        return division.getDivisionId();
    }

    public int getScheduledAppointments()
    {
        return scheduledAppointments;
    }
}//Customer