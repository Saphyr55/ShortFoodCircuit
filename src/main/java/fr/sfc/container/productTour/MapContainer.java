package fr.sfc.container.productTour;

import fr.sfc.controller.productTour.MapController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

public class MapContainer extends Pane implements Container {

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
