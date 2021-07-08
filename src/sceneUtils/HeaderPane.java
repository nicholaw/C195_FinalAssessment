package sceneUtils;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class HeaderPane extends BorderPane {
    private Label currentTimeLabel;
    private Label languageZoneLabel;
    private ComboBox languageZoneCombo;

    public HeaderPane() {
        currentTimeLabel = new Label("08:00 EST"); //TODO: placeholder; Time zone separate label?
        languageZoneLabel = new Label("Language: ");
        languageZoneCombo = new ComboBox<String>();
        languageZoneCombo.getItems().addAll("EN", "FR");
        languageZoneCombo.setValue("EN");
        HBox clockPane = new HBox(currentTimeLabel);
        clockPane.setAlignment(Pos.CENTER_LEFT);
        HBox languagePane = new HBox(languageZoneLabel, languageZoneCombo);
        languagePane.setAlignment(Pos.CENTER_RIGHT);
        this.setLeft(clockPane);
        this.setRight(languagePane);
    }
}
