package sceneUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import java.time.LocalDate;
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

    /**
     * Constructs this scene element with the given ResourceBundle and LocalDateTime.
     * @param rb    -the ResourceBundle to use
     * @param date  -the LocalDateTime to use
     * @param error -the error label used to inform the user of any invalid date selections
     */
    public DateBox(ResourceBundle rb, LocalDate date, Label error) {
        dateLabel   =   new Label("");
        monthCombo	=	new ComboBox<>(FXCollections.observableArrayList(Month.values()));
        dayCombo	=	new ComboBox<>();
        yearCombo   =   new ComboBox<>(FXCollections.observableArrayList(date.getYear(), date.getYear() + 1));
        setDate(date);
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
     * Returns the user-selected date.
     * @return -the date selected by the user via the ComboBoxes on this scene element
     */
    public LocalDate getDate() {
        return LocalDate.of(yearCombo.getValue(), monthCombo.getValue().getMonthOfYear(), dayCombo.getValue());
    }

    /**
     * Sets the combo boxes to match the given LocalDateTime.
     * @param date	-the provided LocalDateTime
     */
    public void setDate(LocalDate date) {
        if(date != null) {
            yearCombo.setValue(date.getYear());
            monthCombo.setValue(Month.getMonth(date.getMonthValue()));
            if(dayCombo.getItems() == null || dayCombo.getItems().size() <= 0)
                initializeDayCombo();
            updateDays();
            dayCombo.setValue(date.getDayOfMonth());
        }
    }//setDateTime

    /**
     *
     */
    private void initializeDayCombo() {
        for(int i = 1; i <= monthCombo.getValue().getNumDays(); i++) {
            dayCombo.getItems().add(i);
        }
    }//initializeDayCombo

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
            dayCombo.getItems().add(dayCombo.getItems().size() + 1);
            updateDays();
        } else if(dayCombo.getItems().size() > monthCombo.getValue().getNumDays()) {
            dayCombo.getItems().remove(dayCombo.getItems().size() - 1);
            updateDays();
        }
    }//updateDays
}