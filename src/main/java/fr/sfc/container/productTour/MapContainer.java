package fr.sfc.container.productTour;

import fr.sfc.container.MainContainer;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.controller.productTour.MapController;
import fr.sfc.framework.persistence.annotation.Inject;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class MapContainer extends HBox implements Container {

    @AutoController
    private MapController controller;

    private WebView wwMap;

    @Inject
    private ContainerManager containerManager;

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
