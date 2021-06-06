package controller;

import appointment.Appointment;
import customer.Customer;
import customer.CustomerFieldCode;
import database.CustomerColumns;
import database.DBConnection;
import io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import sceneUtils.HeaderPane;
import sceneUtils.SceneCode;
import scenes.*;
import utils.Country;
import utils.User;

public class Controller
{
    //final attributes
    private final Scene appScene;
    private final HeaderPane header;
    private final LoginPage login;
    private AddEditAppointment editAppt;
    private AddEditCustomer editCust;
    private CustomerOverview custOverview;
    private AppointmentOverview apptOverview;
    private final DBConnection dbConnection;
	private final File loginAttemptDestinaiton;

    //non-final attributes
    private User currentUser;
    private Alert confirmationAlert;
    private int nextCustomerId;
    private int nextAppointmentId;
    private ObservableList<Country> countries;
	private ObservableList<Customer> customers;
	private HashMap<String, String> customerUpdates;
	private HashMap<String, String> appointmentUpdates;

    public Controller(Scene scn)
    {
        appScene = scn;
        header = new HeaderPane();
		loginAttemptDestinaiton = new File(IOConstants.LOGIN_ATTEMPT_DESTINATION);
        dbConnection = new DBConnection(this);
        login = new LoginPage(this);
        this.changeScene(SceneCode.LOGIN, null);
    }//constructor

    public void changeScene(SceneCode code, Object participant)
    {
        switch(code)
        {
            case LOGIN:
                appScene.setRoot(login);
                break;
            case CUSTOMER_OVERVIEW:
                appScene.setRoot(custOverview);
                break;
            case APPOINTMENT_OVERVIEW:
                if(participant instanceof Customer)
                {
                    Customer c = (Customer)participant;
                    apptOverview.loadOverview(c, FXCollections.observableArrayList(dbConnection.getCustomerAppointments(c.getCustomerId())));
                }
                appScene.setRoot(apptOverview);
                break;
            case EDIT_CUSTOMER:
                if(participant instanceof Customer)
                    editCust.loadCustomerInfo((Customer)participant);
                else
                    editCust.loadNewCustomer();
                appScene.setRoot(editCust);
                break;
            case EDIT_APPOINTMENT:
                if(participant instanceof Appointment)
                    editAppt.loadAppointmentInfo((Appointment)participant);
                else
                    editAppt.loadNewAppointment();
                appScene.setRoot(editAppt);
                break;
            default:
                System.out.println("ERROR: Scene code was not recognized");
                appScene.setRoot(custOverview);
        }//switch
    }//changeScene

	public boolean addAppointment(Appointment a)
	{
		return  false;
	}//addAppointment
	
	public void addAppointmentUpdate()
	{
		
	}//addAppointmentUpdate

    public boolean addCustomer(Customer c)
    {
        //TODO: Remember to include date and time created plus creator
		return false;
    }//addCustomer

	/**
	 *
	 */
	public void addCustomerUpdate(CustomerFieldCode code, String update)
	{
		switch(code) {
			case NAME_FIELD:
				customerUpdates.put(CustomerColumns.CUSTOMER_NAME.getColName(), update);
				break;
			case PHONE_FIELD:
				customerUpdates.put(CustomerColumns.CUSTOMER_PHONE.getColName(), update);
				break;
			case ADDRESS_FIELD:
				customerUpdates.put(CustomerColumns.CUSTOMER_ADDRESS.getColName(), update);
				break;
			case CITY_FIELD:
				customerUpdates.put(CustomerColumns.CUSTOMER_CITY.getColName(), update);
				break;
			case POST_CODE_FIELD:
				customerUpdates.put(CustomerColumns.CUSTOMER_POSTAL_CODE.getColName(), update);
				break;
			//case COUNTRY_FIELD:
				//customerUpdates.put(CustomerColumns.CUSTOMER_COUNTRY_ID.getColName(), update);
				//break;
			case DIVISION_FIELD:
				customerUpdates.put(CustomerColumns.CUSTOMER_DIVISION_ID.getColName(), update);
				break;
			default:
		}
	}//addCustomerUpdate
	
	public void clearAppointmentUpdates() {
		
	}
	
	public void clearCustomerUpdates() {
		customerUpdates = null;
		customerUpdates = new HashMap<>();
	}

    public boolean deleteAppointment(Appointment a)
    {
		return dbConnection.deleteAppointment(a.getAppointmentId());
    }//deleteAppointment

    public boolean deleteCustomer(Customer c)
    {
		if(c.getScheduledAppointments() > 0)
		{
			confirmationAlert.setAlertType(Alert.AlertType.INFORMATION);
			confirmationAlert.setContentText("Unable to delete customer " + c.getCustomerId() + " because they still have scheduled appointments.");
			confirmationAlert.showAndWait();
			return false;
		}
		if(dbConnection.deleteCustomer(c.getCustomerId()))
		{
			customers.remove(c);
			return true;
		} else
		{
			confirmationAlert.setAlertType(Alert.AlertType.ERROR);
			confirmationAlert.setContentText("There was an error trying to delete customer " + c.getCustomerId() + ".");
			confirmationAlert.showAndWait();
			return false;
		}
    }//deleteCustomer

    /**
     *
     * @return
     */
    public HeaderPane getHeader()
    {
        return header;
    }

