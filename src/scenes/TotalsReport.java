package scenes;

import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sceneUtils.Refreshable;
import sceneUtils.SceneCode;
import utils.Contact;
import java.util.ResourceBundle;

public class TotalsReport extends BorderPane implements Refreshable {
    private Controller controller;
    private Label sceneLabel;
    private ToggleGroup mainGroup;
    private RadioButton totalsRadio;
    private RadioButton schedulesRadio;
    private ToggleGroup categoryGroup;
    private RadioButton byTypeButton;
    private RadioButton byMonthButton;
    private RadioButton byLocationButton;
    private ComboBox<Contact> contactCombo;
    private HBox selectorPane;
    private BorderPane tablePane;
    private Button returnButton;

    public TotalsReport(Controller controller) {
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel = new Label("");
        mainGroup = new ToggleGroup();
        totalsRadio = new RadioButton();
        schedulesRadio = new RadioButton();
        categoryGroup = new ToggleGroup();
        byMonthButton = new RadioButton();
        byTypeButton  = new RadioButton();
        byLocationButton = new RadioButton();
        contactCombo = new ComboBox<>();
        returnButton = new Button("");
        setElementText();

        //Set initial states of scene elements
        totalsRadio.setToggleGroup(mainGroup);
        schedulesRadio.setToggleGroup(mainGroup);
        totalsRadio.setSelected(true);
        byMonthButton.setToggleGroup(categoryGroup);
        byTypeButton.setToggleGroup(categoryGroup);
        byLocationButton.setToggleGroup(categoryGroup);
        byMonthButton.setSelected(true);
        contactCombo.getItems().setAll(this.controller.getContacts());

        //Add event listeners
        totalsRadio.setOnAction(event -> {

        });
        schedulesRadio.setOnAction(event -> {

        });
        returnButton.setOnAction(event -> controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null));

        //Add scene elements to containers
        var titlePane = new GridPane();
        titlePane.add(sceneLabel, 0, 0);
        var mainTogglePane = new GridPane();
        mainTogglePane.add(totalsRadio, 0, 0);
        mainTogglePane.add(schedulesRadio, 1, 0);
        selectorPane = new HBox();
        tablePane = new BorderPane();
        setTotals();
        var buttonPane = new GridPane();
        buttonPane.add(returnButton, 0, 0);
        var contentPane = new GridPane();
        contentPane.add(titlePane, 0, 0);
        contentPane.add(mainTogglePane, 0, 1);
        contentPane.add(selectorPane, 0, 2);
        contentPane.add(tablePane, 0, 3);
        contentPane.add(buttonPane, 0, 4);
        this.setCenter(contentPane);

        //Style scene elements
        sceneLabel.getStyleClass().add("scene-label");
        contentPane.setVgap(10);
        contentPane.setAlignment(Pos.CENTER);
        selectorPane.setSpacing(10);
        mainTogglePane.setHgap(10);
    }

    @Override
    public void refresh(ResourceBundle rb) {
        setElementText();
    }//refresh

    private void setElementText() {
        sceneLabel.setText(controller.getString("appointment_totals"));
        totalsRadio.setText(controller.getString("totals"));
        schedulesRadio.setText(controller.getString("schedules"));
        byMonthButton.setText(controller.getString("by_month"));
        byTypeButton.setText(controller.getString("by_type"));
        byLocationButton.setText(controller.getString("by_location"));
        returnButton.setText(controller.getString("return"));
    }//setElementText

    private void setTotals() {
        selectorPane.getChildren().setAll(byMonthButton, byTypeButton, byLocationButton);
        tablePane.setCenter(new Label("Hello, Nora"));
    }

    private void setSchedules() {
        selectorPane.getChildren().setAll(contactCombo);
    }
}//
