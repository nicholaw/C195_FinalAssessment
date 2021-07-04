package sceneUtils;

import customer.Customer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Displays customer information to inform the user which customer for
 * which they are creating or editing an appointment
 */
public class CustomerHeader extends HBox {
	private final Label label;
	private TextField idField;
	private TextField nameField;
	private TextField phoneField;
	private Customer customer;
	
	/**
	 *
	 */
	public CustomerHeader() {
		label		=	new Label("Customer: ");
		idField		=	new TextField("");
		nameField	=	new TextField("");
		phoneField	=	new TextField("");
		customer	=	null;
		idField.setDisable(true);
		nameField.setDisable(true);
		phoneField.setDisable(true);
		this.getChildren().addAll(label, idField, nameField, phoneField);
	}//constructor

	/**
	 *
	 * @param c
	 */
	public void setCustomer(Customer c) {
		if(c != null) {
			this.customer = c;
			setCustomerInfo();
		}
	}//setCustomerInfo

	/**
	 *
	 */
	private void setCustomerInfo() {
		idField.setText("" + customer.getCustomerId());
		nameField.setText(customer.getName());
		phoneField.setText(customer.getPhone());
	}

	/**
	 *
	 * @return
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 *
	 * @return
	 */
	public long getCustomerId() {
		if(customer != null)
			return customer.getCustomerId();
		else
			return -1;
	}
	
	/**
	 *
	 */
	public void clear() {
		idField.setText("");
		nameField.setText("");
		phoneField.setText("");
		customer = null;
	}//clear
}//CustomerHeader