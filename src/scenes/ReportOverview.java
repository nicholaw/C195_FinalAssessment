package scenes;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
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
    private ComboBox<MonthlyReport> months;
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
        months.setItems(MonthlyReport.getMonths());
        setElementText();

        //Add event listeners to button and toggles
        returnButton.setOnAction(event -> {
            this.clear();
            controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
        });
        months.setOnAction(event -> {
            HashMap[] reports = controller.getMonthlyReports(months.getValue().getMonthToReport());
            appointmentReport.generateReports(reports[0], reports[1]);
            if(byTypeToggle.isSelected())
                appointmentReport.displayByType();
            else
                appointmentReport.displayByLocation();
        });
        byTypeToggle.setOnAction(event -> {
            appointmentReport.displayByType();
        });
        byLocationToggle.setOnAction(event -> {
            appointmentReport.displayByLocation();
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
        //set value of months to the current month
        months.setValue(MonthlyReport.getMonthToReport(LocalDateTime.now().getMonthValue()));
        //set byType toggle as selected
        byTypeToggle.setSelected(true);
        //get reports form db and generate gridPanes to display them
        HashMap[] reports = controller.getMonthlyReports(months.getValue().getMonthToReport());
        appointmentReport.generateReports(reports[0], reports[1]);
        //display the reports by type
        appointmentReport.displayByType();
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

    /**
     * Represents a monthly report which can be generated and displayed on the report overview scene.
     */
    private enum MonthlyReport {
        JAN(1),
        FEB(2),
        MAR(3),
        APR(4),
        MAY(5),
        JUN(6),
        JUL(7),
        AUG(8),
        SEP(9),
        OCT(10),
        NOV(11),
        DEC(12);

        private ZonedDateTime monthToReport;

        /**
         * Constructs this MonthlyReport for the given month of the year.
         * @param monthOfYear -the month of the year
         */
        MonthlyReport(int monthOfYear) {
            monthToReport = ZonedDateTime.of(LocalDateTime.of(LocalDateTime.now().getYear(), monthOfYear, 1, 0, 0), ZoneId.ofOffset("UTC", ZoneOffset.UTC));
        }

        /**
         * Returns the LocalDateTime associated with this MonthlyReport.
         * @return -the month to report
         */
        public ZonedDateTime getMonthToReport() {
            return monthToReport;
        }

        /**
         * Returns the MonthlyReport whose monthToReport matches the provided month of the year. Returns
         * MonthlyReport for January if no such month exists.
         * @param monthOfYear -the month of the year to match
         * @return -the matching MonthlyReport
         */
        public static MonthlyReport getMonthToReport(int monthOfYear) {
            for(MonthlyReport m : MonthlyReport.values()) {
                if(m.monthToReport.getMonthValue() == monthOfYear)
                    return m;
            }
            return JAN;
        }//getMonthToReport

        /**
         * Returns an ObservableList of all the months which can be reported.
         * @return -the list of reportable months
         */
        public static ObservableList<MonthlyReport> getMonths() {
            return FXCollections.observableArrayList(MonthlyReport.values());
        }

        @Override
        public String toString() {
            return monthToReport.getMonth().toString() + ", " + monthToReport.getYear();
        }
    }//MonthlyReport
}//ReportOverview