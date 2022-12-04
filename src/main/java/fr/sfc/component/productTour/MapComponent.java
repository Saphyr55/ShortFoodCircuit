package fr.sfc.component.productTour;

import fr.sfc.api.controlling.AutoController;
import fr.sfc.api.controlling.Component;
import fr.sfc.controller.MapController;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import static fr.sfc.component.MainComponent.PREF_HEIGHT;
import static fr.sfc.component.MainComponent.PREF_WIDTH;

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
