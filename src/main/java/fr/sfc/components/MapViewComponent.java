package fr.sfc.components;

import fr.sfc.SFCApplication;
import fr.sfc.controllers.MapViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapViewComponent extends AnchorPane implements IComponent<MapViewComponent> {

    @FXML
    private WebView map;
    private MapViewComponent self;
    private final MapViewController controller;

    public MapViewComponent() {
        super();
        controller = new MapViewController(this);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("map.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(controller);
            self = fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public MapViewComponent getSelf() {
        return self;
    }

}
