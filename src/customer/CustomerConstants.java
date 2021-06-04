package customer;

public class CustomerConstants
{
    //Constants for button text
    public static final String ADD_CUSTOMER = "Add Customer";
    public static final String UPDATE_CUSTOMER = "Update Customer";

    //Legal non-alphanumeric characters
    public static final String LEGAL_ADDRESS_CHARACTERS = ".,'#-";
    public static final String LEGAL_PHONE_CHARACTERS = ".-#*";

    //Varchar limits for database fields
    public static final int MAX_CHAR_DEFAULT = 50;
    public static final int MAX_CHAR_ADDRESS = 100;
	
	//Indexes for customer updadtes
	public static final int EDITABLE_ATTRIBUTES	= 6;
	public static final int NAME_INDEX 			= 0;
	public static final int ADDRESS_INDEX 		= 1;
	public static final int PHONE_INDEX 		= 2;
	public static final int POSTAL_CODE_INDEX	= 3;
	public static final int COUNTRY_INDEX 		= 4;
	public static final int DIVISION_INDEX 		= 5;
}