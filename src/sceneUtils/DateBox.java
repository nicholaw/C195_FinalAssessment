package sceneUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import java.time.LocalDateTime;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import utils.Month;

public class DateBox extends GridPane {
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

        //Add elements
        this.add(new Label("Date"), 0, 0);
        var comboPane = new GridPane();
        comboPane.add(monthCombo, 0, 0);
        comboPane.add(dayCombo, 1, 0);
        comboPane.add(yearCombo, 2, 0);
        this.add(comboPane, 0, 1);

        //Style elements
        this.setVgap(10);
        comboPane.setHgap(10);
    }//constructor

    public DateBox(LocalDateTime date, Label error) {
        monthCombo	=	new ComboBox<>(FXCollections.observableArrayList(Month.values()));
        dayCombo	=	new ComboBox<>(days);
        yearCombo   =   new ComboBox<>(FXCollections.observableArrayList(date.getYear(), date.getYear() + 1));
        setDateTime(date);
        monthCombo.setOnAction(event -> {
            updateDays();
        });

        //Add elements
        var labelPane = new HBox(new Label("Date"), error);
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

    public int getDayOfMonth() {
        return dayCombo.getValue();
    }

    public int getMonthOfYear() {
        return monthCombo.getValue().getMonthOfYear();
    }

    public int getYear() {
        return yearCombo.getValue();
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