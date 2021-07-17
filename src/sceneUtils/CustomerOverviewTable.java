package sceneUtils;

import customer.Customer;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ResourceBundle;

/**
 * TableView for displaying customer information on the
 * customer overview page.
 */
public class CustomerOverviewTable extends TableView<Customer> {
    private TableColumn<Customer, String> idCol;
    private TableColumn<Customer, String> nameCol;
    private TableColumn<Customer, String> phoneCol;
    private TableColumn<Customer, String> countryCol;
    private TableColumn<Customer, String> appointmentsCol;
    private ResourceBundle rb;

    /**
     * Constructs the AppointmentOverviewTable with the given ResourceBundle.
     * @param rb -the ResourceBundle
     * @param customers -the customers to be displayed in this TableView
     */
    public CustomerOverviewTable(ResourceBundle rb, ObservableList<Customer> customers) {
        //Set class attributes
        this.rb = rb;
        this.setItems(customers);

        //Instantiate table columns
        idCol = new TableColumn<>();
        nameCol = new TableColumn<>();
        phoneCol = new TableColumn<>();
        countryCol = new TableColumn<>();
        appointmentsCol = new TableColumn<>();

        //Name each column
        setColumnNames();

        //Set cell value factory for each column
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        appointmentsCol.setCellValueFactory(new PropertyValueFactory<>("appointments"));

        //Add columns to the table
        this.getColumns().setAll(idCol, nameCol, phoneCol, countryCol, appointmentsCol);
    }

    /**
     * Returns the customer the user has selected from the TableView.
     * @return  -the selected customer
     */
    public Customer getSelectedCustomer() {
        return getSelectionModel().getSelectedItem();
    }

    /**
     * Sets the ResourceBundle for this class to use.
     * @param rb -the ResourceBundle
     */
    public void setResourceBundle(ResourceBundle rb) {
        this.rb = rb;
        setColumnNames();
    }

    /**
     * Sets the names of each of the columns in this TableView.
     */
    private void setColumnNames() {
        idCol.setText(rb.getString("customer_id"));
        nameCol.setText(rb.getString("customer"));
        phoneCol.setText(rb.getString("phone"));
        countryCol.setText(rb.getString("country"));
        appointmentsCol.setText(rb.getString("appointments"));
    }
}