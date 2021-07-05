package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.time.LocalDateTime;

public class AppointmentScheduler extends Application {
    private final static String STYLE_URL = "styles\\style.css";
    private Controller controller;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Label("Launch Error"));
        stage.setScene(scene);
        stage.setTitle("Appointment Scheduler");
        controller = new Controller(scene);
        stage.show();
    }

    @Override
    public void stop() {
        if(controller.getCurrentUser() != null)
            controller.storeIds(LocalDateTime.now().getYear());
    }

    public static void main(String[] args) {
        launch(args);
    }
}