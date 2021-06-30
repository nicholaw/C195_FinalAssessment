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

public class TimeBox extends HBox {
	private Label boxLabel;
	private ComboBox<Hour>    hourCombo;
	private ComboBox<Minute>  minuteCombo;
	private ComboBox<Month>   monthCombo;
	private ComboBox<Integer> dayCombo;
	private ObservableList<Integer> days = FXCollections.observableArrayList(new Integer[] {
		1,	2,	3,	4,	5,	6,	7,	8,	9,	10,
		11,	12,	13,	14,	15,	16,	17,	18,	19,	20,
		21,	22,	23,	24,	25,	26,	27,	28,	29,	30});
	
	public TimeBox(String labelText) {
		boxLabel	=	new Label(labelText);
		hourCombo	=	new ComboBox<>(FXCollections.observableArrayList(Hour.values()));
		minuteCombo =	new ComboBox<>(FXCollections.observableArrayList(Minute.values()));
		monthCombo	=	new ComboBox<>(FXCollections.observableArrayList(Month.values()));
		dayCombo	=	new ComboBox<>(days);
		resetValues(); //TODO: use today as initially selected day
		monthCombo.setOnAction(event -> {
			updateDays();
		});
		this.getChildren().addAll(boxLabel, monthCombo, dayCombo, hourCombo, minuteCombo);
	}//constructor
	
	public LocalDateTime getSelectedDateTime() {
		return LocalDateTime.of(LocalDateTime.now().getYear(), monthCombo.getValue().getMonthOfYear(), dayCombo.getValue(), 
				hourCombo.getValue().getHourOfDay(), minuteCombo.getValue().getMinuteOfHour(), 0);
	}//getSelectedDateTime
		
	/**
	 * Sets the combo boxes to match the given LocalDateTime.
	 *
	 * @param time	The provided LocalDateTime
	 */
	public void setDateTime(LocalDateTime time) {
	}//setDateTime
	
	public void resetValues() {
		monthCombo.setValue(Month.JAN);
		hourCombo.setValue(Hour.TWELVE);
		minuteCombo.setValue(Minute.ZERO);
		updateDays();
		dayCombo.setValue(1);
	}//setValues
	
	private void updateDays() {
		if(dayCombo.getItems().size() < monthCombo.getValue().getNumDays()) {
			days.add(new Integer(dayCombo.getItems().size() + 1));
			updateDays();
		} else if(dayCombo.getItems().size() > monthCombo.getValue().getNumDays()) {
			days.remove(new Integer(dayCombo.getItems().size()));
			updateDays();
		}
	}//updateDays
}