package sceneUtils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import utils.Country;
import utils.Division;
import java.util.Collection;

public class CountryAndDivisionsBox extends HBox
{
    private ComboBox<Country> countryCombo;
    private ComboBox<Division> firstDivisionCombo;
    private final Label countryLabel = new Label("Country");
    private final Label divisionLabel = new Label("First-Level Division");

    public CountryAndDivisionsBox(Collection<Country> coll)
    {
        //Declare country combo box and add countries from db to combo box
        countryCombo = new ComboBox<>();
        for(Country c : coll)
            countryCombo.getItems().add(c);
        //Set initial value to first country in list if any countries exist on list
        if(countryCombo.getItems().size() > 0)
            countryCombo.setValue(countryCombo.getItems().get(0));
        else
            countryCombo.setValue(null);
        //Declare first-level division combo box and add divisions based on the selected country
        firstDivisionCombo = new ComboBox<>();
        updateFirstDivisions();
        //Add event handler to country combo box to update first-level division combo box when a new country is selected
        countryCombo.setOnAction(event -> {
            updateFirstDivisions();
        });

        //Add elements to this pane
        this.getChildren().addAll(countryLabel, countryCombo, divisionLabel, firstDivisionCombo);
    }//constructor

    private void updateFirstDivisions()
    {
        if(countryCombo.getValue() != null)
        {
            for(Division d : countryCombo.getValue().getFirstLevelDivisions())
                firstDivisionCombo.getItems().add(d);
            if(firstDivisionCombo.getItems().size() > 0)
                firstDivisionCombo.setValue(firstDivisionCombo.getItems().get(0));
            else
                firstDivisionCombo.setValue(null);
        }
    }//updateFirstDivisions
}