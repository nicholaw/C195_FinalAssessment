package scenes;

import controller.Controller;
import customer.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sceneUtils.CustomerOverviewTable;
import sceneUtils.Refreshable;
import sceneUtils.SceneCode;

import java.util.ResourceBundle;

public class CustomerOverview  extends BorderPane implements Refreshable {
    Controller controller;
    Label sceneLabel;
    CustomerOverviewTable customersTable;
    Button addCustomerButton;
    Button editCustomerButton;
    Button viewAppointmentsButton;
    Button deleteCustomerButton;
    Button logoutButton;

    public CustomerOverview(Controller controller) {
        this.controller = controller;

        //Instantiate scene elements
        customersTable          = new   CustomerOverviewTable(controller.getResourceBundle(), controller.getCustomers());
        sceneLabel              = new   Label("");
        addCustomerButton       = new   Button("");
        viewAppointmentsButton  = new   Button("");
        deleteCustomerButton    = new   Button("");
        logoutButton            = new   Button("");
        editCustomerButton      = new   Button("");
        setElementText();
		
		//set initial states for buttons
		editCustomerButton.setDisable(true);
		viewAppointmentsButton.setDisable(true);
		deleteCustomerButton.setDisable(true);

        //Add event listeners to scene elements
        addCustomerButton.setOnAction(event -> {
            controller.changeScene(SceneCode.EDIT_CUSTOMER, null);
        });//addCustomerButton
        editCustomerButton.setOnAction(event -> {
            controller.changeScene(SceneCode.EDIT_CUSTOMER, customersTable.getSelectedCustomer());
        });//editCustomerButton
        viewAppointmentsButton.setOnAction(event -> {
            controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, customersTable.getSelectedCustomer());
        });//viewAppointmentsButton
        deleteCustomerButton.setOnAction(event -> {
            if(customersTable.getSelectedCustomer() != null) {
                Customer selectedCustomer = customersTable.getSelectedCustomer();
                if(controller.displayConfirmationAlert(controller.getResourceBundle().getString("confirm_delete_title"),
                        controller.getResourceBundle().getString("confirm_delete_appointment"))) {
                    if(controller.deleteCustomer(selectedCustomer)) {
                        viewAppointmentsButton.setDisable(true);
                        editCustomerButton.setDisable(true);
                        deleteCustomerButton.setDisable(true);
                        customersTable.refresh();
                    }
                }
            }
        });//deleteCustomerButton
        logoutButton.setOnAction(event -> {
			this.clear();
            controller.changeScene(SceneCode.LOGIN, null);
        });//logoutButton
		customersTable.setOnMouseClicked(event -> {
			if(customersTable.getSelectedCustomer() != null) {
				editCustomerButton.setDisable(false);
				viewAppointmentsButton.setDisable(false);
				deleteCustomerButton.setDisable(false);
			}
		});//customersTable

        //Add scene elements to containers
        var buttonPane = new HBox(addCustomerButton, editCustomerButton, viewAppointmentsButton, deleteCustomerButton);
        var logoutPane = new HBox(logoutButton);
        var tablePane = new GridPane();
        tablePane.add(customersTable, 0, 0);
        tablePane.add(buttonPane, 0, 1);
        var contentPane = new GridPane();
        contentPane.add(sceneLabel, 0, 0);
        contentPane.add(tablePane, 0, 1);
        contentPane.add(logoutPane, 0, 2);
        this.setCenter(contentPane);

        //Style containers
        contentPane.setAlignment(Pos.CENTER);
        buttonPane.setAlignment(Pos.CENTER);
        logoutPane.setAlignment(Pos.CENTER);
        tablePane.setVgap(10);
        contentPane.setVgap(20);
        buttonPane.setSpacing(10);
        logoutPane.setPadding(new Insets(10, 10, 10, 10));
        sceneLabel.getStyleClass().add("scene-label");
    }//constructor

    public void refresh(ResourceBundle rb) {
        setElementText();
        customersTable.setResourceBundle(controller.getResourceBundle());
    }

    private void setElementText() {
        sceneLabel.setText(this.controller.getResourceBundle().getString("customer_overview"));
        addCustomerButton.setText(this.controller.getResourceBundle().getString("add") + " " +
                this.controller.getResourceBundle().getString("customer"));
        editCustomerButton.setText(this.controller.getResourceBundle().getString("edit") + " " +
                this.controller.getResourceBundle().getString("customer"));
        viewAppointmentsButton.setText(this.controller.getResourceBundle().getString("view") + " " +
                this.controller.getResourceBundle().getString("appointments"));
        deleteCustomerButton.setText(this.controller.getResourceBundle().getString("delete") + " " +
                this.controller.getResourceBundle().getString("customer"));
        logoutButton.setText(this.controller.getResourceBundle().getString("logout"));
    }

    public void clear() {
		editCustomerButton.setDisable(true);
		viewAppointmentsButton.setDisable(true);
		deleteCustomerButton.setDisable(true);
		customersTable.refresh();
	}

	public void refreshCustomersTable() {
        customersTable.refresh();
    }
}//CustomerOverview