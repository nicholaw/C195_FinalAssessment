package scenes;

import appointment.Appointment;
import appointment.Location;
import controller.Controller;
import customer.Customer;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sceneUtils.DateBox;

public class AddEditAppointment extends BorderPane
{
    private Controller controller;
    private Pane header;            private Label sceneLabel;
    private Label apptIdLabel;      private TextField apptIdField;
    private Label apptTitleLabel;   private TextField apptTitleField;
    private Label apptTypeLabel;    private ComboBox apptTypeCombo;
    private Label customerIdLabel;  private TextField customerIdField;      private TextField customerContactField;
    private Label contactIdLabel;   private ComboBox contactIdCombo;        private TextField contactContactField;
    private Label startLabel;       private TextField startTimeField;       private DateBox startDateBox;
    private Label endLabel;         private TextField endTimeField;         private DateBox endDateBox;
    private Label locationLabel;    private ComboBox locationCombo;
    private Label descriptionLabel; private TextArea descriptionArea;
    private Button scheduleButton;  private Button cancelButton;
    private Alert confirmCancel;

    public AddEditAppointment(Controller controller)
    {
        this.controller = controller;

        //Instantiate scene elements
        header = this.controller.getHeader();
        sceneLabel = new Label("Add/Edit Appointment");
        apptIdLabel = new Label("Id");
        apptIdField = new TextField("");
        apptTitleLabel = new Label("Title");
        apptTitleField = new TextField("");
        apptTypeLabel = new Label("Type");
        apptTypeCombo = new ComboBox<String>();
        customerIdLabel = new Label("Customer Id");
        customerIdField = new TextField("");
        customerContactField = new TextField("");
        contactIdLabel = new Label("Contact");
        contactIdCombo = new ComboBox<String>();
        contactContactField = new TextField("");
        startLabel = new Label("Start Time");
        startDateBox = new DateBox();
        startTimeField = new TextField("10:00");
        endLabel = new Label("End Time");
        endDateBox = new DateBox();
        endTimeField = new TextField("22:00");
        locationLabel = new Label("Location");
        locationCombo = new ComboBox<Location>();
        descriptionLabel = new Label("Description");
        descriptionArea = new TextArea();
        scheduleButton = new Button("Submit");
        cancelButton = new Button("Cancel");

        //set initial states for scene elements
        customerContactField.setDisable(true);              //customer information fields are disabled
        customerIdField.setDisable(true);
        apptIdField.setDisable(true);                       //appoint id is auto-generated
        contactContactField.setDisable(true);               //contact info auto-filled based on selection
        for(Appointment a : controller.testAppointments)    //Add employee contacts
        {
            contactIdCombo.getItems().add(a.getContactId());
        }
        //contactIdCombo.setValue(contactIdCombo.getItems().get(0));
        locationCombo.getItems().addAll(Location.LONDON, Location.NEW_YORK, Location.QUEBEC);
        locationCombo.setValue(locationCombo.getItems().get(0));
        apptTypeCombo.getItems().addAll("Type 1", "Type 2", "Type 3");
        apptTypeCombo.setValue(apptTypeCombo.getItems().get(0));

        //Add event handlers to scene elements

        //add scene elements to container
        GridPane contentPane = new GridPane();
        contentPane.addRow(0, sceneLabel);
        contentPane.addRow(1, customerIdLabel, customerIdField, customerContactField);
        contentPane.addRow(2, apptIdLabel, apptIdField);
        contentPane.addRow(3, apptTitleLabel, apptTitleField);
        contentPane.addRow(4, apptTypeLabel, apptTypeCombo);
        contentPane.addRow(5, contactIdLabel, contactIdCombo, contactContactField);
        contentPane.addRow(6, startLabel, startDateBox, startTimeField);
        contentPane.addRow(7, endLabel, endDateBox, endTimeField);
        contentPane.addRow(8, locationLabel, locationCombo);
        contentPane.addRow(9, descriptionLabel, descriptionArea);
        contentPane.addRow(10, scheduleButton, cancelButton);
        this.setCenter(contentPane);
        this.setTop(header);
    }

    public void loadAppointmentInfo(Appointment a)
    {
        customerIdField.setText("" + a.getCustomerId());
        customerContactField.setText("xxx-xxx-xxxx");
        apptTitleField.setText(a.getTitle());
    }

    public void loadCustomerInfo(Customer c)
    {
        customerIdField.setText(c.getCustomerId() + "");
        customerContactField.setText(c.getPhoneNum());
    }

    public void loadNewAppointment()
    {

    }
}