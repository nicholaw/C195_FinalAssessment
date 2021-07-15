package sceneUtils;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.time.ZonedDateTime;
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
	public TimeBox(ZonedDateTime time) {
		hourCombo	= new ComboBox<>(FXCollections.observableArrayList(Hour.values()));
		minuteCombo = new ComboBox<>(FXCollections.observableArrayList(Minute.values()));
		setTime(time);

		//Add elements to containers
		this.getChildren().setAll(hourCombo, new Label(":"), minuteCombo);

		//Style elements
		this.setSpacing(5);
	}//constructor

	/**
	 * Returns the user-selected hour.
	 * @return	-the selected hour
	 */
	public int getHour() {
		return hourCombo.getValue().getHourOfDay();
	}

	/**
	 * Returns the user-selected minute of the hour.
	 * @return	-the selected minute
	 */
	public int getMinute() {
		return minuteCombo.getValue().getMinuteOfHour();
	}

	/**
	 * Sets the time displayed by this TimeBox
	 * @param currentTime
	 */
	public void setTime(ZonedDateTime currentTime) {
		hourCombo.setValue(Hour.getHour(currentTime.getHour()));
		minuteCombo.setValue(Minute.getMinute(currentTime.getMinute()));
	}//setValues
}//TimeBox