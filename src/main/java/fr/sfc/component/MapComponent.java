package fr.sfc.component;

import fr.sfc.api.controlling.AutoController;
import fr.sfc.api.controlling.Component;
import fr.sfc.controller.MapController;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class MapComponent extends HBox implements Component {

    @AutoController
    private MapController controller;

    private WebView wwMap;

    @Override
    public void setup() {
        wwMap = new WebView();
        getChildren().add(wwMap);
        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
    }

    public WebView getWwMap() {
        return wwMap;
    }

}
