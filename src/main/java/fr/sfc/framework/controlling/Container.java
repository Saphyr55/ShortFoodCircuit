package fr.sfc.framework.controlling;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * To create component, we need to implement this interface
 * It permit to be detected through the ComponentClassLoader
 */
public interface Container {

    /**
     * At override if we use fxml loader
     * Permits to detect the controller attached to component
     *
     * @return a fxml loader
     */
    default FXMLLoader getLoader() {
        return null;
    }

    /**
     * Constructor with zero parameters
     */
    void setup();

}
