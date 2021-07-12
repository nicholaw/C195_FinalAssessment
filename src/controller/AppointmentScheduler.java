package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Application which allows a user to add, edit, and delete customers and their
 * appointments to a linked database.
 */
public class AppointmentScheduler extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Label("Launch Error"));
        stage.setScene(scene);
        stage.setTitle("Appointment Scheduler");
        new Controller(scene);
        stage.show();
    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}