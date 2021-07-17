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
import java.util.regex.Pattern;

public class HeaderPane extends BorderPane {
    private Controller controller;
    //private Label clock;
    private Label languageZoneLabel;
    private ComboBox languageZoneCombo;
    //private ComboBox timeZoneCombo;

    public HeaderPane(Controller controller, ObservableList<SupportedLocale> supportedLocales, SupportedLocale defaultLocale) {
        this.controller = controller;
        /*
        clock = new Label(ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        timeZoneCombo = new ComboBox<String>();
        ZoneId.getAvailableZoneIds()
                .stream()
                .filter(str -> Pattern.compile("GMT[\\+\\-]\\d{1,2}").matcher(str).find())
                .forEach(str -> timeZoneCombo.getItems().add(str));
         */
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

        //Add elements to containers
        //var clockPane = new HBox(clock, timeZoneCombo);
        var languagePane = new HBox(languageZoneLabel, languageZoneCombo);
        //this.setLeft(clockPane);
        this.setRight(languagePane);

        //Style elements and containers
        languagePane.setAlignment(Pos.CENTER_RIGHT);
        languagePane.setSpacing(10);
        //clockPane.setAlignment(Pos.CENTER_LEFT);
        //clockPane.setSpacing(20);
        this.setPadding(new Insets(0, 10, 0, 10));
    }
}