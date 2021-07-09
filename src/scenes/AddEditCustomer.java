package scenes;

import controller.Controller;
import customer.Customer;
import customer.CustomerConstants;
import customer.CustomerFieldCode;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

import sceneUtils.*;
import utils.Division;

public class AddEditCustomer extends BorderPane implements Refreshable {
    //Controller
    private Controller controller;

    //Scene elements
    private Label sceneLabel;
    private Label idLabel;              private TextField idField;
    private Label nameLabel;            private TextField nameField;      	private ErrorLabel nameErrorLabel;
    private Label phoneLabel;           private TextField phoneField;     	private ErrorLabel phoneErrorLabel;
    private Label addressLabel;         private TextArea addressArea;     	private ErrorLabel addressErrorLabel;
    private Label postCodeLabel;		private TextField postCodeField;	private ErrorLabel postCodeErrorLabel;
    private Button submitButton;        private Button cancelButton;
    private CountryAndDivisionsBox countryAndDivisionsCombos;
	
	//Scene attributes
	private boolean newCustomer;
	private Customer customerToEdit;

    public AddEditCustomer(Controller controller){
        //Set Controller
        this.controller = controller;

        //Instantiate scene elements
        sceneLabel			= new Label("");
        idLabel				= new Label("");
        idField				= new TextField("");
        nameLabel			= new Label("");
        nameField			= new TextField("");
        phoneLabel			= new Label("");
        phoneField			= new TextField("");
        addressLabel		= new Label("");
        addressArea			= new TextArea("");
        postCodeLabel       = new Label("");
        postCodeField       = new TextField("");
        nameErrorLabel		= new ErrorLabel(this.controller.getResourceBundle());
        phoneErrorLabel		= new ErrorLabel(this.controller.getResourceBundle());
        addressErrorLabel	= new ErrorLabel(this.controller.getResourceBundle());
        postCodeErrorLabel  = new ErrorLabel(this.controller.getResourceBundle());
        countryAndDivisionsCombos = new CountryAndDivisionsBox(controller.getCountries(), controller.getResourceBundle());
		
		//Instantiate scene attributes
		newCustomer = true;
		customerToEdit = null;

        //Instantiate buttons and add event listeners
        submitButton = new Button("");
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
        cancelButton = new Button("");
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

        //Set initial text for scene labels and buttons
        setElementText();

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
        nameField.setText("");
        phoneField.setText("");
        addressArea.setText("");
        postCodeField.setText("");
        sceneLabel.setText(controller.getResourceBundle().getString("add") + " " +
                controller.getResourceBundle().getString("customer"));
        countryAndDivisionsCombos.reset();
        submitButton.setText(CustomerConstants.ADD_CUSTOMER);
		newCustomer = true;
		customerToEdit = null;
		clearErrors();
    }//clearAll

    public void clearErrors() {
        nameErrorLabel.clear();
        phoneErrorLabel.clear();
        addressErrorLabel.clear();
        postCodeErrorLabel.clear();
    }//clearErrors

    /**
     *	Loads an existing customer's information into fields for editing and stores the
     *  original value of each field in a HashMap of updates.
     *	@param	c	the customer to edit
     */
    public void loadCustomerInfo(Customer c) {
        if(c != null) {
			try {
				customerToEdit = c;
				idField.setText("" + c.getCustomerId());
				nameField.setText(c.getName());
				phoneField.setText(c.getPhone());
				addressArea.setText(c.getAddress());
				postCodeField.setText(c.getPostCode());
				countryAndDivisionsCombos.setSelectedCountry(c.getCountry());
				countryAndDivisionsCombos.setSelectedDivision(c.getDivision());
				newCustomer = false;
				submitButton.setText(controller.getResourceBundle().getString("update"));
				sceneLabel.setText(controller.getResourceBundle().getString("edit") + " " +
                        controller.getResourceBundle().getString("customer"));
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
        sceneLabel.setText(controller.getResourceBundle().getString("add") + " " +
                controller.getResourceBundle().getString("customer"));
		newCustomer = true;
		submitButton.setText(controller.getResourceBundle().getString("add"));
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

        //Check postal code
		tempString = postCodeField.getText();
		if(!customerToEdit.getPostCode().equals(tempString)) {
            changesMade = true;
            if(commitChanges) {
                controller.addCustomerUpdate(CustomerFieldCode.POST_CODE_FIELD, tempString);
                customerToEdit.setPostCode(tempString);
            }
		}

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
     *
     * @param rb
     */
    public void refresh(ResourceBundle rb) {
        setElementText();
        nameErrorLabel.setResourceBundle(rb);
        phoneErrorLabel.setResourceBundle(rb);
        addressErrorLabel.setResourceBundle(rb);
        postCodeErrorLabel.setResourceBundle(rb);
        countryAndDivisionsCombos.setResourceBundle(rb);
    }

    /**
     *
     */
    private void setElementText() {
	    if(newCustomer) {
            sceneLabel.setText(controller.getResourceBundle().getString("add") + " " +
                    controller.getResourceBundle().getString("customer"));
            submitButton.setText(controller.getResourceBundle().getString("add"));
        } else {
            sceneLabel.setText(controller.getResourceBundle().getString("edit") + " " +
                    controller.getResourceBundle().getString("customer"));
            submitButton.setText(controller.getResourceBundle().getString("update"));
        }
	    cancelButton.setText(controller.getResourceBundle().getString("cancel"));
	    nameLabel.setText(controller.getResourceBundle().getString("name"));
	    phoneLabel.setText(controller.getResourceBundle().getString("phone"));
	    addressLabel.setText(controller.getResourceBundle().getString("address"));
	    postCodeLabel.setText(controller.getResourceBundle().getString("postal_code"));
    }

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
            nameErrorLabel.setError(ErrorCode.CUSTOMER_NAME_REQUIRED_ERROR);
        }

        //check phone is not blank and matches regEx
        input = phoneField.getText().trim();
        if(input.isEmpty() || input.isBlank()) {
            valid = false;
            phoneErrorLabel.setError(ErrorCode.CUSTOMER_PHONE_REQUIRED_ERROR);
        } else if (Pattern.compile("[^0-9]").matcher(input).find()) {
            valid = false;
            phoneErrorLabel.setError(ErrorCode.CUSTOMER_PHONE_DIGITS_ERROR);
        }

        //check that address is not blank
        input = addressArea.getText().trim();
        if(input.isBlank() || input.isEmpty()) {
            valid = false;
            addressErrorLabel.setError(ErrorCode.CUSTOMER_ADDRESS_REQUIRED_ERROR);
        }

        //check that postal code is not blank and only contains digits
        input = phoneField.getText().trim();
        if(input.isEmpty() || input.isBlank()) {
            valid = false;
            postCodeErrorLabel.setError(ErrorCode.CUSTOMER_POSTCODE_REQUIRED_ERROR);
        } else if (Pattern.compile("[^0-9]").matcher(input).find()) {
            valid = false;
            postCodeErrorLabel.setError(ErrorCode.CUSTOMER_POSTCODE_DIGITS_ERROR);
        }

        return valid;
    }//validateForm
}//class AddEditCustomer