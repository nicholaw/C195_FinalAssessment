package sceneUtils;

import appointment.Appointment;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * TableView for displaying appointment information for a chosen customer on the
 * appointment overview page.
 */
public class AppointmentOverviewTable extends GridPane {
    private ToggleGroup unitGroup;
    private RadioButton weeklyRadio;
    private RadioButton monthlyRadio;
    private ComboBox<Month> monthSelector;
    private ComboBox weekSelector;
    private TableView<Appointment> appointmentTable;
    private TableColumn<Appointment, Long> idCol;
    private TableColumn<Appointment, String> titleCol;
    private TableColumn<Appointment, String> typeCol;
    private TableColumn<Appointment, String> dateCol;
    private TableColumn<Appointment, String> startCol;
    private TableColumn<Appointment, String> endCol;
    private TableColumn<Appointment, String> descCol;
    private ResourceBundle rb;

    /**
     * Constructs the AppointmentOverviewTable with the given ResourceBundle.
     * @param rb -the ResourceBundle
     */
    public AppointmentOverviewTable(ResourceBundle rb) {
        this.rb = rb;

        //Instantiate table columns
        appointmentTable = new TableView<>();
        idCol = new TableColumn<>();
        titleCol = new TableColumn<>();
        typeCol = new TableColumn<>();
        dateCol = new TableColumn<>();
        startCol = new TableColumn<>();
        endCol = new TableColumn<>();
        descCol = new TableColumn<>();
        setColumnNames();

        //Set cell value factory for each column
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        //Add columns to the table
        appointmentTable.getColumns().setAll(idCol, titleCol, typeCol, dateCol, startCol, endCol, descCol);

        //Instantiate other scene elements
        unitGroup = new ToggleGroup();
        monthlyRadio = new RadioButton();
        weeklyRadio = new RadioButton();
        monthSelector = new ComboBox();
        weekSelector = new ComboBox();

        //Set initial states for scene elements
        monthlyRadio.setToggleGroup(unitGroup);
        weeklyRadio.setToggleGroup(unitGroup);
        setToggleText();
        unitGroup.selectToggle(monthlyRadio);
        monthSelector.getItems().setAll(Month.values());
        monthSelector.setValue(LocalDateTime.now().getMonth());
        weekSelector.getItems().setAll(1, 2, 3, 4);
        weekSelector.setValue(weekSelector.getItems().get(0));

        //Add elements to containers
        var selectorPane = new HBox(monthSelector, weekSelector);
        var togglePane = new HBox(monthlyRadio, weeklyRadio);
        add(selectorPane, 0, 0);
        add(togglePane, 0, 1);
        add(appointmentTable, 0, 2);

        //Style scene elements
        selectorPane.setSpacing(10);
        togglePane.setSpacing(10);
        setVgap(10);
    }//constructor

    /**
     * Returns the appointment the user has selected from the TableView.
     * @return  -the selected appointment
     */
    public Appointment getSelectedAppointment() {
        return appointmentTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Refreshes the TableView displaying the appointments
     */
    public void refresh() {
        appointmentTable.refresh();
    }//refresh

    /**
     * Sets the items for this TableView to the provided ObservableList of appointments.
     * @param appointments  -the provided appointments
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        appointmentTable.setItems(appointments);
        appointmentTable.refresh();
    }//setAppointments

    /**
     * Sets the ResourceBundle for this class to use.
     * @param rb -the ResourceBundle
     */
    public void setResourceBundle(ResourceBundle rb) {
        this.rb = rb;
        setColumnNames();
        setToggleText();
    }//setResourceBundle

    /**
     * Sets the names of each of the columns in this TableView.
     */
    private void setColumnNames() {
        idCol.setText(rb.getString("appointment_id"));
        titleCol.setText(rb.getString("title"));
        typeCol.setText(rb.getString("type"));
        dateCol.setText(rb.getString("date"));
        startCol.setText(rb.getString("start"));
        endCol.setText(rb.getString("end"));
        descCol.setText(rb.getString("description"));
    }//setColumnNames

    /**
     * Set the text of the radio buttons used for toggling between
     * monthly and weekly views.
     */
    private void setToggleText() {
        monthlyRadio.setText(rb.getString("monthly"));
        weeklyRadio.setText(rb.getString("weekly"));
    }//setToggleText
}//AppointmentOverviewTable