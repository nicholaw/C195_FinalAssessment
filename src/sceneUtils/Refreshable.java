package sceneUtils;

import java.util.ResourceBundle;

/**
 * Denotes that the implementing class can be refreshed to
 * display text with the given ResourceBundle. Used when
 * the user chooses to change the display language.
 */
public interface Refreshable {
    /**
     * Sets text of all nodes on the scene to match the provided Resource Bundle.
     * @param rb    -the ResourceBundle to use
     */
    void refresh(ResourceBundle rb);
}
