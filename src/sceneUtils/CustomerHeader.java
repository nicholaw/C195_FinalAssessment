package sceneUtils;

import customer.Customer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Displays customer information matching the customer for which the user is scheduling
 * or editing an appointment.
 */
public class CustomerHeader extends GridPane {
	private final Label label;
	private TextField idField;
	private TextField nameField;
	private TextField phoneField;
	private Customer customer;
	
	/**
	 * Constructs this scene element
	 * @param labelText -the text to be displayed in this header
	 */
	public CustomerHeader(String labelText) {
		label =	new Label(labelText + ":");
		idField	= new TextField("");
		nameField =	new TextField("");
		phoneField = new TextField("");
		customer = null;
		idField.setDisable(true);
		nameField.setDisable(true);
		phoneField.setDisable(true);

		//Add elements to containers
		var fieldPane = new HBox(idField, nameField);
		this.add(label, 0, 0);
		this.add(fieldPane, 0, 1);

		//Style elements
		fieldPane.setSpacing(10);
		nameField.setPrefColumnCount(16);
	}//constructor

	/**
	 * Set the customer whose information is to be displayed.
	 * @param c	-the customer to be displayed
	 */
	public void setCustomer(Customer c) {
		if(c != null) {
			this.customer = c;
			setCustomerInfo();
		}
	}//setCustomerInfo

	/**
	 * Sets the text of each scene element to the information of the customer to display.
	 */
	private void setCustomerInfo() {
		idField.setText("#" + customer.getCustomerId());
		nameField.setText(customer.getName());
		phoneField.setText(customer.getPhone());
	}

	public void setText(String labelText) {
		label.setText(labelText + ":");
	}

	/**
	 * Returns the customer whose information is on display
	 * @return	-the customer being displayed
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Returns the id of the customer on display.
	 * @return -the id of the customer being displayed
	 */
	public long getCustomerId() {
		if(customer != null)
			return customer.getCustomerId();
		else
			return -1;
	}
	
	/**
	 * Set the text of each scene element to the empty string and sets the
	 * customer on display to null.
	 */
	public void clear() {
		idField.setText("");
		nameField.setText("");
		phoneField.setText("");
		customer = null;
	}//clear
}//CustomerHeader