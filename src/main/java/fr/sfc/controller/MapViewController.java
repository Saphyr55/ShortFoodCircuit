package fr.sfc.controller;

import com.google.common.io.Resources;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.component.MainComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapViewController implements Controller {

    @FXML
    private WebView map;
    @FXML
    private AnchorPane selfAnchorPane;

    private MainComponent component;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final WebEngine engine = map.getEngine();
        engine.load(Resources.getResource("html/index.html").toExternalForm());
    }

}