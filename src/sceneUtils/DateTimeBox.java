package sceneUtils;

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

        this.addRow(0, new Label("Date"), date.getDatePane(), date.getYearPane());
        this.addRow(1, new Label("Start"), start, new Label("End"), end);
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
}
