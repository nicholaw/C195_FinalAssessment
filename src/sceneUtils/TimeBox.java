package sceneUtils;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.time.LocalDateTime;
import javafx.scene.layout.HBox;
import utils.Hour;
import utils.Minute;

public class TimeBox extends HBox {
	private ComboBox<Hour>    hourCombo;
	private ComboBox<Minute>  minuteCombo;
	
	public TimeBox(LocalDateTime time) {
		hourCombo	=	new ComboBox<>(FXCollections.observableArrayList(Hour.values()));
		minuteCombo =	new ComboBox<>(FXCollections.observableArrayList(Minute.values()));
		setTime(time);

		//Add elements to containers
		this.getChildren().setAll(hourCombo, new Label(":"), minuteCombo);

		//Style elements
		this.setSpacing(5);
	}//constructor

	public int getHour() {
		return hourCombo.getValue().getHourOfDay();
	}

	public int getMinute() {
		return minuteCombo.getValue().getMinuteOfHour();
	}
	
	public void setTime(LocalDateTime currentTime) {
		hourCombo.setValue(Hour.getHour(currentTime.getHour()));
		minuteCombo.setValue(Minute.getMinute(currentTime.getMinute()));
	}//setValues
}