package sceneUtils;

import appointment.Appointment;
import customer.Customer;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.GregorianCalendar;
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
    private Customer parentCustomer;
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
        parentCustomer = null;

        //Set initial states for scene elements
        monthlyRadio.setToggleGroup(unitGroup);
        weeklyRadio.setToggleGroup(unitGroup);
        monthSelector.getItems().setAll(Month.values());
        weekSelector.getItems().setAll(1, 2, 3, 4);
        setToggleText();
        clear();

        //Add event listeners to radio buttons and combo boxes
        monthlyRadio.setOnAction(event -> {
            weekSelector.setDisable(true);
            setTableItems();
        });
        weeklyRadio.setOnAction(event -> {
            weekSelector.setDisable(false);
            setTableItems();
        });
        monthSelector.setOnAction(event -> setTableItems());
        weekSelector.setOnAction(event -> setTableItems());

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
     * Resets each element on this scene to its initial state.
     */
    public void clear() {
        unitGroup.selectToggle(monthlyRadio);
        monthSelector.setValue(LocalDateTime.now().getMonth());
        weekSelector.setValue(weekSelector.getItems().get(0));
        weekSelector.setDisable(true);
    }//clear

    /**
     * Returns the appointment the user has selected from the TableView.
     * @return  -the selected appointment
     */
    public Appointment getSelectedAppointment() {
        return appointmentTable.getSelectionModel().getSelectedItem();
    }//getSelectedAppointment

    /**
     * Refreshes the TableView displaying the appointments
     */
    public void refresh() {
        appointmentTable.refresh();
    }//refresh

    /**
     * Sets the items for this TableView to the provided ObservableList of appointments.
     * @param c  -the customer whose appointments to display
     */
    public void setParentCustomer(Customer c) {
        parentCustomer = c;
        setTableItems();
    }//setAppointments

    /**
     * Set the items displayed in the tableview based on the parent customer and the time range the
     * user has selected.
     */
    private void setTableItems() {
        if(weeklyRadio.isSelected()) {

        } else {
            appointmentTable.setItems(parentCustomer.getAppointmentsByMonth(monthSelector.getValue(),
                    LocalDateTime.now().getYear()));
            appointmentTable.refresh();
        }
        appointmentTable.refresh();
    }//setMonthData

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

    private void calculateDates() {
        //TODO: use LocalDate
    }
}//AppointmentOverviewTable