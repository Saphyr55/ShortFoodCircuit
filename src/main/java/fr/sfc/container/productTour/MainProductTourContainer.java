package fr.sfc.container.productTour;

import fr.sfc.controller.MainController;
import fr.sfc.framework.common.Tag;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainProductTourContainer extends GridPane implements Container {

    @AutoController
    private MainController mainController;

    @SetContainer
    @Tag("details")
    private DetailsProductTourContainer detailsProductTour;

    @SetContainer
    @Tag("list")
    private ListProductTourContainer listProductTourContainer;

    @Inject
    private ContainerManager containerManager;

    @Override
    public void setup() {
        final Pane parent = (Pane) getParent();

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());

        listProductTourContainer.prefHeightProperty().bind(heightProperty());
        listProductTourContainer.prefWidthProperty().bind(widthProperty().divide(1.5d));

        listProductTourContainer.setMinWidth(300);
        listProductTourContainer.setMaxWidth(3 * 3 * 100);

        detailsProductTour.prefWidthProperty().bind(widthProperty());
        detailsProductTour.prefHeightProperty().bind(heightProperty());

        addColumn(0, listProductTourContainer);
        addColumn(1, detailsProductTour);
    }

    public DetailsProductTourContainer getDetailsProductTour() {
        return detailsProductTour;
    }

}
