package fr.sfc.component;

import fr.sfc.api.RuntimeApplication;
import fr.sfc.api.RuntimeApplicationConfiguration;
import fr.sfc.api.component.ComponentFXML;
import fr.sfc.api.component.Component;
import fr.sfc.controller.MapViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.io.IOException;

@ComponentFXML(resource = "map.fxml")
public class MapViewComponent extends AnchorPane implements Component {

    private final FXMLLoader loader;

    public MapViewComponent() throws IOException {
        super();
        loader = new FXMLLoader(getClass().getResource( "map.fxml"));
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }
}
