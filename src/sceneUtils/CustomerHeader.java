package sceneUtils;

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
	
	/**
	 *
	 */
	public CustomerHeader() {
		label		=	new Label("Customer: ");
		idField		=	new TextField("");
		nameField	=	new TextField("");
		phoneField	=	new TextField("");
		idField.setDisable(true);
		nameField.setDisable(true);
		phoneField.setDisable(true);
		this.getChildren().addAll(label, idField, nameField, phoneField);
	}//constructor

	/**
	 * 
	 *
	 * @param id	the customer's id
	 * @param name	the customer's name
	 * @param phone	the customer's phone number
	 */
	public void setCustomerInfo(long id, String name, String phone) {
		idField.setText("" + id);
		nameField.setText(name);
		phoneField.setText(phone);
	}//setCustomerInfo

	public long getCustomerId() {
		return Long.parseLong(idField.getText());
	}
	
	/**
	 *
	 */
	public void clear() {
		idField.setText("");
		nameField.setText("");
		phoneField.setText("");
	}//clear
}//CustomerHeader