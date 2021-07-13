package sceneUtils;

import appointment.Appointment;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ResourceBundle;

/**
 * TableView for displaying appointment information for a chosen customer on the
 * appointment overview page.
 */
public class AppointmentOverviewTable extends TableView<Appointment> {
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
        this.getColumns().setAll(idCol, titleCol, typeCol, dateCol, startCol, endCol, descCol);
    }

    /**
     * Returns the appointment the user has selected from the TableView.
     * @return  -the selected appointment
     */
    public Appointment getSelectedAppointment() {
        return getSelectionModel().getSelectedItem();
    }

    /**
     * Sets the items for this TableView to the provided ObservableList of appointments.
     * @param appointments  -the provided appointments
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.setItems(appointments);
        this.refresh();
    }

    /**
     * Sets the ResourceBundle for this class to use.
     * @param rb -the ResourceBundle
     */
    public void setResourceBundle(ResourceBundle rb) {
        this.rb = rb;
        setColumnNames();
    }

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
    }
}