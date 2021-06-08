package scenes;

import appointment.Appointment;
import controller.Controller;
import customer.Customer;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sceneUtils.ContactBox;
import sceneUtils.CustomerHeader;
import sceneUtils.DateBox;

public class AddEditAppointment extends BorderPane
{
    private Controller	controller;
    private Pane 		header;            	private Label			sceneLabel;
    private Label 		apptIdLabel;      	private TextField		apptIdField;
    private Label 		apptTitleLabel;   	private TextField		apptTitleField;
    private Label 		apptTypeLabel;    	private ComboBox		apptTypeCombo;
    private ContactBox 	contactBox;  		private CustomerHeader	customerInfo;
    private Label 		startLabel;       	private TextField 		startTimeField;       
	private DateBox 	startDateBox;		private DateBox 		endDateBox;
    private Label 		endLabel;         	private TextField 		endTimeField;
    private Label 		descriptionLabel; 	private TextArea 		descriptionArea;
    private Button 		scheduleButton;		private Button 			cancelButton;

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
		contactBox = new ContactBox(this.controller.getContacts());
        startLabel = new Label("Start Time");
        startDateBox = new DateBox();
        startTimeField = new TextField("10:00");
        endLabel = new Label("End Time");
        endDateBox = new DateBox();
        endTimeField = new TextField("22:00");
        descriptionLabel = new Label("Description");
        descriptionArea = new TextArea();
        scheduleButton = new Button("Submit");
        cancelButton = new Button("Cancel");

        //set initial states for scene elements
        customerContactField.setDisable(true);              //customer information fields are disabled
        customerIdField.setDisable(true);
        apptIdField.setDisable(true);                       //appoint id is auto-generated

        //Add event handlers to scene elements

        //add scene elements to container
        GridPane contentPane = new GridPane();
        contentPane.addRow(0, sceneLabel);
        contentPane.addRow(1, customerIdLabel, customerIdField, customerContactField);
        contentPane.addRow(2, apptIdLabel, apptIdField);
        contentPane.addRow(3, apptTitleLabel, apptTitleField);
        contentPane.addRow(4, apptTypeLabel, apptTypeCombo);
        contentPane.addRow(5, contactBox);
        contentPane.addRow(6, startLabel, startDateBox, startTimeField);
        contentPane.addRow(7, endLabel, endDateBox, endTimeField);
        contentPane.addRow(8, descriptionLabel, descriptionArea);
        contentPane.addRow(9, scheduleButton, cancelButton);
        this.setCenter(contentPane);
        this.setTop(header);
    }//constructor

    public void loadAppointmentInfo(Appointment a) {
        customerIdField.setText("" + a.getCustomerId());
        customerContactField.setText("xxx-xxx-xxxx");
        apptTitleField.setText(a.getTitle());
    }//loadAppointmentInfo

    public void loadCustomerInfo(Customer c) {
        customerIdField.setText(c.getCustomerId() + "");
        customerContactField.setText(c.getPhone());
    }//loadCustomerInfo

    public void loadNewAppointment() {

    }//loadNewAppointment
}