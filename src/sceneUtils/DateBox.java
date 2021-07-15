package sceneUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import utils.Month;

/**
 * Displays combo boxes and labels which allow the user to select the date of an appointment
 * when scheduling or editing an appointment.
 */
public class DateBox extends GridPane {
    private Label dateLabel;
    private ResourceBundle rb;
    private ComboBox<Month>   monthCombo;
    private ComboBox<Integer> dayCombo;
    private ComboBox<Integer> yearCombo;
    private ObservableList<Integer> days = FXCollections.observableArrayList(new Integer[] {
            1,	2,	3,	4,	5,	6,	7,	8,	9,	10,
            11,	12,	13,	14,	15,	16,	17,	18,	19,	20,
            21,	22,	23,	24,	25,	26,	27,	28,	29,	30});

    /**
     * Constructs this scene element with the given ResourceBundle and LocalDateTime.
     * @param rb    -the ResourceBundle to use
     * @param date  -the LocalDateTime to use
     * @param error -the error label used to inform the user of any invalid date selections
     */
    public DateBox(ResourceBundle rb, ZonedDateTime date, Label error) {
        dateLabel   =   new Label("");
        monthCombo	=	new ComboBox<>(FXCollections.observableArrayList(Month.values()));
        dayCombo	=	new ComboBox<>(days);
        yearCombo   =   new ComboBox<>(FXCollections.observableArrayList(date.getYear(), date.getYear() + 1));
        setDateTime(date);
        setResourceBundle(rb);
        monthCombo.setOnAction(event -> {
            updateDays();
        });

        //Add elements
        var labelPane = new HBox(dateLabel, error);
        this.add(labelPane, 0, 0);
        var comboPane = new GridPane();
        comboPane.add(monthCombo, 0, 0);
        comboPane.add(dayCombo, 1, 0);
        comboPane.add(yearCombo, 2, 0);
        this.add(comboPane, 0, 1);

        //Style elements
        this.setVgap(10);
        labelPane.setSpacing(20);
        comboPane.setHgap(10);
    }//constructor

    /**
     * Returns the user-selected day of the month.
     * @return -the selected day
     */
    public int getDayOfMonth() {
        return dayCombo.getValue();
    }

    /**
     * Returns the user-selected month.
     * @return -the selected month
     */
    public int getMonthOfYear() {
        return monthCombo.getValue().getMonthOfYear();
    }

    /**
     * Returns the user-selected year
     * @return -the selected year
     */
    public int getYear() {
        return yearCombo.getValue();
    }

    /**
     * Sets the combo boxes to match the given LocalDateTime.
     * @param currentDate	-the provided LocalDateTime
     */
    public void setDateTime(ZonedDateTime currentDate) {
        if(currentDate != null) {
            yearCombo.setValue(currentDate.getYear());
            monthCombo.setValue(Month.getMonth(currentDate.getMonthValue()));
            updateDays();
            dayCombo.setValue(currentDate.getDayOfMonth());
        }
    }//setDateTime

    /**
     * Sets the ResourceBundle used by this scene element.
     * @param rb -the ResourceBundle to use
     */
    public void setResourceBundle(ResourceBundle rb) {
        this.rb = rb;
        dateLabel.setText(this.rb.getString("date"));
    }

    /**
     * Updates the number of days in the day combo box to match the number of days in the selected month.
     */
    private void updateDays() {
        if(dayCombo.getItems().size() < monthCombo.getValue().getNumDays()) {
            days.add(dayCombo.getItems().size() + 1);
            updateDays();
        } else if(dayCombo.getItems().size() > monthCombo.getValue().getNumDays()) {
            days.remove(dayCombo.getItems().size());
            updateDays();
        }
    }//updateDays
}