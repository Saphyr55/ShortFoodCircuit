package fr.sfc.controllers;

import fr.sfc.SFCApplication;
import fr.sfc.components.IComponent;
import fr.sfc.components.MapViewComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapViewController implements Initializable  {


    @FXML
    private WebView map;

    public MapViewController(IComponent<?> component) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final WebEngine engine = map.getEngine();
        engine.load(SFCApplication.index.toExternalForm());
    }

}