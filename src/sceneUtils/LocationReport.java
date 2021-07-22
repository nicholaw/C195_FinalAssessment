package sceneUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import utils.Location;
import java.util.HashMap;

/**
 * Represents a report detailing the total number of
 * scheduled appointments for each location.
 */
public class LocationReport extends GridPane {
    /**
     * Constructs the report with the provided HashMap.
     * @param report -the report data
     */
    public LocationReport(HashMap<Location, Integer> report) {
        int row = 0;
        for(Location l : report.keySet()) {
            this.add(new Label("" + l), 0, row);
            this.add(new Label("" + report.get(l)), 1, row);
            row++;
        }

        //Style
        this.setHgap(10);
        this.setVgap(10);
    }//constructor
}//LocationReport
