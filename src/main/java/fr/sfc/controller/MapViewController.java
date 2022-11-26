package fr.sfc.controller;

import fr.sfc.SFCApplication;
import fr.sfc.api.controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapViewController extends Controller implements Initializable  {

    @FXML
    private WebView map;

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final WebEngine engine = map.getEngine();
        engine.load(SFCApplication.index.toExternalForm());
    }

}