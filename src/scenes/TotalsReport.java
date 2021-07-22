package scenes;

import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import sceneUtils.*;
import utils.Contact;
import java.util.HashMap;
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
    private GridPane selectorPane;
    private BorderPane tablePane;
    private Button returnButton;
    private MonthReport monthReport;
    private LocationReport locationReport;
    private TypeReport typeReport;

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
        try {
            contactCombo.setValue(contactCombo.getItems().get(0));
        } catch(NullPointerException e) {
            contactCombo.setValue(null);
        }

        //Add event listeners
        totalsRadio.setOnAction(event -> {
            setTotals();
        });
        schedulesRadio.setOnAction(event -> {
            setSchedules();
        });
        byMonthButton.setOnAction(event -> tablePane.setCenter(monthReport));
        byTypeButton.setOnAction(event -> tablePane.setCenter(typeReport));
        byLocationButton.setOnAction(event -> tablePane.setCenter(locationReport));
        returnButton.setOnAction(event -> controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null));


        //Add scene elements to containers
        var titlePane = new GridPane();
        titlePane.add(sceneLabel, 0, 0);
        selectorPane = new GridPane();
        //GridPane.add(Node, col, row, colSpan, rowSpan);
        selectorPane.add(totalsRadio, 0, 0, 1, 1);
        selectorPane.add(schedulesRadio, 1, 0, 1, 1);
        tablePane = new BorderPane();
        setTotals();
        var buttonPane = new GridPane();
        buttonPane.add(returnButton, 0, 0);
        var contentPane = new GridPane();
        contentPane.add(titlePane, 0, 0);
        contentPane.add(selectorPane, 0, 1);
        contentPane.add(tablePane, 0, 2);
        contentPane.add(buttonPane, 0, 3);
        this.setCenter(contentPane);

        //Style scene elements
        sceneLabel.getStyleClass().add("scene-label");
        contentPane.setVgap(10);
        contentPane.setAlignment(Pos.CENTER);
        selectorPane.setHgap(10);
        selectorPane.setVgap(10);
    }//constructor

    private void parseReports(HashMap[] reports) {
        monthReport = new MonthReport(reports[0]);
        typeReport = new TypeReport(reports[1]);
        locationReport = new LocationReport(reports[2]);
    }//parseReports

    public void initialize() {
        parseReports(controller.getReports());
        totalsRadio.setSelected(true);
        byMonthButton.setSelected(true);
        tablePane.setCenter(monthReport);
    }//initialize

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
        selectorPane.getChildren().remove(contactCombo);
        selectorPane.add(byMonthButton, 0, 1, 1, 1);
        selectorPane.add(byTypeButton, 1, 1, 1, 1);
        selectorPane.add(byLocationButton, 2, 1, 1, 1);
        byMonthButton.setSelected(true);
        tablePane.setCenter(monthReport);
    }//setTotals

    private void setSchedules() {
        selectorPane.getChildren().removeAll(byMonthButton, byTypeButton, byLocationButton);
        selectorPane.add(contactCombo, 0, 1, 2, 1);
        tablePane.setCenter(null);
    }//setSchedules
}//
