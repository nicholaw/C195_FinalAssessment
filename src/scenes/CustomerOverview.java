package scenes;

import controller.Controller;
import customer.Customer;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

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

        //Set initial states for scene elements
        customersTable.setItems(FXCollections.observableArrayList(controller.getCustomers()));
        TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer");
        TableColumn<Customer, String> divCol = new TableColumn<>("First-Level Division");
        TableColumn<Customer, String> countryCol = new TableColumn<>("Country");
        TableColumn<Customer, Integer> apptsCol = new TableColumn<>("Outstanding Appointments");
        customersTable.getColumns().setAll(idCol, nameCol, divCol, countryCol, apptsCol);

        //Add event listeners to scene elements
        //Add scene elements to containers
        HBox buttonBox = new HBox(addCustomerButton, editCustomerButton, viewAppointmentsButton, logoutButton);
        this.setTop(header);
        this.setCenter(customersTable);
        this.setBottom(buttonBox);
    }//constructor
}
