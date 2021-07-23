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
    private GridPane byUser;

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
     * Displays the GridPane with number of appointments by user.
     */
    public void displayByUser() {
        this.setCenter(byUser);
    }

    /**
     * Generates the GridPane that will be used to display the number of appointments by location.
     * @param locations -the number of appointments by location
     */
    private void generateByLocation(HashMap<Location, Integer> locations) {
        byLocation = new GridPane();
        int row = 0;
        for(Location l : locations.keySet()) {
            byLocation.addRow(row, new Label(l.getLocation()), new Label("" + locations.get(l)));
            row++;
        }
        byLocation.setVgap(10);
        byLocation.setHgap(20);
    }//generateByLocation

    /**
     * Generates the GridPane that will display the number of appointments by type.
     * @param types -the number of appointments by type
     */
    private void generateByType(HashMap<Type, Integer> types) {
        byType = new GridPane();
        int row = 0;
        for(Type t : types.keySet()) {
            byType.addRow(row, new Label(t.getType()), new Label("" + types.get(t)));
            row++;
        }
        byType.setVgap(10);
        byType.setHgap(20);
    }//generateByType

    /**
     * Generates the GridPane that will display the number of appointments by user.
     * @param users -the number of appointments by type
     */
    private void generateByUser(HashMap<String, Integer> users) {
        byUser = new GridPane();
        int row = 0;
        for(String str : users.keySet()) {
            byUser.addRow(row, new Label(str), new Label("" + users.get(str)));
            row++;
        }
        byUser.setVgap(10);
        byUser.setHgap(20);
    }//generateByUsers

    /**
     * Generates the GridPanes which will display the number of scheduled appointments either
     * by type or location.
     * @param types -the number of appointments by type
     * @param locations -the number of appointment by location
     */
    public void generateReports(HashMap<Type, Integer> types, HashMap<Location, Integer> locations, HashMap<String, Integer> users) {
        generateByLocation(locations);
        generateByType(types);
        generateByUser(users);
    }//generateReports
}//report