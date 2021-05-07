package controller;

import appointment.Appointment;
import appointment.AppointmentType;
import appointment.Location;
import customer.Customer;
import database.DBConnection;
import javafx.scene.Scene;
import sceneUtils.HeaderPane;
import sceneUtils.SceneCode;
import scenes.*;
import utils.Country;
import utils.Division;
import utils.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Controller
{
    private final Scene appScene;
    private final HeaderPane header;
    private final LoginPage login;
    private final AddEditAppointment editAppt;
    private final AddEditCustomer editCust;
    private final CustomerOverview custOverview;
    private final AppointmentOverview apptOverview;
    private final DBConnection dbConnection;
    private final HashSet<Country> countries;
    private User currentUser;

    /////////////////FOR TESTING/////////////////////////////////////////////////////////////
    private final String testUsername = "test";
    private final int testPassword = hashPassword("test");
    private final Customer testCustomer1 = new Customer(
            0,
            "Nick Warner",
            "111-111-1111",
            "500 Tenshi Ave.",
            "Apt. 101",
            "Salt Lake City",
            "UT",
            "USA",
            "8400",
            1
    );
    private final Appointment testAppointment1 = new Appointment(
            "Appointment.001",
            "Employee.001",
            "mkinkead@wgu.edu",
            "Customer.001",
            "nwarn@yahoo.com",
            "Welcome Meeting",
            AppointmentType.TYPE_ONE,
            "Orientation for new employees",
            LocalDateTime.now(),
            LocalDateTime.now(),
            Location.LONDON
    );
    public ArrayList<Customer> testCustomers = new ArrayList<>();
    public ArrayList<Appointment> testAppointments = new ArrayList<>();
    /////////////////////////////////////////////////////////////////////////////////////////

    public Controller(Scene scn)
    {
        appScene = scn;
        header = new HeaderPane();
        dbConnection = new DBConnection(this);
        countries = initializeCountries();
        login = new LoginPage(this);
        editAppt = new AddEditAppointment(this);
        editCust = new AddEditCustomer(this);
        custOverview = new CustomerOverview(this);
        apptOverview = new AppointmentOverview();
        this.changeScene(SceneCode.LOGIN);

        ///////////FOR TESTING////////////////////////////////////////////////////////////////
        testCustomers.add(testCustomer1);
        currentUser = new User(0, "Gary");
        testAppointments.add(testAppointment1);
        //dbConnection.insertCustomer(testCustomer1, currentUser.getName(), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        //////////////////////////////////////////////////////////////////////////////////////
    }//constructor

    public void changeScene(SceneCode code)
    {
        switch(code)
        {
            case LOGIN:
                loadLogin();
                break;
            case CUSTOMER_OVERVIEW:
                loadCustomerOverview();
                break;
            case APPOINTMENT_OVERVIEW:
                loadAppointmentOverview(null);
                break;
            case EDIT_CUSTOMER:
                loadEditCustomer(null);
                break;
            case EDIT_APPOINTMENT:
                loadEditAppointment();
                break;
            default:
        }//switch
    }//changeScene

    public void createCustomer()
    {
        //TODO: Remember to include date and time created
    }

    //Returns the header used for each page
    public HeaderPane getHeader()
    {
        return header;
    }

    public Collection<Country> getCountries()
    {
        return countries;
    }//getCountries

    //Hashes user-provided password for validation
    private static int hashPassword(CharSequence password)
    {
        //TODO: Make a real hash
        int sum = 0;
        for(int i = 0; i < password.length(); i++)
            sum += password.charAt(i);
        return sum;
    }

    private HashSet<Country> initializeCountries() {
        //Declare local variables
        var allCountries = new HashSet<Country>();
        Country country;
        int countryId;
        //Obtain result set of all countries in Countries table
        ResultSet rs_countries = dbConnection.getCountries();
        ResultSet rs_divisions;
        if(rs_countries != null)
        {
            try
            {
                //Add countries and their divisions to the set of countries
                while(rs_countries.next())
                {
                    countryId = rs_countries.getInt("id");
                    country = new Country(countryId, rs_countries.getString("name"));
                    rs_divisions = dbConnection.getCountryDivisions(countryId);
                    while(rs_divisions.next())
                    {
                        country.addDivision(new Division(rs_divisions.getInt("id"), rs_divisions.getString("name")));
                    }
                    allCountries.add(country);
                }
            } catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        return allCountries;
    }//initializeCountries

    private void loadLogin()
    {
        appScene.setRoot(login);
    }

    private void loadAppointmentOverview(Customer c)
    {

    }

    public void loadAppointmentToEdit(Appointment a)
    {
        if(a != null)
            editAppt.loadAppointmentInfo(a);
        else
            editAppt.loadAppointmentInfo(testAppointment1);
        changeScene(SceneCode.EDIT_APPOINTMENT);
    }

    private void loadCustomerOverview()
    {
        appScene.setRoot(custOverview);
    }

    public void loadCustomerToEdit()
    {
        //editCust.loadCustomerInfo(testCustomer1);
        changeScene(SceneCode.EDIT_CUSTOMER);
    }

    private void loadEditCustomer(Customer c)
    {
        appScene.setRoot(editCust);
    }

    private void loadEditAppointment()
    {
        appScene.setRoot(editAppt);
    }

    private void logLoginAttempt(String username, boolean valid)
    {

    }

    public String nextAppointmentId()
    {
        return "A1";
    }

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
        changeScene(SceneCode.CUSTOMER_OVERVIEW);
    }
}
