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
    private Division state;
    private String postCode;

    public Customer(
            int id,
            String name,
            String phoneNum,
            String address,
            String city,
            Country country,
            Division state,
            String postCode)
    {
        customerId = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.city = city;
        this.country = country;
        this.state = state;
        this.postCode = postCode;
    }

    public Customer(
            String name,
            String phoneNum,
            String address,
            String city,
            Country country,
            Division state,
            String postCode)
    {
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.city = city;
        this.country = country;
        this.state = state;
        this.postCode = postCode;
        customerId = generateId();
    }

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
        return state;
    }

    public int getDivisionId()
    {
        return state.getDivisionId();
    }
}//Customer