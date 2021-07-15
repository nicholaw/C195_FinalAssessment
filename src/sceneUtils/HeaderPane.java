package sceneUtils;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import localization.SupportedLocale;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HeaderPane extends BorderPane {
    private Controller controller;
    private Label clock;
    private Label languageZoneLabel;
    private ComboBox languageZoneCombo;
    private ComboBox timeZoneCombo;

    public HeaderPane(Controller controller, ObservableList<SupportedLocale> supportedLocales, SupportedLocale defaultLocale) {
        this.controller = controller;
        clock = new Label(ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        timeZoneCombo = new ComboBox<String>();
        timeZoneCombo.getItems().setAll("EST", "GMT");
        timeZoneCombo.setValue(timeZoneCombo.getItems().get(0));
        languageZoneLabel = new Label(this.controller.getResourceBundle().getString("language"));
        languageZoneCombo = new ComboBox<>(supportedLocales);
        languageZoneCombo.setValue(defaultLocale);

        //Add event listeners to elements
        languageZoneCombo.setOnAction(event -> {
            SupportedLocale sl = (SupportedLocale)languageZoneCombo.getValue();
            if(sl != null) {
                if(!(sl.equals(this.controller.getCurrentLocale()))) {
                    controller.setLocale(sl);
                    languageZoneLabel.setText(controller.getResourceBundle().getString("language"));
                }
            }
        });
        timeZoneCombo.setOnAction(event -> {
            //TODO: populate method
        });

        //Add elements to containers
        var clockPane = new HBox(clock, timeZoneCombo);
        var languagePane = new HBox(languageZoneLabel, languageZoneCombo);
        this.setLeft(clockPane);
        this.setRight(languagePane);

        //Style elements and containers
        clockPane.setAlignment(Pos.CENTER_LEFT);
        languagePane.setAlignment(Pos.CENTER_RIGHT);
        this.setPadding(new Insets(0, 10, 0, 10));
    }
}