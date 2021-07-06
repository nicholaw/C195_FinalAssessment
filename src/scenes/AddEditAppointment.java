package scenes;

import appointment.Appointment;
import appointment.AppointmentConstants;
import appointment.AppointmentFieldCode;
import appointment.AppointmentType;
import controller.Controller;
import controller.ControllerConstants;
import customer.Customer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sceneUtils.*;
import utils.Contact;
import utils.Location;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;

public class AddEditAppointment extends BorderPane {
    private Controller	controller;			private boolean			newAppointment;
    private Label 		apptIdLabel;      	private TextField		apptIdField;
    private Label 		apptTitleLabel;   	private TextField		apptTitleField;
    private Label 		apptTypeLabel;    	private ComboBox		apptTypeCombo;
    private Label		locationLabel;		private ComboBox		locationBox;
    private ContactBox 	contactBox;  		private CustomerHeader	customerInfo;
    private Label 		descriptionLabel; 	private TextField 		descriptionArea;
    private Button 		submitButton;		private Button 			cancelButton;
	private Label		titleErrorLabel;	private Label			timeErrorLabel;
	private DateTimeBox dateTimePane;		private Label			descriptionErrorLabel;
	private Appointment appointmentToEdit;	private Label			sceneLabel;

    public AddEditAppointment(Controller controller) {
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel 			= new Label("Schedule Appointment");
		customerInfo		= new CustomerHeader();
        apptIdLabel 		= new Label("Id");
        apptIdField 		= new TextField("" + controller.getNextAppointmentId());
        apptTitleLabel 		= new Label("Title");
        apptTitleField 		= new TextField("");
        apptTypeLabel 		= new Label("Type");
        dateTimePane		= new DateTimeBox();
        apptTypeCombo 	 	= new ComboBox<AppointmentType>(this.controller.getAppointmentTypes());
        locationLabel		= new Label("Location");
        locationBox			= new ComboBox<Location>(this.controller.getLocations());
		contactBox 			= new ContactBox(this.controller.getContacts());
        descriptionLabel	= new Label("Description");
        descriptionArea 	= new TextField("");
        submitButton 	 	= new Button("Schedule");
        cancelButton 		= new Button("Cancel");
        timeErrorLabel		= new Label("");
        titleErrorLabel		= new Label("");
        descriptionErrorLabel = new Label("");
        appointmentToEdit 	= null;

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
			clearErrors();
			if(this.validateForm()) {
				if(newAppointment) {
					Appointment a = new Appointment(Integer.parseInt(apptIdField.getText()), apptTitleField.getText(), descriptionArea.getText(),
							apptTypeCombo.getValue().toString(), dateTimePane.startDateTime(), dateTimePane.endDateTime(), customerInfo.getCustomerId(), contactBox.getSelectedContact(),
							(Location)locationBox.getValue());
					controller.addAppointment(a);
					customerInfo.getCustomer().addAppointment(a);
				} else {
					processChanges(true);
					controller.updateAppointment(Integer.parseInt(apptIdField.getText()));
				}
				clear();
				controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, customerInfo.getCustomer());
			}
			this.setDisable(false);
		});
		cancelButton.setOnAction(event -> {
			if(newAppointment) {
				if(checkForInput()) {
					if(controller.displayConfirmationAlert("Confirm Navigation", "You have not finished scheduling this appointment. Are you sure you would like to " +
							"leave without finishing?")) {
						clear();
						controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, null);
					}
				} else {
					clear();
					controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, null);
				}
			} else {
				if(processChanges(false)) {
					if(controller.displayConfirmationAlert("Confirm Navigation", "You have made changes to this appointment. Are you sure you would like to " +
							"leave without saving these changes?")) {
						clear();
						controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, null);
					}
				} else {
					clear();
					controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, null);
				}
			}
		});
		apptTitleField.setOnKeyReleased(event -> {
			checkForMaximumCharacters(apptTitleField, AppointmentConstants.MAX_CHARS_TITLE);
		});
		descriptionArea.setOnKeyReleased(event -> {
			checkForMaximumCharacters(descriptionArea, AppointmentConstants.MAX_CHARS_DESC);
		});

        //add scene elements to container
		var submitPane = new HBox(submitButton);
		var cancelPane = new HBox(cancelButton);
		var buttonPane = new BorderPane();
		buttonPane.setRight(cancelPane);
		buttonPane.setLeft(submitPane);
		var contentPane = new GridPane();
		contentPane.add(sceneLabel, 0, 0);
		contentPane.add(customerInfo, 0, 1);
		contentPane.add(apptTitleLabel, 0, 2);
		contentPane.add(apptTitleField, 0, 3);
		contentPane.add(apptTypeLabel, 0, 4);
		contentPane.add(apptTypeCombo, 0, 5);
		contentPane.add(locationLabel, 0, 6);
		contentPane.add(locationBox, 0, 7);
		contentPane.add(dateTimePane, 0, 8);
		contentPane.add(descriptionLabel, 0, 9);
		contentPane.add(descriptionArea, 0, 10);
		contentPane.add(contactBox, 0, 11);
		contentPane.add(buttonPane, 0, 12);
		this.setCenter(contentPane);

		//Style scene elements
		contentPane.setVgap(10);
		contentPane.setAlignment(Pos.CENTER);
		submitPane.setAlignment(Pos.CENTER_LEFT);
		cancelPane.setAlignment(Pos.CENTER_RIGHT);
    }//constructor

	private boolean checkForInput() {
    	boolean newInput = false;
    	String tempString;

    	tempString = apptTitleField.getText();
    	if(!(tempString.isBlank() || tempString.isEmpty())) {
			newInput = true;
		}

    	tempString = descriptionArea.getText();
    	if(!(tempString.isEmpty() || tempString.isBlank())) {
			newInput = true;
		}

    	return newInput;
	}

	private void checkForMaximumCharacters(TextInputControl inputElement, int maximum) {
		String oldString = inputElement.getText();
		if(oldString.length() > maximum) {
			String newString = oldString.substring(0, (maximum));
			inputElement.setText(newString);
			inputElement.positionCaret(maximum);
		}
	}//checkForMaximumCharacters

	/**
	 *
	 */
	public void clear() {
		apptIdField.setText("");
		apptTitleField.setText("");
		descriptionArea.setText("");
		contactBox.reset();
		customerInfo.clear();
		this.clearCombo(apptTypeCombo);
		this.clearCombo(locationBox);
		appointmentToEdit = null;
		newAppointment = true;
		submitButton.setText("Schedule");
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
				break;
			case DESC_AREA:
				descriptionErrorLabel.setText(message);
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
			descriptionArea.setText(a.getDescription());
			appointmentToEdit = a;
			dateTimePane.setStart(a.getStartDateTime());
			dateTimePane.setEnd(a.getEndDateTime());
			submitButton.setText("Update");
			newAppointment = false;
		}
    }//loadAppointmentInfo

    public void loadCustomerInfo(Customer c) {
		if(c != null) {
			customerInfo.setCustomer(c);
		}
    }//loadCustomerInfo

    public void loadNewAppointment() {
		apptIdField.setText("" + controller.getNextAppointmentId());
		dateTimePane.setDateTime(LocalDateTime.now());
		submitButton.setText("Schedule");
		newAppointment = true;
    }//loadNewAppointment
	
	private boolean processChanges(boolean commitChanges) {
		boolean changesMade = false;
		String tempString = "";

		//check for title change
		tempString = apptTitleField.getText();
		if(!tempString.equals(appointmentToEdit.getTitle())) {
			changesMade = true;
			if(commitChanges) {
				controller.addAppointmentUpdate(AppointmentFieldCode.TITLE_FIELD, tempString);
				appointmentToEdit.setTitle(tempString);
			}
		}

		//check for description change
		tempString = descriptionArea.getText();
		if(!tempString.equals(appointmentToEdit.getDescription())) {
			changesMade = true;
			if(commitChanges) {
				controller.addAppointmentUpdate(AppointmentFieldCode.DESC_AREA, tempString);
				appointmentToEdit.setDescription(tempString);
			}
		}

		//check for location change
		if(appointmentToEdit.getLocation() != locationBox.getValue()) {
			changesMade = true;
			if(commitChanges) {
				controller.addAppointmentUpdate(AppointmentFieldCode.LOCATION_COMBO, locationBox.getValue().toString());
				appointmentToEdit.setLocation((Location) locationBox.getValue());
			}
		}

		//check for type change
		if(appointmentToEdit.getType() != apptTypeCombo.getValue()) {
			changesMade = true;
			if(commitChanges) {
				controller.addAppointmentUpdate(AppointmentFieldCode.TYPE_COMBO, apptTypeCombo.getValue().toString());
				appointmentToEdit.setType((AppointmentType) apptTypeCombo.getValue());
			}
		}

		//check for start change
		LocalDateTime tempDateTime = dateTimePane.startDateTime();
		if(!appointmentToEdit.getStartDateTime().equals(tempDateTime)) {
			changesMade = true;
			if(commitChanges) {
				controller.addAppointmentUpdate(AppointmentFieldCode.START_TIME, tempDateTime.format(ControllerConstants.TIMESTAMP_FORMAT));
				appointmentToEdit.setStartDateTime(tempDateTime);
			}
		}

		//check for end change
		tempDateTime = dateTimePane.endDateTime();
		if(!appointmentToEdit.getStartDateTime().equals(tempDateTime)) {
			changesMade = true;
			if(commitChanges) {
				controller.addAppointmentUpdate(AppointmentFieldCode.END_TIME, tempDateTime.format(ControllerConstants.TIMESTAMP_FORMAT));
				appointmentToEdit.setEndDateTime(tempDateTime);
			}
		}

		//check for contact change
		Contact tempContact = contactBox.getSelectedContact();
		if(!appointmentToEdit.getContact().equals(tempContact)) {
			changesMade = true;
			if(commitChanges) {
				controller.addAppointmentUpdate(AppointmentFieldCode.CONTACT_COMBO, ("" + tempContact.getId()));
				appointmentToEdit.setContact(tempContact);
			}
		}

		return changesMade;
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
		var start = dateTimePane.startDateTime();
		var end = dateTimePane.endDateTime();
		if(start.isAfter(end)) {
			valid = false;
			flag(AppointmentFieldCode.START_TIME, "End time must be after start time.");
		}

		//Check that meeting time is within business hours (08:00-22:00 EST)
		var startTime = LocalTime.of(start.getHour(), start.getMinute());
		var endTime = LocalTime.of(end.getHour(), end.getMinute());
		if(startTime.isBefore(AppointmentConstants.OPEN_HOURS) || startTime.isAfter(AppointmentConstants.CLOSE_HOURS)) {
			valid = false;
			flag(AppointmentFieldCode.START_TIME, "Appointment must be within the business hours of 08:00 - 22:00 EST.");
		} else if(endTime.isAfter(AppointmentConstants.CLOSE_HOURS)) {
			valid = false;
			flag(AppointmentFieldCode.START_TIME, "Appointment must be within the business hours of 08:00 - 22:00 EST.");
		}

		//Check that meeting does not overlap another existing appointment
		Customer c = customerInfo.getCustomer();
		var overlappingAppts = new HashSet<Appointment>();
		if(c.getAppointments() == null) {
			c.setAppointments(controller.getCustomerAppointments(customerInfo.getCustomer()));
		}
		for(Appointment a : c.getAppointments()) {
			if(a.overlaps(start, end)) {
				if(newAppointment) {
					overlappingAppts.add(a);
				} else {
					if(!(a.equals(appointmentToEdit)))
						overlappingAppts.add(a);
				}
			}
		}
		if(overlappingAppts.size() > 0) {
			valid = false;
			String message = "Appointment overlaps the following existing appointments:\n";
			for(Appointment a : overlappingAppts) {
				message += "\t" + a.getAppointmentId() + "(" + a.getTitle() + ")\n";
			}
			flag(AppointmentFieldCode.START_TIME, message);
		}

		return valid;
	}//validateForm
}