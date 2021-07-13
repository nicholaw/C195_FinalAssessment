package sceneUtils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import utils.Contact;

import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Displays contact information (name and email address) and allows user
 * to select a contact when scheduling or editing an appointment.
 */
public class ContactBox extends GridPane {
	private Label contactLabel;
	private ComboBox<Contact> contactsCombo;
	private TextField emailField;
	private ResourceBundle rb;

	/**
	 * Constructs this ContactBox with the given ResourceBundle and ObservableList of contacts.
	 * @param rb	-the ResourceBundle
	 * @param contacts	-the list of available contacts
	 */
	public ContactBox(ResourceBundle rb, Collection<Contact> contacts) {
		contactLabel = new Label("");
		contactsCombo = new ComboBox<>();
		emailField = new TextField("");
		setResourceBundle(rb);
		
		//Set initial values
		contactsCombo.getItems().setAll(contacts);
		reset();
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

	/**
	 * Returns the contact whom has been selected by the user in the
	 * ComboBox.
	 * @return	-the selected Contact
	 */
	public Contact getSelectedContact() {
		return contactsCombo.getValue();
	}

	/**
	 * Sets the value of the contact ComboBox to the first Contact in the box's
	 * items. Sets the value to null if not such items exist.
	 */
	public void reset() {
		if(contactsCombo.getItems().size() > 0) {
			contactsCombo.setValue(contactsCombo.getItems().get(0));
		} else {
			contactsCombo.setValue(null);
		}
		updateEmail();
	}//selectFirst

	/**
	 * Sets the ResourceBundle used by this scene element.
	 * @param rb -the ResourceBundle
	 */
	public void setResourceBundle(ResourceBundle rb) {
		this.rb = rb;
		contactLabel.setText(this.rb.getString("contact"));
	}

	/**
	 * Updates the email being displayed to match the contact selected by the user
	 * via the ComboBox.
	 */
	private void updateEmail() {
		if(contactsCombo.getValue() != null) {
			emailField.setText(contactsCombo.getValue().getEmail());
		} else {
			emailField.setText("");
		}
	}//updateEmail
}