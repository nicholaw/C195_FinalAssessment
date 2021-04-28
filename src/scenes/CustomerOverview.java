package scenes;

import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import sceneUtils.SceneCode;

public class CustomerOverview  extends BorderPane
{
    Controller controller;
    Label messageLabel;
    Button returnButton;
    Button addCustomerButton;
    Button testCustomerButton;

    public CustomerOverview(Controller controller)
    {
        this.controller = controller;
        messageLabel = new Label("Hello Nora!!");
        returnButton = new Button("Return");
        addCustomerButton = new Button("Add Customer");
        ///////////////FOR TESTING/////////////////////////////////////////////////////////////////////////////////
        Button addApptButton = new Button("Add Appointment");
        Button editApptButton = new Button("Edit Appointment");
        testCustomerButton = new Button("Load");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        HBox buttonPane = new HBox(addCustomerButton, testCustomerButton, addApptButton, returnButton);
        this.setTop(controller.getHeader());
        this.setCenter(messageLabel);
        this.setBottom(buttonPane);
        returnButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.LOGIN);
        });//returnButton
        addCustomerButton.setOnAction(event -> {
            controller.changeScene(SceneCode.EDIT_CUSTOMER);
        });//addCustomerButton
        testCustomerButton.setOnAction(event -> {
            controller.loadCustomerToEdit();
        });
        addApptButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.EDIT_APPOINTMENT);
        });
        editApptButton.setOnAction(event ->
        {
            controller.loadAppointmentToEdit(null);
        });
    }//constructor
}
