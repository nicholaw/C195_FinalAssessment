package scenes;

import controller.Controller;
import customer.Customer;
import javafx.scene.control.*; //TODO: only what you need
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sceneUtils.CountryAndDivisionsBox;
import customer.CustomerConstants;
import customer.CustomerFieldCode;
import sceneUtils.HeaderPane;
import sceneUtils.SceneCode;

public class AddEditCustomer  extends BorderPane
{
    //Controller
    private Controller controller;

    //Scene elements
    private Label sceneLabel;           private HeaderPane header;
    private Label idLabel;              private TextField idField;
    private Label nameLabel;            private TextField nameField;        private Label nameErrorLabel;
    private Label phoneLabel;           private TextField phoneField;       private Label phoneErrorLabel;
    private Label addressLabel;         private TextArea addressArea;       private Label addressErrorLabel;
    private Label cityLabel;			private TextField cityField;		private Label cityErrorLabel;
    private Label postCodeLabel;		private TextField postCodeField;	private Label postCodeErrorLabel;
    private Button submitButton;        private Button cancelButton;
    private CountryAndDivisionsBox countryAndDivisionsCombos;
    private Alert confirmCancelAlert;

    public AddEditCustomer(Controller controller)
    {
        //Set Controller
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel			= new Label("Add New Customer");
        header				= controller.getHeader();
        idLabel				= new Label("Cusotmer Id");
        idField				= new TextField("");
        nameLabel			= new Label("Name");
        nameField			= new TextField("");
        phoneLabel			= new Label("Phone");
        phoneField			= new TextField("");
        addressLabel		= new Label("Address");
        addressArea			= new TextArea("");
        cityLabel			= new Label("City");
        cityField			= new TextField("");
        countryAndDivisionsCombos = controller.getCountryCombo();
        postCodeLabel       = new Label("Postal Code");
        postCodeField       = new TextField("");
        nameErrorLabel		= new Label("");
        phoneErrorLabel		= new Label("");
        addressErrorLabel	= new Label("");
        cityErrorLabel      = new Label("");
        postCodeErrorLabel  = new Label("");
        confirmCancelAlert	= new Alert(AlertType.CONFIRMATION);

        //Instantiate buttons and add event listeners
        submitButton = new Button("Add Customer");
        submitButton.setOnAction(event -> {
            if(this.validateForm())
            {
                //controller.addNewCustomer(new Customer(...));
                //controller.changeScene(sceneCode.CUSTOMER_OVERVIEW);
            }
        });
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            //TODO: if new input, confirm navigation from page
            clearAll();
            controller.changeScene(SceneCode.CUSTOMER_OVERVIEW);
        });

        //Add key event listener to text fields and areas to prevent number of characters over maximum allowed
        nameField.setOnKeyReleased(event ->
        {
            checkForMaximumCharacters(nameField, CustomerConstants.MAX_CHAR_DEFAULT);
        });
        phoneField.setOnKeyReleased(event ->
        {
            checkForMaximumCharacters(phoneField, CustomerConstants.MAX_CHAR_DEFAULT);
        });
        addressArea.setOnKeyReleased(event ->
        {
            checkForMaximumCharacters(addressArea, CustomerConstants.MAX_CHAR_ADDRESS);
        });
        cityField.setOnKeyReleased(event ->
        {
            checkForMaximumCharacters(cityField, CustomerConstants.MAX_CHAR_DEFAULT);
        });
        postCodeField.setOnKeyReleased(event ->
        {
            checkForMaximumCharacters(postCodeField, CustomerConstants.MAX_CHAR_DEFAULT);
        });

        //Set initial states of scene elements
        idField.setDisable(true);

        //Add elements to containers
        var buttonPane = new HBox(submitButton, cancelButton);
        var fieldsPane = new GridPane();
        fieldsPane.addRow(0, sceneLabel);
        fieldsPane.addRow(1, idLabel, idField);
        fieldsPane.addRow(2, nameLabel, nameField);
        fieldsPane.addRow(3, phoneLabel, phoneField);
        fieldsPane.addRow(4, addressLabel, addressArea);
        fieldsPane.addRow(5, cityLabel, cityField);
        fieldsPane.addRow(6, countryAndDivisionsCombos);
        fieldsPane.addRow(7, postCodeLabel, postCodeField);
        fieldsPane.addRow(8, buttonPane);
        this.setTop(header);
        this.setCenter(fieldsPane);
    }//constructor

    private void checkForMaximumCharacters(TextInputControl inputElement, int maximum)
    {
        String oldString = inputElement.getText();
        if(oldString.length() > maximum)
        {
            String newString = oldString.substring(0, (maximum));
            inputElement.setText(newString);
            inputElement.positionCaret(maximum);
        }
    }//checkForMaximumCharacters

    //Clears all values from input fields
    public void clearAll()
    {
        nameErrorLabel.setText("");
        cityErrorLabel.setText("");
        phoneErrorLabel.setText("");
        addressErrorLabel.setText("");
        postCodeErrorLabel.setText("");
        nameField.setText("");
        phoneField.setText("");
        addressArea.setText("");
        cityField.setText("");
        postCodeField.setText("");
        countryAndDivisionsCombos.clear();
        submitButton.setText(CustomerConstants.ADD_CUSTOMER);
    }//clearAll

    public void clearErrors()
    {
        nameErrorLabel.setText("");
        cityErrorLabel.setText("");
        phoneErrorLabel.setText("");
        addressErrorLabel.setText("");
        postCodeErrorLabel.setText("");
    }//clearErrors

    /**
     *	Loads an existing customer's information into fields for editing.
     *	@param	c	the customer to edit
     */
    public void loadCustomerInfo(Customer c)
    {
        if(c != null)
        {
            idField.setText("" + c.getCustomerId());
            nameField.setText(c.getName());
            phoneField.setText(c.getPhoneNum());
            addressArea.setText(c.getAddress());
            cityField.setText(c.getCity());
            //TODO: set value for country and division combo boxes
        }
        submitButton.setText("Update Customer");
    }//loadCustomerInfo

    /**
     *	Checks that the information entered into the form fields are valid. Returns true if all fields are valid and false otherwise.
     *	@return	validity of the form
     */
    private boolean validateForm()
    {
        boolean valid = true;
        String input = "";

        //check name is not blank
        input = nameField.getText().trim();
        if(input.isEmpty() || input.isBlank())
        {
            flag(CustomerFieldCode.NAME_FIELD, "Name is required");
            valid = false;
        }
        //check address is not blank and only contains legal characters
        input = addressArea.getText().trim();
        if(input.isEmpty() || input.isBlank())
        {
            flag(CustomerFieldCode.ADDRESS_FIELD, "Address is required");
            valid = false;
        }
        if(!("^[a-zA-Z0-9\\Q" + CustomerConstants.LEGAL_ADDRESS_CHARACTERS + "\\E]*$").matches(input))
        {
            flag(CustomerFieldCode.ADDRESS_FIELD, "Address may only contain letters, numbers, or the symbols " +
                    CustomerConstants.LEGAL_ADDRESS_CHARACTERS);
            valid = false;
        }
        //check phone number--only numbers(after punctuation is removed)
        input = phoneField.getText().trim();
        if(input.isEmpty() || input.isBlank())
        {
            flag(CustomerFieldCode.PHONE_FIELD, "Phone number is required");
            valid = false;
        }
        if(!("^[0-9\\Q" + CustomerConstants.LEGAL_PHONE_CHARACTERS + "\\E]*$").matches(input))
        {
            flag(CustomerFieldCode.ADDRESS_FIELD, "Phone number may only contain numbers or the symbols " +
                    CustomerConstants.LEGAL_PHONE_CHARACTERS); //TODO: also check pattern matches legal patterns
            valid = false;
        }
        //check city is not blank
        input = cityField.getText().trim();
        if(input.isEmpty() || input.isBlank())
        {
            flag(CustomerFieldCode.CITY_FIELD, "City is required");
            valid = false;
        }
        //check postal code is not blank and contains only numbers
        input = postCodeField.getText().trim();
        if(input.isEmpty() || input.isBlank())
        {
            flag(CustomerFieldCode.POST_CODE_FIELD, "Postal code is required");
            valid = false;
        }
        if(!"^[0-9]*$".matches(input))
        {
            flag(CustomerFieldCode.POST_CODE_FIELD, "Postal code may only contain numbers");
            valid = false;
        }
        //country and state are from combo box; validation not required
        //id is auto-generated; validation not required
        return valid;
    }//validateForm

    /**
     *	Displays an error message to inform the user of a validation error.
     *	@param code		code denoting which field contained a validation error
     *	@param message	error message to be displayed
     */
    private void flag(CustomerFieldCode code, String message)
    {
        switch(code)
        {
            case NAME_FIELD :
                nameErrorLabel.setText(message);
                break;
            case PHONE_FIELD :
                phoneErrorLabel.setText(message);
                break;
            case ADDRESS_FIELD :
                addressErrorLabel.setText(message);
                break;
            case CITY_FIELD :
                cityErrorLabel.setText(message);
                break;
            case POST_CODE_FIELD :
                postCodeErrorLabel.setText(message);
                break;
            default :
                Alert unknownError = new Alert(AlertType.ERROR);
                if(message.isBlank() || message.isEmpty() || message == null)
                    unknownError.setContentText("An unknow validation error occurred.");
                else
                    unknownError.setContentText(message);
                unknownError.showAndWait();
        }//switch
    }//flag
}//class AddEditCustomer