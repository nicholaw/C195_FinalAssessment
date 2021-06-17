package sceneUtils;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import utils.Country;
import utils.Division;
import java.util.ArrayList;

public class CountryAndDivisionsBox extends HBox
{
    //TODO: use observableList for items of combo boxes
    private ComboBox<Country> countryCombo;
    private ComboBox<Division> firstLevelDivisionsCombo;
    private final Label countryLabel = new Label("Country");
    private final Label divisionLabel = new Label("First-Level Division");

    public CountryAndDivisionsBox(ObservableList<Country> countries)
    {
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
        this.getChildren().addAll(countryLabel, countryCombo, divisionLabel, firstLevelDivisionsCombo);
    }//constructor

    /**
     *
     * @param countryId
     * @return
     */
    public Country getCountry(int countryId)
    {
        for(Country c : countryCombo.getItems())
        {
            //TODO: replace linear search
            if(c.getCountryId() == countryId)
                return c;
        }
        return null;
    }//getCountry

    /**
     *
     * @param c
     * @param divisionId
     * @return
     */
    public Division getDivision(Country c, int divisionId)
    {
        for(Division d : c.getFirstLevelDivisions())
            if(d.getDivisionId() == divisionId)
                return d;
        return null;
    }//getDivision

    public Country getSelectedCountry()
    {
        return countryCombo.getValue();
    }

    public Division getSelectedDivision()
    {
        return firstLevelDivisionsCombo.getValue();
    }

    /**
	 * 
	 */
    public void setSelectedCountry(int id)
    {
        for(Country c : countryCombo.getItems())
        {
            if(c.getCountryId() == id)
            {
                countryCombo.setValue(c);
                updateFirstDivisions();
            }
        }
    }//setSelectedCountry

    public void setSelectedDivision(Division div) {
        if(div == null) {
            firstLevelDivisionsCombo.setValue(null);
        } else {
            for(Division d : firstLevelDivisionsCombo.getItems()) {
                if(d.equals(div)) {
                    firstLevelDivisionsCombo.setValue(d);
                    return;
                }
            }
            firstLevelDivisionsCombo.setValue(null);
        }
    }//setSelectedDivision

    /**
     *
     */
    private void updateFirstDivisions() {
        //Set items to first-level divisions of the selected country
        if(countryCombo.getValue() != null)
            firstLevelDivisionsCombo.getItems().setAll(countryCombo.getValue().getFirstLevelDivisions());
        else
            firstLevelDivisionsCombo.getItems().setAll(new ArrayList<>(0));
        //Set the selected value as the first first-level division
        if(firstLevelDivisionsCombo.getItems().size() > 0)
            firstLevelDivisionsCombo.setValue(firstLevelDivisionsCombo.getItems().get(0));
        else
            firstLevelDivisionsCombo.setValue(null);
    }//updateFirstDivisions

    public void clear() {
        if(countryCombo.getItems().size() > 0) {
            countryCombo.setValue(countryCombo.getItems().get(0));
            updateFirstDivisions();
        }
        else {
            countryCombo.setValue(null);
            firstLevelDivisionsCombo.setValue(null);
        }
    }//clear
}