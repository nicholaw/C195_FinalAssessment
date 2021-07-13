package sceneUtils;

import javafx.scene.control.Label;
import java.util.ResourceBundle;

/**
 * Displays text explaining the cause of an error or invalid input when
 * such and error is encountered by the user.
 */
public class ErrorLabel extends Label {
    private ErrorCode error;
    private ResourceBundle rb;

    /**
     * Constructs this object with the given ResourceBundle.
     * @param rb -the ResourceBundle to use
     */
    public ErrorLabel(ResourceBundle rb) {
        this.error = null;
        this.setText("");
        this.getStyleClass().add("error-label");
        this.rb = rb;
    }

    /**
     * Sets this ErrorLabel's ErrorCode to null and text to the empty String.
     */
    public void clear() {
        error = null;
        setText("");
    }

    /**
     * Returns this ErrorLabel's ErrorCode.
     * @return  -the ErrorCode
     */
    public ErrorCode getError() {
        return error;
    }

    /**
     * Sets this ErrorLabel's ErrorCode to the provided code.
     * @param error -the ErrorCode to set
     */
    public void setError(ErrorCode error) {
        this.error = error;
        this.setText(rb.getString(this.error.getLocaleKey()));
    }

    /**
     * Sets this ErrorLabel's ErrorCode to the provided code and set its text
     * to the provided custom message.
     * @param error -the ErrorCode to set
     * @param str   -the text to set
     */
    public void setError(ErrorCode error, String str) {
        this.error = error;
        this.setText(str);
    }

    /**
     * Sets the ResourceBundle to use.
     * @param rb    -the ResourceBundle to use
     */
    public void setResourceBundle(ResourceBundle rb) {
        this.rb = rb;
        if(error != null) {
            this.setText(rb.getString(error.getLocaleKey()));
        }
    }
}