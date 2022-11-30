package fr.sfc.api.component;

import javafx.fxml.FXMLLoader;

/**
 * To create component, we need to implement this interface
 * It permit to be detected through the ComponentClassLoader
 */
public interface Component {

    /**
     * At override if we use fxml loader
     * Permits to detect the controller attached to component
     *
     * @return a fxml loader
     */
    default FXMLLoader getLoader() {
        return null;
    }

}
