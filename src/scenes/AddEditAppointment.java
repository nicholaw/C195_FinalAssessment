package scenes;

import appointment.Appointment;
import controller.Controller;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class AddEditAppointment extends BorderPane
{
    private Controller controller;
    private Pane header;            private Label sceneLabel;
    private Label apptIdLabel;      private TextField apptIdField;
    private Label apptTitleLabel;   private TextField apptTitleField;
    private Label apptTypeLabel;    private ComboBox apptTypeCombo;
    private Label customerIdLabel;  private TextField customerIdField;      private TextField customerContactField;
    private Label contactIdLabel;   private TextField contactIdField;       private TextField contactContactField;
    private Label startLabel;       private TextField startDateField;       private TextField startTimeField;
    private Label endLabel;         private TextField endDateField;         private TextField endTimeField;
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
        contactIdField = new TextField("");
        contactContactField = new TextField("");
        startLabel = new Label("Start Time");
        startDateField = new TextField("10/10/10");
        startTimeField = new TextField("10:00");
        endLabel = new Label("End Time");
        endDateField = new TextField("10/10/10");
        endTimeField = new TextField("22:00");
        locationLabel = new Label("Location");
        locationCombo = new ComboBox<String>();
        descriptionLabel = new Label("Description");
        descriptionArea = new TextArea();
        scheduleButton = new Button("Submit");
        cancelButton = new Button("Cancel");

        //add scene elements to container
        GridPane contentPane = new GridPane();
        contentPane.addRow(0, sceneLabel);
        contentPane.addRow(1, customerIdLabel, customerIdField, customerContactField);
        contentPane.addRow(2, apptIdLabel, apptIdField);
        contentPane.addRow(3, apptTitleLabel, apptTitleField);
        contentPane.addRow(4, apptTypeLabel, apptTypeCombo);
        contentPane.addRow(5, contactIdLabel, contactIdField, contactContactField);
        contentPane.addRow(6, startLabel, startDateField, startTimeField);
        contentPane.addRow(7, endLabel, endDateField, endTimeField);
        contentPane.addRow(8, locationLabel, locationCombo);
        contentPane.addRow(9, descriptionLabel, descriptionArea);
        contentPane.addRow(10, scheduleButton, cancelButton);
        this.setCenter(contentPane);
    }
}
