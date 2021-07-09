package controller;

import appointment.Appointment;
import appointment.AppointmentFieldCode;
import appointment.AppointmentType;
import customer.Customer;
import customer.CustomerFieldCode;
import database.AppointmentColumns;
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
import java.util.*;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import localization.SupportedLocale;
import sceneUtils.HeaderPane;
import sceneUtils.Refreshable;
import sceneUtils.SceneCode;
import scenes.*;
import utils.*;

public class Controller {
    //final attributes
    private final HeaderPane header;
    private final BorderPane contentPane;
    private final LoginPage login;
    private AddEditAppointment editAppt;
    private AddEditCustomer editCust;
    private CustomerOverview custOverview;
    private AppointmentOverview apptOverview;
    private final DBConnection dbConnection;
	private final File loginAttemptDestinaiton;
	private final Set<Refreshable> scenes;

    //non-final attributes
    private User currentUser;
    private Alert messageAlert;
	private ResourceBundle rb;
	private SceneCode currentScene;
    private ObservableList<Country> countries;
	private ObservableList<Customer> customers;
	private ObservableList<Contact> contacts;
	private HashMap<String, String> customerUpdates;
	private HashMap<String, String> appointmentUpdates;

    public Controller(Scene scn) {
    	rb = ResourceBundle.getBundle("localization.Localization", Locale.ENGLISH);
		contentPane = new BorderPane();
		header = new HeaderPane(this, FXCollections.observableArrayList(SupportedLocale.values()), SupportedLocale.LOCALE_ENGLISH);
		contentPane.setTop(header);
		scn.getStylesheets().add(ControllerConstants.STYLE_DESTINATION);
		scn.setRoot(contentPane);
		loginAttemptDestinaiton = new File(IOConstants.LOGIN_ATTEMPT_DESTINATION);
        dbConnection = new DBConnection(this);
        scenes = new HashSet<>();
        scenes.add(login = new LoginPage(this));
        this.changeScene(SceneCode.LOGIN, null);
        currentUser = null;
    }//constructor

