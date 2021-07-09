package sceneUtils;

import javafx.scene.control.Label;

import java.util.ResourceBundle;

public class ErrorLabel extends Label {
    private ErrorCode error;
    private ResourceBundle rb;

    public ErrorLabel(ResourceBundle rb) {
        this.error = null;
        this.setText("");
        this.getStyleClass().add("error-label");
        this.rb = rb;
    }

    public void clear() {
        error = null;
        setText("");
    }

    public ErrorCode getError() {
        return error;
    }

    public void setError(ErrorCode error) {
        this.error = error;
        this.setText(rb.getString(this.error.getLocaleKey()));
    }

    public void setResourceBundle(ResourceBundle rb) {
        this.rb = rb;
        if(error != null) {
            this.setText(rb.getString(error.getLocaleKey()));
        }
    }
}
