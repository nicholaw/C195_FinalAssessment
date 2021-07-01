package scenes;

import appointment.Appointment;
import appointment.AppointmentFieldCode;
import appointment.AppointmentType;
import controller.Controller;
import customer.Customer;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sceneUtils.ContactBox;
import sceneUtils.CustomerHeader;
import sceneUtils.SceneCode;
import sceneUtils.TimeBox;
import utils.Location;

public class AddEditAppointment extends BorderPane
{
    private Controller	controller;			private boolean			newAppointment;
    private Pane 		header;            	private Label			sceneLabel;
    private Label 		apptIdLabel;      	private TextField		apptIdField;
    private Label 		apptTitleLabel;   	private TextField		apptTitleField;
    private Label 		apptTypeLabel;    	private ComboBox		apptTypeCombo;
    private Label		locationLabel;		private ComboBox		locationBox;
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
		customerInfo		= new CustomerHeader();
        apptIdLabel 		= new Label("Id");
        apptIdField 		= new TextField("" + controller.getNextAppointmentId());
        apptTitleLabel 		= new Label("Title");
        apptTitleField 		= new TextField("");
        apptTypeLabel 		= new Label("Type");
        apptTypeCombo 	 	= new ComboBox<AppointmentType>(this.controller.getAppointmentTypes());
        locationLabel		= new Label("Location");
        locationBox			= new ComboBox<Location>(this.controller.getLocations());
		startTimeBox		= new TimeBox("Start:");
		endTimeBox			= new TimeBox("End:");
		contactBox 			= new ContactBox(this.controller.getContacts());
        descriptionLabel	= new Label("Description");
        descriptionArea 	= new TextArea("");
        submitButton 	 	= new Button("Schedule");
        cancelButton 		= new Button("Cancel");

        //set initial states for scene elements
        apptIdField.setDisable(true);
		newAppointment = true;
		if(locationBox.getItems().size() > 0)
			locationBox.setValue(locationBox.getItems().get(0));
		if(apptTypeCombo.getItems().size() > 0)
			apptTypeCombo.setValue(apptTypeCombo.getItems().get(0));

        //Add event handlers to scene elements
		submitButton.setOnAction(event -> {
			this.setDisable(true);
			if(this.validateForm()) {
				if(newAppointment) {
					controller.addAppointment(null); //TODO
				} else {
					processChanges();
					controller.updateAppointment(Integer.parseInt(apptIdField.getText()));
				}
				clear();
				controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, null);
			}
			this.setDisable(false);
		});
		cancelButton.setOnAction(event -> {
			clear();
			controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, null);
		});

        //add scene elements to container
        GridPane contentPane = new GridPane();
        contentPane.addRow(0, sceneLabel);
        contentPane.addRow(1, customerInfo);
        contentPane.addRow(2, apptIdLabel, apptIdField);
        contentPane.addRow(3, apptTitleLabel, apptTitleField);
        contentPane.addRow(4, apptTypeLabel, apptTypeCombo, locationLabel, locationBox);
		contentPane.addRow(5, startTimeBox);
		contentPane.addRow(6, endTimeBox);
        contentPane.addRow(7, contactBox);
        contentPane.addRow(8, descriptionLabel, descriptionArea);
        contentPane.addRow(9, submitButton, cancelButton);
        this.setCenter(contentPane);
        this.setTop(header);
    }//constructor

	/**
	 *
	 */
	public void clear() {
		apptIdField.setText("");
		apptTitleField.setText("");
		descriptionArea.setText("");
		contactBox.reset();
		startTimeBox.resetValues();
		endTimeBox.resetValues();
		customerInfo.clear();
		this.clearCombo(apptTypeCombo);
	}//clear
	
	/**
	 *
	 */
	private void clearCombo(ComboBox box) {
		if(box != null) {
			if(box.getItems().size() > 0) {
				box.setValue(box.getItems().get(0));
			} else {
				box.setValue(null);
			}
		}
	}//clearCombo

	/**
	 *
	 */
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

    public void loadCustomerInfo(Customer c) {
		if(c != null) {
			customerInfo.setCusomterInfo(c.getCustomerId(), c.getName(), c.getPhone());
		}
    }//loadCustomerInfo

    public void loadNewAppointment() {

    }//loadNewAppointment
	
	private void processChanges() {
		
	}//processChanges
	
	private boolean validateForm() {
		return false;
	}//validateForm
}