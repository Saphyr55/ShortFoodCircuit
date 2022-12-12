package fr.sfc.container.admin;

import fr.sfc.controller.admin.MainAdminController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainAdminContainer extends GridPane implements Container {

    @AutoController
    private MainAdminController controller;

    @SetContainer
    private ListProducerContainer listProducer;

    @SetContainer
    private SpecificsCompanyContainer detailsCompany;

    @SetContainer
    private SpecificsProducerContainer detailsProducer;

    @Override
    public void setup() {
        final Pane parent = (Pane) getParent();
        GridPane detailsGridPane = new GridPane();

        detailsGridPane.addRow(0, detailsProducer);
        detailsGridPane.addRow(1, detailsCompany);

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());

        listProducer.prefHeightProperty().bind(heightProperty());
        listProducer.prefWidthProperty().bind(widthProperty().divide(1.5d));
        listProducer.setMinWidth(300);
        listProducer.setMaxWidth(3 * 3 * 100);

        detailsGridPane.prefWidthProperty().bind(widthProperty());
        detailsGridPane.prefHeightProperty().bind(heightProperty());

        addColumn(0, listProducer);
        addColumn(1, detailsGridPane);
    }

    public MainAdminController getController() {
        return controller;
    }

    public ListProducerContainer getListProducer() {
        return listProducer;
    }

    public SpecificsCompanyContainer getDetailsCompany() {
        return detailsCompany;
    }

    public SpecificsProducerContainer getDetailsProducer() {
        return detailsProducer;
    }

}
