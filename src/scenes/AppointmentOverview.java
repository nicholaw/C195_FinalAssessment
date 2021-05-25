package scenes;

import appointment.Appointment;
import controller.Controller;
import customer.Customer;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import sceneUtils.SceneCode;
import java.time.LocalDateTime;

public class AppointmentOverview  extends BorderPane
{
    private Controller controller;
    private Label sceneLabel;               private Pane header;
    private Label customerLabel;            private TextField customerIdField;		private TextField customerNameField;
    private TextField customerPhoneField;	private TableView<Appointment> appointmentsTable;
    private Button scheduleButton;          private Button editButton;
    private Button deleteButton;            private Button returnButton;
    private Alert confirmDelete;			private Appointment selectedAppointment;

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
        confirmDelete		=	this.controller.getConfirmationAlert();
        selectedAppointment	=	null;

        //Instantiate table columns
        TableColumn<Appointment, Integer> 		idCol 		= 	new TableColumn<>("ID");
        TableColumn<Appointment, String> 		titleCol 	= 	new TableColumn<>("Title");
        TableColumn<Appointment, String> 		typeCol 	= 	new TableColumn<>("Type");
        TableColumn<Appointment, LocalDateTime>	startCol	= 	new TableColumn<>("Start");
        TableColumn<Appointment, LocalDateTime>	endCol 		= 	new TableColumn<>("End");
        TableColumn<Appointment, String> 		descCol 	= 	new TableColumn<>("Description");

        //Set initial states for scene elements
        customerIdField.setDisable(true);
        customerNameField.setDisable(true);
        customerPhoneField.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        appointmentsTable.getColumns().setAll(idCol, titleCol, typeCol, startCol, endCol, descCol);

        //Add event listeners to buttons
        scheduleButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.EDIT_APPOINTMENT);
        });
        editButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.EDIT_APPOINTMENT);
        });
        deleteButton.setOnAction(event ->
        {
            confirmDelete.setAlertType(Alert.AlertType.CONFIRMATION);
            confirmDelete.setContentText("Are you sure you would like to delete this appointment?");
            confirmDelete.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> controller.deleteAppointment(selectedAppointment));
        });
        returnButton.setOnAction(event ->
        {
            this.clear();
            controller.changeScene(SceneCode.CUSTOMER_OVERVIEW);
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

    /*
     */
    private void clear()
    {
        customerIdField.setText("");
        customerNameField.setText("");
        customerPhoneField.setText("");
        selectedAppointment = null;
        appointmentsTable.getItems().removeAll(appointmentsTable.getItems());
        deleteButton.setDisable(true);
        editButton.setDisable(true);
    }//clear


    public void loadCustomerAppointmentInformation(Customer c, ObservableList<Appointment> list)
    {
        if(c != null)
        {
            customerIdField.setText("" + c.getCustomerId());
            customerNameField.setText(c.getName());
            customerPhoneField.setText("" + c.getPhoneNum());
            appointmentsTable.setItems(list);
        }
    }//loadCustomerAppointmentInformation
}//class AppointmentOverview
