package sceneUtils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import utils.Contact;

import java.util.Collection;

public class ContactBox extends HBox {
	private Label contactLabel;
	private ComboBox<Contact> contactsCombo;
	private TextField emailField;
	
	public ContactBox(Collection<Contact> contacts) {
		//Instantiate elements
		contactLabel = new Label("Contact:");
		contactsCombo = new ComboBox<>();
		emailField = new TextField("");
		
		//Set initial values
		contactsCombo.getItems().setAll(contacts);
		selectFirst();
		emailField.setDisable(true);
		
		//Add elements to container
		this.getChildren().addAll(contactLabel, contactsCombo, emailField);
	}//constructor
	
	private void selectFirst() {
		if(contactsCombo.getItems().size() > 0) {
			contactsCombo.setValue(contactsCombo.getItems().get(0));
		} else {
			contactsCombo.setValue(null);
		}
		updateEmail();
	}//selectFirst
	
	private void updateEmail() {
		if(contactsCombo.getValue() != null) {
			emailField.setText(contactsCombo.getValue().getEmail());
		} else {
			emailField.setText("");
		}
	}//updateEmail
	
	public Contact getSelectedContact() {
		return contactsCombo.getValue();
	}
}