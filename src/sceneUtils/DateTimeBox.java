package sceneUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
        var currentDateTime = LocalDateTime.now();
        date    =   new DateBox(rb, currentDateTime.toLocalDate(), error);
        start   =   new TimeBox(currentDateTime.toLocalTime());
        end     =   new TimeBox(currentDateTime.toLocalTime());
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
    public LocalDateTime endDateTime() {
        return LocalDateTime.of(date.getDate(), end.getTime());
    }

    /**
     * Returns the appointment start time  as entered by the user.
     * @return  -LocalDateTime of the start of the appointment
     */
    public LocalDateTime startDateTime() {
        return LocalDateTime.of(date.getDate(), start.getTime());
    }

    /**
     * Sets the date and times to be displayed by this scene element.
     * @param dateTime  -the LocalDateTime to be displayed
     */
    public void setDateTime(LocalDateTime dateTime) {
        date.setDate(dateTime.toLocalDate());
        start.setTime(dateTime.toLocalTime());
        end.setTime(dateTime.toLocalTime());
    }

    /**
     * Sets the start time to be displayed by this scene element.
     * @param dateTime  -the time to be displayed
     */
    public void setStart(LocalDateTime dateTime) {
        date.setDate(dateTime.toLocalDate());
        start.setTime(dateTime.toLocalTime());
    }

    /**
     * Sets the end time to be displayed by this scene element.
     * @param dateTime -the time to be displayed
     */
    public void setEnd(LocalDateTime dateTime) {
        end.setTime(dateTime.toLocalTime());
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