package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AppointmentScheduler extends Application
{
    private final static String STYLE_URL = "styles\\style.css";

    @Override
    public void start(Stage stage)
    {
        Scene scene = new Scene(new Label("Launch Error"));
        stage.setScene(scene);
        stage.setTitle("Appointment Scheduler");
        Controller controller = new Controller(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}