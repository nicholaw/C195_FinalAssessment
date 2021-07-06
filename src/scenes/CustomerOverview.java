package scenes;

import controller.Controller;
import customer.Customer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import sceneUtils.SceneCode;

public class CustomerOverview  extends BorderPane
{
    //Declare scene attributes and elements
    Controller controller;
    Label sceneLabel;
    Label userWelcomeLabel;
    TableView<Customer> customersTable;
    Button addCustomerButton;
    Button editCustomerButton;
    Button viewAppointmentsButton;
    Button deleteCustomerButton;
    Button logoutButton;
    Customer selectedCustomer;

    public CustomerOverview(Controller controller) {
        //Instantiate scene elements
        this.controller = controller;
        sceneLabel = new Label("Customer Overview");
        userWelcomeLabel = new Label("Welcome ...");
        customersTable = new TableView<>();
        addCustomerButton = new Button("Add Customer");
        editCustomerButton = new Button("Edit Customer");
        viewAppointmentsButton = new Button("View Appointments");
        deleteCustomerButton = new Button("Delete Customer");
        logoutButton = new Button("Logout");
        selectedCustomer = null;

        //Set initial states for tableview
        customersTable.setItems(controller.getCustomers());
        TableColumn<Customer, Long> idCol = new TableColumn<>("Customer ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Customer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<Customer, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        TableColumn<Customer, Integer> apptsCol = new TableColumn<>("Appointments");
        apptsCol.setCellValueFactory(new PropertyValueFactory<>("appointments"));
        customersTable.getColumns().setAll(idCol, nameCol, phoneCol, countryCol, apptsCol);
		
		//set initial states for buttons
		editCustomerButton.setDisable(true);
		viewAppointmentsButton.setDisable(true);
		deleteCustomerButton.setDisable(true);

        //Add event listeners to scene elements
        addCustomerButton.setOnAction(event -> {
            controller.changeScene(SceneCode.EDIT_CUSTOMER, null);
        });//addCustomerButton
        editCustomerButton.setOnAction(event -> {
            controller.changeScene(SceneCode.EDIT_CUSTOMER, selectedCustomer);
        });//editCustomerButton
        viewAppointmentsButton.setOnAction(event -> {
            controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, selectedCustomer);
        });//viewAppointmentsButton
        deleteCustomerButton.setOnAction(event -> {
            if(controller.displayConfirmationAlert("Confirm Delete", "Are you sure you would like to " +
                    "delete this customer?")) {
                if(controller.deleteCustomer(selectedCustomer)) {
                    selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
                    if(selectedCustomer == null) {
                        editCustomerButton.setDisable(true);
                        viewAppointmentsButton.setDisable(true);
                        deleteCustomerButton.setDisable(true);
                    }
                }
                customersTable.refresh();
            }
        });//deleteCustomerButton
        logoutButton.setOnAction(event ->
        {
			this.clear();
            controller.changeScene(SceneCode.LOGIN, null);
        });//logoutButton
		customersTable.setOnMouseClicked(event -> {
			Object obj = customersTable.getSelectionModel().getSelectedItem();
			if(obj != null) {
				selectedCustomer = (Customer)obj;
				editCustomerButton.setDisable(false);
				viewAppointmentsButton.setDisable(false);
				deleteCustomerButton.setDisable(false);
			}
		});//customersTable

        //Add scene elements to containers
        var buttonSubpane1 = new GridPane();
        buttonSubpane1.add(addCustomerButton, 0, 0);
        buttonSubpane1.add(editCustomerButton, 1, 0);
        buttonSubpane1.add(viewAppointmentsButton, 2, 0);
        var buttonSubane2 = new HBox(deleteCustomerButton);
        var buttonPane = new GridPane();
        buttonPane.add(buttonSubpane1, 0, 0);
        buttonPane.add(buttonSubane2, 1, 0);
        var logoutPane = new HBox(logoutButton);
        var contentPane = new GridPane();
        contentPane.add(customersTable, 0, 0);
        contentPane.add(buttonPane, 0, 1);
        contentPane.add(logoutPane, 0, 2);
        this.setCenter(contentPane);

        //Style containers
        contentPane.setAlignment(Pos.CENTER);
        contentPane.setVgap(10);
        contentPane.setHgap(10);
        buttonSubpane1.setAlignment(Pos.CENTER_LEFT);
        buttonSubane2.setAlignment(Pos.CENTER_RIGHT);
        buttonPane.setHgap(10);
        logoutPane.setAlignment(Pos.CENTER);
    }//constructor
	
	private void clear() {
		selectedCustomer = null;
		editCustomerButton.setDisable(true);
		viewAppointmentsButton.setDisable(true);
		deleteCustomerButton.setDisable(true);
	}

	public void refreshCustomersTable() {
        customersTable.refresh();
    }
}//CustomerOverview