package fr.sfc.component;

import fr.sfc.framework.controlling.AutoController;
import fr.sfc.framework.controlling.Component;
import fr.sfc.framework.controlling.SetComponent;
import fr.sfc.component.productTour.AdderProdutTourComponent;
import fr.sfc.component.productTour.DetailsProductTourComponent;
import fr.sfc.controller.MainController;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainComponent extends GridPane implements Component {

    public static final double PREF_WIDTH = 2440;
    public static final double PREF_HEIGHT = 1860;

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
