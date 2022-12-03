package fr.sfc.component;

import fr.sfc.api.controlling.Component;
import fr.sfc.api.controlling.ComponentFXML;
import fr.sfc.api.controlling.AutoController;
import fr.sfc.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainComponent extends HBox implements Component {

    private final FXMLLoader loader;

    @AutoController
    private MainController mainController;

    public MainComponent() throws IOException {
        super();
        loader = new FXMLLoader(getClass().getResource( "main.fxml"));
        loader.setRoot(this);
        loader.setController(mainController);
        loader.load();
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }

}
