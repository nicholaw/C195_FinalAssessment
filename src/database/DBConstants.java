package database;

public class DBConstants
{
	//Table Names
	public static final String APPOINTMENT_TABLE	=	"appointments";
	public static final String CONTACT_TABLE		=	"contacts";
	public static final String COUNTRY_TABLE		=	"countries";
	public static final String CUSTOMER_TABLE		=	"customers";
	public static final String DIVISION_TABLE		=	"first_level_divisions"; 
	public static final String USER_TABLE			=	"users";

	//Customers Table Column Names
	public static final String CUSTOMER_ID 			=	"Customer_ID";
	public static final String CUSTOMER_NAME 		=	"Customer_Name";
	public static final String CUSTOMER_ADDRESS 	=	"Address";
	public static final String CUSTOMER_POSTAL_CODE	=	"Postal_Code";
	public static final String CUSTOMER_PHONE 		=	"Phone";
	public static final String CUSTOMER_CREATE_DATE	=	"Create_Date";
	public static final String CUSTOMER_CREATE_BY 	=	"Created_By";
	public static final String CUSTOMER_LAST_UPDATE =	"Last_Update";
	public static final String CUSTOMER_UPDATE_BY 	=	"Last_Updated_By";
	public static final String CUSTOMER_DIVISION_ID =	"Division_ID";

	//Appointment Table Column Names
	public static final String APPOINTMENT_CONTACT_ID = "Contact_ID";
}