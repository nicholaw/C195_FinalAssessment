package sceneUtils;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.time.LocalDateTime;
import utils.Hour;
import utils.Minute;

public class TimeBox extends GridPane {
	private ComboBox<Hour>    hourCombo;
	private ComboBox<Minute>  minuteCombo;
	
	public TimeBox(LocalDateTime time) {
		hourCombo	=	new ComboBox<>(FXCollections.observableArrayList(Hour.values()));
		minuteCombo =	new ComboBox<>(FXCollections.observableArrayList(Minute.values()));
		setTime(time);
		this.addRow(0, hourCombo, new Label(" : "), minuteCombo);
	}//constructor

	public int getHour() {
		return hourCombo.getValue().getHourOfDay();
	}

	public int getMinute() {
		return minuteCombo.getValue().getMinuteOfHour();
	}
	
	public void setTime(LocalDateTime currentTime) {
		System.out.printf("%d:%d\n", currentTime.getHour(), currentTime.getMinute());
		hourCombo.setValue(Hour.getHour(currentTime.getHour()));
		minuteCombo.setValue(Minute.getMinute(currentTime.getMinute()));
	}//setValues
}