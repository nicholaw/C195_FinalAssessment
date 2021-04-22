package scenes;

import appointment.Appointment;
import controller.Controller;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AddEditAppointment extends BorderPane
{
    private Controller controller;
    private Pane header;            private Label sceneLabel;
    private Label apptIdLabel;      private TextField apptIdField;
    private Label apptTitleLabel;   private TextField apptTitleField;
    private Label apptTypeLabel;    private ComboBox ApptTypeCombo;
    private Label customerIdLabel;  private TextField customerIdField;      private TextField customerContactField;
    private Label contactIdLabel;   private TextField contactIdField;       private TextField contactContactField;
    private Label startLabel;       private TextField startDateField;       private TextField startTimeField;
    private Label endLabel;         private TextField endDateField;         private TextField endTimeField;
    private Label locationLabel;    private ComboBox locationCombo;
    private Label descriptionLabel; private TextArea descriptionArea;
    private Button scheduleButton;  private Button cancelButton;

    public AddEditAppointment()
    {
        //header = controller.getHeader();

    }
}
