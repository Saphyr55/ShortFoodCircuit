package fr.sfc.component;

import fr.sfc.component.productTour.AdderProdutTourComponent;
import fr.sfc.component.productTour.DetailsProductTourComponent;
import fr.sfc.controller.MainController;
import fr.sfc.framework.controlling.Component;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetComponent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainComponent extends GridPane implements Component {

    @AutoController
    private MainController mainController;

    @SetComponent
    private DetailsProductTourComponent detailsProductTourComponent;

    @SetComponent
    private AdderProdutTourComponent adderProdutTourComponent;

    @Override
    public void setup() {
        final Pane parent = (Pane) getParent();

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());
        adderProdutTourComponent.prefHeightProperty().bind(heightProperty());
        adderProdutTourComponent.prefWidthProperty().bind(widthProperty().divide(1.5d));
        adderProdutTourComponent.setMinWidth(300);
        adderProdutTourComponent.setMaxWidth(3 * 3 * 100);
        detailsProductTourComponent.prefWidthProperty().bind(widthProperty());
        detailsProductTourComponent.prefHeightProperty().bind(heightProperty());
        addColumn(0, adderProdutTourComponent);
        addColumn(1, detailsProductTourComponent);
    }

    public DetailsProductTourComponent getDetailsProductTourComponent() {
        return detailsProductTourComponent;
    }

}
