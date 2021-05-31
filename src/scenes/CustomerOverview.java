package scenes;

import controller.Controller;
import customer.Customer;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import sceneUtils.SceneCode;

public class CustomerOverview  extends BorderPane
{
    //Declare scene attributes and elements
    Controller controller;
    Pane header;
    Label sceneLabel;
    Label userWelcomeLabel;
    TableView<Customer> customersTable;
    Button addCustomerButton;
    Button editCustomerButton;
    Button viewAppointmentsButton;
    Button deleteCustomerButton;
    Button logoutButton;
    Customer selectedCustomer;

    public CustomerOverview(Controller controller)
    {
        //Instantiate scene elements
        this.controller = controller;
        this.header = controller.getHeader();
        sceneLabel = new Label("Customer Overview");
        userWelcomeLabel = new Label("Welcome ...");
        customersTable = new TableView<>();
        addCustomerButton = new Button("Add Customer");
        editCustomerButton = new Button("Edit Customer");
        viewAppointmentsButton = new Button("View Appointments");
        deleteCustomerButton = new Button("Delete Customer");
        logoutButton = new Button("Logout");
        selectedCustomer = null;

        //Set initial states for scene elements
        customersTable.setItems(controller.getCustomers());
        TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Customer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        TableColumn<Customer, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        TableColumn<Customer, Integer> apptsCol = new TableColumn<>("Appointments");
        apptsCol.setCellValueFactory(new PropertyValueFactory("scheduledAppointments"));
        customersTable.getColumns().setAll(idCol, nameCol, phoneCol, countryCol, apptsCol);

        //Add event listeners to scene elements
        addCustomerButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.EDIT_CUSTOMER, null);
        });
        editCustomerButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.EDIT_CUSTOMER, selectedCustomer);
        });
        viewAppointmentsButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.APPOINTMENT_OVERVIEW, selectedCustomer);
        });
        deleteCustomerButton.setOnAction(event ->
        {
            Alert confirmDelete = controller.getConfirmationAlert();
            confirmDelete.setAlertType(Alert.AlertType.CONFIRMATION);
            confirmDelete.setContentText("Are you sure you would like to delete this customer?");
            confirmDelete.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> controller.deleteCustomer(selectedCustomer));
        });
        logoutButton.setOnAction(event ->
        {
            controller.changeScene(SceneCode.LOGIN, null);
        });

        //Add scene elements to containers
        HBox buttonBox = new HBox(addCustomerButton, editCustomerButton, viewAppointmentsButton, logoutButton);
        this.setTop(header);
        this.setCenter(customersTable);
        this.setBottom(buttonBox);
    }//constructor
}//CustomerOverview