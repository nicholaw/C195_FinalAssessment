package sceneUtils;

import customer.Customer;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ResourceBundle;

public class CustomerOverviewTable extends TableView<Customer> {
    private TableColumn<Customer, String> idCol;
    private TableColumn<Customer, String> nameCol;
    private TableColumn<Customer, String> phoneCol;
    private TableColumn<Customer, String> countryCol;
    private TableColumn<Customer, String> appointmentsCol;
    private ResourceBundle rb;

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

    public Customer getSelectedCustomer() {
        return getSelectionModel().getSelectedItem();
    }

    public void setResourceBundle(ResourceBundle rb) {
        this.rb = rb;
        setColumnNames();
    }

    private void setColumnNames() {
        idCol.setText(rb.getString("customer_id"));
        nameCol.setText(rb.getString("customer"));
        phoneCol.setText(rb.getString("phone"));
        countryCol.setText(rb.getString("country"));
        appointmentsCol.setText(rb.getString("appointments"));
    }
}