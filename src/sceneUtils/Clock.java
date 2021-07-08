package sceneUtils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock extends HBox {
    private Label clockFace;
    private LocalTime time;

    public Clock() {
        //Instantiate elements
        time = LocalTime.now();
        clockFace = new Label(time.format(DateTimeFormatter.ofPattern("HH:mm")));

        //Add elements to containers
        this.getChildren().add(clockFace);

        //Style elements and containers
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

    private void run() {

    }
}
