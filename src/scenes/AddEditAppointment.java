package scenes;

import appointment.Appointment;
import appointment.AppointmentConstants;
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
import java.time.LocalTime;
import java.util.Set;

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
	private Label		descriptionErrorLabel;

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
        timeErrorLabel		= new Label("");
        titleErrorLabel		= new Label("");
        descriptionErrorLabel = new Label("");

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
		this.clearCombo(locationBox);
		clearErrors();
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
		descriptionErrorLabel.setText("");
	}//clearErrors
	
	/**
	 *
	 */
	private void flag(AppointmentFieldCode code, String message) {
		switch(code) {
			case TITLE_FIELD:
				titleErrorLabel.setText(message);
				break;
			case START_TIME:
				timeErrorLabel.setText(message);
				break;
			case END_TIME:
				timeErrorLabel.setText(message);
			case DESC_AREA:
				descriptionErrorLabel.setText(message);
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
			descriptionArea.setText(a.getDescription());
		}
    }//loadAppointmentInfo

    public void loadCustomerInfo(Customer c) {
		if(c != null) {
			customerInfo.setCustomerInfo(c.getCustomerId(), c.getName(), c.getPhone());
		}
    }//loadCustomerInfo

    public void loadNewAppointment() {

    }//loadNewAppointment
	
	private void processChanges() {
		
	}//processChanges

	/**
	 *
	 * @return
	 */
	private boolean validateForm() {
		boolean valid = true;
		String tempString;

		//Check that title is not blank
		tempString = apptTitleField.getText();
		if(tempString.isBlank() || tempString.isEmpty()) {
			valid = false;
			flag(AppointmentFieldCode.TITLE_FIELD, "Title is required.");
		}

		//Check that description is not blank
		tempString = descriptionArea.getText();
		if(tempString.isBlank() || tempString.isEmpty()) {
			valid = false;
			flag(AppointmentFieldCode.DESC_AREA, "Description is required.");
		}

		//Check that start date\time is before end date\time
		var start = startTimeBox.getSelectedDateTime();
		var end = endTimeBox.getSelectedDateTime();
		if(start.isAfter(end)) {
			valid = false;
			flag(AppointmentFieldCode.START_TIME, "End time must be after start time.");
		}

		//Check that meeting time is within business hours (08:00-22:00 EST)
		var startTime = LocalTime.of(start.getHour(), start.getMinute());
		var endTime = LocalTime.of(start.getHour(), start.getMinute());
		if(startTime.isBefore(AppointmentConstants.OPEN_HOURS) || startTime.isAfter(AppointmentConstants.OPEN_HOURS)) {
			valid = false;
			flag(AppointmentFieldCode.START_TIME, "Appointment must be within the business hours of 08:00 - 22:00 EST.");
		} else if(endTime.isAfter(AppointmentConstants.CLOSE_HOURS)) {
			valid = false;
			flag(AppointmentFieldCode.START_TIME, "Appointment must be within the business hours of 08:00 - 22:00 EST.");
		}

		//Check that meeting does not overlap another existing appointment
		Set<Long> overlappingAppointments = controller.checkForOverlappingAppointments(start, end, customerInfo.getCustomerId());
		if(!overlappingAppointments.isEmpty()) {
			valid = false;
			String overlapError = "The following existing appointments overlap this appointment: \n";
			for(Long l : overlappingAppointments) {
				overlapError += ("\t" + l + "\n");
			}
			flag(AppointmentFieldCode.START_TIME, overlapError);
		}

		return valid;
	}//validateForm
}