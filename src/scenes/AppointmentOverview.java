package scenes;

import appointment.Appointment;
import controller.Controller;
import customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import sceneUtils.SceneCode;
import sceneUtils.CustomerHeader;
import java.util.Collection;

public class AppointmentOverview  extends BorderPane {
    private Controller controller;
    private Label sceneLabel;               //private Pane header;
    private CustomerHeader customerInfo;	private TableView<Appointment> appointmentsTable;
    private Button scheduleButton;          private Button editButton;
    private Button deleteButton;            private Button returnButton;
    private Appointment selectedAppointment;
    private Customer customerToDisplay;

    public AppointmentOverview(Controller controller) {
        //Instantiate scene elements
        this.controller 	= 	controller;
        //this.header 		= 	this.controller.getHeader();
        sceneLabel 			= 	new Label("Appointment Overview");
        customerInfo        =   new CustomerHeader();
        appointmentsTable 	= 	new TableView<>();
        scheduleButton 		= 	new Button("Schedule Appointment");
        editButton 			= 	new Button("Edit Appointment");
        deleteButton 		= 	new Button("Delete Appointment");
        returnButton 		= 	new Button("Return");
        selectedAppointment	=	null;

        //Instantiate table columns
        TableColumn<Appointment, Integer> 	idCol 			= 	new TableColumn<>("ID");
        TableColumn<Appointment, String> 	titleCol 		= 	new TableColumn<>("Title");
        TableColumn<Appointment, String> 	typeCol 		= 	new TableColumn<>("Type");
        TableColumn<Appointment, String>	startDateCol	= 	new TableColumn<>("Start Date");
		TableColumn<Appointment, String>	endDateCol		= 	new TableColumn<>("End Date");
		TableColumn<Appointment, String>	startTimeCol	= 	new TableColumn<>("Start Time");
		TableColumn<Appointment, String>	endTimeCol		= 	new TableColumn<>("End Time");
        TableColumn<Appointment, String> 	descCol 		= 	new TableColumn<>("Description");
		idCol.setCellValueFactory		(new PropertyValueFactory<>("appointmentId"));
		titleCol.setCellValueFactory	(new PropertyValueFactory<>("title"));
		typeCol.setCellValueFactory		(new PropertyValueFactory<>("type"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDateCol.setCellValueFactory	(new PropertyValueFactory<>("endDate"));
		startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		endTimeCol.setCellValueFactory	(new PropertyValueFactory<>("endTime"));
		descCol.setCellValueFactory		(new PropertyValueFactory<>("description"));
		appointmentsTable.getColumns().setAll(idCol, titleCol, typeCol, startDateCol, endDateCol, startTimeCol, endTimeCol, descCol);

        //Set initial states for scene elements
        deleteButton.setDisable(true);
        editButton.setDisable(true);

        //Add event listeners to buttons
        scheduleButton.setOnAction(event -> {
            controller.changeScene(SceneCode.EDIT_APPOINTMENT, null);
        });
        editButton.setOnAction(event -> {
            controller.changeScene(SceneCode.EDIT_APPOINTMENT, selectedAppointment);
        });
        deleteButton.setOnAction(event -> {
            if(controller.displayConfirmationAlert("Confirm Delete", "Are you sure you would " +
                    "like to delete this appointment?")) {
                if(controller.deleteAppointment(selectedAppointment)) {
                    customerInfo.getCustomer().removeAppointment(selectedAppointment);
                    selectedAppointment = null;
                }
                appointmentsTable.refresh();
            }
        });
        returnButton.setOnAction(event -> {
            this.clear();
            controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
        });
        appointmentsTable.setOnMouseClicked(event -> {
            Object obj = appointmentsTable.getSelectionModel().getSelectedItem();
            if(obj != null) {
                if(obj instanceof Appointment) {
                    selectedAppointment = (Appointment)obj;
                    deleteButton.setDisable(false);
                    editButton.setDisable(false);
                }
            }
        });

        //Add scene elements to containers
        HBox leftButtonPane = new HBox(scheduleButton, editButton, deleteButton);
        HBox rightButtonPane = new HBox(returnButton);
        BorderPane buttonPane = new BorderPane();
        buttonPane.setLeft(leftButtonPane);
        buttonPane.setRight(rightButtonPane);
        GridPane contentPane = new GridPane();
        contentPane.addRow(0, sceneLabel);
        contentPane.addRow(1, customerInfo);
        contentPane.addRow(2, appointmentsTable);
        contentPane.addRow(3, buttonPane);
        this.setTop(this.controller.getHeader());
        this.setCenter(contentPane);
    }//constructor

    /**
	 *
     */
    private void clear() {
        customerInfo.clear();
        selectedAppointment = null;
        appointmentsTable.getItems().removeAll(appointmentsTable.getItems());
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
            appointmentsTable.setItems(c.getAppointments());
            appointmentsTable.refresh();
        }
	}//loadOverview

    public Customer getCustomerToDisplay() {
        return customerToDisplay;
    }//getCustomerToDisplay
}//class AppointmentOverview