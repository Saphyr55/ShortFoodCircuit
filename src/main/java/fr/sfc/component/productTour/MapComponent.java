package fr.sfc.component.productTour;

import fr.sfc.framework.controlling.AutoController;
import fr.sfc.framework.controlling.Component;
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
        wwMap.prefWidthProperty().bind(widthProperty());
        wwMap.prefHeightProperty().bind(heightProperty());
        getChildren().add(wwMap);
    }

    public WebView getWwMap() {
        return wwMap;
    }

}
