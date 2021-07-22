package sceneUtils;

import appointment.Appointment;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.Contact;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * TableView which represents the schedule of a given employee contact
 */
public class Schedule extends TableView<Appointment> {
    private HashMap<Contact, Set<Appointment>> schedule;
    private TableColumn<Appointment, String> dateColumn;
    private TableColumn<Appointment, String> startColumn;
    private TableColumn<Appointment, String> endColumn;
    private TableColumn<Appointment, String> locationColumn;
    private TableColumn<Appointment, Integer> idColumn;
    private TableColumn<Appointment, String> titleColumn;
    private TableColumn<Appointment, String> typeColumn;
    private TableColumn<Appointment, String> descColumn;
    private TableColumn<Appointment, Integer> customerColumn;

    /**
     * Constructs the TableView with the given HashMap of employee schedules, the initial employee
     * to display, and ResourceBundle for text localization.
     * @param schedule -HashMap of employee schedules
     * @param initialContact -employee to display
     * @param rb -ResourceBundle for localization
     */
    public Schedule(HashMap<Contact, Set<Appointment>> schedule, Contact initialContact, ResourceBundle rb) {
        this.schedule = schedule;

        //Instantiate table columns
        dateColumn = new TableColumn<>();
        startColumn = new TableColumn<>();
        endColumn = new TableColumn<>();
        locationColumn = new TableColumn<>();
        idColumn = new TableColumn<>();
        titleColumn = new TableColumn<>();
        typeColumn = new TableColumn<>();
        descColumn = new TableColumn<>();
        customerColumn = new TableColumn<>();
        setColumnNames(rb);

        //Set cell value factories for columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        //Add columns to table
        this.getColumns().addAll(dateColumn, startColumn, endColumn, locationColumn, idColumn,
                titleColumn, typeColumn, descColumn, customerColumn);

        setItems(initialContact);
    }//constructor

    /**
     * Refreshes the text on this element to use the provided ResourceBundle
     * @param rb -the ResourceBundle
     */
    public void refreshText(ResourceBundle rb) {
        setColumnNames(rb);
    }//refreshText

    /**
     * Sets the items in this table to the appointments assigned to the given contact.
     * @param c -the contact
     */
    public void setItems(Contact c) {
        this.setItems(FXCollections.observableArrayList(schedule.get(c)));
    }//setItems

    private void setColumnNames(ResourceBundle rb) {
        dateColumn.setText(rb.getString("date"));
        startColumn.setText(rb.getString("start"));
        endColumn.setText(rb.getString("end"));
        locationColumn.setText(rb.getString("location"));
        idColumn.setText(rb.getString("appointment_id"));
        titleColumn.setText(rb.getString("title"));
        typeColumn.setText(rb.getString("type"));
        descColumn.setText(rb.getString("description"));
        customerColumn.setText(rb.getString("customer_id"));
    }//setColumnNames
}//Schedule