package fr.sfc.component;

import fr.sfc.component.productTour.AdderProdutTourComponent;
import fr.sfc.component.productTour.DetailsProductTourComponent;
import fr.sfc.controller.MainController;
import fr.sfc.framework.common.Tag;
import fr.sfc.framework.controlling.Component;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetComponent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainComponent extends GridPane implements Component {

    @AutoController
    private MainController mainController;

    @SetComponent
    @Tag(value = "detailsProductTour")
    private DetailsProductTourComponent detailsProductTourComponent;
    
    @SetComponent
    @Tag(value = "adderProductTour")
    private AdderProdutTourComponent adderProductTourComponent;

    @Override
    public void setup() {
        final Pane parent = (Pane) getParent();

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());
        adderProductTourComponent.prefHeightProperty().bind(heightProperty());
        adderProductTourComponent.prefWidthProperty().bind(widthProperty().divide(1.5d));
        adderProductTourComponent.setMinWidth(300);
        adderProductTourComponent.setMaxWidth(3 * 3 * 100);
        detailsProductTourComponent.prefWidthProperty().bind(widthProperty());
        detailsProductTourComponent.prefHeightProperty().bind(heightProperty());
        addColumn(0, adderProductTourComponent);
        addColumn(1, detailsProductTourComponent);
    }

    public DetailsProductTourComponent getDetailsProductTourComponent() {
        return detailsProductTourComponent;
    }

}
