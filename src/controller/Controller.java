package controller;

import appointment.Appointment;
import appointment.AppointmentType;
import appointment.Location;
import customer.Customer;
import javafx.scene.Scene;
import sceneUtils.HeaderPane;
import sceneUtils.SceneCode;
import scenes.*;

import java.util.Date;

public class Controller
{
    private final Scene appScene;
    private final HeaderPane header;
    private final LoginPage login;
    private final AddEditAppointment editAppt;
    private final AddEditCustomer editCust;
    private final CustomerOverview custOverview;
    private final AppointmentOverview apptOverview;

    /////////////////FOR TESTING/////////////////////////////////////////////////////////////
    private final String testUsername = "test";
    private final int testPassword = hashPassword("test");
    private Customer testCustomer1 = new Customer(
            "C1",
            "Nicholas",
            "Warner",
            "8012314827",
            "130 S 1300 E",
            "Apt. 605",
            "Salt Lake City",
            "USA",
            "Utah",
            "84102"
    );
    private final Appointment testAppointment1 = new Appointment(
            "A1",
            "E1",
            "C1",
            "Welcome Meeting",
            AppointmentType.TYPE_ONE,
            "Orientation for new employees",
            new Date(2021, 10, 1),
            new Date(2021, 10, 1),
            Location.LONDON
    );
    /////////////////////////////////////////////////////////////////////////////////////////

    public Controller(Scene scn)
    {
        appScene = scn;
        header = new HeaderPane();
        login = new LoginPage(this);
        editAppt = new AddEditAppointment(this);
        editCust = new AddEditCustomer(this);
        custOverview = new CustomerOverview(this);
        apptOverview = new AppointmentOverview();
        this.changeScene(SceneCode.LOGIN);
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

    //Hashes user-provided password for validation
    private static int hashPassword(CharSequence password)
    {
        //TODO: Make a real hash
        int sum = 0;
        for(int i = 0; i < password.length(); i++)
            sum += password.charAt(i);
        return sum;
    }

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
        editCust.loadCustomerInfo(testCustomer1);
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
