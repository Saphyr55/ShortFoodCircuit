package fr.sfc.container;

import fr.sfc.container.productTour.ListProductTourContainer;
import fr.sfc.container.productTour.DetailsProductTourContainer;
import fr.sfc.controller.MainController;
import fr.sfc.framework.common.Tag;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainContainer extends GridPane implements Container {

    @AutoController
    private MainController mainController;

    @SetContainer
    @Tag("details")
    private DetailsProductTourContainer detailsProductTour;

    @SetContainer
    private ListProductTourContainer adderProductTour;

    @Inject
    private ContainerManager containerManager;

    @Override
    public void setup() {
        final Pane parent = (Pane) getParent();

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());

        adderProductTour.prefHeightProperty().bind(heightProperty());
        adderProductTour.prefWidthProperty().bind(widthProperty().divide(1.5d));

        adderProductTour.setMinWidth(300);
        adderProductTour.setMaxWidth(3 * 3 * 100);

        detailsProductTour.prefWidthProperty().bind(widthProperty());
        detailsProductTour.prefHeightProperty().bind(heightProperty());

        addColumn(0, adderProductTour);
        addColumn(1, detailsProductTour);
    }

    public DetailsProductTourContainer getDetailsProductTour() {
        return detailsProductTour;
    }

}