	/**
     *
     */
	public boolean addAppointment(Appointment a) {
		if(dbConnection.insertAppointment(a, currentUser,
				LocalDateTime.now().format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)))) {
			return true;
		}
		return false;
	}//addAppointment
	
	/**
     *
     */
	public void addAppointmentUpdate(AppointmentFieldCode code, String update) {
		switch(code) {
			case TITLE_FIELD:
				appointmentUpdates.put(AppointmentColumns.APPOINTMENT_TITLE.getColName(), update);
				break;
			case DESC_AREA:
				appointmentUpdates.put(AppointmentColumns.APPOINTMENT_DESCRIPTION.getColName(), update);
				break;
			case LOCATION_COMBO:
				appointmentUpdates.put(AppointmentColumns.APPOINTMENT_LOCATION.getColName(), update);
				break;
			case TYPE_COMBO:
				appointmentUpdates.put(AppointmentColumns.APPOINTMENT_TYPE.getColName(), update);
				break;
			case START_TIME:
				appointmentUpdates.put(AppointmentColumns.APPOINTMENT_START.getColName(), update);
				break;
			case END_TIME:
				appointmentUpdates.put(AppointmentColumns.APPOINTMENT_END.getColName(), update);
				break;
			case CONTACT_COMBO:
				appointmentUpdates.put(AppointmentColumns.APPOINTMENT_CONTACT_ID.getColName(), update);
				break;
			default:
		}
	}//addAppointmentUpdate

	/**
     *
     */
    public boolean addCustomer(Customer c) {
		if(dbConnection.insertCustomer(c, currentUser.getUsername(), 
			LocalDateTime.now().format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)))) {
			customers.add(c);
			System.out.printf("Customer %d(%s) added successfully.\n\n", c.getCustomerId(), c.getName()); //FOR TESTING
			return true;
		} else {
			System.out.printf("Customer %d(%s) added unsuccessfully.\n\n", c.getCustomerId(), c.getName()); //FOR TESTING
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

	public void changeScene(SceneCode code, Object participant) {
		switch(code)
		{
			case LOGIN:
				contentPane.setCenter(login);
				currentUser = null;
				break;
			case CUSTOMER_OVERVIEW:
				this.clearCustomerUpdates();
				custOverview.refreshCustomersTable();
				contentPane.setCenter(custOverview);
				break;
			case APPOINTMENT_OVERVIEW:
				if(participant instanceof Customer) {
					apptOverview.loadOverview((Customer)participant);
				}
				this.clearAppointmentUpdates();
				contentPane.setCenter(apptOverview);
				break;
			case EDIT_CUSTOMER:
				if(participant instanceof Customer)
					editCust.loadCustomerInfo((Customer)participant);
				else
					editCust.loadNewCustomer();
				contentPane.setCenter(editCust);
				break;
			case EDIT_APPOINTMENT:
				if(participant instanceof appointment.Appointment)
					editAppt.loadAppointmentInfo((appointment.Appointment)participant);
				else
					editAppt.loadNewAppointment();
				editAppt.loadCustomerInfo(apptOverview.getCustomerToDisplay());
				contentPane.setCenter(editAppt);
				break;
			default:
				System.out.println("ERROR: Scene code was not recognized");
				contentPane.setCenter(custOverview);
		}//switch
		currentScene = code;
	}//changeScene
	
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
	
	/**
     *
     */
	public void clearAppointmentUpdates() {
		for(String str : appointmentUpdates.keySet()) {
			appointmentUpdates.put(str, null);
		}
	}

    /**
     *
     */
	public void clearCustomerUpdates() {
		for(String str : customerUpdates.keySet()) {
		    customerUpdates.put(str, null);
        }
	}

	/**
     *
     */
    public boolean deleteAppointment(appointment.Appointment a) {
		return dbConnection.deleteAppointment(a.getAppointmentId());
    }//deleteAppointment

	/**
     *
     */
    public boolean deleteCustomer(Customer c) {
    	if(c.getAppointments() == null)
    		c.setAppointments(dbConnection.getCustomerAppointments(c.getCustomerId()));
		if(c.getAppointments().size() > 0) { //TODO: filter past dated appointments
			messageAlert.setAlertType(Alert.AlertType.INFORMATION);
			messageAlert.setTitle("Delete Customer");
			String message = "Cannot delete customer " + c.getName() + "(#" + c.getCustomerId() + ") because " +
					"they still have the following " + c.getAppointments().size() + " appointments:\n";
			for(appointment.Appointment a : c.getAppointments()) {
				message += ("\t" + a.getAppointmentId() + "\n");
			}
			messageAlert.setContentText(message);
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
	 * Displays a confirmation alert with the provided title and message. Returns true if the user selects the 'OK'
	 * button and false otherwise.
	 *
	 * @param title
	 * @param message
	 * @return
	 */
	public boolean displayConfirmationAlert(String title, String message) {
    	messageAlert.setAlertType(Alert.AlertType.CONFIRMATION);
    	messageAlert.setTitle(title);
    	messageAlert.setContentText(message);
		var result = messageAlert.showAndWait();
		return (result.isPresent() && result.get() == ButtonType.OK);
	}

	/**
	 *
	 * @return
	 */
	public ObservableList<AppointmentType> getAppointmentTypes() {
		return FXCollections.observableArrayList(AppointmentType.values());
	}

	public Locale getCurrentLocale() {
		return rb.getLocale();
	}

    public Location getLocation(String name) {
    	for(Location l : Location.values()) {
    		if(l.equals(name))
    			return l;
		}
    	return null;
	}

	/**
	 *
	 * @return
	 */
	public ObservableList<Location> getLocations() {
    	return FXCollections.observableArrayList(Location.values());
	}

    /**
     *
     * @return
     */
    public Alert getMessageAlert() {
        return messageAlert;
    }//getMessageAlert

	/**
     *
     */
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
    public ObservableList<Country> getCountries() {
        return countries;
    }//getCountries

    public Country getCountry(int id) {
        for(Country c : countries) {
            if(c.getCountryId() == id)
            	return c;
        }
        return null;
    }//getCountry

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
    public ObservableList<appointment.Appointment> getCustomerAppointments(Customer c)
    {
        return FXCollections.observableArrayList(dbConnection.getCustomerAppointments(c.getCustomerId()));
    }//getCustomerAppointments

    /**
     *
     * @return
     */
    public long getNextAppointmentId() {
        return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMQDDDYYYY")));
    }

    /**
     *
     * @return
     */
    public long getNextCustomerId() {
        return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("kkMMmmddYY")));
    }

    public User getCurrentUser() {
        return currentUser;
    }//getSessionUser

	public ResourceBundle getResourceBundle() {
    	return rb;
	}

	/**
	 *
	 * @param password
	 * @return
	 */
    public static CharSequence pseudoHashPassword(CharSequence password) {
        //TODO: Make a real hash
		String hash = "";
        for(int i = 0; i < password.length(); i++) {
			hash += (int)password.charAt(i);
		}
        return hash.subSequence(0, hash.length());
    }//pseudoHashPassword

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
		appointmentUpdates = new HashMap<>();
		for(AppointmentColumns col : AppointmentColumns.values()) {
			appointmentUpdates.put(col.getColName(), null);
		}
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

	/**
	 *
	 */
	private void refreshScenes() {
		for(Refreshable scene : scenes) {
			scene.refresh(rb);
		}
	}

	/**
	 *
	 * @param locale
	 */
	public void setLocale(SupportedLocale locale) {
		rb = ResourceBundle.getBundle("localization.Localization", locale.getLocale());
		refreshScenes();
	}

    /**
     * Updates an existing appointment in the database.
	 *
     * @param appointmentId		Id of the appointment to update
     * @return	Whether the database was updated successfully
     */
    public boolean updateAppointment(long appointmentId) {
		if(appointmentUpdates != null) {
			//add user and date to appointment updates for last updated by and last updated
			appointmentUpdates.put(AppointmentColumns.APPOINTMENT_UPDATED_BY.getColName(), currentUser.getUsername());
			appointmentUpdates.put(AppointmentColumns.APPOINTMENT_UPDATE_DATE.getColName(), LocalDateTime.now().format(ControllerConstants.TIMESTAMP_FORMAT));
			return dbConnection.updateAppointment(appointmentUpdates, appointmentId);
		}
        return false;
    }

	/**
	 * Updates an existing customer in the database.
	 *
	 * @param customerId  Id of the customer to update
	 * @return  whether the database updated successfully
	 */
    public boolean updateCustomer(long customerId) {
		if(customerUpdates != null) {
			//add user and date to customer updates for last updated by and last updated
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
    	try {
			if(dbConnection.validateCredentials(username).equals(Controller.pseudoHashPassword(password))) {
				logLoginAttempt(username, true);
				validLogin(username);
			} else {
				logLoginAttempt(username, false);
				login.invalidLogin();
			}
		} catch(NullPointerException e) {
    		e.printStackTrace();
			logLoginAttempt(username, false);
			login.invalidLogin();
		}
    }//validateLoginCredentials

	/**
	 *
	 * @param username name of the user who successfully logged on
	 */
    private void validLogin(String username) {
        login.clearAll();
        countries = FXCollections.observableArrayList(dbConnection.getCountries());
        customers = FXCollections.observableArrayList(dbConnection.getCustomers());
		contacts = FXCollections.observableArrayList(dbConnection.getContacts());
        initializeCustomerUpdates();
        initializeAppointmentUpdates();
        scenes.add(editAppt = new AddEditAppointment(this));
        scenes.add(editCust = new AddEditCustomer(this));
		scenes.add(custOverview = new CustomerOverview(this));
		scenes.add(apptOverview = new AppointmentOverview(this));
        messageAlert = new Alert(Alert.AlertType.NONE);
        currentUser = dbConnection.getUser(username);
        changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
		checkForUpcomingAppointments();
    }
}//class Controller