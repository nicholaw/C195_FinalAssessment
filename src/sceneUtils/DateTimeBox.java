package sceneUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class DateTimeBox extends GridPane {
    private Label startLabel;
    private Label endLabel;
    private DateBox date;
    private TimeBox start;
    private TimeBox end;

    public DateTimeBox(ResourceBundle rb, Label error) {
        LocalDateTime currentDateTime = LocalDateTime.now();
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

    public LocalDateTime endDateTime() {
        return LocalDateTime.of(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), end.getHour(), end.getMinute());
    }

    public LocalDateTime startDateTime() {
        return LocalDateTime.of(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), start.getHour(), start.getMinute());
    }

    public void setDateTime(LocalDateTime dateTime) {
        date.setDateTime(dateTime);
        start.setTime(dateTime);
        end.setTime(dateTime);
    }

    public void setStart(LocalDateTime dateTime) {
        date.setDateTime(dateTime);
        start.setTime(dateTime);
    }

    public void setEnd(LocalDateTime dateTime) {
        end.setTime(dateTime);
    }

    public void setResourceBundle(ResourceBundle rb) {
        date.setResourceBundle(rb);
        startLabel.setText(rb.getString("start"));
        endLabel.setText(rb.getString("end"));
    }
}
