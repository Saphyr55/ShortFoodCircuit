package fr.sfc.component.productTour;

import fr.sfc.controller.productTour.DetailsProductTourController;
import fr.sfc.framework.common.Tag;
import fr.sfc.framework.controlling.Component;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetComponent;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class DetailsProductTourComponent extends Pane implements Component {

    @AutoController
    private DetailsProductTourController controller;

    @Tag(value = "map")
    @SetComponent
    private MapComponent mapComponent;

    @Tag(value = "configComponent")
    @SetComponent
    private ConfigProductTourComponent configProductTourComponent;

    private Parent middle;

    @Override
    public void setup() {
        mapComponent.prefWidthProperty().bind(widthProperty());
        mapComponent.prefHeightProperty().bind(heightProperty());
        getChildren().setAll(configProductTourComponent);
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

    public MapComponent getMapComponent() {
        return mapComponent;
    }

    public ConfigProductTourComponent getConfigProductTourComponent() {
        return configProductTourComponent;
    }

    public void setMiddle(Parent middle) {
        this.middle = middle;
    }

}
