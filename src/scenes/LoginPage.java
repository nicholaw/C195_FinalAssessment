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
import sceneUtils.ErrorLabel;
import sceneUtils.ErrorCode;
import sceneUtils.Refreshable;

import java.util.ResourceBundle;

public class LoginPage extends BorderPane implements Refreshable {
    private Controller      controller;
    private TextField       usernameField;
    private PasswordField   passwordField;
    private Button          submitButton;
    private Label           usernameLabel;
    private Label           passwordLabel;
    private ErrorLabel      errorMessageLabel;

    public LoginPage(Controller controller) {
        //Initialize fields
        this.controller = controller;
        usernameLabel       =   new Label(this.controller.getResourceBundle().getString("username"));
        usernameField       =   new TextField();
        passwordLabel       =   new Label(this.controller.getResourceBundle().getString("password"));
        passwordField       =   new PasswordField();
        errorMessageLabel   =   new ErrorLabel(this.controller.getResourceBundle());
        submitButton        =   new Button(this.controller.getResourceBundle().getString("submit"));

        //Add nodes to containers and style
        var usernamePane = new HBox(usernameLabel);
        usernamePane.setAlignment(Pos.CENTER_RIGHT);
        var passwordPane = new HBox(passwordLabel);
        passwordPane.setAlignment(Pos.CENTER_RIGHT);
        var fieldPane = new GridPane();
        fieldPane.setPadding(new Insets(10,10,10,10));
        fieldPane.setHgap(10);
        fieldPane.setVgap(10);
        fieldPane.setAlignment(Pos.CENTER);
        fieldPane.add(usernamePane, 0, 0);   fieldPane.add(usernameField, 1, 0);
        fieldPane.add(passwordPane, 0, 1);   fieldPane.add(passwordField, 1, 1);
        var buttonPane = new HBox(submitButton);
        buttonPane.setAlignment(Pos.CENTER_RIGHT);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        var contentPane = new GridPane();
        contentPane.setPadding(new Insets(10,10,10,10));
        contentPane.setHgap(10);
        contentPane.setVgap(10);
        contentPane.setAlignment(Pos.CENTER);
        contentPane.add(errorMessageLabel, 0, 0);
        contentPane.add(fieldPane, 0, 1);
        contentPane.add(buttonPane, 0, 2);
        this.setCenter(contentPane);
        this.setPrefSize(500, 500);

        //Add event listeners to buttons and fields
        submitButton.setOnAction(event -> {
            submitForm();
        });//submitButton
        passwordField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                submitForm();
            }
        });
    }//constructor

    /**
     *
     */
    public void clearAll() {
        usernameField.setText("");
        passwordField.setText("");
        errorMessageLabel.clear();
    }

    /**
     *
     */
    private void clearErrorMessage() {
        errorMessageLabel.clear();
    }

    /**
     *
     */
    public void invalidLogin() {
        passwordField.setText("");
        errorMessageLabel.setError(ErrorCode.LOGIN_INVALID_CREDENTIAL_ERROR);
    }

    /**
     *
     */
    public void refresh(ResourceBundle rb) {
        usernameLabel.setText(this.controller.getResourceBundle().getString("username"));
        passwordLabel.setText(this.controller.getResourceBundle().getString("password"));
        submitButton.setText(this.controller.getResourceBundle().getString("submit"));
        errorMessageLabel.setResourceBundle(rb);
    }

    /**
     *
     */
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
            errorMessageLabel.setError(ErrorCode.LOGIN_USERNAME_REQUIRED_ERROR);
        }

        //Check that password is not blank
        if(!(passwordField.getCharacters().length() > 0)) {
            valid = false;
            errorMessageLabel.setError(ErrorCode.LOGIN_PASSWORD_REQUIRED_ERROR);
        }

        return valid;
    }
}