package scenes;

import controller.Controller;
import customer.Customer;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sceneUtils.CountryAndDivisionsBox;
import sceneUtils.HeaderPane;
import sceneUtils.SceneCode;

public class AddEditCustomer  extends BorderPane
{
    //Controller
    private Controller controller;

    //Scene elements

    private Label sceneLabel;           private HeaderPane header;
    private CountryAndDivisionsBox countryAndDivisionsCombos;
    private Button submitButton;        private Button cancelButton;

    public AddEditCustomer(Controller controller)
    {
        //Set Controller
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel = new Label("Add New Customer");
        header = controller.getHeader();
        countryAndDivisionsCombos = new CountryAndDivisionsBox(controller.getCountries());

        //Instantiate buttons and add event listeners
        submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            System.out.println("Your request is being processed...");
        });
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            //TODO: if new input, confirm navigation from page
            clearAll();
            controller.changeScene(SceneCode.CUSTOMER_OVERVIEW);
        });

        //Add elements to containers
        var contentPane = new VBox(sceneLabel, countryAndDivisionsCombos);
        var buttonPane = new HBox(submitButton, cancelButton);
        this.setTop(header);
        this.setCenter(contentPane);
        this.setBottom(buttonPane);
    }

    //Clears all values from input fields
    public void clearAll()
    {
    }

    //Loads an existing customer's information into fields for editing
    public void loadCustomerInfo(Customer c)
    {
        if(c != null)
        {
        }
    }//loadCustomerInfo

    private boolean validateForm()
    {
        //check name--not blank
        //check address--not blank
        //check phone number--only numbers(after punctuation is removed)
        //check city--not blank; only characters
        //country and state are from combo box; validation not required
        //id is auto-generated; validation not required
        return false;
    }

    private void flag()
    {

    }
}