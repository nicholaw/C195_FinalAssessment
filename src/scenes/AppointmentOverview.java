package scenes;

import appointment.Appointment;
import controller.Controller;
import customer.Customer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sceneUtils.AppointmentOverviewTable;
import sceneUtils.Refreshable;
import sceneUtils.SceneCode;
import sceneUtils.CustomerHeader;
import java.util.ResourceBundle;

/**
 * Scene which displays an overview of scheduled appointments for a given customer. Scene contains
 * buttons which allow user to edit or delete a displayed appointment or schedule a new one.
 */
public class AppointmentOverview  extends BorderPane implements Refreshable {
    private Controller controller;              private Label sceneLabel;
    private CustomerHeader customerInfo;	    private AppointmentOverviewTable appointmentsTable;
    private Button scheduleButton;              private Button editButton;
    private Button deleteButton;                private Button returnButton;
    private Customer customerToDisplay;         private Button reportsButton;

    /**
     * Constructs this scene.
     * @param controller -the application controller
     */
    public AppointmentOverview(Controller controller) {
        //Instantiate scene elements
        this.controller 	= 	controller;
        sceneLabel 			= 	new Label("");
        customerInfo        =   new CustomerHeader(this.controller.getResourceBundle().getString("customer"));
        appointmentsTable 	= 	new AppointmentOverviewTable(this.controller.getResourceBundle());
        scheduleButton 		= 	new Button("");
        editButton 			= 	new Button("");
        deleteButton 		= 	new Button("");
        returnButton 		= 	new Button("");
        reportsButton       =   new Button("");
        setElementText();

        //Set initial states for scene elements
        deleteButton.setDisable(true);
        editButton.setDisable(true);

        //Add event listeners to buttons
        scheduleButton.setOnAction(event -> {
            controller.changeScene(SceneCode.EDIT_APPOINTMENT, null);
        });
        editButton.setOnAction(event -> {
            controller.changeScene(SceneCode.EDIT_APPOINTMENT, appointmentsTable.getSelectedAppointment());
        });
        deleteButton.setOnAction(event -> {
            if(controller.displayConfirmationAlert(controller.getResourceBundle().getString("confirm_delete_title"),
                    controller.getResourceBundle().getString("confirm_delete_appointment"))) {
                Appointment appt = appointmentsTable.getSelectedAppointment();
                if(controller.deleteAppointment(appt)) {
                    customerInfo.getCustomer().removeAppointment(appt);
                    appointmentsTable.refreshTable();
                    controller.displayInformationalAlert(controller.getResourceBundle().getString("successful_delete_title"),
                            controller.getResourceBundle().getString("successful_delete_appointment1") +
                            " " + appt.getAppointmentId() + " " +
                            controller.getResourceBundle().getString("successful_delete_appointment2"));
                }
            }
        });
        returnButton.setOnAction(event -> {
            this.clear();
            controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
        });
        appointmentsTable.setOnMouseClicked(event -> {
            if(appointmentsTable.getSelectedAppointment() != null) {
                deleteButton.setDisable(false);
                editButton.setDisable(false);
            }
        });
        reportsButton.setOnAction(event -> {
            controller.changeScene(SceneCode.REPORT_OVERVIEW, customerInfo.getCustomer());
            this.clear();
        });

        //Add scene elements to containers
        var headerPane = new GridPane();
        headerPane.add(sceneLabel, 0, 0);
        headerPane.add(customerInfo, 0, 1);
        var buttonPane = new HBox(scheduleButton, editButton, deleteButton);
        var footerPane = new HBox(reportsButton, returnButton);
        var tablePane = new GridPane();
        tablePane.add(appointmentsTable.getContentPane(), 0, 0);
        tablePane.add(buttonPane, 0, 1);
        var contentPane = new GridPane();
        contentPane.add(headerPane, 0, 0);
        contentPane.add(tablePane, 0, 1);
        contentPane.add(footerPane, 0, 2);
        this.setCenter(contentPane);

        //Style elements
        contentPane.setAlignment(Pos.CENTER);
        footerPane.setAlignment(Pos.CENTER);
        contentPane.setVgap(20);
        tablePane.setVgap(10);
        buttonPane.setSpacing(10);
        footerPane.setSpacing(10);
        sceneLabel.getStyleClass().add("scene-label");
    }//constructor

    /**
	 * Clears the controls for displaying customer information and disables and relevant buttons.
     */
    private void clear() {
        customerInfo.clear();
        deleteButton.setDisable(true);
        editButton.setDisable(true);
    }//clear

	/**
	 * Displays the given customers information and sets the TableView's items to the list
     * of appointments scheduled by the given customer.
     * @param c -the customer to display
	 */
	public void loadOverview(Customer c) {
		if(c != null) {
		    customerToDisplay = c;
		    customerInfo.setCustomer(customerToDisplay);
            if(c.getAppointments() == null)
                c.setAppointments(controller.getCustomerAppointments(c));
            appointmentsTable.setParentCustomer(c);
        }
	}//loadOverview

    /**
     * Returns the customer whose appointment's are on display.
     * @return -the customer being displayed
     */
    public Customer getCustomerToDisplay() {
        return customerToDisplay;
    }//getCustomerToDisplay

    @Override
    public void refresh(ResourceBundle rb) {
        setElementText();
        customerInfo.setText(rb.getString("customer"));
        appointmentsTable.setResourceBundle(rb);
    }//refresh

    /**
     * Sets the text for each label and button on this scene based on the user-selected language.
     */
    private void setElementText() {
	    sceneLabel.setText(controller.getString("appointment_overview"));
	    returnButton.setText(controller.getString("return"));
	    scheduleButton.setText(controller.getString("schedule_appointment"));
        deleteButton.setText(controller.getString("delete") + " " + controller.getString("appointment"));
        editButton.setText(controller.getString("update_appointment"));
        reportsButton.setText(controller.getString("customer_reports"));
    }//setElementText
}//class AppointmentOverview