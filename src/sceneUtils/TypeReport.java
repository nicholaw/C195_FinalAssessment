package sceneUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import utils.Type;
import java.util.HashMap;

/**
 * Represents a report of the total number of appointments scheduled for
 * each type.
 */
public class TypeReport extends GridPane {
    /**
     * Constructs the report with the provided HashMap
     * @param report -the report data
     */
    public TypeReport(HashMap<Type, Integer> report) {
        int row = 0;
        int col = 0;
        boolean leftCol = true;

        //Add report data
        for(Type t : report.keySet()) {
            var itemPane = new GridPane();
            itemPane.add(new Label(t + ":"), 0, 0);
            itemPane.add(new Label("" + report.get(t)), 1, 0);
            itemPane.setHgap(10);
            this.add(itemPane, col, row);
            col++;
            if(leftCol) {
               leftCol = false;
            } else {
                leftCol = true;
                col = 0;
                row++;
            }
        }//for Type : report

        //Style
        this.setHgap(30);
        this.setVgap(10);
    }//constructor
}//TypeReport
