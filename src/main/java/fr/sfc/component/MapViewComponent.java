package fr.sfc.component;

import fr.sfc.api.component.AutoComponent;
import fr.sfc.api.component.Component;
import fr.sfc.api.component.IComponent;
import fr.sfc.api.controller.AutoController;
import fr.sfc.controller.MapViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.io.IOException;

@Component(source = "map.fxml")
public class MapViewComponent extends AnchorPane implements IComponent<AnchorPane> {

    @FXML
    private WebView map;
    private MapViewComponent self;

    @AutoController
    private MapViewController controller;

    public MapViewComponent() {
        super();
        controller = new MapViewController();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("map.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(controller);
            self = fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}
