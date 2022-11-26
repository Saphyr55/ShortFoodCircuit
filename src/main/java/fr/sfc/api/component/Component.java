package fr.sfc.api.component;

import javafx.fxml.FXMLLoader;

public interface Component {

    default FXMLLoader getLoader() {
        return null;
    }

}
