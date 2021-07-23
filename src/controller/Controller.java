package controller;

import appointment.Appointment;
import appointment.AppointmentConstants;
import appointment.AppointmentFieldCode;
import utils.Type;
import customer.Customer;
import customer.CustomerFieldCode;
import database.AppointmentColumns;
import database.CustomerColumns;
import database.DBConnection;
import database.DBConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import java.io.*;
import java.time.ZonedDateTime;
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

/**
 * Master controller the the C195 appointment scheduler application. This controller is mainly responsible
 * for communicating with the database and transitioning scenes.
 */
public class Controller {
    //final attributes
    private final HeaderPane header;
    private final BorderPane contentPane;
    private final LoginPage login;
    private AddEditAppointment editAppt;
    private AddEditCustomer editCust;
    private CustomerOverview custOverview;
    private AppointmentOverview apptOverview;
    private CustomerReport customerReport;
    private TotalsReport totalsReport;
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

	/**
	 * Constructs this controller. Established connection with the database and displays the login scene.
	 * @param scn -the main scene for this application
	 */
    public Controller(Scene scn) {
    	rb = ResourceBundle.getBundle("localization.Localization", Locale.getDefault());
		contentPane = new BorderPane();
		header = new HeaderPane(this, FXCollections.observableArrayList(SupportedLocale.values()),
				SupportedLocale.contains(Locale.getDefault().getLanguage()));
		contentPane.setTop(header);
		contentPane.setPrefSize(800, 700);
		scn.getStylesheets().add(ControllerConstants.STYLE_DESTINATION);
		scn.setRoot(contentPane);
		loginAttemptDestinaiton = new File(ControllerConstants.LOGIN_ATTEMPT_DESTINATION);
        dbConnection = new DBConnection(this);
        scenes = new HashSet<>();
        scenes.add(login = new LoginPage(this));
        this.changeScene(SceneCode.LOGIN, null);
        currentUser = null;
    }//constructor

