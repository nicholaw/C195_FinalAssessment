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
import java.util.ResourceBundle;

public class AddEditAppointment extends BorderPane implements Refreshable {
    private Controller	controller;			private boolean			newAppointment;
    private Label 		apptIdLabel;      	private TextField		apptIdField;
    private Label 		apptTitleLabel;   	private TextField		apptTitleField;
    private Label 		apptTypeLabel;    	private ComboBox		apptTypeCombo;
    private Label		locationLabel;		private ComboBox		locationBox;
    private ContactBox 	contactBox;  		private CustomerHeader	customerInfo;
    private Label 		descriptionLabel; 	private TextField 		descriptionArea;
    private Button 		submitButton;		private Button 			cancelButton;
	private Label		titleErrorLabel;	private Label			timeErrorLabel;
	private DateTimeBox dateTimePane;		private Label descErrorLabel;
	private Appointment appointmentToEdit;	private Label			sceneLabel;

    public AddEditAppointment(Controller controller) {
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel 			= new Label(this.controller.getResourceBundle().getString("schedule_appointment"));
		customerInfo		= new CustomerHeader();
        apptIdLabel 		= new Label(this.controller.getResourceBundle().getString("id"));
        apptIdField 		= new TextField("" + controller.getNextAppointmentId());
        apptTitleLabel 		= new Label(this.controller.getResourceBundle().getString("title"));
        apptTitleField 		= new TextField("");
        apptTypeLabel 		= new Label(this.controller.getResourceBundle().getString("type"));
		timeErrorLabel		= new Label("");
		titleErrorLabel		= new Label("");
		descErrorLabel 		= new Label("");
        dateTimePane		= new DateTimeBox(timeErrorLabel);
        apptTypeCombo 	 	= new ComboBox<AppointmentType>(this.controller.getAppointmentTypes());
        locationLabel		= new Label(this.controller.getResourceBundle().getString("location"));
        locationBox			= new ComboBox<Location>(this.controller.getLocations());
		contactBox 			= new ContactBox(this.controller.getContacts());
        descriptionLabel	= new Label(this.controller.getResourceBundle().getString("description"));
        descriptionArea 	= new TextField("");
        submitButton 	 	= new Button(this.controller.getResourceBundle().getString("schedule"));
        cancelButton 		= new Button(this.controller.getResourceBundle().getString("cancel"));
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
		var buttonPane = new HBox(submitButton, cancelButton);
		var headerPane = new GridPane();
		headerPane.add(sceneLabel, 0, 0);
		headerPane.add(customerInfo, 0, 1);
		var titlePane = new HBox(apptTitleLabel, titleErrorLabel);
		var descPane = new HBox(descriptionLabel, descErrorLabel);
		var fieldsPane = new GridPane();
		fieldsPane.add(titlePane, 0, 0);
		fieldsPane.add(apptTitleField, 0, 1);
		fieldsPane.add(apptTypeLabel, 0, 2);
		fieldsPane.add(apptTypeCombo, 0, 3);
		fieldsPane.add(locationLabel, 0, 4);
		fieldsPane.add(locationBox, 0, 5);
		fieldsPane.add(dateTimePane, 0, 6);
		fieldsPane.add(descPane, 0, 7);
		fieldsPane.add(descriptionArea, 0, 8);
		fieldsPane.add(contactBox, 0, 9);
		var contentPane = new GridPane();
		contentPane.add(headerPane, 0, 0);
		contentPane.add(fieldsPane, 0, 1);
		contentPane.add(buttonPane, 0, 2);
		this.setCenter(contentPane);

		//Style scene elements
		sceneLabel.getStyleClass().add("scene-label");
		fieldsPane.setVgap(10);
		fieldsPane.setAlignment(Pos.CENTER);
		buttonPane.setAlignment(Pos.CENTER_RIGHT);
		contentPane.setAlignment(Pos.CENTER);
		contentPane.setVgap(20);
		buttonPane.setSpacing(20);
		titlePane.setSpacing(20);
		descPane.setSpacing(20);
		timeErrorLabel.getStyleClass().add("error-label");
		titleErrorLabel.getStyleClass().add("error-label");
		descErrorLabel.getStyleClass().add("error-label");
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
		submitButton.setText(this.controller.getResourceBundle().getString("schedule"));
		sceneLabel.setText(this.controller.getResourceBundle().getString("schedule_appointment"));
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
		descErrorLabel.setText("");
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
				descErrorLabel.setText(message);
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
			submitButton.setText(this.controller.getResourceBundle().getString("update"));
			sceneLabel.setText(this.controller.getResourceBundle().getString("update_appointment"));
			newAppointment = false;
		} else {
			loadNewAppointment();
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
		submitButton.setText(this.controller.getResourceBundle().getString("schedule"));
		sceneLabel.setText(this.controller.getResourceBundle().getString("schedule_appointment"));
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

	@Override
	public void refresh(ResourceBundle rb) {

	}

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
			flag(AppointmentFieldCode.TITLE_FIELD, "- Title is required");
		}

		//Check that description is not blank
		tempString = descriptionArea.getText();
		if(tempString.isBlank() || tempString.isEmpty()) {
			valid = false;
			flag(AppointmentFieldCode.DESC_AREA, "- Description is required");
		}

		//Check that start date\time is before end date\time
		var start = dateTimePane.startDateTime();
		var end = dateTimePane.endDateTime();
		if(start.isAfter(end)) {
			valid = false;
			flag(AppointmentFieldCode.START_TIME, "- End time must be after start time");
		}

		//Check that meeting time is within business hours (08:00-22:00 EST)
		var startTime = LocalTime.of(start.getHour(), start.getMinute());
		var endTime = LocalTime.of(end.getHour(), end.getMinute());
		if(startTime.isBefore(AppointmentConstants.OPEN_HOURS) || startTime.isAfter(AppointmentConstants.CLOSE_HOURS)) {
			valid = false;
			flag(AppointmentFieldCode.START_TIME, "- Appointment must be within the business hours of 08:00 - 22:00 EST");
		} else if(endTime.isAfter(AppointmentConstants.CLOSE_HOURS)) {
			valid = false;
			flag(AppointmentFieldCode.START_TIME, "- Appointment must be within the business hours of 08:00 - 22:00 EST");
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
			String message = "- Appointment overlaps the following existing appointments:\n";
			for(Appointment a : overlappingAppts) {
				message += "\t" + a.getAppointmentId() + "(" + a.getTitle() + ")\n";
			}
			flag(AppointmentFieldCode.START_TIME, message);
		}

		return valid;
	}//validateForm
}