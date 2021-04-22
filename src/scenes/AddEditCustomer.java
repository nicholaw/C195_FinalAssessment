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
import sceneUtils.SceneCode;

public class AddEditCustomer  extends BorderPane
{
    //Controller
    private Controller controller;

    //Scene elements
    private Label customerIdLabel;      private TextField customerIdField;
    private Label firstNameLabel;       private TextField firstNameField;
    private Label lastNameLabel;        private TextField lastNameField;
    private Label address1Label;        private TextField address1Field;
    private Label address2Label;        private TextField address2Field;
    private Label cityLabel;            private TextField cityField;
    private Label countryLabel;         private ComboBox countryCombo;
    private Label stateLabel;           private ComboBox stateCombo;
    private Label phoneLabel;           private TextField phoneField;
    private Label postCodeLabel;        private TextField postCodeField;
    private Button submitButton;        private Button cancelButton;
    private Label sceneLabel;

    public AddEditCustomer(Controller controller)
    {
        //Set Controller
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel = new Label("Add New Customer");
        customerIdLabel = new Label("CustomerId");      customerIdField = new TextField("");
        firstNameLabel = new Label("First Name");       firstNameField = new TextField("");
        lastNameLabel = new Label("Last Name");         lastNameField = new TextField("");
        address1Label = new Label("Address Line 1");    address1Field = new TextField("");
        address2Label = new Label("Address Line 2");    address2Field = new TextField("");
        cityLabel = new Label("City");                  cityField = new TextField("");
        countryLabel = new Label("Country");            countryCombo = new ComboBox<String>();
        stateLabel = new Label("State");                stateCombo = new ComboBox<String>();
        phoneLabel = new Label("Phone Number");         phoneField = new TextField("");
        postCodeLabel = new Label("Postal Code");       postCodeField = new TextField("");

        //set initial values for scene elements
        customerIdField.setDisable(true);
        countryCombo.getItems().addAll("Canada", "United Kingdom", "USA"); //TODO: only HQ countries?
        countryCombo.setValue("Canada");
        stateCombo.getItems().addAll("Quebec", "Ontario", "California", "New York", "Utah", "Wessex", "Cornwall"); //TODO: change with country
        stateCombo.setValue("Quebec");

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
        HBox sceneLabelPane = new HBox(sceneLabel);
        GridPane idPane = new GridPane();
        idPane.add(customerIdLabel, 0, 0);
        idPane.add(customerIdField, 1, 0);
        GridPane namePane = new GridPane();
        namePane.add(firstNameLabel, 0, 1);
        namePane.add(firstNameField, 1, 1);
        namePane.add(lastNameLabel, 2, 1);
        namePane.add(lastNameField, 3, 1);
        GridPane contactPane = new GridPane();
        contactPane.add(phoneLabel, 0, 2);
        contactPane.add(phoneField, 1, 2);
        GridPane addressPane1 = new GridPane();
        addressPane1.add(address1Label,0, 3);
        addressPane1.add(address1Field,1, 3, 3, 1);
        GridPane addressPane2 = new GridPane();
        addressPane2.add(address2Label,0, 4);
        addressPane2.add(address2Field,1, 4, 3, 1);
        GridPane cityPane = new GridPane();
        cityPane.add(cityLabel, 0, 5);
        cityPane.add(cityField, 1, 5, 2, 1);
        GridPane countryStatePane = new GridPane();
        countryStatePane.add(countryLabel, 0, 6);
        countryStatePane.add(countryCombo, 1, 6);
        countryStatePane.add(stateLabel, 2, 6);
        countryStatePane.add(stateCombo, 3, 6);
        GridPane postPane = new GridPane();
        postPane.add(postCodeLabel, 0, 7);
        postPane.add(postCodeField, 1, 7);
        HBox buttonPane = new HBox(submitButton, cancelButton);
        VBox fieldsPane = new VBox(sceneLabelPane, idPane, namePane, contactPane, addressPane1, addressPane2, cityPane, countryStatePane, postPane);
        this.setTop(controller.getHeader());
        this.setCenter(fieldsPane);
        this.setBottom(buttonPane);
    }

    //Clears all values from input fields
    public void clearAll()
    {
        customerIdField.setText("");
        customerIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        address1Field.setText("");
        address2Field.setText("");
        cityField.setText("");
        phoneField.setText("");
        postCodeField.setText("");
        countryCombo.setValue(null);
        stateCombo.setValue(null);
    }

    //Loads an existing customer's information into fields for editing
    public void loadCustomerInfo(Customer c)
    {
        if(c != null)
        {
            customerIdField.setText(c.getCustomerId());
            firstNameField.setText(c.getFirstName());
            lastNameField.setText(c.getLastName());
            address1Field.setText(c.getAddressLine1());
            address2Field.setText(c.getAddressLine2());
            cityField.setText(c.getCity());
            phoneField.setText(c.getPhoneNum());
            postCodeField.setText(c.getPostCode());
            String temp = c.getCountry();
            if(countryCombo.getItems().contains(temp))
                countryCombo.setValue(temp);
            else
                countryCombo.setValue(null);
            temp = c.getState();
            if (stateCombo.getItems().contains(temp))
                stateCombo.setValue(temp);
            else
                stateCombo.setValue(null);
            temp = "";
        }
    }
}