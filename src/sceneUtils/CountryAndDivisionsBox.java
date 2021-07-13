package sceneUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import utils.Country;
import utils.Division;
import java.util.ResourceBundle;

/**
 * Displays ComboBoxes for selecting the country and region when adding or editing
 * a Customer.
 */
public class CountryAndDivisionsBox extends GridPane {
    private ComboBox<Country> countryCombo;
    private ComboBox<Division> firstLevelDivisionsCombo;
    private final Label countryLabel;
    private final Label divisionLabel;
    private ResourceBundle rb;

    /**
     * Constructs this Object with the given list of countries and the given resource bundle.
     * @param countries -the list of supported countries
     * @param rb -the provided ResourceBundle
     */
    public CountryAndDivisionsBox(ObservableList<Country> countries, ResourceBundle rb) {
        this.rb = rb;

        //Instantiate labels
        countryLabel = new Label("");
        divisionLabel = new Label("");
        setLabelText();

        //Instantiate country combo box and add countries from db to combo box
        countryCombo = new ComboBox<>();
        countryCombo.getItems().setAll(countries);
        if(countryCombo.getItems().size() > 0)
            countryCombo.setValue(countryCombo.getItems().get(0));
        else
            countryCombo.setValue(null);

        //Declare first-level division combo box and add divisions based on the selected country
        firstLevelDivisionsCombo = new ComboBox<>();
        updateFirstDivisions();

        //Add event handler to country combo box to update first-level division combo box when a new country is selected
        countryCombo.setOnAction(event -> {
            updateFirstDivisions();
        });

        //Add elements to this pane
        this.add(countryLabel, 0, 0);   this.add(divisionLabel, 1, 0);
        this.add(countryCombo, 0, 1);   this.add(firstLevelDivisionsCombo, 1, 1);

        //Style this pane
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(10);
    }//constructor

    /**
     * Returns the country selected by the user via the country combo box.
     * @return -the selected country
     */
    public Country getSelectedCountry()
    {
        return countryCombo.getValue();
    }

    /**
     * Returns the first-level-division selected by the user via the first-level-division
     * combo box.
     * @return  -the selected division
     */
    public Division getSelectedDivision()
    {
        return firstLevelDivisionsCombo.getValue();
    }

    /**
     * Sets the selected values of the country and division combo boxes to the first item in each
     * box. Sets the value to null if no such items exist.
     */
    public void reset() {
        if(countryCombo.getItems().size() > 0) {
            countryCombo.setValue(countryCombo.getItems().get(0));
            updateFirstDivisions();
        } else {
            countryCombo.setValue(null);
            firstLevelDivisionsCombo.setValue(null);
        }
    }//reset

    /**
     * Sets the text of each label in this scene element according to the ResourceBundle being used.
     */
    private void setLabelText() {
        countryLabel.setText(rb.getString("country"));
        divisionLabel.setText(rb.getString("first_level_division"));
    }

    /**
     * Sets the ResourceBundle being used by this scene element.
     * @param rb    -the ResourceBundle to use
     */
    public void setResourceBundle(ResourceBundle rb) {
        this.rb = rb;
        setLabelText();
    }

    /**
     * Sets the value of the country combo box to the given country. Sets the value of the country combo box
     * to null if the combo box does not contain any such country.
     * @param c -the country to set
     */
    public void setSelectedCountry(Country c) {
        if(c != null) {
            if(countryCombo.getItems().contains(c)) {
                countryCombo.setValue(c);
            } else {
                countryCombo.setValue(null);
            }
        } else {
            countryCombo.setValue(null);
        }
        updateFirstDivisions();
    }//setSelectedCountry

    /**
     * Sets the value of the division combo box to the given first-level-division. Sets the value of the division
     * combo box to null if the combo box does not contain any such division.
     * @param div -the first-level-division to set
     */
    public void setSelectedDivision(Division div) {
        if(div != null) {
            if(firstLevelDivisionsCombo.getItems().contains(div)) {
                firstLevelDivisionsCombo.setValue(div);
            } else {
                firstLevelDivisionsCombo.setValue(null);
            }
        } else {
			firstLevelDivisionsCombo.setValue(null);
		}
    }//setSelectedDivision

    /**
     * Updates the first-level-division combo box to match the selected value of the country combo box.
     */
    private void updateFirstDivisions() {
        //Set items to first-level divisions of the selected country
        if(countryCombo.getValue() != null) {
            firstLevelDivisionsCombo.setItems(FXCollections.observableArrayList(countryCombo.getValue().getFirstLevelDivisions()));
            if(firstLevelDivisionsCombo.getItems().size() > 0) {
                firstLevelDivisionsCombo.setValue(firstLevelDivisionsCombo.getItems().get(0));
            } else {
                firstLevelDivisionsCombo.setValue(null);
            }
        } else {
            firstLevelDivisionsCombo.setItems(null);
            firstLevelDivisionsCombo.setValue(null);
        }
    }//updateFirstDivisions
}