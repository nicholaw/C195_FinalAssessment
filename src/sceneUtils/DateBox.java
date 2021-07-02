package sceneUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.time.LocalDateTime;
import javafx.scene.layout.Pane;
import utils.Month;

public class DateBox {
    private ComboBox<Month>   monthCombo;
    private ComboBox<Integer> dayCombo;
    private ComboBox<Integer> yearCombo;
    private ObservableList<Integer> days = FXCollections.observableArrayList(new Integer[] {
            1,	2,	3,	4,	5,	6,	7,	8,	9,	10,
            11,	12,	13,	14,	15,	16,	17,	18,	19,	20,
            21,	22,	23,	24,	25,	26,	27,	28,	29,	30});

    public DateBox(LocalDateTime date) {
        monthCombo	=	new ComboBox<>(FXCollections.observableArrayList(Month.values()));
        dayCombo	=	new ComboBox<>(days);
        yearCombo   =   new ComboBox<>(FXCollections.observableArrayList(date.getYear(), date.getYear() + 1));
        setDateTime(date);
        monthCombo.setOnAction(event -> {
            updateDays();
        });
    }//constructor

    public int getDayOfMonth() {
        return dayCombo.getValue();
    }

    public Pane getDatePane() {
        var pane = new GridPane();
        pane.addRow(0, monthCombo, new Label(""), dayCombo);
        return pane;
    }

    public int getMonthOfYear() {
        return monthCombo.getValue().getMonthOfYear();
    }

    public int getYear() {
        return yearCombo.getValue();
    }

    public Node getYearPane() {
        return yearCombo;
    }

    /**
     * Sets the combo boxes to match the given LocalDateTime.
     *
     * @param currentDate	The provided LocalDateTime
     */
    public void setDateTime(LocalDateTime currentDate) {
        if(currentDate != null) {
            yearCombo.setValue(currentDate.getYear());
            monthCombo.setValue(Month.getMonth(currentDate.getMonthValue()));
            updateDays();
            dayCombo.setValue(currentDate.getDayOfMonth());
        }
    }//setDateTime

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