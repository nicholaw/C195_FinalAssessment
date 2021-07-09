package sceneUtils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import utils.Contact;

import java.util.Collection;
import java.util.ResourceBundle;

public class ContactBox extends GridPane {
	private Label contactLabel;
	private ComboBox<Contact> contactsCombo;
	private TextField emailField;
	private ResourceBundle rb;
	
	public ContactBox(ResourceBundle rb, Collection<Contact> contacts) {
		contactLabel = new Label("");
		contactsCombo = new ComboBox<>();
		emailField = new TextField("");
		setResourceBundle(rb);
		
		//Set initial values
		contactsCombo.getItems().setAll(contacts);
		selectFirst();
		emailField.setDisable(true);

		//Add event handler to contact combo
		contactsCombo.setOnAction(event -> {
			if(contactsCombo.getValue() != null) {
				updateEmail();
			}
		});
		
		//Add elements to container
		var comboPane = new GridPane();
		comboPane.add(contactsCombo, 0, 0);
		comboPane.add(emailField, 1, 0);
		this.add(contactLabel, 0, 0);
		this.add(comboPane, 0, 1);

		//Style elements
		comboPane.setHgap(10);
		this.setVgap(10);
	}//constructor

	public Contact getSelectedContact() {
		return contactsCombo.getValue();
	}

	public void reset() {
		selectFirst();
	}
	
	private void selectFirst() {
		if(contactsCombo.getItems().size() > 0) {
			contactsCombo.setValue(contactsCombo.getItems().get(0));
		} else {
			contactsCombo.setValue(null);
		}
		updateEmail();
	}//selectFirst

	public void setResourceBundle(ResourceBundle rb) {
		this.rb = rb;
		contactLabel.setText(this.rb.getString("contact"));
	}
	
	private void updateEmail() {
		if(contactsCombo.getValue() != null) {
			emailField.setText(contactsCombo.getValue().getEmail());
		} else {
			emailField.setText("");
		}
	}//updateEmail
}