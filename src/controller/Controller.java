package controller;

import appointment.Appointment;
import customer.Customer;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import sceneUtils.HeaderPane;
import sceneUtils.SceneCode;
import scenes.*;
import utils.Country;
import utils.User;
import java.util.ArrayList;
import java.util.Collection;

public class Controller
{
    //final attributes
    private final Scene appScene;
    private final HeaderPane header;
    private final LoginPage login;
    private final AddEditAppointment editAppt;
    private final AddEditCustomer editCust;
    private final CustomerOverview custOverview;
    private final AppointmentOverview apptOverview;
    private final DBConnection dbConnection;

    //non-final attributes
    private User currentUser;
    private Alert confirmationAlert;
    private int nextCustomerId;
    private int nextAppointmentId;
    private ObservableList<Country> countries;
	private ObservableList<Customer> customers;

    /////////////////FOR TESTING/////////////////////////////////////////////////////////////
    private final String testUsername = "test";
    private final int testPassword = hashPassword("test");
    public ArrayList<Customer> testCustomers = new ArrayList<>();
    public ArrayList<Appointment> testAppointments = new ArrayList<>();
    /////////////////////////////////////////////////////////////////////////////////////////

    public Controller(Scene scn)
    {
        appScene = scn;
        header = new HeaderPane();
        dbConnection = new DBConnection(this);
        login = new LoginPage(this);
		customers = FXCollections.ObservableArrayList(dbConnection.getCustomers());
        //TODO: present login page but wait for credential validation before instantiating db connection and other scenes
        countries = FXCollections.observableArrayList(dbConnection.getCountries());
        editAppt = new AddEditAppointment(this);
        editCust = new AddEditCustomer(this);
        custOverview = new CustomerOverview(this);
        apptOverview = new AppointmentOverview(this);
        confirmationAlert = new Alert(Alert.AlertType.NONE);
        this.changeScene(SceneCode.LOGIN, null);
        //TODO: check for appointments starting within fifteen minutes

        ///////////FOR TESTING////////////////////////////////////////////////////////////////
        //testCustomers.add(testCustomer1);
        currentUser = new User(0, "Gary");
        //testAppointments.add(testAppointment1);
        //dbConnection.insertCustomer(testCustomer1, currentUser.getName(), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        //////////////////////////////////////////////////////////////////////////////////////
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
                    apptOverview.loadCustomerAppointmentInformation(c, FXCollections.observableArrayList(dbConnection.getCustomerAppointments(c.getCustomerId())));
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

    public boolean addCustomer(Customer c)
    {
        //TODO: Remember to include date and time created plus creator
		return false;
    }//addCustomer

    public void deleteAppointment(Appointment a)
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
		if(DBConnection.deleteCustomer(c.getCustomerId()))
		{
			customers.remove(c);
			return true;
		} else
		{
			confirmationAlert.setAlertType(Alert.AlertType.ERROR);
			confirmationAlert.setContentText("There was an error trying to delete customer " + c.getCustomerId + ".");
			confirmationAlert.showAndWait();
			return false;
		}
    }//deleteCustomer
	
	public boolean editAppointment()
	{
		return false;
	}//editAppointment
	
	public boolean editCustomer()
	{
		return false;
	}//editCusotmer

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
    public Alert getConfirmationAlert()
    {
        return confirmationAlert;
    }//getConfirmationAlert

    /**
     *
     * @param countryId
     * @return
     */
    public Country getCountryById(int countryId)
    {
        for(Country c : countries)
        {
            if(c.getCountryId() == countryId)
                return c;
        }
        return null;
    }//getCountry

    public ObservableList<Country> getCountries()
    {
        return countries;
    }//getCountryCombo

    public ObservableList<Customer> getCustomers()
    {
        return customers;
    }//getCustomers

    public ObservableList<Appointment> getCustomerAppointments(Customer c)
    {
        return FXCollections.observableArrayList(dbConnection.getCustomerAppointments(c.getCustomerId()));
    }//getCustomerAppointments

    public int getNextAppointmentId()
    {
        return nextAppointmentId;
    }

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

    private void logLoginAttempt(String username, boolean valid)
    {}
	
	private boolean overlapsExistingAppointment(Appointment a)
	{
		return false;
	}//overlapsExistingAppointmet

    public void updateCustomer()
    {
        //TODO: remember to include date and time last updated
    }

    public void validateLoginCredentials(String username, CharSequence password)
    {
        if(username.equals(testUsername))
        {
            int hashedPassword = hashPassword(password);
            if(hashedPassword == testPassword)
            {
                hashedPassword = -1;
                logLoginAttempt(username, true);
                validLogin(username);
                return;
            }
        }
        logLoginAttempt(username, false);
        login.invalidLogin();
    }//validateLoginCredentials

    private void validLogin(String username)
    {
        login.clearAll();
        changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
    }
}//class Controller