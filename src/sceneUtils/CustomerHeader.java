package sceneUtils;

import customer.Customer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Displays customer information to inform the user which customer for
 * which they are creating or editing an appointment
 */
public class CustomerHeader extends GridPane {
	private final Label label;
	private TextField idField;
	private TextField nameField;
	private TextField phoneField;
	private Customer customer;
	
	/**
	 *
	 */
	public CustomerHeader(String labelText) {
		label		=	new Label(labelText);
		idField		=	new TextField("");
		nameField	=	new TextField("");
		phoneField	=	new TextField("");
		customer	=	null;
		idField.setDisable(true);
		nameField.setDisable(true);
		phoneField.setDisable(true);

		//Add elements to containers
		var fieldPane = new HBox(nameField, phoneField);
		this.add(label, 0, 0);
		this.add(fieldPane, 0, 1);

		//Style elements
		fieldPane.setSpacing(10);
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

	public void setText(String labelText) {
		label.setText(labelText);
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