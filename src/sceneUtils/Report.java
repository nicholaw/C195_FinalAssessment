package sceneUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import utils.Location;
import utils.Type;
import java.util.HashMap;

/**
 * Represents a monthly report which displays the number of scheduled appointments for a given month
 * either by appointment type or location.
 */
public class Report extends BorderPane {
    private GridPane byType;
    private GridPane byLocation;

    public Report() {
        byType = new GridPane();
        byLocation = new GridPane();

        //style elements
        byType.setVgap(10);
        byLocation.setVgap(10);
    }//constructor

    /**
     * Displays the GridPane with the number of appointments by location.
     */
    public void displayByLocation() {
        this.setCenter(byLocation);
    }//displayByLocation

    /**
     * Displays the GridPane with number of appointments by type.
     */
    public void displayByType() {
        this.setCenter(byType);
    }//displayByType

    /**
     * Generates the GridPane that will be used to display the number of appointments by location.
     * @param locations -the number of appointments by location
     */
    private void generateByLocation(HashMap<Location, Integer> locations) {
        int row = 0;
        for(Location l : locations.keySet()) {
            byLocation.addRow(row, new Label(l.getLocation()), new Label("" + locations.get(l)));
            row++;
        }
    }//generateByLocation

    /**
     * Generates the GridPane that will display the number of appointments by type.
     * @param types -the number of appointments by type
     */
    private void generateByType(HashMap<Type, Integer> types) {
        int row = 0;
        for(Type t : types.keySet()) {
            byType.addRow(row, new Label(t.getType()), new Label("" + types.get(t)));
            row++;
        }
    }//generateByType

    /**
     * Generates the GridPanes which will display the number of scheduled appointments either
     * by type or location.
     * @param types -the number of appointments by type
     * @param locations -the number of appointment by location
     */
    public void generateReports(HashMap<Type, Integer> types, HashMap<Location, Integer> locations) {
        generateByLocation(locations);
        generateByType(types);
    }//generateReports
}//report