package sceneUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * Displays combo boxes and labels for selecting the date, start time, and end time of an
 * appointment when scheduling or editing an appointment.
 */
public class DateTimeBox extends GridPane {
    private Label startLabel;
    private Label endLabel;
    private DateBox date;
    private TimeBox start;
    private TimeBox end;

    /**
     * Constructs this scene element with the given ResourceBundle.
     * @param rb    -the ResourceBundle to use
     * @param error -label to display any errors with the selected date or times
     */
    public DateTimeBox(ResourceBundle rb, Label error) {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        date    =   new DateBox(rb, currentDateTime, error);
        start   =   new TimeBox(currentDateTime);
        end     =   new TimeBox(currentDateTime);
        startLabel  = new Label("");
        endLabel    = new Label("");
        setResourceBundle(rb);

        //Add elements
        var startPane = new GridPane();
        startPane.add(startLabel, 0, 0);
        startPane.add(start, 0, 1);
        var endPane = new GridPane();
        endPane.add(endLabel, 0, 0);
        endPane.add(end, 0, 1);
        var timePane = new GridPane();
        timePane.add(startPane, 0, 0);
        timePane.add(endPane, 1, 0);
        this.add(date, 0, 0);
        this.add(timePane, 0, 1);

        //Style elements
        this.setVgap(10);
        this.setHgap(10);
        timePane.setHgap(30);
    }

    /**
     * Returns the appointment end time as entered by the user.
     * @return  -LocalDateTime of the end of the appointment
     */
    public ZonedDateTime endDateTime() {
        return ZonedDateTime.of(LocalDateTime.of(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), end.getHour(), end.getMinute()), ZoneId.systemDefault());
    }

    /**
     * Returns the appointment start time  as entered by the user.
     * @return  -LocalDateTime of the start of the appointment
     */
    public ZonedDateTime startDateTime() {
        return ZonedDateTime.of(LocalDateTime.of(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), start.getHour(), start.getMinute()), ZoneId.systemDefault());
    }

    /**
     * Sets the date and times to be displayed by this scene element.
     * @param dateTime  -the LocalDateTime to be displayed
     */
    public void setDateTime(ZonedDateTime dateTime) {
        date.setDateTime(dateTime);
        start.setTime(dateTime);
        end.setTime(dateTime);
    }

    /**
     * Sets the start time to be displayed by this scene element.
     * @param dateTime  -the time to be displayed
     */
    public void setStart(ZonedDateTime dateTime) {
        date.setDateTime(dateTime);
        start.setTime(dateTime);
    }

    /**
     * Sets the end time to be displayed by this scene element.
     * @param dateTime -the time to be displayed
     */
    public void setEnd(ZonedDateTime dateTime) {
        end.setTime(dateTime);
    }

    /**
     * Sets the ResourceBundle to be used.
     * @param rb -the ResourceBundle
     */
    public void setResourceBundle(ResourceBundle rb) {
        date.setResourceBundle(rb);
        startLabel.setText(rb.getString("start"));
        endLabel.setText(rb.getString("end"));
    }
}