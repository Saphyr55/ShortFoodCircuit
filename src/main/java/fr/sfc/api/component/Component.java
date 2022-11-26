package fr.sfc.api.component;

import fr.sfc.controller.MapViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public interface Component {

    default FXMLLoader getLoader() {
        return null;
    }

}