    /**
     *
     * @return
     */
    public HashMap<String, String> getAppointmentUpdates()
    {
        return appointmentUpdates;
    }

    /**
     *
     * @return
     */
    public Alert getConfirmationAlert()
    {
        return confirmationAlert;
    }//getConfirmationAlert

    /**
     *
     * @return
     */
    public ObservableList<Country> getCountries()
    {
        return countries;
    }//getCountryCombo

    public int getCountryIdFromDivId(int divId)
    {
        return 0;
    }

    /**
     *
     * @return
     */
    public ObservableList<Customer> getCustomers()
    {
        return customers;
    }//getCustomers

    /**
     *
     * @param c
     * @return
     */
    public ObservableList<Appointment> getCustomerAppointments(Customer c)
    {
        return FXCollections.observableArrayList(dbConnection.getCustomerAppointments(c.getCustomerId()));
    }//getCustomerAppointments

    /**
     *
     * @return
     */
    public HashMap<String, String> getCustomerUpdates()
    {
        return customerUpdates;
    }

    /**
     *
     * @return
     */
    public int getNextAppointmentId()
    {
        return nextAppointmentId;
    }

    /**
     *
     * @return
     */
    public int getNextCustomerId()
    {
        return nextCustomerId;
    }

    public User getSessionUser()
    {
        return currentUser;
    }//getSessionUser

    //Hashes user-provided password for validation
    private static int hashPassword(CharSequence password)
    {
        //TODO: Make a real hash
        int sum = 0;
        for(int i = 0; i < password.length(); i++)
            sum += password.charAt(i);
        return sum;
    }//hashPassword

    /**
     * Initializes the HashMap for tracking changes to an existing customer
     */
    private void initializeCustomerUpdates() {
        customerUpdates = new HashMap<>();
        for(CustomerColumns col : CustomerColumns.values()) {
            customerUpdates.put(col.getColName(), null);
        }
    }//initializeCustomerUpdates

    /**
     * Initializes the Hashmap for tracking changes to an existing appointment
     */
    private void initializeAppointmentUpdates() {

    }//initializeAppointmentUpdates

	/**
	 * Writes login attempt information to a text file
	 *
	 * @param username	username entered for the login attempt
	 * @param valid		whether the login attempt was successful
	 */
    private void logLoginAttempt(String username, boolean valid)
    {
		try(var fw = new FileWriter(loginAttemptDestinaiton, true);
			var bw = new BufferedWriter(fw)) {
			LocalDateTime currentTime = LocalDateTime.now();
			String text = currentTime.format(DateTimeFormatter.ofPattern(IOConstants.DATE_TIME_FORMAT));
			text += (username + "\t");
			if(valid)
				text += IOConstants.SUCCESSFUL_LOGIN;
			else
				text += IOConstants.FAILED_LOGIN;
			bw.write(text);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//logLoginAttempt
	
	private boolean overlapsExistingAppointment(Appointment a)
	{
		//check when adding appointment or updating appointment
		//can check in observable list from appointment overview
		return false;
	}//overlapsExistingAppointmet

	/**
	 * Updates an existing customer in the database.
	 *
	 * @param customerId  Id of the customer to update
	 * @return  whether the database updated successfully
	 */
    public boolean updateCustomer(int customerId)
    {
		if(customerUpdates != null) {
			customerUpdates.put(CUSTOMER_UPDATE_BY, currentUser.getName());
			customerUpdates.put(CUSTOMER_LAST_UPDATE, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)));
			return dbConnection.updateCustomer(customerUpdates, customerId);
		}
        return false;
    }//updateCustomer

    /**
     * Driver method for validating login credentials.
     *
     * @param username  Username entered in the login form
     * @param password  Password entered in the login form
     */
    public void validateLoginCredentials(String username, CharSequence password)
    {
        /*
        if(validateCredentials(username, password)) {
            logLoginAttempt(username, true);
            validLogin(username);
        } else {
            logLoginAttempt(username, false);
            login.invalidLogin();
        }
         */
        validLogin("Gary");
    }//validateLoginCredentials

    /**
     *  Checks that the given username exists in the database and if the given password matches the associated
     *  username in the database. Returns true if both the user exists and the password matches and false otherwise.
     *
     * @param username  The given username
     * @param password  The given password
     * @return  Whether the given username and password exist and are associated in the database.
     */
    private boolean validateCredentials(String username, CharSequence password)
    {
        CharSequence cs = dbConnection.validateCredentials(username);
        if(cs == null) {
            return false;
        } else if (hashPassword(password) == hashPassword(cs)) {
            return true;
        } else {
            return false;
        }
    }//validateLoginCredentials

    private void validLogin(String username)
    {
        login.clearAll();
        countries = FXCollections.observableArrayList(dbConnection.getCountries());
        customers = FXCollections.observableArrayList(dbConnection.getCustomers());
        initializeCustomerUpdates();
        initializeAppointmentUpdates();
        editAppt = new AddEditAppointment(this);
        editCust = new AddEditCustomer(this);
        custOverview = new CustomerOverview(this);
        apptOverview = new AppointmentOverview(this);
        confirmationAlert = new Alert(Alert.AlertType.NONE);
        currentUser = new User(0, username);
        //TODO: check for appointments starting within fifteen minutes
        changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
    }
}//class Controller