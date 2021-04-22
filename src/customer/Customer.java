package customer;

public class Customer
{
    private String customerId;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private String state;
    private String postCode;

    public Customer(
            String id,
            String firstName,
            String lastName,
            String phoneNum,
            String addressLine1,
            String addressLine2,
            String city,
            String country,
            String state,
            String postCode)
    {
        customerId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.country = country;
        this.state = state;
        this.postCode = postCode;
    }

    public Customer(
            String firstName,
            String lastName,
            String phoneNum,
            String addressLine1,
            String addressLine2,
            String city,
            String country,
            String state,
            String postCode)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.country = country;
        this.state = state;
        this.postCode = postCode;
        customerId = generateId();
    }

    private static String generateId()
    {
        return "";
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

    public String getCustomerId()
    {
        return customerId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
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
}//Customer