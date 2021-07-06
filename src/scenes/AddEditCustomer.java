package scenes;

import controller.Controller;
import customer.Customer;
import customer.CustomerConstants;
import customer.CustomerFieldCode;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.regex.Pattern;
import sceneUtils.CountryAndDivisionsBox;
import sceneUtils.HeaderPane;
import sceneUtils.SceneCode;
import utils.Division;

public class AddEditCustomer extends BorderPane
{
    //Controller
    private Controller controller;

    //Scene elements
    private Label sceneLabel;
    private Label idLabel;              private TextField idField;
    private Label nameLabel;            private TextField nameField;      	private Label nameErrorLabel;
    private Label phoneLabel;           private TextField phoneField;     	private Label phoneErrorLabel;
    private Label addressLabel;         private TextArea addressArea;     	private Label addressErrorLabel;
    //private Label cityLabel;			private TextField cityField;		private Label cityErrorLabel;
    private Label postCodeLabel;		private TextField postCodeField;	private Label postCodeErrorLabel;
    private Button submitButton;        private Button cancelButton;
    private CountryAndDivisionsBox countryAndDivisionsCombos;
	
	//Scene attributes
	private boolean newCustomer;
	private Customer customerToEdit;

    public AddEditCustomer(Controller controller)
    {
        //Set Controller
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel			= new Label("Add Customer");
        idLabel				= new Label("Customer Id");
        idField				= new TextField("");
        nameLabel			= new Label("Name");
        nameField			= new TextField("");
        phoneLabel			= new Label("Phone");
        phoneField			= new TextField("");
        addressLabel		= new Label("Address");
        addressArea			= new TextArea("");
        //cityLabel			= new Label("City");
        //cityField			= new TextField("");
        countryAndDivisionsCombos = new CountryAndDivisionsBox(controller.getCountries());
        postCodeLabel       = new Label("Postal Code");
        postCodeField       = new TextField("");
        nameErrorLabel		= new Label("");
        phoneErrorLabel		= new Label("");
        addressErrorLabel	= new Label("");
        //cityErrorLabel      = new Label("");
        postCodeErrorLabel  = new Label("");
		
		//Instantiate scene attributes
		newCustomer = true;
		customerToEdit = null;

        //Instantiate buttons and add event listeners
        submitButton = new Button("Add");
        submitButton.setOnAction(event -> {
            this.setDisable(true);
			if(this.validateForm()) {
                if(newCustomer) {
					controller.addCustomer(new Customer(Long.parseLong(idField.getText()), nameField.getText(), phoneField.getText(),
                            addressArea.getText(), postCodeField.getText(), countryAndDivisionsCombos.getSelectedCountry(),
                            countryAndDivisionsCombos.getSelectedDivision(), null));
                } else {
					processChanges(true);
					controller.updateCustomer(Integer.parseInt(idField.getText()));
					controller.clearCustomerUpdates();
                }
                controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
                this.clearAll();
            }
			this.setDisable(false);
        });
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            if(newCustomer) {
                if(checkForInput()) {
                    if(controller.displayConfirmationAlert("Confirm Navigation", "You have not finished " +
                            "this customer. Are you sure you would like to cancel without saving?")) {
                        clearAll();
                        controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
                    }
                } else {
                    clearAll();
                    controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
                }
            } else if(processChanges(false)) {
                if(controller.displayConfirmationAlert("Confirm Navigation", "You have made changes to " +
                        "this customer. Are you sure you would like to cancel without saving these changes?")) {
                    clearAll();
                    controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
                }
            } else {
                clearAll();
                controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
            }
        });

        //Add key event listener to text fields and areas to prevent number of characters over maximum allowed
        nameField.setOnKeyReleased(event -> {
            checkForMaximumCharacters(nameField, CustomerConstants.MAX_CHAR_DEFAULT);
        });
        phoneField.setOnKeyReleased(event -> {
            checkForMaximumCharacters(phoneField, CustomerConstants.MAX_CHAR_DEFAULT);
        });
        addressArea.setOnKeyReleased(event -> {
            checkForMaximumCharacters(addressArea, CustomerConstants.MAX_CHAR_ADDRESS);
        });
        /*cityField.setOnKeyReleased(event -> {
            checkForMaximumCharacters(cityField, CustomerConstants.MAX_CHAR_DEFAULT);
        });*/
        postCodeField.setOnKeyReleased(event -> {
            checkForMaximumCharacters(postCodeField, CustomerConstants.MAX_CHAR_DEFAULT);
        });

        //Set initial states of scene elements
        idField.setDisable(true);

        //Add elements to containers
        var buttonPane = new HBox(submitButton, cancelButton);
        var namePane = new GridPane();
        namePane.add(nameLabel, 0, 0);
        namePane.add(nameErrorLabel, 1, 0);
        var phonePane = new GridPane();
        phonePane.add(phoneLabel, 0, 0);
        phonePane.add(phoneErrorLabel, 1, 0);
        var addressPane = new GridPane();
        addressPane.add(addressLabel, 0, 0);
        addressPane.add(addressErrorLabel, 1, 0);
        var postCodePane = new GridPane();
        postCodePane.add(postCodeLabel, 0, 0);
        postCodePane.add(postCodeErrorLabel, 1, 0);
        var contentPane = new GridPane();
        contentPane.add(sceneLabel, 0, 0);
        contentPane.add(namePane, 0, 1);
        contentPane.add(nameField, 0, 2);
        contentPane.add(phonePane, 0, 3);
        contentPane.add(phoneField, 0, 4);
        contentPane.add(addressPane, 0, 5);
        contentPane.add(addressArea, 0, 6);
        contentPane.add(countryAndDivisionsCombos, 0, 7);
        contentPane.add(postCodePane, 0, 8);
        contentPane.add(postCodeField, 0, 9);
        contentPane.add(buttonPane, 0, 10);
        this.setCenter(contentPane);

        //Style containers
        contentPane.setAlignment(Pos.CENTER);
        contentPane.setVgap(10);
        buttonPane.setAlignment(Pos.CENTER_RIGHT);
        buttonPane.setSpacing(20);
        sceneLabel.getStyleClass().add("scene-label");
        namePane.setHgap(20);
        addressPane.setHgap(20);
        phonePane.setHgap(20);
        postCodePane.setHgap(20);
        nameErrorLabel.getStyleClass().add("error-label");
        phoneErrorLabel.getStyleClass().add("error-label");
        addressErrorLabel.getStyleClass().add("error-label");
        postCodeErrorLabel.getStyleClass().add("error-label");
    }//constructor

    /**
     *
     * @return
     */
    private boolean checkForInput() {
       if(!nameField.getText().isBlank())
           return true;
       else if(!phoneField.getText().isBlank())
           return true;
       else if(!addressArea.getText().isBlank())
           return true;
       else if(!postCodeField.getText().isBlank())
           return true;
       else
           return false;
    }

    private void checkForMaximumCharacters(TextInputControl inputElement, int maximum) {
        String oldString = inputElement.getText();
        if(oldString.length() > maximum) {
            String newString = oldString.substring(0, (maximum));
            inputElement.setText(newString);
            inputElement.positionCaret(maximum);
        }
    }//checkForMaximumCharacters

    //Clears all values from input fields
    public void clearAll() {
        nameErrorLabel.setText("");
        //cityErrorLabel.setText("");
        phoneErrorLabel.setText("");
        addressErrorLabel.setText("");
        postCodeErrorLabel.setText("");
        nameField.setText("");
        phoneField.setText("");
        addressArea.setText("");
        //cityField.setText("");
        postCodeField.setText("");
        sceneLabel.setText("Add Customer");
        countryAndDivisionsCombos.reset();
        submitButton.setText(CustomerConstants.ADD_CUSTOMER);
		newCustomer = true;
		customerToEdit = null;
    }//clearAll

    public void clearErrors()
    {
        nameErrorLabel.setText("");
        //cityErrorLabel.setText("");
        phoneErrorLabel.setText("");
        addressErrorLabel.setText("");
        postCodeErrorLabel.setText("");
    }//clearErrors

    /**
     *	Loads an existing customer's information into fields for editing and stores the
     *  original value of each field in a HashMap of updates.
     *	@param	c	the customer to edit
     */
    public void loadCustomerInfo(Customer c) {
        if(c != null)
        {
			try {
				customerToEdit = c;
				idField.setText("" + c.getCustomerId());
				nameField.setText(c.getName());
				phoneField.setText(c.getPhone());
				addressArea.setText(c.getAddress());
				postCodeField.setText(c.getPostCode());
				//cityField.setText(c.getCity());
				countryAndDivisionsCombos.setSelectedCountry(c.getCountry());
				countryAndDivisionsCombos.setSelectedDivision(c.getDivision());
				newCustomer = false;
				submitButton.setText("Update");
				sceneLabel.setText("Edit Customer");
			} catch(NullPointerException e) {
				clearAll();
				controller.changeScene(SceneCode.CUSTOMER_OVERVIEW, null);
				e.printStackTrace();
			}
        } else {
			clearAll();
			loadNewCustomer();
		}
    }//loadCustomerInfo

    public void loadNewCustomer() {
        idField.setText("" + controller.getNextCustomerId());
        sceneLabel.setText("Add Customer");
		newCustomer = true;
		submitButton.setText("Add");
    }//loadNewCustomer

    /**
     *
     * @param commitChanges
     * @return
     */
	private boolean processChanges(boolean commitChanges) {
        boolean changesMade = false;
		String tempString;

		//Check name
        tempString = nameField.getText();
		if(!customerToEdit.getName().equals(tempString)) {
			changesMade = true;
            if(commitChanges) {
                controller.addCustomerUpdate(CustomerFieldCode.NAME_FIELD, tempString);
                customerToEdit.setName(tempString);
            }
		}

		//Check phone
		tempString = phoneField.getText();
		if(!customerToEdit.getPhone().equals(tempString)) {
            changesMade = true;
            if(commitChanges) {
                controller.addCustomerUpdate(CustomerFieldCode.PHONE_FIELD, tempString);
                customerToEdit.setPhone(tempString);
            }
		}

		//Check address
		tempString = addressArea.getText();
		if(!customerToEdit.getAddress().equals(tempString)) {
            changesMade = true;
            if(commitChanges) {
                controller.addCustomerUpdate(CustomerFieldCode.ADDRESS_FIELD, tempString);
                customerToEdit.setAddress(tempString);
            }
		}

		//Check city
		/*tempString = cityField.getText();
		if(!customerToEdit.getCity().equals(tempString)) {
            changesMade = true;
            if(commitChanges) {
                controller.addCustomerUpdate(CustomerFieldCode.CITY_FIELD, tempString);
			    customerToEdit.setCity(tempString);
            }
		}*/

        //Check postal code
		tempString = postCodeField.getText();
		if(!customerToEdit.getPostCode().equals(tempString)) {
            changesMade = true;
            if(commitChanges) {
                controller.addCustomerUpdate(CustomerFieldCode.POST_CODE_FIELD, tempString);
                customerToEdit.setPostCode(tempString);
            }
		}

		//Check country
		/* Country tempCountry = countryAndDivisionsCombos.getSelectedCountry();
		if(!customerToEdit.getCountry().equals(tempCountry)) {
            changesMade = true;
            if(commitChanges) {
                controller.addCustomerUpdate(CustomerFieldCode.COUNTRY_BOX, "" + tempCountry.getCountryId());
                customerToEdit.setCountry(tempCountry);
            }
		} */

		//Check division
        try {
            Division tempDivision = countryAndDivisionsCombos.getSelectedDivision();
            if(!(customerToEdit.getDivision().equals(tempDivision))) {
                changesMade = true;
                if(commitChanges) {
                    controller.addCustomerUpdate(CustomerFieldCode.DIVISION_BOX, ("" + tempDivision.getDivisionId()));
                    customerToEdit.setDivision(tempDivision);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("There was a NullPointerException testing the customer division.");
        }

		return changesMade;
	}//processChanges

    /**
     *	Checks that the information entered into the form fields are valid. Returns true if all fields are valid and false otherwise.
     *	@return	validity of the form
     */
    private boolean validateForm() {
        clearErrors();
        boolean valid = true;
        String input = "";

        //check that name is not blank
        input = nameField.getText().trim();
        if(input.isBlank() || input.isEmpty()) {
            valid = false;
            flag(CustomerFieldCode.NAME_FIELD, "- Name is required");
        }

        //check phone is not blank and matches regEx
        input = phoneField.getText().trim();
        if(input.isEmpty() || input.isBlank()) {
            valid = false;
            flag(CustomerFieldCode.PHONE_FIELD, "- Phone number is required");
        } else if (Pattern.compile("[^0-9]").matcher(input).find()) {
            valid = false;
            flag(CustomerFieldCode.PHONE_FIELD, "- Phone number may only contain digits");
        }

        //check that address is not blank
        input = addressArea.getText().trim();
        if(input.isBlank() || input.isEmpty()) {
            valid = false;
            flag(CustomerFieldCode.ADDRESS_FIELD, "- Address is required");
        }

        //check that postal code is not blank and only contains digits
        input = phoneField.getText().trim();
        if(input.isEmpty() || input.isBlank()) {
            valid = false;
            flag(CustomerFieldCode.POST_CODE_FIELD, "- Postal code is required");
        } else if (Pattern.compile("[^0-9]").matcher(input).find()) {
            valid = false;
            flag(CustomerFieldCode.POST_CODE_FIELD, "- Postal code may only contain digits");
        }

        return valid;
    }//validateForm

    /**
     *	Displays an error message to inform the user of a validation error.
     *	@param code		code denoting which field contained a validation error
     *	@param message	error message to be displayed
     */
    private void flag(CustomerFieldCode code, String message) {
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
                //cityErrorLabel.setText(message);
                break;
            case POST_CODE_FIELD :
                postCodeErrorLabel.setText(message);
                break;
            default :
                Alert unknownError = new Alert(AlertType.ERROR);
                if(message.isBlank() || message.isEmpty() || message == null)
                    unknownError.setContentText("An unknown validation error occurred.");
                else
                    unknownError.setContentText(message);
                unknownError.showAndWait();
        }//switch
    }//flag
}//class AddEditCustomer