package scenes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AppointmentOverview  extends BorderPane
{
    private Label sceneLabel;               private Pane header;
    private Label customerLabel;            private TextField customerIdField;
    private TextField customerEmailField;   private TextField customerPhoneField;
    private Label tableLabel;               private TableView appointmentsTable;
    private Button scheduleButton;          private Button editButton;
    private Button deleteButton;            private Button returnButton;

    public AppointmentOverview()
    {

    }
}
