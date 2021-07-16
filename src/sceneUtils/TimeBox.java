package sceneUtils;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.scene.layout.HBox;
import utils.Hour;
import utils.Minute;

/**
 * Displays labels and combo boxes for selecting a time of day. Used for selecting the start
 * and end times of a customer appointment.
 */
public class TimeBox extends HBox {
	private ComboBox<Hour>    hourCombo;
	private ComboBox<Minute>  minuteCombo;

	/**
	 * Constructs this scene element with the provided LocalTime.
	 * @param time -the initial time to set
	 */
	public TimeBox(LocalTime time) {
		hourCombo	= new ComboBox<>(FXCollections.observableArrayList(Hour.values()));
		minuteCombo = new ComboBox<>(FXCollections.observableArrayList(Minute.values()));
		setTime(time);

		//Add elements to containers
		this.getChildren().setAll(hourCombo, new Label(":"), minuteCombo);

		//Style elements
		this.setSpacing(5);
	}//constructor

	public LocalTime getTime() {
		return LocalTime.of(hourCombo.getValue().getHourOfDay(), minuteCombo.getValue().getMinuteOfHour());
	}

	/**
	 * Sets the time displayed by this TimeBox
	 * @param time -the time to set
	 */
	public void setTime(LocalTime time) {
		hourCombo.setValue(Hour.getHour(time.getHour()));
		minuteCombo.setValue(Minute.getMinute(time.getMinute()));
	}//setValues
}//TimeBox