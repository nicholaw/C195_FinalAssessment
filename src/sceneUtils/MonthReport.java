package sceneUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.time.Month;
import java.util.HashMap;

/**
 * Represents a report which displays the total number of appointments
 * scheduled for each month.
 */
public class MonthReport extends GridPane {
    /**
     * Constructs this report with the provided HashMap
     * @param report -the report
     */
    public MonthReport(HashMap<Month, Integer> report) {
        //Declare quarterly grid panes
        var quarter1 = new GridPane();
        var quarter2 = new GridPane();
        var quarter3 = new GridPane();
        var quarter4 = new GridPane();

        //Add labels to each quarter pane
        quarter1.add(new Label("Q1"), 0, 0, 2, 1);
        quarter2.add(new Label("Q2"), 0, 0, 2, 1);
        quarter3.add(new Label("Q3"), 0, 0, 2, 1);
        quarter4.add(new Label("Q4"), 0, 0, 2, 1);

        //Add appointment data to each quarter pane
        for(Month m : report.keySet()) {
            switch (m) {
                case JANUARY:
                    quarter1.add(new Label(m.name() + ":"), 0, 1, 1, 1);
                    quarter1.add(new Label(report.get(m) + ""), 1, 1, 1, 1);
                    break;
                case FEBRUARY:
                    quarter1.add(new Label(m.name() + ":"), 0, 2, 1, 1);
                    quarter1.add(new Label(report.get(m) + ""), 1, 2, 1,1);
                    break;
                case MARCH:
                    quarter1.add(new Label(m.name() + ":"), 0, 3, 1, 1);
                    quarter1.add(new Label(report.get(m) + ""), 1, 3, 1, 1);
                    break;
                case APRIL:
                    quarter2.add(new Label(m.name() + ":"), 0, 1, 1, 1);
                    quarter2.add(new Label(report.get(m) + ""), 1, 1, 1, 1);
                    break;
                case MAY:
                    quarter2.add(new Label(m.name() + ":"), 0, 2, 1, 1);
                    quarter2.add(new Label(report.get(m) + ""), 1, 2, 1,1);
                    break;
                case JUNE:
                    quarter2.add(new Label(m.name() + ":"), 0, 3, 1, 1);
                    quarter2.add(new Label(report.get(m) + ""), 1, 3, 1, 1);
                    break;
                case JULY:
                    quarter3.add(new Label(m.name() + ":"), 0, 1, 1, 1);
                    quarter3.add(new Label(report.get(m) + ""), 1, 1, 1, 1);
                    break;
                case AUGUST:
                    quarter3.add(new Label(m.name() + ":"), 0, 2, 1, 1);
                    quarter3.add(new Label(report.get(m) + ""), 1, 2, 1,1);
                    break;
                case SEPTEMBER:
                    quarter3.add(new Label(m.name() + ":"), 0, 3, 1, 1);
                    quarter3.add(new Label(report.get(m) + ""), 1, 3, 1, 1);
                    break;
                case OCTOBER:
                    quarter4.add(new Label(m.name() + ":"), 0, 1, 1, 1);
                    quarter4.add(new Label(report.get(m) + ""), 1, 1, 1, 1);
                    break;
                case NOVEMBER:
                    quarter4.add(new Label(m.name() + ":"), 0, 2, 1, 1);
                    quarter4.add(new Label(report.get(m) + ""), 1, 2, 1,1);
                    break;
                case DECEMBER:
                    quarter4.add(new Label(m.name() + ":"), 0, 3, 1, 1);
                    quarter4.add(new Label(report.get(m) + ""), 1, 3, 1, 1);
                    break;
            }//switch
        }//for Month : report.keySet

        //Add elements to containers
        this.add(quarter1, 0, 0);
        this.add(quarter2, 0, 1);
        this.add(quarter3, 1, 0);
        this.add(quarter4, 1, 1);

        //Style elements
        this.setVgap(30);
        this.setHgap(30);
    }//constructor
}//MonthReport
