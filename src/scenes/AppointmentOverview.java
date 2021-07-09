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

public class AppointmentOverview  extends BorderPane implements Refreshable {
    private Controller controller;              private Label sceneLabel;
    private CustomerHeader customerInfo;	    private AppointmentOverviewTable appointmentsTable;
    private Button scheduleButton;              private Button editButton;
    private Button deleteButton;                private Button returnButton;
    private Customer customerToDisplay;

    public AppointmentOverview(Controller controller) {
        //Instantiate scene elements
        this.controller 	= 	controller;
        sceneLabel 			= 	new Label("");
        customerInfo        =   new CustomerHeader();
        appointmentsTable 	= 	new AppointmentOverviewTable(this.controller.getResourceBundle());
        scheduleButton 		= 	new Button("");
        editButton 			= 	new Button("");
        deleteButton 		= 	new Button("");
        returnButton 		= 	new Button("");
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
            if(controller.displayConfirmationAlert("Confirm Delete", "Are you sure you would " +
                    "like to delete this appointment?")) {
                Appointment appt = appointmentsTable.getSelectedAppointment();
                if(controller.deleteAppointment(appt)) {
                    customerInfo.getCustomer().removeAppointment(appt);
                }
                appointmentsTable.refresh();
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

        //Add scene elements to containers
        var headerPane = new GridPane();
        headerPane.add(sceneLabel, 0, 0);
        headerPane.add(customerInfo, 0, 1);
        var buttonPane = new HBox(scheduleButton, editButton, deleteButton);
        var footerPane = new HBox(returnButton);
        var tablePane = new GridPane();
        tablePane.add(appointmentsTable, 0, 0);
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
        sceneLabel.getStyleClass().add("scene-label");
    }//constructor

    /**
	 *
     */
    private void clear() {
        customerInfo.clear();
        deleteButton.setDisable(true);
        editButton.setDisable(true);
    }//clear

	/**
	 *
	 */
	public void loadOverview(Customer c) {
		if(c != null) {
		    customerToDisplay = c;
		    customerInfo.setCustomer(customerToDisplay);
            if(c.getAppointments() == null)
                c.setAppointments(controller.getCustomerAppointments(c));
            appointmentsTable.setAppointments(c.getAppointments());
        }
	}//loadOverview

    public Customer getCustomerToDisplay() {
        return customerToDisplay;
    }//getCustomerToDisplay

    public void refresh(ResourceBundle rb) {
        setElementText();
        appointmentsTable.setResourceBundle(rb);
    }

    private void setElementText() {
	    sceneLabel.setText(controller.getResourceBundle().getString("appointment_overview"));
	    returnButton.setText(controller.getResourceBundle().getString("return"));
	    scheduleButton.setText(controller.getResourceBundle().getString("schedule_appointment"));
        deleteButton.setText(controller.getResourceBundle().getString("delete") + " " +
                controller.getResourceBundle().getString("appointment"));
        editButton.setText(controller.getResourceBundle().getString("update_appointment"));
    }
}//class AppointmentOverview