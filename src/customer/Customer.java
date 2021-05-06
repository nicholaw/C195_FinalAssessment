package customer;

public class Customer
{
    private int customerId;
    private String name;
    private String phoneNum;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private String state;
    private String postCode;
    private int divisionId;

    public Customer(
            int id,
            String name,
            String phoneNum,
            String addressLine1,
            String addressLine2,
            String city,
            String country,
            String state,
            String postCode,
            int division)
    {
        customerId = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.country = country;
        this.state = state;
        this.postCode = postCode;
        this.divisionId = division;
    }

    public Customer(
            String name,
            String phoneNum,
            String addressLine1,
            String addressLine2,
            String city,
            String country,
            String state,
            String postCode)
    {
        this.name = name;
        this.phoneNum = phoneNum;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
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
        String str = "";
        str = str + addressLine1 + "\n";
        str = str + addressLine2 + "\n";
        str = str + city + ", " + state + ", " + country;
        return str;
    }

    public String getAddressLine1()
    {
        return addressLine1;
    }

    public String getAddressLine2()
    {
        return addressLine2;
    }

    public String getCountry()
    {
        return country;
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

    public String getState()
    {
        return state;
    }

    public int getDivisionId()
    {
        return divisionId;
    }
}//Customer