package fr.sfc.component;

import fr.sfc.api.RuntimeApplication;
import fr.sfc.api.component.ComponentFXML;
import fr.sfc.api.component.Component;
import fr.sfc.controller.MainController;
import fr.sfc.controller.MapViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

@ComponentFXML
public class MainComponent extends HBox implements Component {

    private final FXMLLoader loader;



    public MainComponent() throws IOException {
        super();
        loader = new FXMLLoader(getClass().getResource( "main.fxml"));
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }

}
