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

/**
 * Scene which displays an overview of customers stored in the database. Scene contains
 * buttons which allow user to edit or delete a displayed customer or insert a new one.
 */
public class CustomerOverview  extends BorderPane implements Refreshable {
    Controller controller;
    Label sceneLabel;
    CustomerOverviewTable customersTable;
    Button addCustomerButton;
    Button editCustomerButton;
    Button viewAppointmentsButton;
    Button deleteCustomerButton;
    Button reportsButton;
    Button logoutButton;

    /**
     * Constructs this scene.
     * @param controller -the application controller
     */
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
        reportsButton = new   Button("");
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
                        controller.getResourceBundle().getString("confirm_delete_customer"))) {
                    if(controller.deleteCustomer(selectedCustomer)) {
                        viewAppointmentsButton.setDisable(true);
                        editCustomerButton.setDisable(true);
                        deleteCustomerButton.setDisable(true);
                        customersTable.refresh();
                        controller.displayInformationalAlert(controller.getString("successful_delete_title"),
                                controller.getString("successful_delete_customer1") + " " +
                                        selectedCustomer.getName() + "(#" + selectedCustomer.getCustomerId() + ") " +
                                        controller.getString("successful_delete_customer2"));
                    }
                }
            }
        });//deleteCustomerButton
        reportsButton.setOnAction(event -> this.controller.changeScene(SceneCode.REPORT_TOTAL, null));
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
        var logoutPane = new HBox(reportsButton, logoutButton);
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
        logoutPane.setSpacing(10);
        logoutPane.setPadding(new Insets(10, 10, 10, 10));
        sceneLabel.getStyleClass().add("scene-label");
    }//constructor

    @Override
    public void refresh(ResourceBundle rb) {
        setElementText();
        customersTable.setResourceBundle(controller.getResourceBundle());
    }//refresh

    /**
     * Sets the text for each label and button on this scene based on the user-selected language.
     */
    private void setElementText() {
        sceneLabel.setText(this.controller.getString("customer_overview"));
        addCustomerButton.setText(this.controller.getString("add") + " " +
                this.controller.getString("customer"));
        editCustomerButton.setText(this.controller.getString("edit") + " " +
                this.controller.getString("customer"));
        viewAppointmentsButton.setText(this.controller.getString("view") + " " +
                this.controller.getString("appointments"));
        deleteCustomerButton.setText(this.controller.getString("delete") + " " +
                this.controller.getString("customer"));
        reportsButton.setText(controller.getString("view_reports"));
        logoutButton.setText(this.controller.getResourceBundle().getString("logout"));
    }//setElementText

    /**
     * Clears the controls for displaying customer information and disables and relevant buttons.
     */
    public void clear() {
		editCustomerButton.setDisable(true);
		viewAppointmentsButton.setDisable(true);
		deleteCustomerButton.setDisable(true);
		customersTable.refresh();
	}//clear

    /**
     * Refreshes the TableView which displays the list of customers.
     */
	public void refreshCustomersTable() {
        customersTable.refresh();
    }
}//CustomerOverview