package sceneUtils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;

public class DateTimeBox extends GridPane {
    private DateBox date;
    private TimeBox start;
    private TimeBox end;

    public DateTimeBox() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        date    =   new DateBox(currentDateTime);
        start   =   new TimeBox(currentDateTime);
        end     =   new TimeBox(currentDateTime);

        //Add elements
        var startPane = new GridPane();
        startPane.add(new Label("Start"), 0, 0);
        startPane.add(start, 0, 1);
        var endPane = new GridPane();
        endPane.add(new Label("End"), 0, 0);
        endPane.add(end, 0, 1);
        var timePane = new GridPane();
        timePane.add(startPane, 0, 0);
        timePane.add(endPane, 1, 0);
        this.add(date, 0, 0);
        this.add(timePane, 0, 1);

        //Style elements
        this.setVgap(10);
        this.setHgap(10);
        timePane.setHgap(10);
        timePane.setAlignment(Pos.CENTER);
        startPane.setAlignment(Pos.CENTER);
        endPane.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
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
}