	/**
	 * Adds an appointment to the database. Returns true if the appointment was successfully inserted and
	 * false otherwise.
	 * @param a	-the appointment to be inserted into the database
	 * @return	-whether the given appointment was successfully inserted
	 */
	public boolean addAppointment(Appointment a) {
		if(dbConnection.insertAppointment(a, currentUser,
				ZonedDateTime.now(AppointmentConstants.ZONE_UTC).format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)))) {
			return true;
		}
		return false;
	}//addAppointment

	/**
	 * Adds an appointment update to a collection of updates to be executed in the database.
	 * @param code		-code denoting which appointment field is to be updated
	 * @param update	-the updated to be added
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
	 * Adds a customer to the database. Returns true if the given customer was successfully inserted into the
	 * database and false otherwise.
	 * @param c	-the customer to be inserted into the database
	 * @return	-whether the customer was successfully inserted into the database
	 */
    public boolean addCustomer(Customer c) {
		if(dbConnection.insertCustomer(c, currentUser.getUsername(),
				ZonedDateTime.now(AppointmentConstants.ZONE_UTC).format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)))) {
			customers.add(c);
			return true;
		} else {//FOR TESTING
			return false;
		}
    }//addCustomer

	/**
	 * Adds a customer update to a collection of updates to be executed in the database.
	 * @param code		-code denoting which customer field is to be updated
	 * @param update	-the updated to be added
	 */
	public void addCustomerUpdate(CustomerFieldCode code, String update) {
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
	 * Changes the scene the user is viewing by setting the center node of the scene's root
	 * node to the scene denoted by the given code.
	 * @param code			-code denoting which scene to change to
	 * @param participant	-appointment or customer to be displayed in the next scene when applicable
	 */
	public void changeScene(SceneCode code, Object participant) {
		switch(code) {
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
				if(participant instanceof Appointment)
					editAppt.loadAppointmentInfo((Appointment)participant);
				else
					editAppt.loadNewAppointment();
				editAppt.loadCustomerInfo(apptOverview.getCustomerToDisplay());
				contentPane.setCenter(editAppt);
				break;
			case REPORT_OVERVIEW:
				customerReport.initiate((Customer)participant);
				contentPane.setCenter(customerReport);
				break;
			case REPORT_TOTAL:
				totalsReport.initialize();
				contentPane.setCenter(totalsReport);
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
		messageAlert.setTitle(rb.getString("upcoming_appointments_title"));
		String message = "";
		Set<String> appointments = new HashSet<>(dbConnection.getUpcomingAppointments(ZonedDateTime.now(AppointmentConstants.ZONE_UTC), DBConstants.TIME_INTERVAL));
		if((appointments != null) && !(appointments.isEmpty())) {
			message += rb.getString("upcoming_appointments_true");
			message += "\n\n";
			for(String str : appointments) {
				message += str + "\n";
			}
		} else {
			message += rb.getString("upcoming_appointments_false");
		}
		messageAlert.setContentText(message);
		messageAlert.showAndWait();
	}//checkForUpcomingAppointments
	
	/**
     *	Clears all updates from the collection of appointment updates to be implemented in the database.
     */
	public void clearAppointmentUpdates() {
		for(String str : appointmentUpdates.keySet()) {
			appointmentUpdates.put(str, null);
		}
	}

    /**
     *	Clears all updates from the collection of customer updates to be implemented in the database.
     */
	public void clearCustomerUpdates() {
		for(String str : customerUpdates.keySet()) {
		    customerUpdates.put(str, null);
        }
	}

	/**
	 * Removes the given appointment from the database. Returns true if the appointment was successfully
	 * deleted and false otherwise.
	 * @param a	-the appointment to be removed
	 * @return	-whether the appointment was successfully removed
	 */
    public boolean deleteAppointment(Appointment a) {
		return dbConnection.deleteAppointment(a.getAppointmentId());
    }//deleteAppointment

	/**
	 * Checks that the given customer has no outstanding appointments and either informs ths user that the
	 * given customer is not eligible to be deleted or removes the given customer from the database. Returns
	 * true if the customer was successfully deleted and false otherwise.
	 * @param c	-the customer to be deleted
	 * @return	-whether the customer was successfully deleted
	 */
    public boolean deleteCustomer(Customer c) {
    	if(c.getAppointments() == null)
    		c.setAppointments(dbConnection.getCustomerAppointments(c.getCustomerId()));
		if(c.getAppointments().size() > 0) {
			messageAlert.setAlertType(Alert.AlertType.INFORMATION);
			messageAlert.setTitle(rb.getString("failed_delete_title"));
			String message = rb.getString("failed_delete_customer1") + " " +
					c.getName() + " (#" + c.getCustomerId() + ") " + rb.getString("failed_delete_customer2");
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
	 * @param title		-title of the alert
	 * @param message	-content text of the alert
	 * @return			-whether the user selected 'OK'
	 */
	public boolean displayConfirmationAlert(String title, String message) {
    	messageAlert.setAlertType(Alert.AlertType.CONFIRMATION);
    	messageAlert.setTitle(title);
    	messageAlert.setContentText(message);
		var result = messageAlert.showAndWait();
		return (result.isPresent() && result.get() == ButtonType.OK);
	}//displayConfirmationAlert

	/**
	 * Displays an Information alert with the provided title and message.
	 * @param title -title of the alert
	 * @param message -content text of the alert
	 */
	public void displayInformationalAlert(String title, String message) {
		messageAlert.setAlertType(Alert.AlertType.INFORMATION);
		messageAlert.setTitle(title);
		messageAlert.setContentText(message);
		messageAlert.showAndWait();
	}//displayInformationalAlert

	/**
	 * Returns an ObservableList of the types of appointments offered by this application. Used for populating
	 * the 'Type' ComboBox on the add/edit appointment form.
	 * @return	-list of appointment types offered by this application
	 */
	public ObservableList<Type> getAppointmentTypes() {
		return FXCollections.observableArrayList(Type.values());
	}

	/**
	 * Returns the currently selected Locale.
	 * @return	-the current Locale
	 */
	public Locale getCurrentLocale() {
		return rb.getLocale();
	}

	/**
	 * Returns an ObservableList of the locations for appointments offered by this application. Used for populating
	 * the 'Location' ComboBox on the add/edit appointment form.
	 * @return	-list of appointment locations offered by this application
	 */
	public ObservableList<Location> getLocations() {
    	return FXCollections.observableArrayList(Location.values());
	}

	/**
     * Returns the Contact with given contact id. Returns null if no contact with that id is found.
	 * @param id 	-id of the Contact to return
	 * @return		-Contact with the given id
     */
    public Contact getContact(int id) {
        for(Contact c : contacts) {
            if(c.getId() == id)
                return c;
        }
        return null;
    }//getContact

	/**
	 * Returns an ObservableList of contacts who can be assigned to appointments.
	 * @return	-the list of contacts
	 */
	public ObservableList<Contact> getContacts() {
		return contacts;
	}//getContacts

	/**
	 * Return a HashMap of each company contact mapped the appointments to which they
	 * are assigned.
	 * @return -the map of contacts and appointments
	 */
	public HashMap<Contact, Set<Appointment>> getContactSchedule() {
		var schedule = new HashMap<Contact, Set<Appointment>>();
		for(Contact c : contacts)
			schedule.put(c, new HashSet<>());
		for(Customer c : customers) {
			if(c.getAppointments() == null)
				c.setAppointments(this.getCustomerAppointments(c));
			for(Appointment a : c.getAppointments()) {
				schedule.get(a.getContact()).add(a);
			}
		}
		return schedule;
	}//getContactSchedule

    /**
     * Returns an ObservableList of countries supported by this application.
     * @return -the list of countries
     */
    public ObservableList<Country> getCountries() {
        return countries;
    }//getCountries

	/**
	 * Returns the Country whose id matches the given id. Returns null if no such country is found.
	 * @param id 	-id of the country to return
	 * @return		-the country whose id matches the given id
	 */
    public Country getCountry(int id) {
        for(Country c : countries) {
            if(c.getCountryId() == id)
            	return c;
        }
        return null;
    }//getCountry

	/**
	 * Returns the username of the user currently logged on.
	 * @return -the username
	 */
	public String getCurrentUsername() {
    	return currentUser.getUsername();
	}//getCurrentUsername

    /**
     * Returns an ObservableList of the customers stored in the database. Used for displaying customers in
	 * the customer overview scene.
     * @return	-the list of customers
     */
    public ObservableList<Customer> getCustomers() {
        return customers;
    }//getCustomers


	/**
	 * Returns an ObservableList of all the appointments the given customer has scheduled.
	 * @param c	-the customer
	 * @return	-appointments scheduled for the given customer
	 */
    public ObservableList<Appointment> getCustomerAppointments(Customer c) {
        return FXCollections.observableArrayList(dbConnection.getCustomerAppointments(c.getCustomerId()));
    }//getCustomerAppointments

	/**
	 * Returns and array of the reports by location and type for the given LocalDateTime.
	 * @param monthToReport -the month for which to get reports
	 * @return -the reports for the provided month (LocalDateTime)
	 */
	public HashMap[] getMonthlyReports(ZonedDateTime monthToReport, Customer customerToReport) {
    	return dbConnection.getMonthlyReports(monthToReport, customerToReport.getCustomerId());
	}

    /**
     * Returns the next appointment id for creating a new appointment
     * @return -the next available id
     */
    public long getNextAppointmentId() {
        return Long.parseLong(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MMmmkkddss")));
    }

	/**
	 * Returns the next available customer id for creating a new appointment
	 * @return -the next available id
	 */
    public long getNextCustomerId() {
        return Long.parseLong(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MMmmkkddYY")));
    }

	/**
	 * Returns an array of three HashMaps which correspond to each of the required reports:
	 * totals appointments each month, total appointments of each type, and total appointments
	 * of each location.
	 * @return -the array of reports
	 */
	public HashMap[] getReports() {
    	return dbConnection.getReports();
	}//getReports

	/**
	 * Returns the ResourceBundle currently being used by this application.
	 * @return	-the ResourceBundle
	 */
	public ResourceBundle getResourceBundle() {
    	return rb;
	}

	/**
	 * Returns the localization matching the given key for the resource bundle this
	 * controller is currently using. Returns the empty string if no such key
	 * is found.
	 * @param key -the localization key
	 * @return -the localization value
	 */
	public String getString(String key) {
		try{
			return rb.getString(key);
		} catch(Exception e) {
			return "";
		}
	}//getString

	/**
	 * Hashes the password entered by the user in the password field on the login form. Used for
	 * comparing entered password to hashed passwords stored in the database.
	 * @param password	-password entered by the user
	 * @return			-hash of the given password
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
     * Initializes the HashMap for tracking changes to an existing customer.
     */
    private void initializeCustomerUpdates() {
        customerUpdates = new HashMap<>();
        for(CustomerColumns col : CustomerColumns.values()) {
            customerUpdates.put(col.getColName(), null);
        }
    }//initializeCustomerUpdates

    /**
     * Initializes the Hashmap for tracking changes to an existing appointment.
     */
    private void initializeAppointmentUpdates() {
		appointmentUpdates = new HashMap<>();
		for(AppointmentColumns col : AppointmentColumns.values()) {
			appointmentUpdates.put(col.getColName(), null);
		}
    }//initializeAppointmentUpdates

	/**
	 * Writes login attempt information to a text file
	 * @param username	-username entered for the login attempt
	 * @param valid		-whether the login attempt was successful
	 */
    private void logLoginAttempt(String username, boolean valid) {
		try(var fw = new FileWriter(loginAttemptDestinaiton, true);
			var bw = new BufferedWriter(fw)) {
			ZonedDateTime currentTime = ZonedDateTime.now();
			String text = currentTime.format(DateTimeFormatter.ofPattern(ControllerConstants.DATE_TIME_FORMAT));
			text += (username + "\t");
			if(valid)
				text += ControllerConstants.SUCCESSFUL_LOGIN;
			else
				text += ControllerConstants.FAILED_LOGIN;
			bw.write(text);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//logLoginAttempt

	/**
	 * Refreshes each scene after the applications ResourceBundle has been changed.
	 */
	private void refreshScenes() {
		for(Refreshable scene : scenes) {
			scene.refresh(rb);
		}
	}

	/**
	 * Sets the Locale of this application's ResourceBundle to the given SupportedLocale
	 * @param locale -the locale to be set
	 */
	public void setLocale(SupportedLocale locale) {
		rb = ResourceBundle.getBundle("localization.Localization", locale.getLocale());
		refreshScenes();
	}

    /**
     * Updates an existing appointment in the database.
     * @param appointmentId	-id of the appointment to update
     * @return				-whether the database was updated successfully
     */
    public boolean updateAppointment(long appointmentId) {
		if(appointmentUpdates != null) {
			//add user and date to appointment updates for last updated by and last updated
			appointmentUpdates.put(AppointmentColumns.APPOINTMENT_UPDATED_BY.getColName(), currentUser.getUsername());
			appointmentUpdates.put(AppointmentColumns.APPOINTMENT_UPDATE_DATE.getColName(), ZonedDateTime.now(AppointmentConstants.ZONE_UTC).format(ControllerConstants.TIMESTAMP_FORMAT));
			return dbConnection.updateAppointment(appointmentUpdates, appointmentId);
		}
        return false;
    }

	/**
	 * Updates an existing customer in the database.
	 * @param customerId	-id of the customer to update
	 * @return  			-whether the database updated successfully
	 */
    public boolean updateCustomer(long customerId) {
		if(customerUpdates != null) {
			//add user and date to customer updates for last updated by and last updated
			customerUpdates.put(CustomerColumns.CUSTOMER_UPDATE_BY.getColName(), currentUser.getUsername());
			customerUpdates.put(CustomerColumns.CUSTOMER_LAST_UPDATE.getColName(), ZonedDateTime.now(AppointmentConstants.ZONE_UTC).format(DateTimeFormatter.ofPattern(DBConstants.TIMESTAMP_PATTERN)));
			return dbConnection.updateCustomer(customerUpdates, customerId);
		}
        return false;
    }//updateCustomer

    /**
     * Checks that the credentials entered by the user on the login form are valid.
     * @param username  -username entered in the login form
     * @param password  -password entered in the login form
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
			logLoginAttempt(username, false);
			login.invalidLogin();
		}
    }//validateLoginCredentials

	/**
	 * Instantiates the rest of this application after a successful login attempt.
	 * @param username -username entered on the login form
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
		scenes.add(customerReport = new CustomerReport(this));
		scenes.add(totalsReport = new TotalsReport(this));
        messageAlert = new Alert(Alert.AlertType.NONE);
        currentUser = dbConnection.getUser(username);
        changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
		checkForUpcomingAppointments();
    }//validLogin
}//class Controller