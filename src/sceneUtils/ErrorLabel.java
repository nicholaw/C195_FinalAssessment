package sceneUtils;

import javafx.scene.control.Label;

public class ErrorLabel extends Label {
    private ErrorMessage error;

    public ErrorLabel() {
        this.error = null;
        this.setText("");
        this.getStyleClass().add("error-label");
    }

    public void clear() {
        error = null;
        setText("");
    }

    public ErrorMessage getError() {
        return error;
    }

    public void setMessage(String text, ErrorMessage em) {
        if(em == null) {
            clear();
        } else {
            error = em;
            setText(text);
        }
    }
}
