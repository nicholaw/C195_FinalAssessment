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
import sceneUtils.TimeBox;

public class AddEditAppointment extends BorderPane
{
    private Controller	controller;			private boolean			newAppointment;
    private Pane 		header;            	private Label			sceneLabel;
    private Label 		apptIdLabel;      	private TextField		apptIdField;
    private Label 		apptTitleLabel;   	private TextField		apptTitleField;
    private Label 		apptTypeLabel;    	private ComboBox		apptTypeCombo;
    private ContactBox 	contactBox;  		private CustomerHeader	customerInfo;
	private TimeBox		startTimeBox;		private TimeBox			endTimeBox;
    private Label 		descriptionLabel; 	private TextArea 		descriptionArea;
    private Button 		submitButton;		private Button 			cancelButton;
	private Label		titleErrorLabel;	private Label			timeErrorLabel;

    public AddEditAppointment(Controller controller)
    {
        this.controller = controller;

        //Instantiate scene elements
        header 				= this.controller.getHeader();
        sceneLabel 			= new Label("Schedule Appointment");
		customerInfo		= controller.getCustomerInfo();
        apptIdLabel 		= new Label("Id");
        apptIdField 		= new TextField("" + controller.getNextAppointmentId());
        apptTitleLabel 		= new Label("Title");
        apptTitleField 		= new TextField("");
        apptTypeLabel 		= new Label("Type");
        apptTypeCombo 	 	= new ComboBox<String>();
		startTimeBox		= new TimeBox("Start:");
		endTimeBox			= new TimeBox("End:");
		contactBox 			= new ContactBox(this.controller.getContacts());
        descriptionLabel	= new Label("Description");
        descriptionArea 	= new TextArea("");
        submitButton 	 	= new Button("Schedule");
        cancelButton 		= new Button("Cancel");

        //set initial states for scene elements
        customerContactField.setDisable(true);              //customer information fields are disabled
        customerIdField.setDisable(true);
        apptIdField.setDisable(true);                       //appoint id is auto-generated
		newAppointment = true;

        //Add event handlers to scene elements
		submitButton.setOnAction(event -> {
			if(this.validateForm()) {
				if(newAppointment) {
					controller.addAppointment(/**/);
				} else {
					processChanges();
					controller.updateAppointment(Integer.parseInt(apptIdField.getText()));
				}
				clear();
				controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW);
			}
		});
		cancelButton.setOnAction(event -> {
			clear();
			controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW);
		});

        //add scene elements to container
        GridPane contentPane = new GridPane();
        contentPane.addRow(0, sceneLabel);
        contentPane.addRow(1, customerIdLabel, customerIdField, customerContactField);
        contentPane.addRow(2, apptIdLabel, apptIdField);
        contentPane.addRow(3, apptTitleLabel, apptTitleField);
        contentPane.addRow(4, apptTypeLabel, apptTypeCombo);
		contentPane.addRow(5, startTimeBox);
		contentPane.addRow(6, endTimeBox);
        contentPane.addRow(7, contactBox);
        contentPane.addRow(8, descriptionLabel, descriptionArea);
        contentPane.addRow(9, scheduleButton, cancelButton);
        this.setCenter(contentPane);
        this.setTop(header);
    }//constructor
	
	public void clear() {
		apptIdField.setText("");
		apptTitleField.setText("");
		this.clearCombo(apptTypeCombo);
	}//clear
	
	/**
	 *
	 */
	private void clearCombo(ComboBox box) {
		if(box != null) {
			if(box.getItems().size() > 0) {
				box.setValue(box.getItems().get(0));
				return;
			}
		}
		box.setValue(null);
	}//clearCombo
	
	private void clearErrors() {
		titleErrorLabel.setText("");
		timeErrorLabel.setText("");
	}//clearErrors
	
	/**
	 *
	 */
	private void flag(AppointmentFieldCode code, String message) {
		switch(code) {
			case TITLE_FIELD :
				titleErrorLabel.setText(message);
				break;
			case DATETIME_BOX :
				timeErrorLabel.setText(message);
				break;
			default :
				controller.getMessageAlert().setAlertType(Alert.AlertType.ERROR);
				controller.getMessageAlert().setContentText("An unknown validation error occurred");
				controller.getMessageAlert().setTitle("Unknown Error");
				controller.getMessageAlert().showAndWait();
		}
	}//flag

    public void loadAppointmentInfo(Appointment a) {
		if(a != null) {
			apptIdField.setText("" + a.getAppointmentId());
			apptTitleField.setText(a.getTitle());
			apptTypeCombo.setValue(a.getType());
			//TODO: continue
		}
    }//loadAppointmentInfo

    public void loadCustomerInfo() {
        customerInfo = controller.getCustomerInfo();
    }//loadCustomerInfo

    public void loadNewAppointment() {

    }//loadNewAppointment
	
	private void processChanges() {
		
	}//processChanges
	
	private boolean validateForm() {
		return false;
	}//validateForm
}