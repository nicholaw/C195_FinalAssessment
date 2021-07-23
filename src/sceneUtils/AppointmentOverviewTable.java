package sceneUtils;

import appointment.Appointment;
import appointment.AppointmentConstants;
import customer.Customer;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.ResourceBundle;
import utils.WeekOfMonth;

/**
 * TableView for displaying appointment information for a chosen customer on the
 * appointment overview page.
 */
public class AppointmentOverviewTable extends GridPane {
    private ToggleGroup unitGroup;
    private RadioButton weeklyRadio;
    private RadioButton monthlyRadio;
    private ComboBox<Month> monthSelector;
    private ComboBox<WeekOfMonth> weekSelector;
    private TableView<Appointment> appointmentTable;
    private TableColumn<Appointment, Long> idCol;
    private TableColumn<Appointment, String> titleCol;
    private TableColumn<Appointment, String> dateCol;
    private TableColumn<Appointment, String> startCol;
    private TableColumn<Appointment, String> endCol;
    private TableColumn<Appointment, String> typeCol;
    private TableColumn<Appointment, String> descCol;
    private TableColumn<Appointment, String> locationCol;
    private TableColumn<Appointment, String> contactCol;
    private TableColumn<Appointment, String> userCol;

    private HashMap<Integer, WeekOfMonth> weeks;
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
        dateCol = new TableColumn<>();
        startCol = new TableColumn<>();
        endCol = new TableColumn<>();
        typeCol = new TableColumn<>();
        descCol = new TableColumn<>();
        locationCol = new TableColumn<>();
        contactCol = new TableColumn<>();
        userCol = new TableColumn<>();
        setColumnNames();

        //Set cell value factory for each column
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));

        //Add columns to the table
        appointmentTable.getColumns().setAll(idCol, titleCol, dateCol, startCol, endCol, typeCol,
                descCol, locationCol, contactCol, userCol);

        //Instantiate other scene elements
        unitGroup = new ToggleGroup();
        monthlyRadio = new RadioButton();
        weeklyRadio = new RadioButton();
        monthSelector = new ComboBox();
        weekSelector = new ComboBox();
        weeks = new HashMap<>();
        for(int i = 0; i < 5; i++) {
            weeks.put(i, null);
            weekSelector.getItems().add(weeks.get(i));
        }
        parentCustomer = null;

        //Set initial states for scene elements
        monthlyRadio.setToggleGroup(unitGroup);
        weeklyRadio.setToggleGroup(unitGroup);
        monthSelector.getItems().setAll(Month.values());
        setToggleText();
        clear();

        //Add event listeners to radio buttons and combo boxes
        monthlyRadio.setOnAction(event -> {
            weekSelector.setDisable(true);
            setTableItems();
        });
        weeklyRadio.setOnAction(event -> {
            weekSelector.setDisable(false);
            calculateWeeks();
            weekSelector.setValue(weekSelector.getItems().get(0));
            setTableItems();
        });
        monthSelector.setOnAction(event -> {
            if(weeklyRadio.isSelected())
                calculateWeeks();
            setTableItems();});
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
        calculateWeeks();
        weekSelector.setValue(weekSelector.getItems().get(0));
        weekSelector.setDisable(true);
        parentCustomer = null;
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
        if(parentCustomer != null) {
            if(weeklyRadio.isSelected()) {
                int mapIndex;
                try {
                    mapIndex = weekSelector.getValue().getMapIndex();
                } catch(NullPointerException e) {
                    mapIndex = 0;
                }
                appointmentTable.setItems(parentCustomer.getAppointmentsByRange(weeks.get(mapIndex).getStart(),
                        weeks.get(mapIndex).getEnd()));
            } else {
                appointmentTable.setItems(parentCustomer.getAppointmentsByMonth(monthSelector.getValue(),
                        LocalDateTime.now().getYear()));
            }
            appointmentTable.refresh();
        } else {
            appointmentTable.setItems(null);
        }
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
        locationCol.setText(rb.getString("location"));
        contactCol.setText(rb.getString("contact"));
        userCol.setText(rb.getString("user"));
    }//setColumnNames

    /**
     * Set the text of the radio buttons used for toggling between
     * monthly and weekly views.
     */
    private void setToggleText() {
        monthlyRadio.setText(rb.getString("monthly"));
        weeklyRadio.setText(rb.getString("weekly"));
    }//setToggleText

    /**
     * Returns a HashMap of the beginning and ends dates for each week of the
     * month the user selected with the month selector combo box.
     * @return -map of the start and end of each week
     */
    private void calculateWeeks() {
        var selectedMonth = monthSelector.getValue();
        var currentYear = LocalDate.now().getYear();
        var month = LocalDate.of(currentYear, selectedMonth, 1);
        int firstDayValue = month.getDayOfWeek().getValue() - 1;
        LocalDate lastDate = month;
        for(int i = 0; i < 5; i++) {
            LocalDate[] dates = new LocalDate[2];
            dates[AppointmentConstants.START_OF_WEEK] = lastDate;
            if(i == 4) {
                dates[AppointmentConstants.END_OF_WEEK] = LocalDate.of(currentYear, selectedMonth, selectedMonth.length(month.isLeapYear()));
            } else {
                dates[AppointmentConstants.END_OF_WEEK] = LocalDate.of(currentYear, selectedMonth, (7 - firstDayValue + 7 * (i)));
                lastDate = dates[AppointmentConstants.END_OF_WEEK].plusDays(1);
            }
            weeks.replace(i, WeekOfMonth.of(dates[0], dates[1], i));
        }//for
        weekSelector.getItems().removeAll(weekSelector.getItems());
        for(Integer i : weeks.keySet())
            weekSelector.getItems().add(weeks.get(i));
        weekSelector.setValue(weekSelector.getItems().get(0));
    }//calculateWeeks
}//AppointmentOverviewTable