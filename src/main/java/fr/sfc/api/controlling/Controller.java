package fr.sfc.api.controlling;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * To create controller, we need to implement this interface
 * It permit to be detected through ControllerClassLoader
 */
public interface Controller extends Initializable {

    @Override
    default void initialize(URL location, ResourceBundle resources) { }

    default void setup() { }

}
