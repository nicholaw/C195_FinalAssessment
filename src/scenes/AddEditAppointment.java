package scenes;

import appointment.Appointment;
import appointment.AppointmentConstants;
import appointment.AppointmentFieldCode;
import appointment.AppointmentType;
import controller.Controller;
import customer.Customer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sceneUtils.*;
import utils.Location;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
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
    private Label 		descriptionLabel; 	private TextArea 		descriptionArea;
    private Button 		submitButton;		private Button 			cancelButton;
	private Label		titleErrorLabel;	private Label			timeErrorLabel;
	private DateTimeBox dateTimePane;		private Label		descriptionErrorLabel;

    public AddEditAppointment(Controller controller) {
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
        dateTimePane		= new DateTimeBox();
        apptTypeCombo 	 	= new ComboBox<AppointmentType>(this.controller.getAppointmentTypes());
        locationLabel		= new Label("Location");
        locationBox			= new ComboBox<Location>(this.controller.getLocations());
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
			clearErrors();
			if(this.validateForm()) {
				if(newAppointment) {
					Appointment a = new Appointment(Integer.parseInt(apptIdField.getText()), apptTitleField.getText(), descriptionArea.getText(),
							apptTypeCombo.getValue().toString(), dateTimePane.startDateTime(), dateTimePane.endDateTime(), customerInfo.getCustomerId(), contactBox.getSelectedContact(),
							(Location)locationBox.getValue());
					controller.addAppointment(a);
					customerInfo.getCustomer().addAppointment(a);
				} else {
					processChanges();
					controller.updateAppointment(Integer.parseInt(apptIdField.getText()));
				}
				clear();
				controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, customerInfo.getCustomer());
			}
			this.setDisable(false);
		});
		cancelButton.setOnAction(event -> {
			clear();
			controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, null);
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
		submitPane.setAlignment(Pos.BOTTOM_LEFT);
		cancelPane.setAlignment(Pos.BOTTOM_RIGHT);
		var buttonPane = new BorderPane();
		buttonPane.setLeft(submitPane);
		buttonPane.setRight(cancelPane);
		var topCenterPane = new GridPane();
		topCenterPane.addRow(0, apptIdLabel, apptIdField, apptTitleLabel, apptTitleField);
		topCenterPane.addRow(1, apptTypeLabel, apptTypeCombo, locationLabel, locationBox);
		var centerPane = new VBox(topCenterPane, dateTimePane, descriptionLabel, descriptionArea, contactBox,
				titleErrorLabel, descriptionErrorLabel, timeErrorLabel);
		var contentPane = new BorderPane();
		contentPane.setTop(new VBox(sceneLabel, customerInfo));
		contentPane.setCenter(centerPane);
		contentPane.setBottom(buttonPane);
        this.setTop(header);
		this.setCenter(contentPane);
    }//constructor

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
			if(a.overlaps(start))
				overlappingAppts.add(a);
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