package scenes;

import appointment.Appointment;
import appointment.AppointmentConstants;
import appointment.AppointmentFieldCode;
import utils.Type;
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
    private final Controller	controller;			private final ErrorLabel 		descErrorLabel;
	private final Label 		sceneLabel;			private final Label 			apptTitleLabel;
	private final Label 		apptTypeLabel;		private final Label				locationLabel;
	private final Label 		descriptionLabel;	private final CustomerHeader	customerInfo;
    private final TextField		apptTitleField;		private final TextField 		descriptionArea;
    private final ContactBox 	contactBox;			private final DateTimeBox 		dateTimePane;
    private final Button 		submitButton;		private final Button 			cancelButton;
	private final ErrorLabel	titleErrorLabel;	private final ErrorLabel		timeErrorLabel;
	private Appointment 		appointmentToEdit;	private boolean					newAppointment;
	private final ComboBox<Location>locationBox;	private final ComboBox<Type>apptTypeCombo;

    public AddEditAppointment(Controller controller) {
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel 			= 	new Label("");
		customerInfo		= 	new CustomerHeader(this.controller.getResourceBundle().getString("customer"));
        apptTitleLabel 		= 	new Label("");
        apptTitleField 		= 	new TextField("");
        apptTypeLabel 		= 	new Label("");
		timeErrorLabel		= 	new ErrorLabel(this.controller.getResourceBundle());
		titleErrorLabel		= 	new ErrorLabel(this.controller.getResourceBundle());
		descErrorLabel 		= 	new ErrorLabel(this.controller.getResourceBundle());
        dateTimePane		= 	new DateTimeBox(this.controller.getResourceBundle(), timeErrorLabel);
        apptTypeCombo 	 	= 	new ComboBox<>(this.controller.getAppointmentTypes());
        locationLabel		= 	new Label("");
        locationBox			= 	new ComboBox<>(this.controller.getLocations());
		contactBox 			= 	new ContactBox(this.controller.getResourceBundle(), this.controller.getContacts());
        descriptionLabel	= 	new Label("");
        descriptionArea 	= 	new TextField("");
        submitButton 	 	= 	new Button("");
        cancelButton 		= 	new Button("");
        appointmentToEdit 	= 	null;
        setElementText();

        //set initial states for scene elements
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
					appointment.Appointment a = new appointment.Appointment(this.controller.getNextAppointmentId(), apptTitleField.getText(), descriptionArea.getText(),
							apptTypeCombo.getValue(), dateTimePane.startDateTime(), dateTimePane.endDateTime(), customerInfo.getCustomerId(), contactBox.getSelectedContact(),
							(Location)locationBox.getValue());
					if(controller.addAppointment(a))
						customerInfo.getCustomer().addAppointment(a);
				} else {
					processChanges(true);
					controller.updateAppointment(appointmentToEdit.getAppointmentId());
				}
				clear();
				controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, customerInfo.getCustomer());
			}
			this.setDisable(false);
		});
		cancelButton.setOnAction(event -> {
			if(newAppointment) {
				if(checkForInput()) {
					if(controller.displayConfirmationAlert(controller.getResourceBundle().getString("confirm_navigation_title"),
							controller.getResourceBundle().getString("confirm_navigation_alert"))) {
						clear();
						controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, null);
					}
				} else {
					clear();
					controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, null);
				}
			} else {
				if(processChanges(false)) {
					if(controller.displayConfirmationAlert(controller.getResourceBundle().getString("confirm_navigation_title"),
							controller.getResourceBundle().getString("confirm_navigation_alert"))) {
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
    	if(!(tempString.isEmpty())) {
			newInput = true;
		}

    	tempString = descriptionArea.getText();
    	if(!(tempString.isEmpty())) {
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
		apptTitleField.setText("");
		descriptionArea.setText("");
		contactBox.reset();
		customerInfo.clear();
		this.clearCombo(apptTypeCombo);
		this.clearCombo(locationBox);
		loadNewAppointment();
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

    public void loadAppointmentInfo(Appointment a) {
		if(a != null) {
			apptTitleField.setText(a.getTitle());
			apptTypeCombo.setValue(a.getType());
			descriptionArea.setText(a.getDescription());
			appointmentToEdit = a;
			dateTimePane.setStart(a.getStartDateTime());
			dateTimePane.setEnd(a.getEndDateTime());
			locationBox.setValue(a.getLocation());
			submitButton.setText(this.controller.getResourceBundle().getString("update"));
			sceneLabel.setText(this.controller.getResourceBundle().getString("update_appointment"));
			newAppointment = false;
		} else {
			clear();
			loadNewAppointment();
		}
    }//loadAppointmentInfo

    public void loadCustomerInfo(Customer c) {
		if(c != null) {
			customerInfo.setCustomer(c);
		}
    }//loadCustomerInfo

    public void loadNewAppointment() {
		dateTimePane.setDateTime(LocalDateTime.now());
		appointmentToEdit = null;
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
		if(appointmentToEdit.getLocation() != locationBox.getSelectionModel().getSelectedItem()) {
			changesMade = true;
			if(commitChanges) {
				controller.addAppointmentUpdate(AppointmentFieldCode.LOCATION_COMBO, locationBox.getValue().toString());
				appointmentToEdit.setLocation((Location) locationBox.getValue());
			}
		}

		//check for type change
		if(appointmentToEdit.getType() != apptTypeCombo.getSelectionModel().getSelectedItem()) {
			changesMade = true;
			if(commitChanges) {
				controller.addAppointmentUpdate(AppointmentFieldCode.TYPE_COMBO, apptTypeCombo.getValue().toString());
				appointmentToEdit.setType((Type) apptTypeCombo.getValue());
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
		setElementText();
		customerInfo.setText(rb.getString("customer"));
		titleErrorLabel.setResourceBundle(rb);
		descErrorLabel.setResourceBundle(rb);
		contactBox.setResourceBundle(rb);
		dateTimePane.setResourceBundle(rb);
		refreshTimeError(rb);
	}//refresh

	private void refreshTimeError(ResourceBundle rb) {
		if(timeErrorLabel.getError() != null) {
			String errorMessage;
			String append = timeErrorLabel.getText().substring(controller.getResourceBundle().getString("overlaps_error").length() - 1);
			timeErrorLabel.setResourceBundle(rb);
			switch(timeErrorLabel.getError()) {
				case APPOINTMENT_BUSINESS_HOURS_ERROR:
					errorMessage = controller.getResourceBundle().getString("hours_error");
					errorMessage = errorMessage + " " + AppointmentConstants.OPEN_HOURS + " " +
							controller.getResourceBundle().getString("to") + " " + AppointmentConstants.CLOSE_HOURS;
					timeErrorLabel.setError(ErrorCode.APPOINTMENT_BUSINESS_HOURS_ERROR, errorMessage);
					break;
				case APPOINTMENT_OVERLAPS_EXISTING_ERROR:
					errorMessage = controller.getResourceBundle().getString("overlaps_error");
					errorMessage = errorMessage + "\n\t" + append;
					timeErrorLabel.setError(ErrorCode.APPOINTMENT_OVERLAPS_EXISTING_ERROR, errorMessage);
					break;
			}//switch
		}//if
	}//refreshTimeError

	private void setElementText() {
		apptTitleLabel.setText(controller.getResourceBundle().getString("title"));
		descriptionLabel.setText(controller.getResourceBundle().getString("description"));
		apptTypeLabel.setText(controller.getResourceBundle().getString("type"));
		locationLabel.setText(controller.getResourceBundle().getString("location"));
		cancelButton.setText(controller.getResourceBundle().getString("cancel"));
	}//setElementText

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
			titleErrorLabel.setError(ErrorCode.APPOINTMENT_TITLE_REQUIRED_ERROR);
		}

		//Check that description is not blank
		tempString = descriptionArea.getText();
		if(tempString.isBlank() || tempString.isEmpty()) {
			valid = false;
			descErrorLabel.setError(ErrorCode.APPOINTMENT_DESC_REQUIRED_ERROR);
		}

		//Check that start date\time is before end date\time
		var start = dateTimePane.startDateTime();
		var end = dateTimePane.endDateTime();
		if(start.isAfter(end)) {
			valid = false;
			timeErrorLabel.setError(ErrorCode.APPOINTMENT_START_END_ERROR);
		}

		//Check that meeting time is within business hours (08:00-22:00 EST)
		var startTime = LocalTime.of(start.getHour(), start.getMinute());
		var endTime = LocalTime.of(end.getHour(), end.getMinute());
		if(startTime.isBefore(AppointmentConstants.OPEN_HOURS) || startTime.isAfter(AppointmentConstants.CLOSE_HOURS)) {
			valid = false;
			String errorMessage = controller.getResourceBundle().getString("hours_error");
			errorMessage = errorMessage + " " + AppointmentConstants.OPEN_HOURS + " " +
					controller.getResourceBundle().getString("to") + " " + AppointmentConstants.CLOSE_HOURS;
			timeErrorLabel.setError(ErrorCode.APPOINTMENT_BUSINESS_HOURS_ERROR, errorMessage);
		} else if(endTime.isAfter(AppointmentConstants.CLOSE_HOURS)) {
			valid = false;
			String errorMessage = controller.getResourceBundle().getString("hours_error");
			errorMessage = errorMessage + " " + AppointmentConstants.OPEN_HOURS + " " +
					controller.getResourceBundle().getString("to") + " " + AppointmentConstants.CLOSE_HOURS;
			timeErrorLabel.setError(ErrorCode.APPOINTMENT_BUSINESS_HOURS_ERROR, errorMessage);
		}

		//Check that meeting does not overlap another existing appointment
		Customer c = customerInfo.getCustomer();
		var overlappingAppts = new HashSet<appointment.Appointment>();
		if(c.getAppointments() == null) {
			c.setAppointments(controller.getCustomerAppointments(customerInfo.getCustomer()));
		}
		for(appointment.Appointment a : c.getAppointments()) {
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
			String errorMessage = controller.getResourceBundle().getString("overlaps_error");
			for(Appointment a : overlappingAppts) {
				errorMessage += "\n\t";
				errorMessage = errorMessage + a.getTitle() + "(" + a.getAppointmentId() + ")";
			}
			timeErrorLabel.setError(ErrorCode.APPOINTMENT_OVERLAPS_EXISTING_ERROR, errorMessage);
		}

		//Check that date/time is not in the past

		return valid;
	}//validateForm
}