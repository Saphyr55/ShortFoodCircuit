package fr.sfc.container.productTour;

import fr.sfc.controller.productTour.DetailsProductTourController;
import fr.sfc.framework.common.Tag;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class DetailsProductTourContainer extends Pane implements Container {

    @AutoController
    private DetailsProductTourController controller;
    
    @Tag(value = "map")
    @SetContainer
    private MapContainer mapContainer;

    @Tag(value = "config")
    @SetContainer
    private ConfigProductTourContainer configProductTourContainer;

    @Override
    public void setup() {
        mapContainer.prefWidthProperty().bind(widthProperty());
        mapContainer.prefHeightProperty().bind(heightProperty());
        getChildren().setAll(configProductTourContainer);
    }

    public void setFor(Parent parent) {
        getChildren().setAll(parent);
    }

    public DetailsProductTourController getController() {
        return controller;
    }

    public void setController(DetailsProductTourController controller) {
        this.controller = controller;
    }

    public MapContainer getMapContainer() {
        return mapContainer;
    }

    public ConfigProductTourContainer getConfigProductTourContainer() {
        return configProductTourContainer;
    }


}
