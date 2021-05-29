package scenes;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    private final static String ERROR_MESSAGE  = "Username or password were invalid";

    public LoginPage(Controller controller)
    {
        this.controller = controller;
        usernameLabel       =   new Label("Username");
        usernameField       =   new TextField();
        passwordLabel       =   new Label("Password");
        passwordField       =   new PasswordField();
        errorMessageLabel   =   new Label("");
        submitButton        =   new Button("Submit");

        VBox fieldBox = new VBox(usernameLabel, usernameField, passwordLabel, passwordField, errorMessageLabel);
        this.setTop(controller.getHeader());
        this.setCenter(fieldBox);
        this.setBottom(submitButton);

        submitButton.setOnAction(event ->
        {
            CharSequence chars = passwordField.getCharacters();
            passwordField.setText("");
            this.controller.validateLoginCredentials(usernameField.getText(), chars);
        });//submitButton
    }//constructor

    public void clearInput()
    {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void clearAll()
    {
        clearInput();
        errorMessageLabel.setText("");
    }

    public void invalidLogin()
    {
        clearInput();
        errorMessageLabel.setText(ERROR_MESSAGE);
    }
}