package scenes;

import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sceneUtils.Refreshable;
import sceneUtils.Report;
import sceneUtils.SceneCode;
import utils.Month;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Displays a report of the number of appointments by month or by location.
 */
public class ReportOverview extends BorderPane implements Refreshable {
    private Controller controller;
    private Label sceneLabel;
    private ToggleGroup selectionGroup;
    private RadioButton byTypeToggle;
    private RadioButton byLocationToggle;
    private ComboBox<String> months;
    private Button returnButton;
    private Report appointmentReport;

    /**
     * Constructs this scene with the given Controller.
     * @param controller -the application's main controller
     */
    public ReportOverview(Controller controller) {
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel = new Label("");
        byTypeToggle = new RadioButton();
        byLocationToggle = new RadioButton();
        selectionGroup = new ToggleGroup();
        months = new ComboBox<>();
        returnButton = new Button("");
        appointmentReport = new Report();

        //Set initial states of scene elements
        byTypeToggle.setToggleGroup(selectionGroup);
        byLocationToggle.setToggleGroup(selectionGroup);
        selectionGroup.selectToggle(byTypeToggle);
        var currentDate = LocalDateTime.now();
        for(Month m : Month.values()) {
            months.getItems().add(m.getName() + " " + currentDate.getYear());
        }
        months.setValue(Month.getMonth(currentDate.getMonthValue()).getName() + " " + currentDate.getYear());
        setElementText();

        //Add event listeners to button and toggles
        returnButton.setOnAction(event -> {
            this.clear();
            controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
        });
        byTypeToggle.setOnAction(event -> {

        });
        byLocationToggle.setOnAction(event -> {

        });

        //Add elements to containers
        var togglePane = new HBox(byTypeToggle, byLocationToggle);
        var contentPane = new GridPane();
        contentPane.add(sceneLabel, 0, 0);
        contentPane.add(months, 0, 1);
        contentPane.add(togglePane, 0, 2);
        contentPane.add(appointmentReport, 0, 3);
        contentPane.add(returnButton, 0, 4);
        this.setCenter(contentPane);

        //Style scene elements
        contentPane.setAlignment(Pos.CENTER);
        contentPane.setVgap(10);
        togglePane.setSpacing(10);
        sceneLabel.getStyleClass().add("scene-label");
    }

    /**
     * Returns each scene element to its default state when the controller transitions away
     * from this scene
     */
    public void clear() {
        selectionGroup.selectToggle(byTypeToggle);
    }//clear

    /**
     * Generates and displays the report to be viewed when the controller transitions
     * to this scene.
     */
    public void initiate() {

    }//initiate

    @Override
    public void refresh(ResourceBundle rb) {
        setElementText();
    }//refresh

    /**
     * Sets the text of each scene element based on the ResourceBundle being used.
     */
    public void setElementText() {
        sceneLabel.setText(controller.getResourceBundle().getString("monthly_appointment_report"));
        byTypeToggle.setText(controller.getResourceBundle().getString("by_type"));
        byLocationToggle.setText(controller.getResourceBundle().getString("by_location"));
        returnButton.setText(controller.getResourceBundle().getString("return"));
    }//setElementText
}//ReportOverview