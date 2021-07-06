package scenes;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

public class LoginPage extends BorderPane
{
    //Scene controls
    private Controller      controller;
    private TextField       usernameField;
    private PasswordField   passwordField;
    private Button          submitButton;
    private Label           usernameLabel;
    private Label           passwordLabel;
    private Label           errorMessageLabel;

    public LoginPage(Controller controller) {
        //Initialize fields
        this.controller = controller;
        usernameLabel       =   new Label("Username");
        usernameField       =   new TextField();
        passwordLabel       =   new Label("Password");
        passwordField       =   new PasswordField();
        errorMessageLabel   =   new Label("");
        submitButton        =   new Button("Submit");

        //Set initial states for scene elements
        //this.setPrefSize(800, 800);

        //Add nodes to containers and style
        var fieldPane = new GridPane();
        fieldPane.setPadding(new Insets(10,10,10,10));
        fieldPane.setHgap(10);
        fieldPane.setVgap(10);
        fieldPane.setAlignment(Pos.CENTER);
        fieldPane.add(usernameLabel, 0, 0);   fieldPane.add(usernameField, 1, 0);
        fieldPane.add(passwordLabel, 0, 1);   fieldPane.add(passwordField, 1, 1);
        var buttonPane = new HBox(submitButton);
        buttonPane.setAlignment(Pos.CENTER_RIGHT);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        var contentPane = new GridPane();
        contentPane.setPadding(new Insets(10,10,10,10));
        contentPane.setHgap(10);
        contentPane.setVgap(10);
        contentPane.setAlignment(Pos.CENTER);
        contentPane.add(fieldPane, 0, 0);
        contentPane.add(buttonPane, 0, 1);
        contentPane.add(errorMessageLabel, 0, 2);
        //contentPane.setBorder(new Border());
        this.setCenter(contentPane);
        this.setPrefSize(500, 500);

        //Add event listeners to buttons
        submitButton.setOnAction(event -> {
            submitForm();
        });//submitButton
        passwordField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                submitForm();
            }
        });
    }//constructor

    public void clearAll() {
        usernameField.setText("");
        passwordField.setText("");
        errorMessageLabel.setText("");
    }

    private void clearErrorMessage() {
        errorMessageLabel.setText("");
    }

    public void invalidLogin() {
        passwordField.setText("");
        errorMessageLabel.setText("Username or password were invalid");
    }

    private void submitForm() {
        this.setDisable(true);
        clearErrorMessage();
        if(validateForm()) {
            CharSequence chars = passwordField.getCharacters();
            String username = usernameField.getText();
            this.controller.validateLoginCredentials(username, chars);
        }
        this.setDisable(false);
    }

    /**
     * Checks that the input provided on this form is valid. Returns ture if the
     * input is valid and false otherwise.
     * @return  Whether the input is valid.
     */
    private boolean validateForm() {
        boolean valid = true;
        String tempString;

        //Check that username is not blank
        tempString = usernameField.getText();
        if(tempString.isEmpty() || tempString.isBlank()) {
            valid = false;
            errorMessageLabel.setText("Username is required");
        }

        //Check that password is not blank
        if(!(passwordField.getCharacters().length() > 0)) {
            valid = false;
            errorMessageLabel.setText("Password is required");
        }

        return valid;
    }
}