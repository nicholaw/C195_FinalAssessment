package sceneUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.time.LocalDateTime;
import utils.Hour;
import utils.Minute;
import utils.Month;

public class TimeBox {
	private Label boxLabel;
	private ComboBox<Hour>    hourCombo;
	private ComboBox<Minute>  minuteCombo;
	private ComboBox<Month>   monthCombo;
	private ComboBox<Integer> dayCombo;
	private ObservableList<Integer> days = FXCollections.ObservableList({
		1,	2,	3,	4,	5,	6,	7,	8,	9,	10,
		11,	12,	13,	14,	15,	16,	17,	18,	19,	20,
		21,	22,	23,	24,	25,	26,	27,	28,	29,	30});
	
	public TimeBox(String labelText) {
		boxLabel	=	new Label(labelText);
		hourCombo	=	new ComboBox<>(FXCollections.ObservableList(Hour.values()));
		minuteCombo =	new ComboBox<>(FXCollections.ObservableList(Minute.values()));
		monthCombo	=	new ComboBox<>(FXCollections.ObservableList(Month.values()));
		dayCombo	=	new ComboBox<>(days);
		setValues(); //TODO: use today as initially selected day
		monthCombo.setOnAction(event -> {
			updateDays();
		});
		this.addAll(boxLabel, monthCombo, dayCombo, hourCombo, minuteCombo);
	}//constructor
	
	public LocalDateTime getSelectedDateTime() {
		return LocalDateTime.of(2021, monthCombo.getValue().getMonthOfYear(), dayCombo.getValue(), 
				hourCombo.getValue().hourOfDay(), minuteCombo.getValue().getMinuteOfHour(), 0);
	}//getSelectedDateTime
	
	private void setValues() { 
		monthCombo.setValue(Month.JAN);
		hourCombo.setValue(Hour.TWELVE);
		minuteCombo.setValue(Minute.ZERO);
		updateDays();
		dayCombo.setValue(1);
	}//setValues
	
	private void updateDays() {
		if(dayCombo.getItems().size() < monthCombo.getValue().getNumDays()) {
			days.add(new Integer(dayCombo.getItems.size() + 1));
			updateDays;
		} else if(dayCombo.getItems().size() > monthCombo.getValue().getNumDays()) {
			days.remove(new Integer(dayCombo.getItems().size()));
			updateDays();
		}
	}//updateDays
}