package fr.sfc.components;

import fr.sfc.SFCApplication;
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

public class MapViewComponent extends AnchorPane implements Initializable {

    @FXML
    private WebView map;

    public MapViewComponent() {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("map.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final WebEngine engine = map.getEngine();
        engine.load(SFCApplication.index.toExternalForm());
    }
}
