package controller;

import appointment.Appointment;
import customer.Customer;
import customer.CustomerFieldCode;
import database.CustomerColumns;
import database.DBConnection;
import database.DBConstants;
import io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import sceneUtils.HeaderPane;
import sceneUtils.SceneCode;
import scenes.*;
import utils.Contact;
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
    private Alert messageAlert;
    private int nextCustomerId;
    private int nextAppointmentId;
    private ObservableList<Country> countries;
	private ObservableList<Customer> customers;
	private ObservableList<Contact> contacts;
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

    public boolean addCustomer(Customer c) {
		if(dbConnection.insertCustomer(c, currentUser.getUsername(), 
			LocalDateTime.now().format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)))) {
			customers.add(c);
			return true;
		} else {
			return false;
		}
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
			//case COUNTRY_BOX:
				//customerUpdates.put(CustomerColumns.CUSTOMER_COUNTRY_ID.getColName(), update);
				//break;
			case DIVISION_BOX:
				customerUpdates.put(CustomerColumns.CUSTOMER_DIVISION_ID.getColName(), update);
				break;
			default:
		}
	}//addCustomerUpdate
	
	/**
	 * Displays an information alert that informs the users which appointments will begin within 
	 * fifteen minutes of login if there are any.
	 */
	private void checkForUpcomingAppointments() {
		messageAlert.setAlertType(Alert.AlertType.INFORMATION);
		messageAlert.setTitle("Upcoming Appointments");
		String message = "";
		Set<String> appointments = new HashSet<>(dbConnection.getUpcomingAppointments(LocalDateTime.now(), DBConstants.TIME_INTERVAL, DBConstants.TIME_UNIT));
		if((appointments != null) && !(appointments.isEmpty())) {
			message += "The following appointments will begin within the next fifteen minutes:";
			message += "\n\n";
			for(String str : appointments) {
				message += str + "\n";
			}
		} else {
			message += "There are no appointments beginning within the next fifteen minutes.";
		}
		messageAlert.setContentText(message);
		messageAlert.showAndWait();
	}//checkForUpcomingAppointments
	
	public void clearAppointmentUpdates() {
		
	}

    /**
     *
     */
	public void clearCustomerUpdates() {
		for(String str : customerUpdates.keySet()) {
		    customerUpdates.put(str, null);
        }
	}

    public boolean deleteAppointment(Appointment a) {
		return dbConnection.deleteAppointment(a.getAppointmentId());
    }//deleteAppointment

    public boolean deleteCustomer(Customer c) {
		if(c.getAppointments() > 0) {
			messageAlert.setAlertType(Alert.AlertType.INFORMATION);
			messageAlert.setContentText("Unable to delete customer " + c.getCustomerId() + " because they still have scheduled appointments.");
			messageAlert.showAndWait();
			return false;
		} else if(dbConnection.deleteCustomer(c.getCustomerId())) {
			customers.remove(c);
			return true;
		} else {
			messageAlert.setAlertType(Alert.AlertType.ERROR);
			messageAlert.setContentText("There was an error trying to delete customer " + c.getCustomerId() + ".");
			messageAlert.showAndWait();
			return false;
		}
    }//deleteCustomer

    /**
     *
     * @return
     */
    public HeaderPane getHeader() {
        return header;
    }

    /**
     *
     * @return
     */
    public HashMap<String, String> getAppointmentUpdates() {
        return appointmentUpdates;
    }

    /**
     *
     * @return
     */
    public Alert getMessageAlert() {
        return messageAlert;
    }//getMessageAlert

    public Contact getContact(int id) {
        for(Contact c : contacts) {
            if(c.getId() == id)
                return c;
        }
        return null;
    }//getContact
	
	public ObservableList<Contact> getContacts() {
		return contacts;
	}//getContacts

    /**
     *
     * @return
     */
    public ObservableList<Country> getCountries()
    {
        return countries;
    }//getCountries

    public Country getCountry(int id) {
        for(Country c : countries) {
            return c;
        }
        return null;
    }//getCountry

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
    public HashMap<String, String> getCustomerUpdates() {
        return customerUpdates;
    }

    /**
     *
     * @return
     */
    public int getNextAppointmentId() {
        return nextAppointmentId;
    }

    /**
     *
     * @return
     */
    public int getNextCustomerId() {
        return nextCustomerId;
    }

    public User getCurrentUser() {
        return currentUser;
    }//getSessionUser

    //Hashes user-provided password for validation
    private static int hashPassword(CharSequence password) {
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
    private void logLoginAttempt(String username, boolean valid) {
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
	
	private boolean overlapsExistingAppointment(Appointment a) {
		//check when adding appointment or updating appointment
		//can check in observable list from appointment overview
		return false;
	}//overlapsExistingAppointment

    public boolean updateAppointment(int appointmentId) {
        if(appointmentUpdates != null) {
            return dbConnection.updateAppointment(appointmentUpdates, appointmentId);
        } else
            return false;
    }//updateAppointment

	/**
	 * Updates an existing customer in the database.
	 *
	 * @param customerId  Id of the customer to update
	 * @return  whether the database updated successfully
	 */
    public boolean updateCustomer(int customerId) {
		if(customerUpdates != null) {
			customerUpdates.put(CustomerColumns.CUSTOMER_UPDATE_BY.getColName(), currentUser.getUsername());
			customerUpdates.put(CustomerColumns.CUSTOMER_LAST_UPDATE.getColName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)));
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
    public void validateLoginCredentials(String username, CharSequence password) {
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
    private boolean validateCredentials(String username, CharSequence password) {
        CharSequence cs = dbConnection.validateCredentials(username);
        if(cs == null) {
            return false;
        } else if (hashPassword(password) == hashPassword(cs)) {
            return true;
        } else {
            return false;
        }
    }//validateLoginCredentials

    private void validLogin(String username) {
        login.clearAll();
        countries = FXCollections.observableArrayList(dbConnection.getCountries());
        customers = FXCollections.observableArrayList(dbConnection.getCustomers());
        customers.add(new Customer(216, "Nora Dahlberg", "8012312564", "300 E Lincoln Ave", "Salt Lake City", "84101", countries.get(0), null));
		contacts = FXCollections.observableArrayList(dbConnection.getContacts());
        initializeCustomerUpdates();
        initializeAppointmentUpdates();
        editAppt = new AddEditAppointment(this);
        editCust = new AddEditCustomer(this);
        custOverview = new CustomerOverview(this);
        apptOverview = new AppointmentOverview(this);
        messageAlert = new Alert(Alert.AlertType.NONE);
        currentUser = dbConnection.getUser(username);
        changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
		checkForUpcomingAppointments();
    }
}//class Controller