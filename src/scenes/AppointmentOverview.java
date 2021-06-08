package scenes;

import appointment.Appointment;
import controller.Controller;
import customer.Customer;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import sceneUtils.SceneCode;
import java.time.LocalDateTime;
import java.util.Collection;

public class AppointmentOverview  extends BorderPane
{
    private Controller controller;
    private Label sceneLabel;               private Pane header;
    private Label customerLabel;            private TextField customerIdField;		private TextField customerNameField;
    private TextField customerPhoneField;	private TableView<Appointment> appointmentsTable;
    private Button scheduleButton;          private Button editButton;
    private Button deleteButton;            private Button returnButton;
    private Appointment selectedAppointment;private ObservableList<Appointment> appointments;

    public AppointmentOverview(Controller controller)
    {
        //Instantiate scene elements
        this.controller 	= 	controller;
        this.header 		= 	this.controller.getHeader();
        sceneLabel 			= 	new Label("Appointment Overview");
        customerLabel 		= 	new Label("Customer:");
        customerIdField 	= 	new TextField("");
        customerNameField 	= 	new TextField("");
        customerPhoneField	= 	new TextField("");
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
		descCol.setCellValueFactory		(new PropertyValueFactory<>("descrition"));
		appointmentsTable.getColumns().setAll(idCol, titleCol, typeCol, startDateCol, endDateCol, startTimeCol, endTimeCol, descCol);

        //Set initial states for scene elements
        customerIdField.setDisable(true);
        customerNameField.setDisable(true);
        customerPhoneField.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);

        //Add event listeners to buttons
        scheduleButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.EDIT_APPOINTMENT, null);
        });
        editButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.EDIT_APPOINTMENT, selectedAppointment);
        });
        deleteButton.setOnAction(event ->
        {
            controller.getMessageAlert().setAlertType(Alert.AlertType.CONFIRMATION);
            controller.getMessageAlert().setContentText("Are you sure you would like to delete this appointment?");
            controller.getMessageAlert().showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> controller.deleteAppointment(selectedAppointment));
        });
        returnButton.setOnAction(event ->
        {
            this.clear();
            controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
        });
        appointmentsTable.setOnMouseClicked(event ->
        {
            if(selectedAppointment != null)
            {
                deleteButton.setDisable(false);
                editButton.setDisable(false);
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
        contentPane.addRow(1, customerLabel, customerIdField, customerNameField, customerPhoneField);
        contentPane.addRow(2, appointmentsTable);
        contentPane.addRow(3, buttonPane);
        this.setTop(header);
        this.setCenter(contentPane);
    }//constructor

    /**
	 *
     */
    private void clear() {
        customerIdField.setText("");
        customerNameField.setText("");
        customerPhoneField.setText("");
        selectedAppointment = null;
        appointmentsTable.getItems().removeAll(appointmentsTable.getItems());
        deleteButton.setDisable(true);
        editButton.setDisable(true);
    }//clear
	
	/**
	 *
	 */
	public Collection<Appointment> getAppointments() {
		return appointments;
	}//getAppointments

	/**
	 *
	 */
	public void loadOverview(Customer c, ObservableList<Appointment> appointments) {
		loadCustomerInformation(c);
		loadAppointmentInformation(appointments);
	}//loadOverview
	
	/**
	 *
	 */
	private void loadAppointmentInformation(ObservableList<Appointment> appointments) {
		if(appointments != null) {
			this.appointments = appointments;
			appointmentsTable.setItems(this.appointments);
		}
	}//loadAppointmentInformation

	/**
	 *
	 */
    private void loadCustomerInformation(Customer c) {
        if(c != null) {
            customerIdField.setText("" + c.getCustomerId());
            customerNameField.setText(c.getName());
            customerPhoneField.setText("" + c.getPhone());
        }
    }//loadCustomerAppointmentInformation
}//class AppointmentOverview